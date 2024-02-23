package com.akapps.paani.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.akapps.paani.Adapters.RecentOrderAdapter;
import com.akapps.paani.Firebase.FirestoreClient;
import com.akapps.paani.Model.Order;
import com.akapps.paani.R;
import com.akapps.paani.databinding.ActivityRecentOrderBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RecentOrderActivity extends AppCompatActivity {

    private FirestoreClient firestoreClient;
    private RecentOrderAdapter recentOrderAdapter;
    private ActivityRecentOrderBinding views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        views = ActivityRecentOrderBinding.inflate(getLayoutInflater());
        setContentView(views.getRoot());

        firestoreClient = new FirestoreClient(FirebaseFirestore.getInstance());

        ArrayList<Order> dummyShimmerList = generateDummies();

        recentOrderAdapter = new RecentOrderAdapter(dummyShimmerList);
        views.recentOrderOrderList.setLayoutManager(new LinearLayoutManager(this));
        views.recentOrderOrderList.setAdapter(recentOrderAdapter);

        loadOrders();

        views.recentOrderNavBack.setOnClickListener(v -> {
            finish();
        });

    }

    private ArrayList<Order> generateDummies() {
        ArrayList<Order> orders = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            orders.add(null);
        }
        return orders;
    }

    private void loadOrders() {

            firestoreClient.loadOrders(0).observe(this, orders -> {
                if(isDestroyed()){
                    return;
                }
                Log.d("Refreshed", "It has been refreshed + "+ orders.size());
                if(orders.isEmpty()){
                    views.noOrderAnimation.setVisibility(View.VISIBLE);
                } else {
                    views.noOrderAnimation.setVisibility(View.GONE);
                }
                recentOrderAdapter.updateDataSource(orders);
                recentOrderAdapter.notifyDataSetChanged();
                views.recentOrderOrderList.scrollToPosition(0);
            });
    }
}