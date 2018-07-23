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

package com.spand.meme.skype.core.exceptions;

/**
 * Represents any exception that may occur while using this API
 *
 * @author samczsun
 */
public class SkypeException extends Exception {
    public SkypeException() {
        super();
    }

    public SkypeException(String message) {
        super(message);
    }
    public SkypeException(String message, Exception chain) {
        super(message, chain);
    }
}
