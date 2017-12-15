package com.a1byone.bloodpressure.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.a1byone.bloodpressure.R;
import com.a1byone.bloodpressure.utils.ToastUtil;
import com.lifesense.ble.LsBleManager;
import com.lifesense.ble.SearchCallback;
import com.lifesense.ble.bean.LsDeviceInfo;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017-12-07.
 */

public class MeasureFragment extends BaseFragment {
    private static final String TAG = "lichao";
    private boolean isScanning;

    @BindView(R.id.iv_measure_connect)
    ImageView ivMeasureConnect;
    Unbinder unbinder;

    private SearchCallback mSearchCallback = new SearchCallback() {
        @Override
        public void onSearchResults(LsDeviceInfo lsDeviceInfo) {
            updateScanResults(lsDeviceInfo);
        }
    };

    protected void updateScanResults(final LsDeviceInfo lsDeviceInfo) {
        if (lsDeviceInfo == null || getActivity() == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_measure, null);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @OnClick({R.id.iv_measure_connect})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_measure_connect:
                ToastUtil.showShort(getActivity(), "搜索");
                Log.i(TAG, "搜索");
                searchBloodPressure();
                break;
        }
    }

    private void searchBloodPressure() {
        if (!LsBleManager.getInstance().isSupportLowEnergy()) {
            Log.i(TAG, "Not support Bluetooth Low Energy");
        }
        if (!LsBleManager.getInstance().isOpenBluetooth()) {
            Log.i(TAG, "Please turn on Bluetooth");
        } else {
            isScanning=LsBleManager.getInstance().searchLsDevice(mSearchCallback, null, null);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
