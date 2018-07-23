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

package com.spand.meme.skype.core.internal.chat.objects;

import com.spand.meme.skype.core.chat.objects.ReceivedFile;

public class ReceivedFileImpl implements ReceivedFile {
    private final String name;
    private final long size;
    private final long tid;

    public ReceivedFileImpl(String name, long size, long tid) {
        this.name = name;
        this.size = size;
        this.tid = tid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public long getTid() {
        return tid;
    }
}
