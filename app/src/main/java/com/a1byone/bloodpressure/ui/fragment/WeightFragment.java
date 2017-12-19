package com.a1byone.bloodpressure.ui.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a1byone.bloodpressure.R;
import com.a1byone.bloodpressure.bean.BluetoothLeDevice;
import com.a1byone.bloodpressure.bean.Records;
import com.a1byone.bloodpressure.service.BluetoothLeScannerInterface;
import com.a1byone.bloodpressure.service.BluetoothLeService;
import com.a1byone.bloodpressure.utils.BLEConstant;
import com.a1byone.bloodpressure.utils.BleHelper;
import com.a1byone.bloodpressure.utils.BlueToolUtil;
import com.a1byone.bloodpressure.utils.BluetoothUtils;
import com.a1byone.bloodpressure.utils.ToastUtil;
import com.a1byone.bloodpressure.utils.Units;
import com.zhl.cbdialog.CBDialogBuilder;

/**
 * 体重测量
 * Created by Administrator on 2017-12-07.
 */

public class WeightFragment extends BaseFragment {
    private static final String TAG = "lichao";

    private BluetoothUtils mBluetoothUtils;
    private BluetoothLeScannerInterface mScanner;
    private BluetoothLeService mBluetoothLeService;
    private String mDeviceAddress;
    private String mDeviceName;
    private Handler scanHandler;
    private boolean mConnected = false;
    private boolean mActivity = false; //页面是否激活
    protected static final int REQUEST_ACCESS_COARSE_LOCATION_PERMISSION = 101;

    private ImageView ivWeightIcon;
    private TextView tvWeightName;
    private TextView tvWeight;
    private TextView tvFat;
    private TextView tvMuscle;
    private TextView tvWater;
    private TextView tvBones;
    private TextView tvVisceralFat;
    private TextView tvBmr;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_weight, null);
        mBluetoothUtils = new BluetoothUtils(getActivity());
        if (mBluetoothUtils.isBluetoothLeSupported()) {
            scanHandler = new Handler();
            mScanner = mBluetoothUtils.initBleScaner(notifyHandler);

            //注册通知
            getActivity().registerReceiver(mGattUpdateReceiver, BluetoothUtils.makeGattUpdateIntentFilter());

            //绑定蓝牙服务服务
            final Intent gattServiceIntent = new Intent(getActivity(), BluetoothLeService.class);
            getActivity().bindService(gattServiceIntent, mServiceConnection, getActivity().BIND_AUTO_CREATE);

            if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermission(Manifest.permission.ACCESS_COARSE_LOCATION,
                        getString(R.string.permission_bluetooth),
                        REQUEST_ACCESS_COARSE_LOCATION_PERMISSION);
            } else {
                //启动扫描
                scanHandler.post(scanThread);
            }
        } else {
            ToastUtil.showShort(getActivity(), "该设备不支持BLE");
            getActivity().finish();
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ivWeightIcon = (ImageView) getView().findViewById(R.id.iv_weight_icon);
        tvWeightName = (TextView) getView().findViewById(R.id.tv_weight_name);

        tvWeight = (TextView) getView().findViewById(R.id.tv_weight);
        tvFat = (TextView) getView().findViewById(R.id.tv_fat);
        tvMuscle = (TextView) getView().findViewById(R.id.tv_muscle);
        tvWater = (TextView) getView().findViewById(R.id.tv_water);
        tvBones = (TextView) getView().findViewById(R.id.tv_bones);
        tvVisceralFat = (TextView) getView().findViewById(R.id.tv_visceral_fat);
        tvBmr = (TextView) getView().findViewById(R.id.tv_bmr);
    }

    /**
     * 扫描线程
     */
    private Runnable scanThread = new Runnable() {
        public void run() {
            // 线程所干的事情
            startScan();
            //十秒后再重复工作
            scanHandler.postDelayed(scanThread, 10000);
        }
    };

    /**
     * 开始扫描蓝牙
     */
    private void startScan() {
        final boolean mIsBluetoothOn = mBluetoothUtils.isBluetoothOn();
        final boolean mIsBluetoothLePresent = mBluetoothUtils.isBluetoothLeSupported();
        mBluetoothUtils.askUserToEnableBluetoothIfNeeded();
        if (mIsBluetoothOn && mIsBluetoothLePresent && mActivity) {//页面激活的状态下才真正扫描
            mScanner.scanLeDevice(8000, true);
        }
    }

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                getActivity().finish();
            }
            Log.e(TAG, "开始连接蓝牙.......");
            if (!TextUtils.isEmpty(mDeviceAddress)) mBluetoothLeService.connect(mDeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    /**
     * 发现蓝牙通知界面相应
     */
    Handler notifyHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BlueToolUtil.FOUND_DEVICE:
                    Log.i(TAG, "[蓝牙连接状态]==" + (null == mBluetoothLeService ? "未初始化" : mBluetoothLeService.getmConnectionState()));
                    BluetoothLeDevice deviceLe = (BluetoothLeDevice) msg.obj;
                    if (null != deviceLe && null != mBluetoothLeService && mBluetoothLeService.getmConnectionState() == BluetoothLeService.STATE_DISCONNECTED) {
                        mDeviceAddress = deviceLe.getAddress();
                        mDeviceName = deviceLe.getName();

                        if (!TextUtils.isEmpty(mDeviceAddress))
                            mBluetoothLeService.connect(mDeviceAddress);
                    }
                    break;

                case BlueToolUtil.RECEIVE_DATA: //接收到数据
                    String data = (String) msg.obj;
                    Records records = null;
                    if (data.startsWith("cf")) {
                        records = BleHelper.getInstance().parseDLScaleMeaage(data, 170f, 1, 26, 0);
                    } else if (data.startsWith("0306")) {
                        records = BleHelper.getInstance().parseScaleData(data, 170d, 1, 26, 0);
                    }
                    Log.i(TAG, "records:" + records.toString());
                    tvWeight.setText(records.getRweight() + "kg");
                    tvVisceralFat.setText(records.getRbodyfat() + "%");
                    tvMuscle.setText(records.getRmuscle() + "kg");
                    tvWater.setText(records.getRbodywater() + "%");
                    tvBones.setText(records.getRbone() + "kg");
                    tvFat.setText(records.getRvisceralfat() + "");
                    tvBmr.setText(records.getRbmr() + "Kcal");
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BLEConstant.ACTION_GATT_CONNECTED.equals(action)) { //蓝牙连接了
                mConnected = true;
                Log.e(TAG, "蓝牙已连接");
                updateConnectionState(R.string.connected);
            } else if (BLEConstant.ACTION_GATT_DISCONNECTED.equals(action)) {//蓝牙断开连接
                mConnected = false;
                Log.e(TAG, "蓝牙断开");
                updateConnectionState(R.string.disconnected);

            } else if (BLEConstant.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {//发现蓝牙服务
                // Show all the supported services and characteristics on the user interface.
                Log.e(TAG, "发现服务>>>>>>");
                if (null != mBluetoothLeService) {
                    if (null != mDeviceName) {
                        if (mDeviceName.toLowerCase().startsWith("heal")) {
                            // 监听 阿里秤 读通道
                            BleHelper.getInstance().listenAliScale(mBluetoothLeService);
                            // 获取用户组
                            String sendData = BleHelper.getInstance().assemblyAliData(Units.UNIT_KG.getCode(), "01");
                            // 发送数据
                            BleHelper.getInstance().sendDateToScale(mBluetoothLeService, sendData);
                        } else {
                            BleHelper.getInstance().sendDateToScale(mBluetoothLeService, BleHelper.getUserInfo(1, 1, 0, 175, 26, BlueToolUtil.UNIT_KG));
                        }
                    }
                }

            } else if (BLEConstant.ACTION_DATA_AVAILABLE.equals(action)) { //接收到数据
                String readMessage = intent.getStringExtra(BLEConstant.EXTRA_DATA);
                Log.e(TAG, "接收到的数据" + readMessage);
                if (readMessage != null && readMessage.length() == 40) {
                    readMessage = readMessage.substring(0, 22);
                    Log.e(TAG, "=====sub后的：" + readMessage);
                }
                Log.e(TAG, "接收到处理后的数据" + readMessage);
                if (!TextUtils.isEmpty(readMessage) && readMessage.length() > 10) {
                    Message msg1 = notifyHandler.obtainMessage(BlueToolUtil.RECEIVE_DATA);
                    msg1.obj = readMessage;
                    notifyHandler.sendMessage(msg1);
                }
            }
        }
    };

    /**
     * 更新界面蓝牙标志
     *
     * @param resourceId
     */
    private void updateConnectionState(final int resourceId) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final int colourId;
                final int mapId;
                String title = "";
                switch (resourceId) {
                    case R.string.connected:
                        colourId = android.R.color.holo_green_dark;
                        title = mDeviceName + "[" + mDeviceAddress + "]";
                        mapId = R.drawable.weight_bluetooth_on;
                        break;
                    case R.string.disconnected:
                        colourId = android.R.color.holo_red_dark;
                        title = "";
                        mapId = R.drawable.weight_bluetooth;
                        break;
                    default:
                        colourId = android.R.color.black;
                        title = "";
                        mapId = R.drawable.weight_bluetooth;
                        break;
                }
                ivWeightIcon.setImageResource(mapId);
                tvWeightName.setText(title);
                tvWeightName.setTextColor(getResources().getColor(colourId));
            }
        });
    }

    protected void showCBDialog(String title, String message, final String permission, final int requestCode) {
        new CBDialogBuilder(getActivity())
                .setTouchOutSideCancelable(true)
                .showCancelButton(true)
                .setTitle(title)
                .setMessage(message)
                .setConfirmButtonText("ok")
                .setCancelButtonText("cancel")
                .setDialogAnimation(CBDialogBuilder.DIALOG_ANIM_SLID_BOTTOM)
                .setDialoglocation(CBDialogBuilder.DIALOG_LOCATION_BOTTOM)
                .setButtonClickListener(true, new CBDialogBuilder.onDialogbtnClickListener() {
                    @Override
                    public void onDialogbtnClick(Context context, Dialog dialog, int whichBtn) {
                        switch (whichBtn) {
                            case BUTTON_CONFIRM:
                                requestPermissions(new String[]{permission}, requestCode);
                                break;
                            case BUTTON_CANCEL:

                                break;
                            default:
                                break;
                        }
                    }
                })
                .create().show();
    }

    /**
     * 请求权限,如果权限被拒绝过，则提示用户需要权限
     * @param permission
     * @param rationale
     * @param requestCode
     */
    @TargetApi(23)
    protected void requestPermission(final String permission, String rationale, final int requestCode) {
        if (shouldShowRequestPermissionRationale(permission)) {
            showCBDialog(getString(R.string.permission_title_rationale), rationale, permission, requestCode);
        } else {
            requestPermissions(new String[]{permission}, requestCode);
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ACCESS_COARSE_LOCATION_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //启动扫描
                    scanHandler.post(scanThread);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mActivity = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scanHandler.removeCallbacks(scanThread);
        getActivity().unregisterReceiver(mGattUpdateReceiver);
        getActivity().unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
