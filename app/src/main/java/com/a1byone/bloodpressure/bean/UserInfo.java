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
    private String name;//姓名
    private String sex;//性别  1男  2女
    private String bron;//出生年份
    private String height;//身高
    private String grade;//级别 1---普通人  2---业余运动员   3---专业运动员
    private String isBaby;//是否有婴儿  1---有  0---没有

    private String babyName;//婴儿名字
    private String babySex;//婴儿性别  1---男  2---女
    private String babyBron;//婴儿月份

    @Generated(hash = 2059515799)
    public UserInfo(Long id, String email, String password, String name, String sex,
            String bron, String height, String grade, String isBaby, String babyName,
            String babySex, String babyBron) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.bron = bron;
        this.height = height;
        this.grade = grade;
        this.isBaby = isBaby;
        this.babyName = babyName;
        this.babySex = babySex;
        this.babyBron = babyBron;
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
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getBron() {
        return this.bron;
    }
    public void setBron(String bron) {
        this.bron = bron;
    }
    public String getHeight() {
        return this.height;
    }
    public void setHeight(String height) {
        this.height = height;
    }
    public String getGrade() {
        return this.grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
    public String getIsBaby() {
        return this.isBaby;
    }
    public void setIsBaby(String isBaby) {
        this.isBaby = isBaby;
    }
    public String getBabyName() {
        return this.babyName;
    }
    public void setBabyName(String babyName) {
        this.babyName = babyName;
    }
    public String getBabySex() {
        return this.babySex;
    }
    public void setBabySex(String babySex) {
        this.babySex = babySex;
    }
    public String getBabyBron() {
        return this.babyBron;
    }
    public void setBabyBron(String babyBron) {
        this.babyBron = babyBron;
    }

}
