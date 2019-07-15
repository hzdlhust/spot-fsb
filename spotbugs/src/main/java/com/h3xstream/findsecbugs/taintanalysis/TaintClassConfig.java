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

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Summary of information about a class related to taint analysis,
 * allows to configure default behavior for return types and type casts.
 *
 * Default configuration is mutable class with null taint state.
 *
 * @author Tomas Polesovsky (Liferay, Inc.)
 */
public class TaintClassConfig implements TaintTypeConfig {
    public static final Taint.State DEFAULT_TAINT_STATE = Taint.State.NULL;
    private static final String IMMUTABLE = "#IMMUTABLE";
    private Taint.State taintState = DEFAULT_TAINT_STATE;
    private boolean immutable;
    private static final Pattern typePattern;
    private static final Pattern taintConfigPattern;

    static {
        String javaIdentifierRegex = "\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*";
        String classWithPackageRegex = javaIdentifierRegex+"(\\/"+javaIdentifierRegex+")*";
        String typeRegex = "(\\[)*((L" + classWithPackageRegex + ";)|B|C|D|F|I|J|S|Z)";
        typePattern = Pattern.compile(typeRegex);

        String taintConfigRegex = "([A-Z_]+|#IMMUTABLE|[A-Z_]+#IMMUTABLE)";
        taintConfigPattern = Pattern.compile(taintConfigRegex);
    }

    public static boolean accepts(String typeSignature, String taintConfig) {
        return typePattern.matcher(typeSignature).matches() && taintConfigPattern.matcher(taintConfig).matches();
    }


    @Override
    public TaintClassConfig load(String taintConfig) throws IOException {
        if (taintConfig == null) {
            throw new NullPointerException("Taint config is null");
        }
        taintConfig = taintConfig.trim();
        if (taintConfig.isEmpty()) {
            throw new IOException("No taint class config specified");
        }
        TaintClassConfig taintClassConfig = new TaintClassConfig();
        if (taintConfig.endsWith(IMMUTABLE)) {
            taintClassConfig.immutable = true;
            taintConfig = taintConfig.substring(0, taintConfig.length() - IMMUTABLE.length());
        }

        if (!taintConfig.isEmpty()) {
            taintClassConfig.taintState = Taint.State.valueOf(taintConfig);
        }

        return taintClassConfig;
    }

    public Taint.State getTaintState() {
        return taintState;
    }

    public boolean isImmutable() {
        return immutable;
    }

    public Taint.State getTaintState(Taint.State defaultState) {
        if (taintState.equals(DEFAULT_TAINT_STATE)) {
            return defaultState;
        }

        return taintState;
    }
}
