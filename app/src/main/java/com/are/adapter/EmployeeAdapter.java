package com.are.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.are.R;
import com.are.model.Employees;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAdapter extends
        RecyclerView.Adapter<EmployeeAdapter.HorizontalViewHolder> {

    private Context mContext;
    public List<Employees> itemsArrayList = new ArrayList<>();

    public EmployeeAdapter(Context mContext, List<Employees> itemsList) {
        this.mContext = mContext;
        this.itemsArrayList = itemsList;
    }


    @Override
    public EmployeeAdapter.HorizontalViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_employees, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HorizontalViewHolder holder, int position) {
        Employees myEnquiries = itemsArrayList.get(position);
        holder.tvName.setText(myEnquiries.getName());
        holder.tv_mobile.setText(myEnquiries.getMobile());
        holder.tv_email.setText(myEnquiries.getEmail());

    }

    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }

    class HorizontalViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tv_mobile, tv_email;

        public HorizontalViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tv_mobile = itemView.findViewById(R.id.tv_mobile);
            tv_email = itemView.findViewById(R.id.tv_email);

        }
    }
}


