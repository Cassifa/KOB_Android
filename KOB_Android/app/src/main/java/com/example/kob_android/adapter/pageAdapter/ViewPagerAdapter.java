package com.example.kob_android.adapter.pageAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.kob_android.fragment.subFragment.AllRecordFragment;
import com.example.kob_android.fragment.subFragment.MyRecordFragment;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-24  03:19
 * @Description:
 */
public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new AllRecordFragment(); // "所有对战"
            case 1:
                return new MyRecordFragment(); // "我的回放"
            default:
                throw new IllegalArgumentException("Unexpected position: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return 2; // 固定的两个页面
    }
}