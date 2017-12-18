package com.a1byone.bloodpressure.service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.a1byone.bloodpressure.bean.BleAdvertisedData;
import com.a1byone.bloodpressure.bean.BluetoothLeDevice;
import com.a1byone.bloodpressure.utils.BleUtil;
import com.a1byone.bloodpressure.utils.BlueToolUtil;
import com.a1byone.bloodpressure.utils.BluetoothUtils;

/**
 * Created by Administrator on 2017-12-18.
 */

public class BluetoothLeScanner extends BluetoothLeScannerInterface {
    private final Handler mHandler;
    //private final BluetoothAdapter.LeScanCallback mLeScanCallback;
    private final BluetoothUtils mBluetoothUtils;
    private boolean mScanning;
    private final Handler notifyHandler; //界面通知handler

    public BluetoothLeScanner(Handler nHandler, BluetoothUtils bluetoothUtils){
        mHandler = new Handler();
        //mLeScanCallback = leScanCallback;
        mBluetoothUtils = bluetoothUtils;
        notifyHandler = nHandler;
    }

    public boolean isScanning() {
        return mScanning;
    }

    @Override
    public void scanLeDevice(final int duration, final boolean enable) {
        if (enable) {
            if(mScanning){return;}
            Log.e("TAG", "~ Starting Scan");
            // Stops scanning after a pre-defined scan period.
            if(duration > 0){
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("TAG", "~ Stopping Scan (timeout)");
                        mScanning = false;
                        mBluetoothUtils.getBluetoothAdapter().stopLeScan(mLeScanCallback);
                    }
                }, duration);
            }
            Log.e("TAG", "BEGIN scane devices scanLeDevice:=");
            mScanning = true;
            mBluetoothUtils.getBluetoothAdapter().startLeScan(mLeScanCallback);
        } else {
            Log.e("TAG", "~ Stopping Scan");
            mScanning = false;
            mBluetoothUtils.getBluetoothAdapter().stopLeScan(mLeScanCallback);
        }
    }


    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            //Log.e("TAG LeScanCallback", "device name:"+device.getName() + "  rssi:"+rssi);
            final BleAdvertisedData badata = BleUtil.parseAdertisedData(scanRecord);
            String deviceName = device.getName();
            if (deviceName == null) {
                deviceName = badata.getName();
            }
            Log.e("BluetoothLeScanner","发现BLE称=" + deviceName + "[" + device.getAddress() + "]");
            if (device != null && deviceName != null && deviceName.toLowerCase().indexOf("scale") >= 0) {
                Log.i("BluetoothLeScanner","发现BLE称=" + deviceName + "[" + device.getAddress() + "]");
                /* 停止蓝牙扫描 */
                if (mScanning) {
                    mBluetoothUtils.getBluetoothAdapter().stopLeScan(mLeScanCallback);
                    mScanning = false;
                }
                final BluetoothLeDevice deviceLe = new BluetoothLeDevice(device, rssi, scanRecord, System.currentTimeMillis());
                //通知发现蓝牙了
                if(null!=notifyHandler){
                    Message msg1 = mHandler.obtainMessage(BlueToolUtil.FOUND_DEVICE);
                    msg1.obj = deviceLe;
                    notifyHandler.sendMessage(msg1);
                }
            }


        }



    };
}
