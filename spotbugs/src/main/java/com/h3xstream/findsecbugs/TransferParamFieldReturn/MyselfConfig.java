package com.h3xstream.findsecbugs.TransferParamFieldReturn;

import com.h3xstream.findsecbugs.taintanalysis.*;
import com.h3xstream.findsecbugs.taintanalysis.data.UnknownSource;
import com.h3xstream.findsecbugs.taintanalysis.data.UnknownSourceType;
import edu.umd.cs.findbugs.ba.CFGBuilderException;
import edu.umd.cs.findbugs.ba.ClassContext;
import edu.umd.cs.findbugs.ba.Location;
import edu.umd.cs.findbugs.bcel.BCELUtil;
import edu.umd.cs.findbugs.classfile.*;
import edu.umd.cs.findbugs.classfile.analysis.ClassNameAndSuperclassInfo;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.*;

import java.util.*;

public class MyselfConfig {
    private static MyselfConfig myselfConfig = new MyselfConfig();
    private TaintConfig taintConfig;
    private Map<String,List<String>> interAndMethod = new HashMap<>();


    private MyselfConfig(){}

    public static MyselfConfig getMyselfConfig(){
        return myselfConfig;
    }

    public void setTaintConfig(TaintConfig taintConfig) {
        this.taintConfig = taintConfig;
    }

    public void visit(ClassDescriptor classDescriptor) throws CheckedAnalysisException{
        List<String> ml = new ArrayList<String>();

        if(taintConfig == null) return;

        if(taintConfig.getTaintClassConfig("L"+classDescriptor.toString().replace('.','/')+';')!=null) {
            return;
        }
        IAnalysisCache analysisCache = Global.getAnalysisCache();
        ClassContext classContext = analysisCache.getClassAnalysis(ClassContext.class, classDescriptor);
        this.visitClass(classContext);
    }

    public void visitClass(ClassContext classContext) throws CheckedAnalysisException {
        List<Method> me = classContext.getMethodsInCallOrder();

        ClassNameAndSuperclassInfo cf = (ClassNameAndSuperclassInfo)classContext.getClassDescriptor();
        if(cf.isInterface()||cf.isAbstract()){
            List<String> sig = new ArrayList<String>();
            for(Method method:me){
                sig.add(method.getName());
            }
            interAndMethod.put(cf.getClassName(),sig);
        }


        ClassDescriptor[] cs = cf.getInterfaceDescriptorList();

        for (Method method : classContext.getMethodsInCallOrder()) {
            if(method.getReturnType() == Type.VOID) continue;
            StringBuffer key = new StringBuffer(classContext.toString().replace('.','/'));
            key.append('.');
            key.append(method.getName());
            key.append(method.getSignature());
            String typeSignature = key.toString();
            if(taintConfig.containsKey(key.toString()) || method.isAbstract()) continue;
            TaintDataflow dataflow = getTaintDataFlow(classContext, method);
            ConstantPoolGen cpg = classContext.getConstantPoolGen();
            Set<Integer> param = new HashSet<>();
            boolean influcebyself = false;
            boolean mutable = false;
            boolean returnSafe = true;
            boolean tag = true;
            int dep = 0;
            int N = 0;
            Type[] arguementTypes = method.getArgumentTypes();
            for(Type type:arguementTypes){
                N += type.getSize();
            }

//            for(String str:taintConfig.keySet()){
//                if(str.startsWith("L")) {
//                    String ss = str;
//                }
//            }

            //method
            String currentMethod = getFullMethodName(classContext.getMethodGen(method));
            for (Iterator<Location> i = getLocationIterator(classContext, method); i.hasNext();) {
                Location location = i.next();
                InstructionHandle handle = location.getHandle();
                TaintFrame fact = dataflow.getFactAtLocation(location);
                Instruction instruction = handle.getInstruction();


                if( instruction instanceof PUTFIELD||instruction instanceof  PUTSTATIC) {
                    if (fact.getStackDepth() == 0) continue;
                    FieldInstruction inv = (FieldInstruction) instruction;
                    Taint t = fact.getTopValue();
                    Set<UnknownSource> s = t.getSources();
                    for (UnknownSource us : s) {
                        if (us.getSourceType() == UnknownSourceType.PARAMETER) {
                            mutable = true;
                        }
                    }
                } else if(instruction instanceof ReturnInstruction){
                    if(fact.getStackDepth() == 0) continue;
                    Taint t = fact.getTopValue();
                    if(t.getState() != Taint.State.SAFE) returnSafe = false;
                    Set<UnknownSource> s = t.getSources();
                    for(UnknownSource us:s){
                        if(us.getSourceType() == UnknownSourceType.FIELD) influcebyself = true;
                        if(us.getSourceType() == UnknownSourceType.PARAMETER) param.add(us.getParameterIndex());
                    }
                } else{

                }
            }


            String config ="";
            String interfaceConfig = "";
            if(returnSafe&& influcebyself == false) config = "SAFE";
            else {
                for(int index:param){
                    config  = config+""+index+",";
                }
                if(influcebyself && !method.isStatic()) config += N;
                else
                {
                    if(config.length()>0)
                        config = config.substring(0,config.length()-1);
                }
            }
            if(mutable) config += "#"+(N);



            try{
                if(!TaintMethodConfig.accepts(typeSignature,config) || config.equals("")) continue;

                TaintMethodConfig taintMethodConfig = new TaintMethodConfig(false).load(config);
                taintMethodConfig.setTypeSignature(typeSignature);
                taintConfig.put(typeSignature, taintMethodConfig);
                if(cs != null){
                    for(int i = 0 ; i < cs.length ; i++){
                        String interfaceName = cs[i].getClassName();
                        if(interAndMethod.containsKey(interfaceName)&&interAndMethod.get(interfaceName).contains(method.getName())){
                            String sign = interfaceName.replace(",","/").concat(".").concat(method.getName()).concat(method.getSignature());
                            sign = sign.replace(typeSignature,sign);
                            if(!taintConfig.keySet().contains(sign))
                                taintConfig.put(sign,taintMethodConfig);
                            else{
                                param.addAll(taintConfig.get(sign).getOutputTaint().getParameters());
                                if(param.size() == 0) continue;
                                String replace = "";
                                for(Integer idx : param){
                                    replace = idx+",";
                                }
                                if(replace.length() == 0) continue;
                                replace = replace.substring(0,replace.length()-1);
                                TaintMethodConfig intermc = new TaintMethodConfig(false).load(replace);
                                intermc.setTypeSignature(sign);
                                taintConfig.put(sign, intermc);
                            }
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private static TaintDataflow getTaintDataFlow(ClassContext classContext, Method method)
            throws CheckedAnalysisException {
        MethodDescriptor descriptor = BCELUtil.getMethodDescriptor(classContext.getJavaClass(), method);
        return Global.getAnalysisCache().getMethodAnalysis(TaintDataflow.class, descriptor);
    }
    private static String getFullMethodName(MethodGen methodGen) {
        String methodNameWithSignature = methodGen.getName() + methodGen.getSignature();
        String slashedClassName = methodGen.getClassName().replace('.', '/');
        return slashedClassName + "." + methodNameWithSignature;
    }
    private static Iterator<Location> getLocationIterator(ClassContext classContext, Method method)
            throws CheckedAnalysisException {
        try {
            return classContext.getCFG(method).locationIterator();
        } catch (CFGBuilderException ex) {
            throw new CheckedAnalysisException("cannot get control flow graph", ex);
        }
    }


}
