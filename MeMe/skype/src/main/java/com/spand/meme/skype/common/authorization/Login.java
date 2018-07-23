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

    private String username;
    private String password;
    private Skype skype;

    public Login(String username, String password) {
        this.skype = new SkypeBuilder(username, password).withAllResources().build();
        this.username = username;
        this.password = password;
    }

//    public static void main(String[] args) throws ConnectionException, NotParticipatingException, InvalidCredentialsException {
//        skype = new SkypeBuilder("spykerfun", "159753159753spayker").withAllResources().build();
//        skype.login();
//        skype.getEventDispatcher().registerListener(new Listener() {
//            @EventHandler
//            public void onMessage(MessageReceivedEvent e) {
//                System.out.println("Got message: " + e.getMessage().getContent());
//            }
//        });
//        skype.subscribe();
//    }

}
