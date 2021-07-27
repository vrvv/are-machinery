package com.are.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.are.R;
import com.are.fragment.ProductFragment;
import com.google.android.material.tabs.TabLayout;

public class MachineryActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private static final String DOG_BREEDS[] = {"Products", "Suppliers"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machinery);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return new ProductFragment();
        }

        @Override
        public int getCount() {
            return DOG_BREEDS.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return DOG_BREEDS[position];
        }
    }
}