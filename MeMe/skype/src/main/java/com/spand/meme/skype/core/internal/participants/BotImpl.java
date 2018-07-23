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

package com.spand.meme.skype.core.internal.participants;

import com.spand.meme.skype.core.chat.messages.ChatMessage;
import com.spand.meme.skype.core.exceptions.ConnectionException;
import com.spand.meme.skype.core.internal.SkypeImpl;
import com.spand.meme.skype.core.internal.chat.ChatBot;
import com.spand.meme.skype.core.internal.chat.ChatImpl;
import com.spand.meme.skype.core.participants.Bot;
import com.spand.meme.skype.core.participants.info.BotInfo;

import java.util.List;
import java.util.UUID;

public class BotImpl extends ParticipantImpl implements Bot {

    private BotInfo botInfo;

    public BotImpl(SkypeImpl skype, ChatImpl chat, String id) throws ConnectionException {
        super(skype, chat, id);
    }

    @Override
    public String getDisplayName() {
        return this.botInfo.getDisplayName();
    }

    @Override
    public BotInfo getBotInfo() {
        return this.botInfo;
    }

    public void setInfo(BotInfo info) {
        this.botInfo = info;
    }
}
