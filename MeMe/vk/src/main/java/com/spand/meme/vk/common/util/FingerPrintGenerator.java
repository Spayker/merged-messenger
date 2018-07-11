package com.spand.meme.vk.common.util;

import android.content.Context;

import com.vk.sdk.util.VKUtil;

public class FingerPrintGenerator {

    public String[] generateFingerPrint(Context ctx, String packageName){
        return VKUtil.getCertificateFingerprint(ctx, packageName);
    }

}
