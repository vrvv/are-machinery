package com.are.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.are.R;
import com.are.adapter.HistoryAdapter;
import com.are.adapter.RecomMachineryAdapter;

public class HistoryActivity extends AppCompatActivity {
    public HistoryActivity instance;
    public RecyclerView rvRecomMachinery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        instance = this;
        rvRecomMachinery = findViewById(R.id.rvRecomMachinery);
        HistoryAdapter itemListDataAdapter =
                new HistoryAdapter(instance);
        rvRecomMachinery.setHasFixedSize(true);
        rvRecomMachinery.setLayoutManager(new LinearLayoutManager(instance));
        rvRecomMachinery.setAdapter(itemListDataAdapter);

        rvRecomMachinery.setNestedScrollingEnabled(false);
    }
}