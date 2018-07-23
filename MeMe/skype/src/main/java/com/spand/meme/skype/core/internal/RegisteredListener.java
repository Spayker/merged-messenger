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

import com.spand.meme.skype.core.events.Event;
import com.spand.meme.skype.core.events.Listener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RegisteredListener {
    private final Listener listener;
    private final Method method;

    public RegisteredListener(Listener l, Method m) {
        this.listener = l;
        this.method = m;
        this.method.setAccessible(true);
    }

    public void handleEvent(Event e) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.method.invoke(listener, e);
    }

    public Method getMethod() {
        return this.method;
    }
}
