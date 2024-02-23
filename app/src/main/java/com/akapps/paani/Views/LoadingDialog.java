package com.akapps.paani.Views;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.akapps.paani.R;

public class LoadingDialog extends Dialog {
    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.LoadingDiloagTheme);
        View view = LayoutInflater.from(context).inflate(R.layout.loading_dialog, null, false);
        setContentView(view);
        setCancelable(false);
    }
}
