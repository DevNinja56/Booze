package com.my.booze.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.my.booze.R;

import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private CurrentOrdersFragment currentOrdersFragment;
    private PastOrdersFragment pastOrdersFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_orders, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabbed_layout);
        currentOrdersFragment = new CurrentOrdersFragment();
        pastOrdersFragment = new PastOrdersFragment();

        tabLayout.setupWithViewPager(viewPager);
        OrdersFragment.ViewPagerAdapter viewPagerAdapter = new OrdersFragment.ViewPagerAdapter(getActivity().getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(currentOrdersFragment, "CURRENT ORDERS");
        viewPagerAdapter.addFragment(pastOrdersFragment, "PAST ORDERS");
        viewPager.setAdapter(viewPagerAdapter);
        return view;
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentTitle.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }
    }

}