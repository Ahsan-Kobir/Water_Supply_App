package com.akapps.paani.Views.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.akapps.paani.Api.ApiRepo;
import com.akapps.paani.Api.CallType;
import com.akapps.paani.Api.LoginRepo;
import com.akapps.paani.Callback.LoadListener;
import com.akapps.paani.Model.RequestModels.ApiRequestBody;
import com.akapps.paani.Utils.RequestHandler;
import com.akapps.paani.Utils.SharedPref;
import com.akapps.paani.databinding.HomeFragmentBinding;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;

import retrofit2.Retrofit;

public class HomeFragment extends Fragment {

    private ApiRepo apiRepo;
    private SharedPref sharedPref;
    ShimmerDrawable shimmerDrawable;
    HomeFragmentBinding views;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        views = HomeFragmentBinding.inflate(inflater);
        return views.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPref = new SharedPref(getContext());
        RequestHandler requestHandler = RequestHandler.getInstance();
        apiRepo = new ApiRepo(sharedPref, requestHandler);

        setShimmerEffect();

        loadBanner();
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

    private void loadBanner() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("uid", sharedPref.getUid());
        ApiRequestBody apiRequestBody = new ApiRequestBody(CallType.BANNER.name(), hashMap);

        apiRepo.postRequest(apiRequestBody, new LoadListener() {
            @Override
            public void onLoadSuccess(Object result) {
                showBanner(result.toString());
            }
            @Override
            public void onLoadFailed(String error) {
                Log.e("HomeFragment", error);
                Snackbar.make(views.homeBannerImage, error, BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }

    private void showBanner(String bannerUrl){
        if(isDetached() || isRemoving() || getContext() == null){
            Log.d("HOmeFragment", "Banner loaded after fragment detached");
            return;
        }
        Glide.with(getContext()).load(bannerUrl).placeholder(shimmerDrawable).into(views.homeBannerImage);
    }


}
