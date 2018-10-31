package com.spandr.meme.core.activity.common.data.database;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

public final class FireBaseDBInitializer {

    private static FireBaseDBInitializer instance;

    // since I can connect from multiple devices, we store each connection instance separately
    // any time that connectionsRef's value is null (i.e. has no children) I am offline
    private static final FirebaseDatabase DATABASE = FirebaseDatabase.getInstance();
    private static final DatabaseReference MY_CONNECTION_REFERENCE = DATABASE.getReference("users/joe/connections");

    // stores the timestamp of my last disconnect (the last time I was seen online)
    private static final DatabaseReference LAST_ONLINE_REF = DATABASE.getReference("/users/joe/lastOnline");

    private static final DatabaseReference CONNECTED_REF = DATABASE.getReference(".info/connected");

    private FireBaseDBInitializer(){}

    public static FireBaseDBInitializer create(){
        if(instance == null){
            instance = new FireBaseDBInitializer();
        }
        return instance;
    }

    public void init(){
        CONNECTED_REF.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    DatabaseReference con = MY_CONNECTION_REFERENCE.push();

                    // when this device disconnects, remove it
                    con.onDisconnect().removeValue();

                    // when I disconnect, update the last time I was seen online
                    LAST_ONLINE_REF.onDisconnect().setValue(ServerValue.TIMESTAMP);

                    // add this device to my connections list
                    // this value could contain info about the device or a timestamp too
                    con.setValue(Boolean.TRUE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.err.println("Listener was cancelled at .info/connected");
            }
        });
    }





}
