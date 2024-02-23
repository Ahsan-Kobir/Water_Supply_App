package com.akapps.paani.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akapps.paani.Model.Order;
import com.akapps.paani.R;
import com.akapps.paani.Utils.Utils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecentOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Order> orderArrayList;
    private boolean showShimmer = true;

    public RecentOrderAdapter(ArrayList<Order> orderArrayList){
        this.orderArrayList = orderArrayList;
    }
    public void updateDataSource(ArrayList<Order> list){
        showShimmer = false;
        this.orderArrayList = list;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==0){
            return new ShimmerHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_recent_order_shimmer, parent, false));
        }
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recent_order, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        if(showShimmer){
            return 0;
        }
        return 1;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){
            ((ViewHolder) holder).setData(orderArrayList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    public class ShimmerHolder extends RecyclerView.ViewHolder{

        public ShimmerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, price, time;
        ImageView photo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.order_title);
            price = itemView.findViewById(R.id.order_price);
            time = itemView.findViewById(R.id.order_time);
            photo = itemView.findViewById(R.id.order_photo);
        }

        @SuppressLint("SetTextI18n")
        public void setData(Order order) {
            try {
                price.setText(order.getTotalPrice());
                time.setText("Ordered On: " + Utils.getTimeInString(order.getTime()));
                title.setText(order.getProductList().get(0).getTitle());
                Glide.with(itemView).load(order.getProductList().get(0).getProductPhotos().get(0)).into(photo);
            } catch (Exception ignored){

            }
        }
    }
}
