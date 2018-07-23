/*
 * Copyright 2016 Sam Sun <me@samczsun.com>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.spand.meme.skype.core.internal.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Encoder {
    private static final Map<String, String> encoded = new HashMap<>();

    public static String encode(String in) {
        try {
            if (!encoded.containsKey(in)) encoded.put(in, URLEncoder.encode(in, "UTF-8"));
            return encoded.get(in);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encode(Map<String, String> data) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            result.append(entry.getKey()).append("=").append(encode(entry.getValue())).append("&");
        }
        return result.toString();
    }
}
