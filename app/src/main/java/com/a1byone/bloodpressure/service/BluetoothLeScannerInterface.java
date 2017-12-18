package com.a1byone.bloodpressure.service;

/**
 * 蓝牙扫描接口
 * Created by Administrator on 2017-12-18.
 */

public abstract class BluetoothLeScannerInterface {

    public void scanLeDevice(final int duration, final boolean enable){}
}
