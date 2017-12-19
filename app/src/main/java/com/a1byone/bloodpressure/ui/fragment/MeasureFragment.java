package com.a1byone.bloodpressure.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.a1byone.bloodpressure.R;
import com.a1byone.bloodpressure.utils.ToastUtil;
import com.lifesense.ble.LsBleManager;
import com.lifesense.ble.PairCallback;
import com.lifesense.ble.ReceiveDataCallback;
import com.lifesense.ble.SearchCallback;
import com.lifesense.ble.bean.BloodPressureData;
import com.lifesense.ble.bean.LsDeviceInfo;
import com.lifesense.ble.bean.constant.BroadcastType;
import com.lifesense.ble.bean.constant.DeviceConnectState;
import com.lifesense.ble.bean.constant.DeviceType;
import com.lifesense.ble.bean.constant.ProtocolType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 血压测量
 * Created by Administrator on 2017-12-07.
 */

public class MeasureFragment extends BaseFragment {
    private static final String TAG = "lichao";
    private boolean isScanning;//是否搜索蓝牙
    private List<DeviceType> mScanDeviceType;//扫描的设备类型
    private BroadcastType mBroadcastType;//广播类型
    private boolean isConnect;//是否连接成功
    private LsBleManager mlsBleManager;
    private LsDeviceInfo currentDevice;

    @BindView(R.id.iv_measure_connect)
    ImageView ivMeasureConnect;
    @BindView(R.id.tv_connect_status)
    TextView tvConnectStatus;
    @BindView(R.id.bt_start_sync)
    Button btStartSync;
    Unbinder unbinder;

    private SearchCallback mSearchCallback = new SearchCallback() {
        @Override
        public void onSearchResults(LsDeviceInfo lsDeviceInfo) {
            handleScanResults(lsDeviceInfo);
        }
    };

    protected void handleScanResults(LsDeviceInfo lsDeviceInfo) {
        if (lsDeviceInfo == null) {
            return;
        }
        currentDevice = lsDeviceInfo;
        LsBleManager.getInstance().stopSearch();//停止搜索，进行配对
        if (ProtocolType.A3.toString().equalsIgnoreCase(lsDeviceInfo.getProtocolType())) {
            if (lsDeviceInfo.getPairStatus() == 1) {
                //直接配对设备
                LsBleManager.getInstance().pairingWithDevice(lsDeviceInfo, mPairCallback);
                Log.e(TAG, "进行蓝牙配对");
            }
        } else {
            //已经配对过的设备
            lsDeviceInfo.setDeviceId(lsDeviceInfo.getMacAddress().replace(":", ""));
            saveDeviceInfo(lsDeviceInfo);
            Log.e(TAG, "已经配对过的设备");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mlsBleManager = LsBleManager.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_measure, null);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @OnClick({R.id.iv_measure_connect, R.id.bt_start_sync})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_measure_connect:
                Log.e(TAG, "搜索蓝牙");
                searchBloodPressure();
                break;
            case R.id.bt_start_sync:
                if (!mlsBleManager.isSupportLowEnergy()) {
                    ToastUtil.showShort(getActivity(), "Not support Bluetooth Low Energy");
                }
                if (!mlsBleManager.isOpenBluetooth()) {
                    ToastUtil.showShort(getActivity(), "Please turn on Bluetooth");
                }
                if (!isConnect && btStartSync.getText().equals("start sync")) {
                    mlsBleManager.stopDataReceiveService();
                    mlsBleManager.setMeasureDevice(null);
                    mlsBleManager.addMeasureDevice(currentDevice);
                    mlsBleManager.startDataReceiveService(mDataCallback);
                    Log.e(TAG, "start sync:" + currentDevice.getMacAddress());
                }
                break;
        }
    }

    private void searchBloodPressure() {
        if (!LsBleManager.getInstance().isSupportLowEnergy()) {
            ToastUtil.showShort(getActivity(), "Not support Bluetooth Low Energy");
        }
        if (!LsBleManager.getInstance().isOpenBluetooth()) {
            ToastUtil.showShort(getActivity(), "Please turn on Bluetooth");
        } else {
            List<DeviceType> scanDeviceType = new ArrayList<DeviceType>();
            scanDeviceType.add(DeviceType.SPHYGMOMANOMETER);
            LsBleManager.getInstance().searchLsDevice(mSearchCallback, scanDeviceType, BroadcastType.ALL);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //用户信息配对回调
    private PairCallback mPairCallback = new PairCallback() {

        @Override
        public void onDiscoverUserInfo(String macAddress, final List userList) {
            if (getActivity() == null) {
                return;
            }
            int userNumber = 1;
            String userName = "sky";
            LsBleManager.getInstance().bindDeviceUser(macAddress, userNumber, userName);
            Log.i(TAG, "MAC:" + macAddress);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (userList == null || userList.size() == 0) {
                        ToastUtil.showShort(getActivity(), "failed to pairing devie,user list is null...");
                        return;
                    }
                }
            });
        }

        @Override
        public void onPairResults(final LsDeviceInfo lsDeviceInfo, final int status) {
            if (getActivity() == null) {
                return;
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (lsDeviceInfo != null && status == 0) {
                        saveDeviceInfo(lsDeviceInfo);
                        ivMeasureConnect.setImageResource(R.drawable.measure_peidui_success);
                        tvConnectStatus.setText("设备连接成功");
                        Log.e(TAG, "Pairing Success");
                    } else {
                        ivMeasureConnect.setImageResource(R.drawable.measure_peidui_fail);
                        tvConnectStatus.setText("设备连接失败");
                        ToastUtil.showShort(getActivity(), "Pairing failed, please try again");
                    }
                }
            });
        }
    };

    private void saveDeviceInfo(LsDeviceInfo lsDeviceInfo) {
        String boradcastId = lsDeviceInfo.getBroadcastID();
        String macAddress = lsDeviceInfo.getMacAddress();
        String deviceType = lsDeviceInfo.getDeviceType();
        String deviceName = lsDeviceInfo.getDeviceName();
        String protocolType = lsDeviceInfo.getProtocolType();

        if (ProtocolType.A3.toString().equalsIgnoreCase(lsDeviceInfo.getProtocolType())) {
            String deviceId = lsDeviceInfo.getDeviceId();
            String password = lsDeviceInfo.getPassword();
        }
    }

    //接受蓝牙数据回调
    private ReceiveDataCallback mDataCallback = new ReceiveDataCallback() {

        @Override
        public void onDeviceConnectStateChange(DeviceConnectState deviceConnectState, String broadcastId) {
            updateDeviceConnectState(deviceConnectState);
        }

        @Override
        public void onReceiveBloodPressureData(BloodPressureData bloodPressureData) {
            //接受血压测量数据
            Log.i(TAG, "接受血压测量数据:");
            Log.i(TAG, formatStringValue(bloodPressureData.toString()));
        }
    };

    /**
     * 更新设备的连接状态信息
     *
     * @param connectState
     */
    private void updateDeviceConnectState(final DeviceConnectState connectState) {
        if (getActivity() == null || DeviceConnectState.CONNECTING == connectState) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String tempState = "unknown";
                if (connectState == DeviceConnectState.CONNECTED_SUCCESS) {
                    isConnect = true;
                    tempState = "Connect Success";
                } else if (connectState == DeviceConnectState.CONNECTED_FAILED) {
                    isConnect = false;
                    tempState = "Connect Failed";
                } else if (connectState == DeviceConnectState.DISCONNECTED) {
                    isConnect = false;
                    tempState = "DisConnect";
                }
                tvConnectStatus.setText(tempState);
            }
        });
    }

    /**
     * 格式化字符串信息
     *
     * @param message
     * @return
     */
    private String formatStringValue(String message) {
        String tempMessage = message.replace("[", "\r\n");
        tempMessage = tempMessage.replace(",", ",\r\n");
        tempMessage = tempMessage.replace("]", "\r\n");
        return tempMessage;
    }
}
