package com.akapps.paani.Utils;

import com.akapps.paani.Firebase.FirebaseAuthClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String getTimeInString(long time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a DD MM");
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }

    public static String getUid(){
        return FirebaseAuth.getInstance().getUid();
    }
}
