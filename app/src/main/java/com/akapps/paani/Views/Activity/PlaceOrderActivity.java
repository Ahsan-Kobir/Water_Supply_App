package com.akapps.paani.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.akapps.paani.Callback.LoadListener;
import com.akapps.paani.Firebase.FirestoreClient;
import com.akapps.paani.Model.DeliveryAddress;
import com.akapps.paani.Model.Order;
import com.akapps.paani.Model.Product;
import com.akapps.paani.R;
import com.akapps.paani.Utils.Utils;
import com.akapps.paani.Views.LoadingDialog;
import com.akapps.paani.databinding.ActivityPlaceOrderBinding;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlaceOrderActivity extends AppCompatActivity {
    private FirestoreClient firestoreClient;
    private ActivityPlaceOrderBinding views;
    private LoadingDialog loadingDialog;
    private int quantity = 1;
    private int productPrice = 0;
    private int deliveryFee = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        views = ActivityPlaceOrderBinding.inflate(getLayoutInflater());
        setContentView(views.getRoot());

        firestoreClient = new FirestoreClient(FirebaseFirestore.getInstance());

        loadProductDetails();

    }

    private void loadProductDetails() {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        firestoreClient.loadProduct(new LoadListener() {
            @Override
            public void onLoadSuccess(Object result) {
                loadingDialog.dismiss();
                Product water = (Product) result;
                setData(water);
            }

            @Override
            public void onLoadFailed(String error) {
                loadingDialog.dismiss();
                Toast.makeText(PlaceOrderActivity.this, error, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void setData(Product water) {
        Glide.with(PlaceOrderActivity.this).load(water.getProductPhotos().get(0))
                .into(views.placeOrderCartProductImage);
        views.placeOrderCartProductTitle.setText(water.getTitle());
        views.placeOrderCartProductPrice.setText(water.getPrice() + " BDT");
        views.placeOrderPriceTotal.setText((productPrice * quantity) + " BDT");

        productPrice = Integer.parseInt(water.getPrice());

        views.placeOrderCartProductQuantityMinus.setOnClickListener(v -> {
            if(quantity > 1){
                views.placeOrderCartProductQuantity.setText(--quantity + "x");
                views.placeOrderPriceProduct.setText((calculatePrice()) + " BDT");
                views.placeOrderPriceTotal.setText((calculatePrice() + deliveryFee) + " BDT");
            }
        });
        views.placeOrderCartProductQuantityPlus.setOnClickListener(v -> {
            if(quantity <= 10){
                views.placeOrderCartProductQuantity.setText(++quantity + "x");
                views.placeOrderPriceProduct.setText((calculatePrice()) + " BDT");
                views.placeOrderPriceTotal.setText((calculatePrice() + deliveryFee) + " BDT") ;
            }
        });

        views.placeOrderNavImage.setOnClickListener(v -> {
            finish();
        });

        views.placeOrderSubmit.setOnClickListener(v -> {
            startOrderProcess(water);
        });
    }

    private void startOrderProcess(Product water){
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();

        Order order = new Order();
        order.setUser_uid(Utils.getUid());
        order.setPaymentMethod("Cash On Delivery");
        order.setTotalPrice(String.valueOf(calculatePrice() + deliveryFee));
        order.setTime(new Date().getTime());
        order.setDeliveryAddress(getDeliveryAddress());
        List<Product> productList = new ArrayList<>();
        productList.add(water);
        order.setProductList(productList);

        firestoreClient.placeNewOrder(order, new LoadListener(){

            @Override
            public void onLoadSuccess(Object result) {
                loadingDialog.dismiss();
                Intent intent = new Intent(PlaceOrderActivity.this, OrderSuccessActivity.class);
                intent.putExtra("order_id", result.toString());
                startActivity(intent);
                finish();
            }

            @Override
            public void onLoadFailed(String error) {
                loadingDialog.dismiss();
                Snackbar.make(views.getRoot(), error, BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }

    private DeliveryAddress getDeliveryAddress() {
        return new DeliveryAddress();
    }

    private int calculatePrice(){
        return productPrice * quantity;
    }
}