package com.a1byone.bloodpressure.bean;

import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017-12-18.
 */

public class BleAdvertisedData {
    private List<UUID> mUuids;
    private String mName;
    public BleAdvertisedData(List<UUID> uuids, String name) {
        mUuids = uuids;
        mName = name;
    }

    public List<UUID> getUuids() {
        return mUuids;
    }

    public String getName() {
        return mName;
    }
}
