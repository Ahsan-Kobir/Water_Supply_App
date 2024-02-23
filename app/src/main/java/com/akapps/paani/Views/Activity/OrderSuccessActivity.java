package com.akapps.paani.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.akapps.paani.R;
import com.akapps.paani.databinding.ActivityOrderSuccessBinding;

public class OrderSuccessActivity extends AppCompatActivity {

    ActivityOrderSuccessBinding views;
    private String orderId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        views = ActivityOrderSuccessBinding.inflate(getLayoutInflater());
        setContentView(views.getRoot());

        views.successNavBack.setOnClickListener( v -> {
            finish();
        });

        if(getIntent().hasExtra("order_id")){
            orderId = getIntent().getStringExtra("order_id");
        }

        views.successOrderTrack.setOnClickListener(v -> {
            Intent intent = new Intent(this, TrackOrderActivity.class);
            intent.putExtra("order_id", orderId);
            startActivity(intent);
        });
    }
}