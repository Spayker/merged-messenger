package com.spand.meme.modules.chat;

import android.content.Context;

import co.chatsdk.core.session.ChatSDK;
import co.chatsdk.core.session.Configuration;
import co.chatsdk.firebase.FirebaseModule;
import co.chatsdk.firebase.file_storage.FirebaseFileStorageModule;
import co.chatsdk.ui.manager.UserInterfaceModule;

public class ChatSDKInitializer {

    public void initChatSdk(Context context) {
        // Create a new configuration
        Configuration.Builder builder = new Configuration.Builder(context);

        // Perform any configuration steps (optional)
        builder.firebaseRootPath("prod");

        builder.facebookLoginEnabled(false);
        builder.twitterLoginEnabled(false);

        // Initialize the Chat SDK
        ChatSDK.initialize(builder.build());
        UserInterfaceModule.activate(context);

        // Activate the FireBase module
        FirebaseModule.activate();
    }

}