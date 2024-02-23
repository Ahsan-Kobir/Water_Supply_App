package com.akapps.paani.Firebase;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.akapps.paani.Callback.LoadListener;
import com.akapps.paani.Model.Order;
import com.akapps.paani.Model.Product;
import com.akapps.paani.Model.Review;
import com.akapps.paani.Utils.Utils;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FirestoreClient {
    private FirebaseFirestore firestore;
    private MutableLiveData<ArrayList<Order>> orders = new MutableLiveData<>();

    public FirestoreClient(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    public void loadHomeData(LoadListener listener){
        firestore.collection("appData")
                .document("home")
                .get()
                .addOnCompleteListener(task->{
                    if(task.isSuccessful()){
                        listener.onLoadSuccess(task.getResult().getData());
                    } else {
                        listener.onLoadFailed(task.getException().getLocalizedMessage());
                    }
                });
    }

    public MutableLiveData<ArrayList<Order>> loadOrders(int limit) {
        if(limit == 0) limit = 999;

        firestore.collection("orders")
                .whereEqualTo("user_uid", Utils.getUid())
                .orderBy("time")
                .limit(limit)
                .addSnapshotListener((value, firebaseFirestoreException) -> {
                    if (value != null) {
                        Log.d("VALUE UPDATED", value.toString());
                    }

                    if (firebaseFirestoreException != null){
                        return;
                    }
                    if(!value.isEmpty()){
                        orders.setValue((ArrayList<Order>) value.toObjects(Order.class));
                    } else {
                        orders.setValue(new ArrayList<>());
                    }
                    Log.d("VALUE END", value.toString());
                });
        return orders;
    }

    public void loadProduct(LoadListener listener) {
        firestore.collection("products")
                .document("water")
                .get()
                .addOnCompleteListener(task ->{
                    if(task.isSuccessful()){
                        if(task.getResult().exists()){
                            try {
                                Product water = task.getResult().toObject(Product.class);
                                listener.onLoadSuccess(water);
                            } catch (Exception e){
                                listener.onLoadFailed(e.getLocalizedMessage());
                            }
                        } else {
                            listener.onLoadFailed("Product does not exist");
                        }
                    } else {
                        listener.onLoadFailed(task.getException().getLocalizedMessage());
                    }
                });
    }

    public void placeNewOrder(Order order, LoadListener loadListener) {
        String orderId = UUID.randomUUID().toString();
        order.setOrderId(orderId);
        firestore.collection("orders")
                .document(orderId)
                .set(order)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        loadListener.onLoadSuccess(orderId);
                    } else {
                        loadListener.onLoadFailed(task.getException().getLocalizedMessage());
                    }
                });
    }

    public void loadReviews(LoadListener listener) {
        firestore.collection("reviews")
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        try {
                            List<Review> reviews = task.getResult().toObjects(Review.class);
                            listener.onLoadSuccess(reviews);
                        } catch (Exception e){
                            listener.onLoadFailed(e.getLocalizedMessage());
                        }
                    } else {
                        listener.onLoadFailed(task.getException().getLocalizedMessage());
                    }
                });
    }
}
