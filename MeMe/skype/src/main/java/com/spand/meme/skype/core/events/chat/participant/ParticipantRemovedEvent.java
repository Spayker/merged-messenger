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

package com.spand.meme.skype.core.events.chat.participant;

import com.spand.meme.skype.core.participants.Participant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParticipantRemovedEvent extends ParticipantEvent {
    private final List<Participant> removed;

    public ParticipantRemovedEvent(Participant initiator, List<Participant> removed) {
        super(initiator);
        this.removed = new ArrayList<>(removed);
    }

    public List<Participant> getRemovedParticipants() {
        return Collections.unmodifiableList(this.removed);
    }
}
