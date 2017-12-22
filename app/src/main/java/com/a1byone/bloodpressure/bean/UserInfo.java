package com.a1byone.bloodpressure.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 用户信息
 * Created by Administrator on 2017-12-22.
 */
@Entity
public class UserInfo {

    /**
     1.@Entity：告诉GreenDao该对象为实体，只有被@Entity注释的Bean类才能被dao类操作
     2.@Id：对象的Id，使用Long类型作为EntityId，否则会报错。(autoincrement = true)表示主键会自增，如果false就会使用旧值
     3.@Property：可以自定义字段名，注意外键不能使用该属性
     4.@NotNull：属性不能为空
     5.@Transient：使用该注释的属性不会被存入数据库的字段中
     6.@Unique：该属性值必须在数据库中是唯一值
     7.@Generated：编译后自动生成的构造函数、方法等的注释，提示构造函数、方法等不能被修改
     */
    //不能用int
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String email;//邮箱
    private String password;//密码

    @Generated(hash = 904979897)
    public UserInfo(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
    @Generated(hash = 1279772520)
    public UserInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
