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

package com.spand.meme.skype.core.internal.chat.messages;

import com.spand.meme.skype.core.chat.Chat;
import com.spand.meme.skype.core.chat.messages.ReceivedMessage;
import com.spand.meme.skype.core.formatting.Message;
import com.spand.meme.skype.core.internal.SkypeImpl;
import com.spand.meme.skype.core.internal.participants.ParticipantImpl;
import com.spand.meme.skype.core.participants.Participant;
import com.spand.meme.skype.core.participants.User;

public class ReceivedMessageImpl extends ChatMessageImpl implements ReceivedMessage {
    public ReceivedMessageImpl(Chat chat, ParticipantImpl user, String id, String clientId, long time, Message message, SkypeImpl skype) {
        super(chat, user, id, clientId, time, message, skype);
    }
}
