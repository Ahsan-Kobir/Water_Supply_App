package com.akapps.paani.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.akapps.paani.Api.LoginRepo;
import com.akapps.paani.Callback.LoginRegisterResultListener;
import com.akapps.paani.Firebase.FirebaseAuthClient;
import com.akapps.paani.Model.User;
import com.akapps.paani.R;
import com.akapps.paani.Utils.RequestHandler;
import com.akapps.paani.Utils.SharedPref;
import com.akapps.paani.Views.LoadingDialog;
import com.akapps.paani.databinding.ActivityLoginBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class LoginActivity extends AppCompatActivity implements LoginRegisterResultListener {

    ActivityLoginBinding views;
    private boolean isLogin;
    private LoginRepo loginRepo;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        views = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(views.getRoot());

        SharedPref sharedPref = new SharedPref(this);
        RequestHandler requestHandler = RequestHandler.getInstance();
        FirebaseAuthClient firebaseAuthClient = new FirebaseAuthClient(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance());
        loginRepo = new LoginRepo(sharedPref, requestHandler, firebaseAuthClient);

        isLogin = true;
        setUiHandler();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginRepo = null;
    }

    private void setUiHandler() {
        views.loginSection.setOnClickListener(v-> {
            if(!isLogin){
                setLoginSectionVisible();
            }
        });

        views.registerSection.setOnClickListener(v -> {
            if(isLogin){
                setRegisterSectionVisible();
            }
        });

        views.loginNavBack.setOnClickListener(v->{
            super.onBackPressed();
        });

        views.loginSubmit.setOnClickListener(v ->{
            if(allFilled()){
                if(isLogin){
                    loginRepo.login(new User(
                            "",
                            "",
                            views.loginEmail.getText().toString(),
                            "",
                            views.loginPassword.getText().toString()
                    ), this);
                } else {
                    loginRepo.register(new User(
                            views.loginName.getText().toString(),
                            null,
                            views.loginEmail.getText().toString(),
                            randomUUID(),
                            views.loginPassword.getText().toString()
                    ), this);
                }

                loadingDialog = new LoadingDialog(this);
                loadingDialog.show();
            }
        });
    }

    private String randomUUID(){
        return UUID.randomUUID().toString();
    }

    private boolean allFilled() {
        if(views.loginEmail.getText().length()==0){
            views.loginEmail.setError(getString(R.string.email_required));
            return false;
        };
        if(views.loginPassword.getText().length()==0){
            views.loginPassword.setError(getString(R.string.password_required));
            return false;
        };
        if( (!isLogin) && views.loginName.getText().length()==0){
            views.loginName.setError(getString(R.string.name_required));
            return false;
        };
        return true;
    }

    private void setRegisterSectionVisible() {
        isLogin = false;
        views.registerSection.setBackground(null);
        views.loginSection.setBackgroundResource(R.drawable.login_label_bg);
        views.registerSection.setAnimation(AnimationUtils.makeInAnimation(this, false));
        views.loginSection.setAnimation(AnimationUtils.makeInAnimation(this, true));
        views.loginName.setVisibility(View.VISIBLE);
        views.loginName.setAnimation(AnimationUtils.makeInAnimation(this, false));
        views.loginSectionText.setTextColor(getColor(R.color.blue));
        views.registerSectionText.setTextColor(getColor(R.color.white));
    }

    private void setLoginSectionVisible() {
        isLogin = true;
        views.registerSection.setBackgroundResource(R.drawable.register_label_bg);
        views.loginSection.setBackground(null);
        views.registerSection.setAnimation(AnimationUtils.makeInAnimation(this, false));
        views.loginSection.setAnimation(AnimationUtils.makeInAnimation(this, true));
        views.loginName.setAnimation(getNameHideAnimation());
        views.loginSectionText.setTextColor(getColor(R.color.white));
        views.registerSectionText.setTextColor(getColor(R.color.blue));
    }

    private Animation getNameHideAnimation(){
        Animation animation = AnimationUtils.makeOutAnimation(this, true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                views.loginName.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return animation;
    }

    private void hideLoadingDialog(){
        if(loadingDialog!=null){
            if(loadingDialog.isShowing()) loadingDialog.dismiss();
        }
    }

    @Override
    public void onLoginFailed(String message) {
        Log.e("LoginFailed", message);
        Snackbar.make(views.loginSubmit, message, BaseTransientBottomBar.LENGTH_LONG).show();
        hideLoadingDialog();
    }

    @Override
    public void onLoginSuccess(User user) {
        hideLoadingDialog();
        loginRepo.saveLogin(user);
        startActivity(new Intent(this, MainActivity.class));
        finishAffinity();
    }
}