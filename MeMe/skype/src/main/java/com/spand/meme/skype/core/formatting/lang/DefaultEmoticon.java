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

package com.spand.meme.skype.core.formatting.lang;

import com.spand.meme.skype.core.formatting.IEmoticon;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum DefaultEmoticon implements IEmoticon {

    ;

    private final String id;
    private final String etag;
    private String desc;
    private List<String> shortcuts;

    DefaultEmoticon(String id, String etag, String desc, String... shortcuts) {
        this.id = id;
        this.etag = etag;
        this.desc = desc;
        this.shortcuts = Arrays.asList(shortcuts);
    }

    public String getId() {
        return this.id;
    }

    public List<String> getShortcuts() {
        return Collections.unmodifiableList(this.shortcuts);
    }

    public String getEtag() {
        return this.etag;
    }

    public String getDescription() {
        return this.desc;
    }
}
