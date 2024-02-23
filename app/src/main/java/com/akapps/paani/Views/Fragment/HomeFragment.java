package com.akapps.paani.Views.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.akapps.paani.Adapters.RecentOrderAdapter;
import com.akapps.paani.Api.ApiRepo;
import com.akapps.paani.Api.CallType;
import com.akapps.paani.Api.LoginRepo;
import com.akapps.paani.Callback.LoadListener;
import com.akapps.paani.Firebase.FirestoreClient;
import com.akapps.paani.Model.Order;
import com.akapps.paani.Model.RequestModels.ApiRequestBody;
import com.akapps.paani.Utils.RequestHandler;
import com.akapps.paani.Utils.SharedPref;
import com.akapps.paani.Views.Activity.RecentOrderActivity;
import com.akapps.paani.databinding.HomeFragmentBinding;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Retrofit;

public class HomeFragment extends Fragment {

//    private ApiRepo apiRepo;
//    private SharedPref sharedPref;

    private FirestoreClient firestoreClient;
    ShimmerDrawable shimmerDrawable;
    HomeFragmentBinding views;

    private RecentOrderAdapter recentOrderAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        views = HomeFragmentBinding.inflate(inflater);
        return views.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        sharedPref = new SharedPref(requireContext());
//        RequestHandler requestHandler = RequestHandler.getInstance();
//        apiRepo = new ApiRepo(sharedPref, requestHandler);
        firestoreClient = new FirestoreClient(FirebaseFirestore.getInstance());

        ArrayList<Order> dummyShimmerList = generateDummies();

        recentOrderAdapter = new RecentOrderAdapter(dummyShimmerList);
        views.homeRecentOrderListRV.setLayoutManager(new LinearLayoutManager(requireContext()));
        views.homeRecentOrderListRV.setAdapter(recentOrderAdapter);

        setShimmerEffect();

       // loadBanner();
        loadHome();
        loadOrders();

        views.homeViewAllOrderText.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), RecentOrderActivity.class));
        });
    }

    private ArrayList<Order> generateDummies() {
        ArrayList<Order> orders = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            orders.add(null);
        }
        return orders;
    }

    private void loadOrders() {

            firestoreClient.loadOrders(6).observe(getViewLifecycleOwner(), orders -> {
                Log.d("Refreshed", "It has been refreshed + "+ orders.size());
                if(orders.isEmpty()){
                    views.viewAllOrder.setVisibility(View.GONE);
                    views.noOrderViews.setVisibility(View.VISIBLE);
                } else {
                    views.viewAllOrder.setVisibility(View.VISIBLE);
                    views.noOrderViews.setVisibility(View.GONE);
                }
                recentOrderAdapter.updateDataSource(orders);
                recentOrderAdapter.notifyDataSetChanged();
                views.homeRecentOrderListRV.scrollToPosition(0);
            });
    }

    private void loadHome() {
        firestoreClient.loadHomeData(new LoadListener() {
            @Override
            public void onLoadSuccess(Object result) {
                Map<String, String> map = (Map) result;
                showBanner(map.get("banner"));
            }

            @Override
            public void onLoadFailed(String error) {

            }
        });
    }

    private void setShimmerEffect() {
        Shimmer shimmer = new Shimmer.AlphaHighlightBuilder()
                .setDuration(1800)
                .setBaseAlpha(0.9f)
                .setHighlightAlpha(0.6f)
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build();
        shimmerDrawable = new ShimmerDrawable();
        shimmerDrawable.setShimmer(shimmer);
        views.homeBannerImage.setImageDrawable(shimmerDrawable);
    }

//    private void loadBanner() {
//        //this method was for old backend
//        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("uid", sharedPref.getUid());
//        ApiRequestBody apiRequestBody = new ApiRequestBody(CallType.BANNER.name(), hashMap);
//
//        apiRepo.postRequest(apiRequestBody, new LoadListener() {
//            @Override
//            public void onLoadSuccess(Object result) {
//                showBanner(result.toString());
//            }
//            @Override
//            public void onLoadFailed(String error) {
//                Log.e("HomeFragment", error);
//                Snackbar.make(views.homeBannerImage, error, BaseTransientBottomBar.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void showBanner(String bannerUrl){
        if(isDetached() || isRemoving() || getContext() == null || bannerUrl == null){
            Log.d("HOmeFragment", "Banner loaded after fragment detached");
            return;
        }
        Glide.with(getContext()).load(bannerUrl).placeholder(shimmerDrawable).into(views.homeBannerImage);
    }


}
