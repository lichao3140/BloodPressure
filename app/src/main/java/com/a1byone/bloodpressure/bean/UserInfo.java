package com.a1byone.bloodpressure.bean;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;

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

    @ToMany(referencedJoinProperty = "uid")
    private List<Remind> reminds;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 437952339)
    private transient UserInfoDao myDao;

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
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1708354117)
    public List<Remind> getReminds() {
        if (reminds == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RemindDao targetDao = daoSession.getRemindDao();
            List<Remind> remindsNew = targetDao._queryUserInfo_Reminds(id);
            synchronized (this) {
                if (reminds == null) {
                    reminds = remindsNew;
                }
            }
        }
        return reminds;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1469250469)
    public synchronized void resetReminds() {
        reminds = null;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 821180768)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserInfoDao() : null;
    }

}
