package com.nepxion.discovery.plugin.strategy.starter.agent.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 *
 * @author zifeihan
 * @version 1.0
 */

public class StringUtil {
    public static boolean isEmpty(final String string) {
        return string == null || string.isEmpty();
    }

    public static List<String> tokenizeToStringList(final String str, final String delimiters) {
        return tokenizeToStringList(str, delimiters, true, true);
    }

    private static List<String> tokenizeToStringList(final String str, final String delimiters, final boolean trimTokens, final boolean ignoreEmptyTokens) {

        if (isEmpty(str)) {
            return Collections.emptyList();
        }
        StringTokenizer st = new StringTokenizer(str, delimiters);
        List<String> tokens = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (trimTokens) {
                token = token.trim();
            }
            if (!ignoreEmptyTokens || token.length() > 0) {
                tokens.add(token);
            }
        }
        return tokens;
    }
}