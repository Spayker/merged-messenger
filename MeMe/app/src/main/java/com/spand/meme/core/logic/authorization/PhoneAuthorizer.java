package com.spand.meme.core.logic.authorization;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.spand.meme.core.ui.activity.ActivityConstants.EMPTY_STRING;

public class PhoneAuthorizer extends Authorizer {

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = PhoneAuthorizer.class.getSimpleName();

    @SuppressLint("StaticFieldLeak")
    private static PhoneAuthorizer instance;

    private Activity currentActivity;
    private String phoneNumber;

    private static String uniqueIdentifier;
    private static final String UNIQUE_ID = "UNIQUE_ID";

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onVerificationCompleted(PhoneAuthCredential credential) {
                    Log.d(TAG, "verification completed" + credential);
                    finishSingUpActivity(currentActivity, userName, phoneNumber, EMPTY_STRING);
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Log.w(TAG, "verification failed", e);
                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(currentActivity,
                                "Trying too many timeS",
                                Toast.LENGTH_SHORT).show();
                    } else if (e instanceof FirebaseTooManyRequestsException) {
                        Toast.makeText(currentActivity,
                                "Trying too many timeS",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                    Log.d(TAG, "code sent " + verificationId);
                    addVerificationDataToFirestore(phoneNumber, verificationId);
                }
            };

    private PhoneAuthorizer() { }

    public static PhoneAuthorizer init(Activity activity, String name, String phoneNumber) {
        if (instance == null) {
            instance = new PhoneAuthorizer(activity, name, phoneNumber);
        }
        return instance;
    }

    private PhoneAuthorizer(Activity currentActivity, String name, String phoneNumber) {
        this.currentActivity = currentActivity;
        this.phoneNumber = phoneNumber;
        userName = name;
        // auth init
        mAuth = FirebaseAuth.getInstance();
        firestoreDB = FirebaseFirestore.getInstance();

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        firestore.setFirestoreSettings(settings);

        getInstallationIdentifier();
    }

    private synchronized void getInstallationIdentifier() {
        if (uniqueIdentifier == null) {
            SharedPreferences sharedPrefs = currentActivity.getSharedPreferences(
                    UNIQUE_ID, Context.MODE_PRIVATE);
            uniqueIdentifier = sharedPrefs.getString(UNIQUE_ID, null);
            if (uniqueIdentifier == null) {
                uniqueIdentifier = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(UNIQUE_ID, uniqueIdentifier);
                editor.apply();
                editor.commit();
            }
        }
    }

    private void addVerificationDataToFirestore(String phone, String verificationId) {
        Map<String, Object> verifyMap = new HashMap<>();
        verifyMap.put("phone", phone);
        verifyMap.put("verificationId", verificationId);
        verifyMap.put("timestamp", System.currentTimeMillis());

        firestoreDB.collection("phoneAuth").document(uniqueIdentifier)
                .set(verifyMap)
                .addOnFailureListener(e -> Log.w(TAG, "Error adding phone auth info", e));
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(currentActivity, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "code verified signIn successful");
                    } else {
                        Log.w(TAG, "code verification failed", task.getException());
                    }
                });
    }

    @Override
    public void verify() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,                  // Phone number to verify
                60,                         // Timeout duration
                TimeUnit.SECONDS,             // Unit of timeout
                currentActivity,              // Activity (for callback binding)
                callbacks);
    }

    public void getVerificationDataFromFirestoreAndVerify(String code) {
        firestoreDB.collection("phoneAuth").document(uniqueIdentifier)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot ds = task.getResult();
                        if(ds.exists()){
                            //disableSendCodeButton(ds.getLong("timestamp"));
                            if(code != null){
                                createCredentialSignIn(ds.getString("verificationId"), code);
                            }else{
                                verifyPhoneNumber(ds.getString("phone"));
                            }
                        }else{
                            //showSendCodeButton();
                            Log.d(TAG, "Code hasn't been sent yet");
                        }

                    } else {
                        Log.d(TAG, "Error getting document: ", task.getException());
                    }
                });
    }

    private void createCredentialSignIn(String verificationId, String verifyCode) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, verifyCode);
        signInWithPhoneAuthCredential(credential);
    }

    private void verifyPhoneNumber(String phno){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phno, 70,
                TimeUnit.SECONDS, currentActivity, callbacks);
    }

    public static PhoneAuthorizer getInstance() {
        return instance;
    }
}
