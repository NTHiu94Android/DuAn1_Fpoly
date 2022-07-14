package com.example.duan1_nhom1.adapterViewPager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.duan1_nhom1.fragment.CartFargment;
import com.example.duan1_nhom1.fragment.HomeFragment;
import com.example.duan1_nhom1.fragment.ProfileFragment;
import com.example.duan1_nhom1.fragment.SettingFragment;
import com.example.duan1_nhom1.fragment.SupportFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new ProfileFragment();
            case 2:
                return new CartFargment();
            case 3:
                return new SupportFragment();
            case 4:
                return new SettingFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
