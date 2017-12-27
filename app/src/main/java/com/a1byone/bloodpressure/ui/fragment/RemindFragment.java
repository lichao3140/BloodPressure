package com.a1byone.bloodpressure.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a1byone.bloodpressure.utils.ToastUtil;

/**
 * 提醒
 * Created by Administrator on 2017-12-07.
 */

public class RemindFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        String email = bundle.getString("email");
        Long userId = bundle.getLong("userId");
        ToastUtil.showShort(getActivity(), email + "  " + userId);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
