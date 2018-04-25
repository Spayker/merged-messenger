package com.spand.meme.telegram.common.authorization;

import com.github.badoualy.telegram.api.Kotlogram;
import com.github.badoualy.telegram.api.TelegramClient;
import com.github.badoualy.telegram.tl.api.TLUser;
import com.github.badoualy.telegram.tl.api.auth.TLAuthorization;
import com.github.badoualy.telegram.tl.api.auth.TLSentCode;
import com.github.badoualy.telegram.tl.exception.RpcErrorException;
import com.spand.meme.telegram.common.configuration.ApiStorage;
import com.spand.meme.telegram.common.configuration.ChatSettings;

import java.io.IOException;
import java.util.Scanner;

import static com.spand.meme.telegram.common.configuration.ChatSettings.PHONE_NUMBER;

public class Login {

    public void Login(String code){
        // This is a synchronous client, that will block until the response arrive (or until timeout)
        TelegramClient client = Kotlogram.getDefaultClient(ChatSettings.application, new ApiStorage());

        // You can start making requests
        try {
            // Send code to account
            TLSentCode sentCode = client.authSendCode(false, PHONE_NUMBER, true);
            // Auth with the received code
            TLAuthorization authorization = client.authSignIn(PHONE_NUMBER, sentCode.getPhoneCodeHash(), code);
            TLUser self = authorization.getUser().getAsUser();
            System.out.println("You are now signed in as " + self.getFirstName() + " " + self.getLastName() + " @" + self.getUsername());
        } catch (RpcErrorException | IOException e) {
            e.printStackTrace();
        } finally {
            client.close(); // Important, do not forget this, or your process won't finish
        }
    }


}
