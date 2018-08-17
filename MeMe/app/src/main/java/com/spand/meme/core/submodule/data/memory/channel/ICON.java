package com.spand.meme.core.submodule.data.memory.channel;

import com.spand.meme.R;

public enum ICON {

    FB(R.mipmap.fb),
    VK(R.mipmap.vk),
    OK(R.mipmap.ok),
    LN(R.mipmap.in),
    TW(R.mipmap.twitter),
    IN(R.mipmap.instagram),
    TL(R.mipmap.telegram),
    SK(R.mipmap.skype),
    ICQ(R.mipmap.icq),
    DC(R.mipmap.discord),
    TUM(R.mipmap.tumblr),
    YT(R.mipmap.youtube),
    GM(R.mipmap.gmail),
    MAIL_RU(R.mipmap.mail_ru);

    private int iconId;

    ICON(int iconId) {
        this.iconId = iconId;
    }

    public int getIconId() {
        return iconId;
    }
}
