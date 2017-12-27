package com.a1byone.bloodpressure.Dao;

import com.a1byone.bloodpressure.application.MyApplication;
import com.a1byone.bloodpressure.bean.Remind;

import java.util.List;

/**
 * 提醒数据库操作
 * Created by Administrator on 2017-12-27.
 */

public class RemindDao {

    public static void insertRemind(Remind remind) {
        MyApplication.getDaoInstant().getRemindDao().insert(remind);
    }

    public static void deleteRemind(long id) {
        MyApplication.getDaoInstant().getRemindDao().deleteByKey(id);
    }

    public static void updateRemind(Remind remind) {
        MyApplication.getDaoInstant().getRemindDao().update(remind);
    }

    public static List<Remind> queryAllRemind() {
        return MyApplication.getDaoInstant().getRemindDao().loadAll();
    }
}
