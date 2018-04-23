package com.spand.bridgecom.skype.common;

import com.samczsun.skype4j.chat.Chat;
import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.exceptions.ChatNotFoundException;
import com.samczsun.skype4j.exceptions.ConnectionException;

public class ChatLoader {

    public void load(Skype skype, String chatDetails) throws ConnectionException, ChatNotFoundException {
        Chat chat = skype.loadChat(chatDetails);
    }


}
