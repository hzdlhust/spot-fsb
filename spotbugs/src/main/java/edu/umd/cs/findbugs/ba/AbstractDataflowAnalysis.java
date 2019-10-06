/*
 * Bytecode Analysis Framework
 * Copyright (C) 2003-2006, University of Maryland
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package edu.umd.cs.findbugs.ba;

import java.util.Iterator;

import javax.annotation.CheckForNull;

import com.h3xstream.findsecbugs.taintanalysis.Taint;
import com.h3xstream.findsecbugs.taintanalysis.TaintFrame;
import org.apache.bcel.generic.*;

import edu.umd.cs.findbugs.SystemProperties;
import edu.umd.cs.findbugs.annotations.CheckReturnValue;

/**
 * Abstract base class providing functionality that will be useful for most
 * dataflow analysis implementations that model instructions within basic
 * blocks.
 *
 * @author David Hovemeyer
 * @see Dataflow
 * @see DataflowAnalysis
 */
public abstract class AbstractDataflowAnalysis<Fact> extends BasicAbstractDataflowAnalysis<Fact> {
    private static final boolean DEBUG = SystemProperties.getBoolean("dataflow.transfer");

    /*
     * ----------------------------------------------------------------------
     * Public methods
     * ----------------------------------------------------------------------
     */

    /**
     * Transfer function for a single instruction.
     *
     * @param handle
     *            the instruction
     * @param basicBlock
     *            the BasicBlock containing the instruction; needed to
     *            disambiguate instructions in inlined JSR subroutines
     * @param fact
     *            which should be modified based on the instruction
     */
    public abstract void transferInstruction(InstructionHandle handle, BasicBlock basicBlock, Fact fact)
            throws DataflowAnalysisException;

    /**
     * Determine whether the given fact is <em>valid</em> (neither top nor
     * bottom).
     */
    @CheckReturnValue
    public abstract boolean isFactValid(Fact fact);

    /**
     * Get the dataflow fact representing the point just before given Location.
     * Note "before" is meant in the logical sense, so for backward analyses,
     * before means after the location in the control flow sense.
     *
     * @param location
     *            the location
     * @return the fact at the point just before the location
     */
    @Override
    public Fact getFactAtLocation(Location location) throws DataflowAnalysisException {
        Fact start = getStartFact(location.getBasicBlock());
        Fact result = createFact();
        makeFactTop(result);
        transfer(location.getBasicBlock(), location.getHandle(), start, result);
        return result;
    }

    /**
     * Get the dataflow fact representing the point just after given Location.
     * Note "after" is meant in the logical sense, so for backward analyses,
     * after means before the location in the control flow sense.
     *
     * @param location
     *            the location
     * @return the fact at the point just after the location
     */
    @Override
    public Fact getFactAfterLocation(Location location) throws DataflowAnalysisException {
        BasicBlock basicBlock = location.getBasicBlock();
        InstructionHandle handle = location.getHandle();

        if (handle == (isForwards() ? basicBlock.getLastInstruction() : basicBlock.getFirstInstruction())) {
            return getResultFact(basicBlock);
        } else {
            return getFactAtLocation(new Location(isForwards() ? handle.getNext() : handle.getPrev(), basicBlock));
        }
    }

    /*
     * ----------------------------------------------------------------------
     * Implementations of interface methods
     * ----------------------------------------------------------------------
     */

    @Override
    public void transfer(BasicBlock basicBlock, @CheckForNull InstructionHandle end, Fact start, Fact result)
            throws DataflowAnalysisException {
        copy(start, result);

        if (isFactValid(result)) {
            Iterator<InstructionHandle> i = isForwards() ? basicBlock.instructionIterator() : basicBlock
                    .instructionReverseIterator();

            while (i.hasNext()) {
                InstructionHandle handle = i.next();
                if (handle == end) {
                    break;
                }

                if (DEBUG && end == null) {
                    System.out.print("Transfer " + handle);
                }
                //判断分支
                judgeBranch(basicBlock,handle,result);
                if((result instanceof TaintFrame)&&(!basicBlock.getBeExce()))  ((TaintFrame) result).setVaildFrame(false);
                // Transfer the dataflow value
                transferInstruction(handle, basicBlock, result);
                if (DEBUG && end == null) {
                    System.out.println(" ==> " + result.toString());
                }
            }
        }
    }


    private void judgeBranch(BasicBlock basicBlock,InstructionHandle handle,Fact fact){

        if(!(fact instanceof TaintFrame)) return;

        Instruction ins = handle.getInstruction();
        TaintFrame tf = (TaintFrame)fact;

        int first,second;
        boolean branchSuccess = false;
        int target = -1;
        TABLESWITCH ts = null;
        if(ins instanceof TABLESWITCH){
            if(!(ins instanceof  TABLESWITCH)) return;
            try{
                basicBlock.setHaveBranch(true);
                String s = tf.getStackValue(0).getConstantValue();
                if(s == null || s == "") return;
                ts = (TABLESWITCH) ins;
                int[] tsmatch = ts.getMatchs();
                int tsbranchtarget = -1;
                boolean tsbranchSuccess = true;
                InstructionHandle[] tstargets = ts.getTargets();
                InstructionHandle tstarget = ts.getTarget();
                int len = tsmatch.length;
                for(int i = 0 ; i < len ; i++){
                    if(s.charAt(0) == tsmatch[i]){
                        tsbranchtarget = tstargets[i].getPosition();
                        break;
                    }
                }
                if(tsbranchtarget == -1) tsbranchtarget = tstarget.getPosition();
                basicBlock.setBranchSeccess(tsbranchSuccess);
                basicBlock.setBranchAddress(tsbranchtarget);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if(!(ins instanceof IfInstruction)) return;
        basicBlock.setHaveBranch(true);
        try{
            if(tf.getStackDepth()<2) return;
            first = tf.getStackValue(1).getConstInt();
            second = tf.getStackValue(0).getConstInt();
            if(first == Integer.MIN_VALUE || second == Integer.MIN_VALUE){
                basicBlock.setBranchAddress(target);
                basicBlock.setBranchSeccess(false);
                return;
            }
            branchSuccess = true;
            int target1 = handle.getNext().getPosition();
            int target2 = ((IF_ICMPLE) ins).getTarget().getPosition();
            if(ins instanceof IF_ICMPLE){
                if(first <= second){
                    target=target2;
                }else {
                    target = target1;
                }
            }else if(ins instanceof IF_ICMPLT){
                if(first < second){
                    target=target2;
                }else {
                    target = target1;
                }
            }else if(ins instanceof IF_ICMPGT){
                if(first > second){
                    target=target2;
                }else {
                    target = target1;
                }
            }else if(ins instanceof  IF_ICMPGE){
                if(first >= second){
                    target=target2;
                }else {
                    target = target1;
                }
            }else if(ins instanceof IF_ICMPEQ){
                if(first == second){
                    target=target2;
                }else {
                    target = target1;
                }
            }else if(ins instanceof IF_ICMPNE){
                if(first != second){
                    target=target2;
                }else {
                    target = target1;
                }
            }else {
                branchSuccess = false;
                target = -1;
            }
            basicBlock.setBranchSeccess(branchSuccess);
            basicBlock.setBranchAddress(target);
        }catch (Exception e){

        }

    }
}

