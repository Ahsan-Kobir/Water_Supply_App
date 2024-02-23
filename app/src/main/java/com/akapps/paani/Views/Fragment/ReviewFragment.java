package com.akapps.paani.Views.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akapps.paani.Adapters.ReviewAdapter;
import com.akapps.paani.Callback.LoadListener;
import com.akapps.paani.Firebase.FirestoreClient;
import com.akapps.paani.Model.Review;
import com.akapps.paani.R;
import com.akapps.paani.Views.LoadingDialog;
import com.akapps.paani.databinding.FragmentReviewBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ReviewFragment extends Fragment {

    FragmentReviewBinding views;
    private FirestoreClient firestoreClient;
    private LoadingDialog loadingDialog;
    private ReviewAdapter reviewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        views = FragmentReviewBinding.inflate(getLayoutInflater());
        return views.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firestoreClient = new FirestoreClient(FirebaseFirestore.getInstance());
        loadReviews();
    }

    private void loadReviews() {
        loadingDialog = new LoadingDialog(requireContext());
        loadingDialog.show();
        firestoreClient.loadReviews(new LoadListener() {
            @Override
            public void onLoadSuccess(Object result) {
                loadingDialog.dismiss();
                ArrayList<Review> reviews = (ArrayList<Review>) result;
                setList(reviews);
            }

            @Override
            public void onLoadFailed(String error) {
                loadingDialog.dismiss();
                Snackbar.make(views.getRoot(), error, BaseTransientBottomBar.LENGTH_LONG);
            }
        });
    }

    private void setList(ArrayList<Review> reviews) {
        views.reviewListRv.setLayoutManager(new LinearLayoutManager(requireContext()));
        reviewAdapter = new ReviewAdapter(reviews);
        views.reviewListRv.setAdapter(reviewAdapter);
    }
}