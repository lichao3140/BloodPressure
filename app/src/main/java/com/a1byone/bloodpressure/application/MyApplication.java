package com.a1byone.bloodpressure.application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.a1byone.bloodpressure.bean.DaoMaster;
import com.a1byone.bloodpressure.bean.DaoSession;
import com.lifesense.ble.LsBleInterface;
import com.lifesense.ble.LsBleManager;

/**
 * Created by Administrator on 2017-12-15.
 */

public class MyApplication extends Application {

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        //应用开始就设置全局捕获异常器没有设置就会用系统默认的
        CauchExceptionHandler.getInstance().setDefaultUnCachExceptionHandler();

        setupDatabase();

        LsBleManager.getInstance().initialize(getApplicationContext());
        LsBleManager.getInstance().registerMessageService();
        LsBleManager.getInstance().registerBluetoothBroadcastReceiver(getApplicationContext());

        Log.d("Bluetooth", "LSDevice Bluetooth SDK Version:" + LsBleInterface.BLUETOOTH_SDK_VERSION);
        LsBleManager.getInstance().setBlelogFilePath("Lifesense/report", "sky","test");
        LsBleManager.getInstance().enableWriteDebugMessageToFiles(true, LsBleInterface.PERMISSION_WRITE_LOG_FILE);
    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "BloodPressure.db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }
}
