package com.a1byone.bloodpressure.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a1byone.bloodpressure.R;
import com.a1byone.bloodpressure.utils.ToastUtil;

/**
 * Created by Administrator on 2017-12-07.
 */

public class HistoryFragment extends BaseFragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    MyAdapter myAdapter;

    private final String[] titels = { "图表", "列表", "活动", "地点", "心率"};
    private int[] drawbles= {};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_hostory, null);
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        Bundle bundle = getArguments();
        String email = bundle.getString("email");
        ToastUtil.showShort(getActivity(), email);
        return view;
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new ImageFrament();
            Bundle bundle = new Bundle();
            bundle.putInt("id", drawbles[position]);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return titels.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titels[position];
        }
    }
}
