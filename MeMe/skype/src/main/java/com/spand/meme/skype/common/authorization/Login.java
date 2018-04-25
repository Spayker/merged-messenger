package com.spand.meme.skype.common.authorization;

import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.SkypeBuilder;
import com.samczsun.skype4j.events.EventHandler;
import com.samczsun.skype4j.events.Listener;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.exceptions.InvalidCredentialsException;
import com.samczsun.skype4j.exceptions.NotParticipatingException;

public class Login {

    private Skype skype;
    private String username;
    private String password;

    public Login(String username, String password){
        this.skype = new SkypeBuilder(username, password).withAllResources().build();
        this.username = username;
        this.password = password;
    }

    public void performLogin() throws ConnectionException, NotParticipatingException, InvalidCredentialsException {
        skype.login();
        skype.getEventDispatcher().registerListener(new Listener() {
            @EventHandler
            public void onMessage(MessageReceivedEvent e) {
                System.out.println("Got message: " + e.getMessage().getContent());
            }
        });
        skype.subscribe();
    }

}
