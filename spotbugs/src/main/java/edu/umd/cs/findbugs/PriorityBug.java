package edu.umd.cs.findbugs;

import java.util.HashMap;

/**
 * 此类用于分类统计bug的等级 .
 */
public class PriorityBug {
    private String description;
    private HashMap<Integer,Integer> prioritys=new HashMap<>();
    public PriorityBug(String description){
        this.description=description;
        prioritys.put(1,0);
        prioritys.put(2,0);
        prioritys.put(3,0);
    }

    public void setPrioritys(HashMap<Integer,Integer> prioritys) {
        this.prioritys = prioritys;
    }
    public HashMap<Integer,Integer> getPrioritys(){
        return prioritys;
    }

    public String getDescription(){
        return description;
    }
  /*  public int getPriority(){
        return priority;
    }
    public int getNumbers(){
        return numbers;
    }*/
}
