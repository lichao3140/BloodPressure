package com.a1byone.bloodpressure.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a1byone.bloodpressure.R;
import com.a1byone.bloodpressure.bean.RemindModel;
import com.a1byone.bloodpressure.ui.adapter.ItemTouchHelpCallback;
import com.a1byone.bloodpressure.ui.adapter.LCItemDecoration;
import com.a1byone.bloodpressure.ui.adapter.RemindFragmentAdapter;
import com.a1byone.bloodpressure.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 提醒
 * Created by Administrator on 2017-12-07.
 */

public class RemindFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private RemindFragmentAdapter remindFragmentAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_remind, null);
        Bundle bundle = getArguments();
        String email = bundle.getString("email");
        Long userId = bundle.getLong("userId");
        ToastUtil.showShort(getActivity(), email + "  " + userId);

        mRecyclerView = view.findViewById(R.id.remind_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        remindFragmentAdapter = new RemindFragmentAdapter(getActivity());
        mRecyclerView.setAdapter(remindFragmentAdapter);
        mRecyclerView.addItemDecoration(new LCItemDecoration(getActivity()));
        remindFragmentAdapter.updateData(createTestDates());

        ItemTouchHelpCallback callback = new ItemTouchHelpCallback();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        return view;
    }

    private List<RemindModel> createTestDates() {
        List<RemindModel> result = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            RemindModel testModel= new RemindModel(i,":Item Swipe Action Button Container Width");
            if (i == 1) {
                testModel = new RemindModel(i, "Item Swipe with Action container width and no spring");
            }
            if (i == 2) {
                testModel = new RemindModel(i, "Item Swipe with RecyclerView Width");
            }
            result.add(testModel);
        }
        return result;
    }


}
