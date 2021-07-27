package com.are.drawer;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

import com.are.MyApp;
import com.are.R;

/**
 * Created by empiere-vaibhav on 8/9/2018.
 */

public class FragmentDrawer extends Fragment {

    private ActionBarDrawerToggle mDrawerToggle;
    public DrawerLayout mDrawerLayout;
    public View containerView;
    public LinearLayout lin_add_vendor, lin_change_pass, lin_contact_us, lin_rate, lin_share, lin_about;
    public TextView txtLogout, nav_username, nav_number;
    public PackageInfo packageInfo;
    public int isActive = 0;
    public ImageView img_edit, nav_img_close, img_fb, img_site;
    public TextView tv_shop;
    public MyApp app;

    public RelativeLayout rel_user;

    public FragmentDrawer() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        return layout;
    }



    public void setUp(int fragmentId, DrawerLayout drawerLayout, ImageView drawerButton) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        drawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT))
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                else
                    mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
//                toolbar.setAlpha(1 - slideOffset / 2);
                mDrawerLayout.bringChildToFront(drawerView);
                mDrawerLayout.requestLayout();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

}

