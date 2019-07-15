package com.h3xstream.findsecbugs.injection.convert;

import com.h3xstream.findsecbugs.injection.BasicInjectionDetector;
import com.h3xstream.findsecbugs.taintanalysis.Taint;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.Priorities;

public class ResourceInjectionDetector extends BasicInjectionDetector {
    public ResourceInjectionDetector(BugReporter bugReporter){
        super(bugReporter);
        loadConfiguredSinks("resource.txt","RESOURCE_INJECTION");
    }

    @Override
    protected int getPriority(Taint taint)
    {

        if(taint.isTainted()){
            return Priorities.HIGH_PRIORITY;
        }
        else if(taint.isUnknown()){
            return Priorities.NORMAL_PRIORITY;
        }
        else
            return Priorities.IGNORE_PRIORITY;
    }
}
