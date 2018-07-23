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

package com.spand.meme.skype.core.internal.client;

import com.eclipsesource.json.JsonObject;
import com.spand.meme.skype.core.chat.GroupChat;
import com.spand.meme.skype.core.exceptions.ConnectionException;
import com.spand.meme.skype.core.exceptions.InvalidCredentialsException;
import com.spand.meme.skype.core.exceptions.NotParticipatingException;
import com.spand.meme.skype.core.exceptions.handler.ErrorHandler;
import com.spand.meme.skype.core.internal.Endpoints;
import com.spand.meme.skype.core.internal.SkypeImpl;
import com.spand.meme.skype.core.internal.Utils;
import com.spand.meme.skype.core.internal.utils.UncheckedRunnable;
import com.spand.meme.skype.core.participants.info.Contact;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class GuestClient extends SkypeImpl {
    private final String chatId;
    private final String requestedUsername;

    private volatile String actualUsername;

    public GuestClient(String username, String chatId, Set<String> resources, Logger logger, List<ErrorHandler> errorHandlers) {
        super(username, resources, logger, errorHandlers);
        this.chatId = chatId;
        this.requestedUsername = username;
    }

    @Override
    public void login() throws ConnectionException, InvalidCredentialsException, DecoderException {
        JsonObject response = Endpoints.NEW_GUEST
                .open(this)
                .as(JsonObject.class)
                .on(303, connection -> {
                    throw new NotParticipatingException();
                })
                .expect(201, "While logging in")
                .header("csrf_token", "skype4j")
                .cookie("csrf_token", "skype4j")
                .post(new JsonObject()
                        .add("name", requestedUsername)
                        .add("threadId", chatId)
                        .add("shortId", "Skype4J")
                        .add("flowId", "Skype4J"));
        this.setSkypeToken(response.get("skypetoken").asString());

        String[] splits = response.get("skypetoken").asString().split("\\.");
        try {
            String decoded = new String(Hex.decodeHex(Utils.makeValidBase64(splits[1])), "UTF-8");
            JsonObject object = JsonObject.readFrom(decoded).asObject();
            this.actualUsername = object.get("skypeid").asString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        List<UncheckedRunnable> tasks = new ArrayList<>();
        tasks.add(() -> {
            HttpURLConnection asmResponse = getAsmToken();
            String[] setCookie = asmResponse.getHeaderField("Set-Cookie").split(";")[0].split("=");
            this.cookies.put(setCookie[0], setCookie[1]);
        });
        tasks.add(this::registerEndpoint);

        try {
            ExecutorService executorService = Executors.newFixedThreadPool(2);
            tasks.forEach(executorService::submit);
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        super.login();
    }

    @Override
    public void logout() throws ConnectionException {
        Endpoints.LEAVE_GUEST
                .open(this, this.chatId)
                .expect(200, "While logging out")
                .cookie("guest_token_" + this.chatId, "skype4j::" + this.getSkypeToken())
                .cookie("csrf_token", "skype4j")
                .cookie("launcher_session_id", "Skype4J")
                .get();
        shutdown();
    }

    @Override
    public GroupChat createGroupChat(Contact... contacts) {
        throw new UnsupportedOperationException("Not supported with a guest account");
    }

    @Override
    public void loadAllContacts() {
        throw new UnsupportedOperationException("Not supported with a guest account");
    }

    @Override
    public void getContactRequests(boolean fromWebsocket) {
        throw new UnsupportedOperationException("Not supported with a guest account");
    }

    @Override
    public void updateContactList() {
        throw new UnsupportedOperationException("Not supported with a guest account");
    }

    @Override
    public String getUsername() {
        if (actualUsername == null) {
            throw new IllegalStateException("Should not be called when login has not completed");
        }
        return actualUsername;
    }
}
