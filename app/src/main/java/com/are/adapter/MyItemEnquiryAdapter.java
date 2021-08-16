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
import com.are.model.MyItem;
import com.are.model.MyItemEnquiries;

import java.util.ArrayList;
import java.util.List;

public class MyItemEnquiryAdapter extends
        RecyclerView.Adapter<MyItemEnquiryAdapter.HorizontalViewHolder> {

    private Context mContext;
    public List<MyItemEnquiries> itemsArrayList = new ArrayList<>();

    public MyItemEnquiryAdapter(Context mContext, List<MyItemEnquiries> itemsList) {
        this.mContext = mContext;
        this.itemsArrayList = itemsList;
    }


    @Override
    public MyItemEnquiryAdapter.HorizontalViewHolder onCreateViewHolder(ViewGroup parent,
                                                                        int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_my_item_enquires, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HorizontalViewHolder holder, int position) {
        MyItemEnquiries myEnquiries = itemsArrayList.get(position);
        holder.tvname.setText(myEnquiries.getName());
        holder.tvnumber.setText(myEnquiries.getMobile());
        holder.tv_location.setText(myEnquiries.getLocation());
        holder.tv_price.setText("INR "+myEnquiries.getDesiredPrice());

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
        public TextView tvname,tvnumber,tv_location,tv_price,tv_call;

        public HorizontalViewHolder(View itemView) {
            super(itemView);
            tvnumber = itemView.findViewById(R.id.tvnumber);
            tv_location = itemView.findViewById(R.id.tv_location);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_call = itemView.findViewById(R.id.tv_call);
            tvname = itemView.findViewById(R.id.tvname);

        }
    }
}


