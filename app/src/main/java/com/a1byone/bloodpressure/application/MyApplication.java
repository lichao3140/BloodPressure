package com.a1byone.bloodpressure.application;

import android.app.Application;
import android.util.Log;

import com.lifesense.ble.LsBleInterface;
import com.lifesense.ble.LsBleManager;

/**
 * Created by Administrator on 2017-12-15.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LsBleManager.getInstance().initialize(getApplicationContext());
        LsBleManager.getInstance().registerMessageService();
        LsBleManager.getInstance().registerBluetoothBroadcastReceiver(getApplicationContext());

        Log.d("Bluetooth", "LSDevice Bluetooth SDK Version:" + LsBleInterface.BLUETOOTH_SDK_VERSION);
        LsBleManager.getInstance().setBlelogFilePath("Lifesense/report", "sky","test");
        LsBleManager.getInstance().enableWriteDebugMessageToFiles(true, LsBleInterface.PERMISSION_WRITE_LOG_FILE);
    }
}
