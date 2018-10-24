package com.spandr.meme.core.data.memory.channel;

import com.spandr.meme.R;

public enum ICON {

    FB(R.mipmap.fb),
    VK(R.mipmap.vk),
    OK(R.mipmap.ok),
    LN(R.mipmap.in),
    TW(R.mipmap.twitter),
    IN(R.mipmap.instagram),
    TL(R.mipmap.telegram),
    PN(R.mipmap.pinterest),
    SK(R.mipmap.skype),
    TWITCH(R.mipmap.twitch),
    ICQ(R.mipmap.icq),
    GADU(R.mipmap.gadu),
    DC(R.mipmap.discord),
    SL(R.mipmap.slack),
    TUM(R.mipmap.tumblr),
    YT(R.mipmap.youtube),
    REDDIT(R.mipmap.reddit),
    QUORA(R.mipmap.quora),
    HABR(R.mipmap.habr),
    SOUNDC(R.mipmap.soundc),
    SPOTIFY(R.mipmap.spotify),
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
