package com.a1byone.bloodpressure.bean;

import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2017-12-18.
 */

public class LimitedLinkHashMap<K, V> extends LinkedHashMap<K, V> {
    private static final long serialVersionUID = -5375660288461724925L;

    private final int mMaxSize;

    public LimitedLinkHashMap(int maxSize){
        super(maxSize + 1, 1, false);
        mMaxSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Entry<K, V> eldest) {
        return this.size() > mMaxSize;
    }
}
