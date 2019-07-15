package com.h3xstream.findsecbugs.injection.convert;

import com.h3xstream.findsecbugs.injection.BasicInjectionDetector;
import edu.umd.cs.findbugs.*;
import edu.umd.cs.findbugs.ba.CFGBuilderException;
import edu.umd.cs.findbugs.ba.ClassContext;
import edu.umd.cs.findbugs.ba.DataflowAnalysisException;
import edu.umd.cs.findbugs.ba.Location;
import edu.umd.cs.findbugs.classfile.CheckedAnalysisException;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.PUTFIELD;

import java.util.Iterator;

public class RaceConditionDetector extends BasicInjectionDetector {

    protected BugInstance bugInstance;
    public  RaceConditionDetector(BugReporter bugReporter)
    {
        super(bugReporter);
    }


    @Override
    protected void analyzeMethod(ClassContext classContext, Method method) throws CheckedAnalysisException {
        //  TaintDataflow dataflow = getTaintDataFlow(classContext, method);
        ConstantPoolGen cpg = classContext.getConstantPoolGen();
        Field[] fields=classContext.getJavaClass().getFields();
        //    String currentMethod = getFullMethodName(classContext.getMethodGen(method));
        String superClassName=classContext.getJavaClass().getSuperclassName();
        if(isHttpName(superClassName)){
            for (Iterator<Location> i = getLocationIterator(classContext, method); i.hasNext();)
            {
                Location location = i.next();
                InstructionHandle handle = location.getHandle();
//                    TaintFrame fact = dataflow.getFactAtLocation(location);
                Instruction instruction = handle.getInstruction();
                if(!(instruction instanceof PUTFIELD)){
                    continue;
                }
                analyzeLocationTwo(classContext, method, instruction, cpg, fields, handle);
            }
        }
    }

    //@Override
    protected void analyzeLocationTwo(ClassContext classContext, Method method, Instruction instruction, ConstantPoolGen cpg, Field[] fields, InstructionHandle handle) throws DataflowAnalysisException {
        //super.analyzeLocation(classContext, method, handle, cpg, invoke, fact, currentMethod);
        SourceLineAnnotation sourceLine = SourceLineAnnotation.fromVisitedInstruction(classContext, method, handle);
        //String name=instruction.toString();
        //  String name=instruction.toString(cpg.getConstantPool()).substring(9);
        bugReporter.reportBug(new BugInstance(this,"RACE_CONDITION", Priorities.LOW_PRIORITY).addClass(classContext.toString()).addSourceLine(sourceLine));
    }

    private boolean isHttpName(String str) {
       /* if (str.indexOf("HttpServlet")!=-1) return true;
        else if(str.indexOf("HttpJspBase")!=-1) return true;
        else return false;*/
        if(str.equals("javax.servlet.http.HttpServlet")||str.equals("org.apache.jasper.runtime.HttpJspBase")){
            return true;
        }
        else return false;
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
