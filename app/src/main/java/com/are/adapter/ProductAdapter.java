package com.are.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.are.R;
import com.are.activities.ProductDetailActivity;

public class ProductAdapter extends
        RecyclerView.Adapter<ProductAdapter.HorizontalViewHolder> {

    private Context mContext;

    public ProductAdapter(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public ProductAdapter.HorizontalViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_products, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HorizontalViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class HorizontalViewHolder extends RecyclerView.ViewHolder {
        public HorizontalViewHolder(View itemView) {
            super(itemView);

        }
    }
}


