/**
 * Find Security Bugs
 * Copyright (c) Philippe Arteau, All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */
package com.h3xstream.findsecbugs.taintanalysis;

import com.h3xstream.findsecbugs.FindSecBugsGlobalConfig;
import com.h3xstream.findsecbugs.taintanalysis.data.UnknownSource;
import com.h3xstream.findsecbugs.taintanalysis.data.UnknownSourceType;
import edu.umd.cs.findbugs.ba.*;
import edu.umd.cs.findbugs.ba.generic.GenericSignatureParser;
import edu.umd.cs.findbugs.classfile.MethodDescriptor;
import edu.umd.cs.findbugs.classfile.analysis.AnnotationValue;
import edu.umd.cs.findbugs.classfile.analysis.MethodInfo;
import edu.umd.cs.findbugs.io.IO;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.MethodGen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Implements taint dataflow operations, in particular meeting facts, transfer
 * function is delegated to {@link TaintFrameModelingVisitor}
 * 
 * @author David Formanek (Y Soft Corporation, a.s.)
 */
public class TaintAnalysis extends FrameDataflowAnalysis<Taint, TaintFrame> {

    private final MethodGen methodGen;
    private final MethodInfo methodDescriptor;
    private final TaintFrameModelingVisitor visitor;
    private int parameterStackSize;
    private List<Integer> slotToParameter;

    private static final List<String> TAINTED_ANNOTATIONS = loadFileContent(
            "taint-config/taint-param-annotations.txt"
    );
    
    /**
     * Constructs analysis for the given method
     * 
     * @param methodGen method to analyze
     * @param dfs DFS algorithm
     * @param descriptor descriptor of the method to analyze
     * @param taintConfig configured and derived taint summaries
     */
    public TaintAnalysis(MethodGen methodGen, DepthFirstSearch dfs,
                         MethodDescriptor descriptor, TaintConfig taintConfig, List<TaintFrameAdditionalVisitor> visitors) {
        super(dfs);
        this.methodGen = methodGen;
        this.methodDescriptor = (MethodInfo) descriptor;
        this.visitor = new TaintFrameModelingVisitor(methodGen.getConstantPool(), descriptor, taintConfig, visitors, methodGen);
        computeParametersInfo(descriptor.getSignature(), descriptor.isStatic());
    }

    @Override
    protected void mergeValues(TaintFrame frame, TaintFrame result, int i)
            throws DataflowAnalysisException {
        if(!frame.isVaildFrame() || !result.isVaildFrame()){
            int j = 0;
        }
        //result.setVaildFrame(frame.isVaildFrame()||result.isVaildFrame());
        if(!frame.isVaildFrame()) return;
        else if(!result.isVaildFrame()) result.setValue(i,frame.getValue(i));
        else result.setValue(i, Taint.merge(result.getValue(i), frame.getValue(i)));
        if(i == frame.getNumSlots()-1)
            result.setVaildFrame(frame.isVaildFrame()||result.isVaildFrame());
    }

    @Override
    public void transferInstruction(InstructionHandle handle, BasicBlock block, TaintFrame fact)
            throws DataflowAnalysisException {
        visitor.setFrameAndLocation(fact, new Location(handle, block));
        visitor.analyzeInstruction(handle.getInstruction());
    }


    @Override
    public TaintFrame createFact() {
        return new TaintFrame(methodGen.getMaxLocals());
    }

    /**
     * Initialize the initial state of a TaintFrame.
     * @param fact Initial frame
     */
    @Override
    public void initEntryFact(TaintFrame fact) {
        fact.setValid();
        fact.clearStack();
        boolean inMainMethod = isInMainMethod();
        int numSlots = fact.getNumSlots();
        int numLocals = fact.getNumLocals();
        for (int i = 0; i < numSlots; ++i) {
            Taint value = new Taint(Taint.State.UNKNOWN);
            if (i < numLocals) {
                if (i < parameterStackSize) {
                    int stackOffset = parameterStackSize - i - 1;
                    if (isTaintedByAnnotation(i - 1)) {
                        value = new Taint(Taint.State.TAINTED);
                        // this would add line number for the first instruction in the method
                        //value.addLocation(new TaintLocation(methodDescriptor, 0,""), true);
                    } else if (inMainMethod) {
                        if (FindSecBugsGlobalConfig.getInstance().isTaintedMainArgument()) {
                            value = new Taint(Taint.State.TAINTED);
                        } else {
                            value = new Taint(Taint.State.SAFE);
                        }
                    } else {
                        value.addParameter(stackOffset);
                    }
                    value.addSource(new UnknownSource(UnknownSourceType.PARAMETER,value.getState()).setParameterIndex(stackOffset));
                }
                value.setVariableIndex(i);
            }
            fact.setValue(i, value);
        }
    }

    /**
     * @return true if the method is the startup point of a console or gui application
     * ("public static void main(String[] args)"), false otherwise
     */
    private boolean isInMainMethod() {
        return methodDescriptor.isStatic()
                && "main".equals(methodDescriptor.getName())
                && "([Ljava/lang/String;)V".equals(methodDescriptor.getSignature())
                && methodGen.getMethod().isPublic();
    }

    private boolean isTaintedByAnnotation(int slotNo) {
        if (slotNo >= 0 && methodDescriptor.hasParameterAnnotations()) {
            int parameter = slotToParameter.get(slotNo);
            Collection<AnnotationValue> annotations = methodDescriptor.getParameterAnnotations(parameter);
            for (AnnotationValue annotation : annotations) {
                if (TAINTED_ANNOTATIONS.contains(annotation.getAnnotationClass().getClassName())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public void meetInto(TaintFrame fact, Edge edge, TaintFrame result)
            throws DataflowAnalysisException {
        if (fact.isValid() && edge.isExceptionEdge()) {
            TaintFrame copy = null;
            // creates modifiable copy
            copy = modifyFrame(fact, copy);
            copy.clearStack();
            // do not trust values that are safe just when an exception occurs
            copy.pushValue(new Taint(Taint.State.UNKNOWN));
            fact = copy;
        }
        mergeInto(fact, result);
    }
    
    /**
     * This method must be called after executing the data flow
     */
    public void finishAnalysis() {
        visitor.finishAnalysis();
    }


    private void computeParametersInfo(String signature, boolean isStatic) {
        assert signature != null && !signature.isEmpty();
        // static methods does not have reference to this
        int stackSize = isStatic ? 0 : 1;
        GenericSignatureParser parser = new GenericSignatureParser(signature);
        Iterator<String> iterator = parser.parameterSignatureIterator();
        int paramIdx = 0;
        slotToParameter = new ArrayList<Integer>();
        while (iterator.hasNext()) {
            String parameter = iterator.next();
            if (parameter.equals("D") || parameter.equals("J")) {
                // double and long types takes two slots
                stackSize += 2;
                slotToParameter.add(paramIdx);
                slotToParameter.add(paramIdx);
            } else {
                stackSize++;
                slotToParameter.add(paramIdx);
            }
            paramIdx++;
        }
        parameterStackSize = stackSize;
    }

    private static List<String> loadFileContent(String path) {
        BufferedReader stream = null;
        try {
            InputStream in = TaintAnalysis.class.getClassLoader().getResourceAsStream(path);
            stream = new BufferedReader(new InputStreamReader(in, "utf-8"));
            String line;
            List<String> content = new ArrayList<String>();
            while ((line = stream.readLine()) != null) {
                content.add(line.trim());
            }
            return content;
        } catch (IOException ex) {
            assert false : ex.getMessage();
        } finally {
            IO.close(stream);
        }
        return new ArrayList<String>();
    }
}
