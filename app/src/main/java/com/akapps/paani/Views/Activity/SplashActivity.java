package com.akapps.paani.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.akapps.paani.Api.LoginRepo;
import com.akapps.paani.R;
import com.akapps.paani.Utils.SharedPref;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPref sharedPref = new SharedPref(this);
        LoginRepo loginRepo = new LoginRepo(sharedPref, null);

        if(loginRepo.isLoggedIn()){
            goToHome();
        } else {
            goToLogin();
        }


    }
    private void goToHome(){
        new Handler().postDelayed(()->{
            startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            finishAffinity();
        }, 2000);
    }

    private void goToLogin(){
        new Handler().postDelayed(()->{
            startActivity(new Intent(this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            finishAffinity();
        }, 2000);
    }
}