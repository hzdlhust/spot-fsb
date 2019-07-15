package com.h3xstream.findsecbugs.injection.convert;

import com.h3xstream.findsecbugs.injection.BasicInjectionDetector;
import com.h3xstream.findsecbugs.taintanalysis.Taint;
import com.h3xstream.findsecbugs.taintanalysis.data.UnknownSource;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.Priorities;

import java.util.Set;

/**
 * This project is used to learn by Snail.
 */

public class PrivacyViolationDetector extends BasicInjectionDetector {
    private String[] privateInfo = {"password", "passwd", "secret", "location", "employment", "credit", "card", "salary", "bank", "debt", "credential"};

    public PrivacyViolationDetector(BugReporter bugReporter) {
        super(bugReporter);
        loadConfiguredSinks("privacy.txt", "PRIVACY_VIOLATION");
    }


    @Override
    protected int getPriority(Taint taint) {
        Set<UnknownSource> sources;
        sources = taint.getSources();
        for (UnknownSource s : sources) {
            String str = s.toString().toLowerCase();
            int i = 0;
            while (i < privateInfo.length) {
                if (str.indexOf(privateInfo[i]) != -1) {
                    return getPriorityTwo(taint);
                } else {
                    i++;
                }
            }
        }
        return Priorities.IGNORE_PRIORITY;
    }

    private int getPriorityTwo(Taint taint) {
        if (!taint.isSafe()) {
            //(Condition extracted for clarity)
            //Either specifically safe for new line or URL encoded which encoded few other characters
            boolean newLineSafe = taint.hasTag(Taint.Tag.CR_ENCODED) && taint.hasTag(Taint.Tag.LF_ENCODED);
            boolean urlSafe = (taint.hasTag(Taint.Tag.URL_ENCODED));
            if (newLineSafe || urlSafe) {
                return Priorities.IGNORE_PRIORITY;
            }
        }
        if (taint.isTainted()) {
            return Priorities.NORMAL_PRIORITY;
        } else if (!taint.isSafe()) {
            return Priorities.LOW_PRIORITY;
        } else {
            return Priorities.IGNORE_PRIORITY;
        }
    }
}


