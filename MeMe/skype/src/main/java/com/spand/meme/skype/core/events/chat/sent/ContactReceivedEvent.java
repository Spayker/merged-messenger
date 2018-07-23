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

package com.spand.meme.skype.core.events.chat.sent;

import com.spand.meme.skype.core.chat.Chat;
import com.spand.meme.skype.core.events.chat.ChatEvent;
import com.spand.meme.skype.core.participants.Participant;
import com.spand.meme.skype.core.participants.info.Contact;
import com.spand.meme.skype.core.participants.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactReceivedEvent extends ChatEvent {
    private final Participant sender;
    private final List<Contact> sentContacts;

    public ContactReceivedEvent(Chat chat, Participant sender, List<Contact> sent) {
        super(chat);
        this.sender = sender;
        this.sentContacts = new ArrayList<>(sent);
    }

    public Participant getSender()
    {
        return this.sender;
    }

    public List<Contact> getSentContacts()
    {
        return Collections.unmodifiableList(sentContacts);
    }
}
