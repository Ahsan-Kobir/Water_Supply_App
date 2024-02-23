package com.akapps.paani.Views.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.window.OnBackInvokedDispatcher;

import com.akapps.paani.R;
import com.akapps.paani.Views.Fragment.HomeFragment;
import com.akapps.paani.Views.Fragment.ProfileFragment;
import com.akapps.paani.Views.Fragment.ReviewFragment;
import com.akapps.paani.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding views;
    private FragmentManager fm;
    public static final String HOME_TAG = "home";
    public static final String PROFILE_TAG = "profile";
    public static final String REVIEW_TAG = "review";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        views = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(views.getRoot());

        fm = getSupportFragmentManager();
        loadFragment(new HomeFragment(), HOME_TAG, false);
        handleBottomNav();
    }

    private void handleBottomNav() {
        views.mainBottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if( itemId == R.id.menu_home){
                loadFragment(new HomeFragment(), HOME_TAG, true);
                return true;
            } else if ( itemId == R.id.menu_place_order){
                startActivity(new Intent(this, PlaceOrderActivity.class));
                return false;
            } else if ( itemId == R.id.menu_reviews){
                loadFragment(new ReviewFragment(), REVIEW_TAG, true);
                return true;
            } else if ( itemId == R.id.menu_profile) {
                loadFragment(new ProfileFragment(), PROFILE_TAG, true);
                return true;
            }
            return false;
        });
    }

    private void loadFragment(Fragment fragment, String tag, boolean replace) {
        FragmentTransaction transaction = fm.beginTransaction();
        if(replace)
            transaction.replace(R.id.main_fragment_container, fragment, tag);
        else
            transaction.replace(R.id.main_fragment_container, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();
    }
}