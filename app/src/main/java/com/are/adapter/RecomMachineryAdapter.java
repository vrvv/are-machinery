package com.are.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.are.R;
import com.are.activities.ProductDetailActivity;
import com.are.model.Items;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class RecomMachineryAdapter extends
        RecyclerView.Adapter<RecomMachineryAdapter.HorizontalViewHolder> {

    private Context mContext;
    public List<Items> itemsArrayList = new ArrayList<>();


    public RecomMachineryAdapter(Context mContext, List<Items> itemsList) {
        this.mContext = mContext;
        this.itemsArrayList = itemsList;
    }


    @Override
    public RecomMachineryAdapter.HorizontalViewHolder onCreateViewHolder(ViewGroup parent,
                                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_recommended_machinary, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HorizontalViewHolder holder, int position) {
        Items items = itemsArrayList.get(position);
        holder.tvUnitPrice.setText(items.getUnitPrice() + "");
        holder.tvName.setText(items.getName());
        holder.tvCompanyName.setText(items.getCompanyName());
        if (items.isCompanyVerified()) {
            holder.imgVerified.setVisibility(View.VISIBLE);
        } else {
            holder.imgVerified.setVisibility(View.INVISIBLE);

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                intent.putExtra("itemId", items);
                mContext.startActivity(intent);
            }
        });
        Glide.with(mContext)
                .load("http://aremachinery.com/items/images/" + items.getItemImage())
                .placeholder(R.drawable.img_machine)
                .transition(withCrossFade())
                .into(holder.imgItemImage);

    }

    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }

    class HorizontalViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUnitPrice, tvName, tvCompanyName;
        public ImageView imgVerified, imgItemImage;

        public HorizontalViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvCompanyName = itemView.findViewById(R.id.tvCompanyName);
            tvUnitPrice = itemView.findViewById(R.id.tvUnitPrice);
            imgVerified = itemView.findViewById(R.id.imgVerified);
            imgItemImage = itemView.findViewById(R.id.imgItemImage);

        }
    }
}


