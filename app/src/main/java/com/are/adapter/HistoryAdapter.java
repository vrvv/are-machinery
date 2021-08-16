package com.are.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.are.R;
import com.are.model.MyEnquiries;
import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class HistoryAdapter extends
        RecyclerView.Adapter<HistoryAdapter.HorizontalViewHolder> {

    private Context mContext;
    public List<MyEnquiries> itemsArrayList = new ArrayList<>();

    public HistoryAdapter(Context mContext, List<MyEnquiries> itemsList) {
        this.mContext = mContext;
        this.itemsArrayList = itemsList;
    }


    @Override
    public HistoryAdapter.HorizontalViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_my_items, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HorizontalViewHolder holder, int position) {
        MyEnquiries myEnquiries = itemsArrayList.get(position);
        holder.tvName.setText(myEnquiries.getItemName());
        holder.tv_price.setText("\u20B9" + myEnquiries.getDesiredPrice());
        holder.tvCompanyName.setText(myEnquiries.getCompanyName());
        holder.tv_date.setText(myEnquiries.getEnquiryDate());
        Glide.with(mContext)
                .load("http://aremachinery.com/items/images/" + myEnquiries.getItemImage())
                .placeholder(R.drawable.img_machine)
                .transition(withCrossFade())
                .into(holder.image);

        holder.tv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + myEnquiries.getMobile()));
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class HorizontalViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName,tv_call,tv_price,tvCompanyName,tv_date;
        public ShapeableImageView image;

        public HorizontalViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tv_call = itemView.findViewById(R.id.tv_call);
            tv_price = itemView.findViewById(R.id.tv_price);
            tvCompanyName = itemView.findViewById(R.id.tvCompanyName);
            tv_date = itemView.findViewById(R.id.tv_date);
            image = itemView.findViewById(R.id.image);

        }
    }
}


