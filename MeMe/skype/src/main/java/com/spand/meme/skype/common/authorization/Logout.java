package com.spand.meme.skype.common.authorization;

import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.exceptions.ConnectionException;

public class Logout {

    private Skype skype;

    public Logout(Skype skype){
        this.skype = skype;
    }

    public void performLogout() throws ConnectionException {
        skype.logout();
    }

}
