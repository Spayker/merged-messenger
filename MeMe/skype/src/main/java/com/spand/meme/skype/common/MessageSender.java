package com.spand.meme.skype.common;

import com.samczsun.skype4j.chat.Chat;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.formatting.Message;
import com.samczsun.skype4j.formatting.Text;

public class MessageSender {

    private Chat chat;

    public MessageSender(Chat chat){
        this.chat = chat;
    }

    public MessageSender(Text text) throws ConnectionException {
        Message message = Message.create().with(text);
        chat.sendMessage(message);
    }

}
