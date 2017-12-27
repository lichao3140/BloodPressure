package com.a1byone.bloodpressure.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 提醒
 * Created by Administrator on 2017-12-27.
 */

@Entity
public class Remind {
    @Id(autoincrement = true)
    private Long id;
    private Long uid;     //外键，用户的id
    private String time;  //时间
    private String date;  //日期
    @Generated(hash = 254009696)
    public Remind(Long id, Long uid, String time, String date) {
        this.id = id;
        this.uid = uid;
        this.time = time;
        this.date = date;
    }
    @Generated(hash = 1173539496)
    public Remind() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUid() {
        return this.uid;
    }
    public void setUid(Long uid) {
        this.uid = uid;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }


}
