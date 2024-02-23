package com.akapps.paani.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akapps.paani.Model.Review;
import com.akapps.paani.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private ArrayList<Review> reviewArrayList = new ArrayList<>();
    public ReviewAdapter(ArrayList<Review> reviews){
        this.reviewArrayList = reviews;
    }
    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder holder, int position) {
        holder.setData(reviewArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return reviewArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, time, message;
        ImageView photo;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemReview_name);
            time = itemView.findViewById(R.id.itemReview_time);
            message = itemView.findViewById(R.id.itemReview_review_message);
            photo = itemView.findViewById(R.id.itemReview_profile);
            ratingBar = itemView.findViewById(R.id.itemReview_ratingbar);
        }

        public void setData(Review review) {
            name.setText(review.getUsername());
            time.setText(review.getTime());
            message.setText(review.getMessage());

            ratingBar.setRating(review.getStarCount());

            Glide.with(itemView).load(review.getUser_profile()).into(photo);
        }
    }
}
