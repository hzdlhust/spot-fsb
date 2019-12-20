package com.h3xstream.findsecbugs;

import com.h3xstream.findsecbugs.taintanalysis.Taint;

import java.util.HashMap;
import java.util.Map;

public class MapHelper {
    private static Map<String, Taint.State> stateMap = new HashMap<>();
    public static void putState(String key, Taint.State state){
        stateMap.put(key,state);
    }
    public static Taint.State getState(String key){
        if(stateMap.containsKey(key)) return stateMap.get(key);
        else    return Taint.State.SAFE;
    }
}
