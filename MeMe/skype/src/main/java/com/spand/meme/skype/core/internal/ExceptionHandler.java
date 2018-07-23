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

package com.spand.meme.skype.core.internal;

import com.spand.meme.skype.core.exceptions.ConnectionException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;

public class ExceptionHandler {
    private static Field POSTER_FIELD;
    private static Field DELEGATE_FIELD;
    private static Field REQUESTS_FIELD;

    static {
        try {
            POSTER_FIELD = HttpURLConnection.class.getDeclaredField("poster");
            POSTER_FIELD.setAccessible(true);
        } catch (NoSuchFieldException ignored) {
        }
        try {
            DELEGATE_FIELD = javax.net.ssl.HttpsURLConnection.class.getDeclaredField("delegate");
            DELEGATE_FIELD.setAccessible(true);
        } catch (NoSuchFieldException ignored) {
        }
        try {
            REQUESTS_FIELD = java.net.HttpURLConnection.class.getDeclaredField("requests");
            REQUESTS_FIELD.setAccessible(true);
        } catch (NoSuchFieldException ignored) {
        }
    }

    public static ConnectionException generateException(String reason, HttpURLConnection connection) {
        try {
            return new ConnectionException(reason, connection);
        } catch (IOException e) {
            throw new RuntimeException(String.format("IOException while constructing exception (%s, %s)", reason, connection));
        } finally {
            connection.disconnect();
        }
    }

    public static ConnectionException generateException(String reason, IOException nested) {
        return new ConnectionException(reason, nested);
    }
}
