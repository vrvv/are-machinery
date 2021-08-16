package com.are.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.are.MyApp;
import com.are.R;
import com.are.activities.MyItemDetailActivity;
import com.are.model.MyItem;
import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MyItemAdapter extends
        RecyclerView.Adapter<MyItemAdapter.HorizontalViewHolder> {

    private Context mContext;
    public List<MyItem> itemsArrayList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public MyItemAdapter(Context mContext, List<MyItem> itemsList) {
        this.mContext = mContext;
        this.itemsArrayList = itemsList;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener myClickListener) {
        this.onItemClickListener = myClickListener;
    }

    @Override
    public MyItemAdapter.HorizontalViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_my_item, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HorizontalViewHolder holder, int position) {
        MyItem myEnquiries = itemsArrayList.get(position);
        holder.tvName.setText(myEnquiries.getName());
        holder.tv_company_name.setText(MyApp.user.getCompanyName());
        holder.tv_price.setText("\u20B9" + myEnquiries.getUnitPrice());
        if (!myEnquiries.isClosed()) {
            holder.btn_edit.setVisibility(View.VISIBLE);
            holder.btn_close.setAlpha(1f);
            holder.btn_close.setClickable(true);
        } else {
            holder.btn_edit.setVisibility(View.INVISIBLE);
            holder.btn_close.setAlpha(0.5f);
            holder.btn_close.setClickable(false);
        }
        Glide.with(mContext)
                .load("http://aremachinery.com/items" + myEnquiries.getItemImage())
                .placeholder(R.drawable.img_machine)
                .transition(withCrossFade())
                .into(holder.image);

        holder.btn_enquire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MyItemDetailActivity.class);
                intent.putExtra("itemId", myEnquiries);
                mContext.startActivity(intent);
            }
        });

        holder.btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }

    class HorizontalViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tv_company_name, tv_price;
        public AppCompatButton btn_close, btn_edit, btn_enquire;
        public ShapeableImageView image;

        public HorizontalViewHolder(View itemView) {
            super(itemView);
            tv_company_name = itemView.findViewById(R.id.tv_company_name);
            tv_price = itemView.findViewById(R.id.tv_price);
            tvName = itemView.findViewById(R.id.tvName);
            btn_close = itemView.findViewById(R.id.btn_close);
            btn_edit = itemView.findViewById(R.id.btn_edit);
            btn_enquire = itemView.findViewById(R.id.btn_enquire);
            image = itemView.findViewById(R.id.image);

        }
    }
}


