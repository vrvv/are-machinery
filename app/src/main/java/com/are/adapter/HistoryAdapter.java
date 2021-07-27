package com.are.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.are.R;

public class HistoryAdapter extends
        RecyclerView.Adapter<HistoryAdapter.HorizontalViewHolder> {

    private Context mContext;

    public HistoryAdapter(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public HistoryAdapter.HorizontalViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_history, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HorizontalViewHolder holder, int position) {


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


