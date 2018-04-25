package com.spand.meme.skype.common.configuration;

import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.Visibility;
import com.samczsun.skype4j.exceptions.ConnectionException;

public class ChatSettings {

    public void setupSkypeSettings(Skype skype) throws ConnectionException {
        skype.setVisibility(Visibility.ONLINE);
        // ...
    }


}