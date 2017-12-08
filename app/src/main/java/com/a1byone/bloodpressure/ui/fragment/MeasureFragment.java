package com.a1byone.bloodpressure.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a1byone.bloodpressure.R;
import com.a1byone.bloodpressure.ui.adapter.MeasureRecyclerViewAdapter;

/**
 * Created by Administrator on 2017-12-07.
 */

public class MeasureFragment extends BaseFragment {

    private MeasureRecyclerViewAdapter measureRecyclerViewAdapter;
    private RecyclerView rvFragmentMeasure;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_measure, null);
        rvFragmentMeasure = (RecyclerView) view.findViewById(R.id.rv_fragment_measure);

        measureRecyclerViewAdapter = new MeasureRecyclerViewAdapter(getActivity());
        rvFragmentMeasure.setAdapter(measureRecyclerViewAdapter);
        rvFragmentMeasure.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
