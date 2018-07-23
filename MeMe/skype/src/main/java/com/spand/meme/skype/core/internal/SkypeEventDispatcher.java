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

import com.spand.meme.skype.core.Skype;
import com.spand.meme.skype.core.events.Event;
import com.spand.meme.skype.core.events.EventDispatcher;
import com.spand.meme.skype.core.events.EventHandler;
import com.spand.meme.skype.core.events.Listener;
import com.spand.meme.skype.core.exceptions.handler.ErrorSource;

import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;

public class SkypeEventDispatcher implements EventDispatcher {
    private Skype instance;

    public SkypeEventDispatcher(Skype instance) {
        this.instance = instance;
    }

    private final Map<Class<?>, List<RegisteredListener>> listeners = Collections.synchronizedMap(new HashMap<>());

    public void registerListener(Listener l) {
        Class<?> c = l.getClass();
        for (Method m : c.getMethods()) {
            if (m.getAnnotation(EventHandler.class) != null && m.getParameterTypes().length == 1 && Event.class.isAssignableFrom(m.getParameterTypes()[0])) {
                RegisteredListener reglistener = new RegisteredListener(l, m);
                Class<?> eventType = m.getParameterTypes()[0];
                List<RegisteredListener> methods = listeners.get(eventType);
                if (methods == null) {
                    methods = new ArrayList<>();
                    listeners.put(eventType, methods);
                }
                methods.add(reglistener);
            }
        }
    }

    public void callEvent(Event e) { //todo bake
        List<RegisteredListener> methods = new ArrayList<>();
        Class<?> eventType = e.getClass();
        while (true) {
            List<RegisteredListener> m = listeners.get(eventType);
            if (m != null) {
                methods.addAll(m);
            }
            if (eventType == Event.class) {
                break;
            }
            eventType = eventType.getSuperclass();
        }
        for (RegisteredListener method : methods) {
            try {
                method.handleEvent(e);
            } catch (Throwable t) {
                instance.getLogger().log(Level.SEVERE, "Error while handling event", t);
                instance.handleError(ErrorSource.DISPATCHING_EVENT, t, false);
            }
        }
    }
}
