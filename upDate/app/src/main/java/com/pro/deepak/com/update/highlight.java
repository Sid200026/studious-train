package com.pro.deepak.com.update;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class highlight extends Fragment {

    private FragmentActivity myContext;

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_highlight, container, false);

        FragmentManager fragManager = myContext.getSupportFragmentManager();

        TabLayout tabLayout = rootView.findViewById(R.id.tabs_highlight);
        tabLayout.addTab(tabLayout.newTab().setText("College"));
        tabLayout.addTab(tabLayout.newTab().setText("Co-Curricular"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        final PagerAdaptor adapter = new PagerAdaptor
                (fragManager, tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //tab.getIcon().setColorFilter(Color.parseColor("#a8a8a8"), PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {


            }
        });

        return rootView;
    }
    public class PagerAdaptor extends FragmentStatePagerAdapter {
        int mNumOfTabs;


        public PagerAdaptor(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;

        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    highlight_academic academic_tab = new highlight_academic();
                    return academic_tab;
                case 1:
                    highlight_cocurr co_curr_tab = new highlight_cocurr();
                    return co_curr_tab;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }



    }
}

