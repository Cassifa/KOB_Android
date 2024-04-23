package com.example.kob_android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.widget.ViewPager2;

import com.example.kob_android.R;
import com.example.kob_android.adapter.pageAdapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-24  02:01
 * @Description:
 */
public class RecordListFragment extends Fragment {

    @androidx.annotation.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager2 viewPager = view.findViewById(R.id.view_pager);

        // 设置 ViewPager2 的适配器
        FragmentManager fragmentManager = getChildFragmentManager();
        Lifecycle lifecycle = getViewLifecycleOwner().getLifecycle();

        ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentManager, lifecycle);
        viewPager.setAdapter(adapter);

        // 使用 TabLayoutMediator 将 TabLayout 与 ViewPager2 关联
        TabLayoutMediator mediator = new TabLayoutMediator(
                tabLayout,
                viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(TabLayout.Tab tab, int position) {
                        switch (position) {
                            case 0:
                                tab.setText("所有对战");
                                break;
                            case 1:
                                tab.setText("我的回放");
                                break;
                        }
                    }
                });

        mediator.attach(); // 启用关联

        return view;
    }
}
