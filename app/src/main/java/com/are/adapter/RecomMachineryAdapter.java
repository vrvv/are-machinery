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
import com.are.activities.MachineryActivity;
import com.are.model.Items;

import java.util.ArrayList;
import java.util.List;

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
        if (items.isCompanyVerified()) {
            holder.imgVerified.setVisibility(View.VISIBLE);
        } else {
            holder.imgVerified.setVisibility(View.INVISIBLE);

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MachineryActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class HorizontalViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUnitPrice;
        public ImageView imgVerified;

        public HorizontalViewHolder(View itemView) {
            super(itemView);
            tvUnitPrice = itemView.findViewById(R.id.tvUnitPrice);
            imgVerified = itemView.findViewById(R.id.imgVerified);

        }
    }
}


