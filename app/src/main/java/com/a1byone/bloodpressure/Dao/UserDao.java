package com.a1byone.bloodpressure.Dao;

import com.a1byone.bloodpressure.application.MyApplication;
import com.a1byone.bloodpressure.bean.UserInfo;
import com.a1byone.bloodpressure.bean.UserInfoDao;

import java.util.List;

/**
 * 用户信息数据库操作
 * Created by Administrator on 2017-12-22.
 */

public class UserDao {

    /**
     * 添加用户
     * @param userInfo
     */
    public static void insertUser(UserInfo userInfo) {
        MyApplication.getDaoInstant().getUserInfoDao().insert(userInfo);
    }

    /**
     * 删除用户
     * @param id
     */
    public static void deleteUser(long id) {
        MyApplication.getDaoInstant().getUserInfoDao().deleteByKey(id);
    }

    /**
     * 更新用户信息
     * @param userInfo
     */
    public static void updateLove(UserInfo userInfo) {
        MyApplication.getDaoInstant().getUserInfoDao().update(userInfo);
    }

    /**
     * 根据邮箱查询用户信息
     * @param email
     * @return
     */
    public static List<UserInfo> queryUser(String email) {
        return MyApplication.getDaoInstant().getUserInfoDao().queryBuilder().where(UserInfoDao.Properties.Email.eq(email)).list();
    }
}
