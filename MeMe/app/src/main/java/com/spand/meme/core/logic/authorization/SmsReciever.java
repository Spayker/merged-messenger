package com.spand.meme.core.logic.authorization;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.spand.meme.core.ui.activity.main.RegisterActivity;

public class SmsReciever extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs;
            String msg_from;
            if (bundle != null){
                //---retrieve the SMS message received---
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        if(msg_from.equalsIgnoreCase("Phone code")) {
                            String msgBody = msgs[i].getMessageBody();
                            RegisterActivity.getInstance().getVerificationDataFromFirestoreAndVerify(msgBody.split("\\D+")[0]);
                            break;
                        }
                    }
                }catch(Exception e){
                }
            }
        }
    }
}
