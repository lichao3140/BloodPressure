package com.a1byone.bloodpressure.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.a1byone.bloodpressure.Dao.UserDao;
import com.a1byone.bloodpressure.R;
import com.a1byone.bloodpressure.bean.UserInfo;
import com.a1byone.bloodpressure.utils.ToastUtil;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人信息
 */
public class UserInfoActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_user_bron)
    EditText etUserBron;
    @BindView(R.id.et_user_height)
    EditText etUserHeight;
    @BindView(R.id.et_baby_name)
    EditText etBabyName;
    @BindView(R.id.et_baby_bron)
    EditText etBabyBron;
    @BindView(R.id.bt_info_cancel)
    Button btInfoCancel;
    @BindView(R.id.bt_info_save)
    Button btInfoSave;
    @BindView(R.id.rb_sex_male)
    RadioButton rbSexMale;
    @BindView(R.id.rb_sex_female)
    RadioButton rbSexFemale;
    @BindView(R.id.rb_common_person)
    RadioButton rbCommonPerson;
    @BindView(R.id.rb_hobby_person)
    RadioButton rbHobbyPerson;
    @BindView(R.id.rb_professional_person)
    RadioButton rbProfessionalPerson;
    @BindView(R.id.rb_baby_sex_male)
    RadioButton rbBabySexMale;
    @BindView(R.id.rb_baby_sex_female)
    RadioButton rbBabySexFemale;

    private CommonTitleBar userInfoToolBar;
    private Switch switchBaby;
    private LinearLayout babyInfoView;
    private RadioGroup userInfoSex;
    private RadioGroup userInfoGrade;
    private RadioGroup babyInfoSex;
    private List<UserInfo> listUserInfo;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        listUserInfo = new ArrayList<>();
        listUserInfo = UserDao.queryUser(email);
        userInfo = listUserInfo.get(0);

        etUserName.setText(userInfo.getName());
        etUserBron.setText(userInfo.getBron());
        etUserHeight.setText(userInfo.getHeight());
        if (userInfo.getSex() == null) {

        } else if (userInfo.getSex().equals("1")) {
            rbSexMale.setChecked(true);
        } else {
            rbSexFemale.setChecked(true);
        }
        if (userInfo.getGrade() == null) {

        } else if (userInfo.getGrade().equals("1")) {
            rbCommonPerson.setChecked(true);
        } else if (userInfo.getGrade().equals("2")) {
            rbHobbyPerson.setChecked(true);
        } else {
            rbProfessionalPerson.setChecked(true);
        }
        if (userInfo.getIsBaby() == null) {

        } else if (userInfo.getIsBaby().equals("1")) {
            switchBaby.setChecked(true);
            etBabyName.setText(userInfo.getBabyName());
            etBabyBron.setText(userInfo.getBabyBron());
            if (userInfo.getBabySex() == null) {

            } else if (userInfo.getBabySex().equals("1")) {
                rbBabySexMale.setChecked(true);
            } else {
                rbBabySexFemale.setChecked(true);
            }
        }
    }

    private void initView() {
        userInfoToolBar = findViewById(R.id.user_info_title_bar);
        switchBaby = findViewById(R.id.switch_lock);
        babyInfoView = findViewById(R.id.user_info_baby);
        userInfoSex = findViewById(R.id.rg_user_info_sex);
        userInfoGrade = findViewById(R.id.rg_user_info_grade);
        babyInfoSex = findViewById(R.id.rg_baby_info_sex);

        userInfoToolBar.getLeftCustomView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        switchBaby.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    babyInfoView.setVisibility(View.VISIBLE);
                    userInfo.setIsBaby("1");
                    UserDao.updateUser(userInfo);
                } else {
                    babyInfoView.setVisibility(View.GONE);
                    userInfo.setIsBaby("0");
                    UserDao.updateUser(userInfo);
                }
            }
        });
    }

    @OnClick({R.id.bt_info_cancel, R.id.bt_info_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_info_cancel:
                onBackPressed();
                break;
            case R.id.bt_info_save:
                addUserInfo();
                break;
        }
    }

    private void addUserInfo() {
        String userName = etUserName.getText().toString().trim();
        String userBron = etUserBron.getText().toString().trim();
        String userHeight = etUserHeight.getText().toString().trim();
        String babyName = etBabyName.getText().toString().trim();
        String babyBron = etBabyBron.getText().toString().trim();

        if (userName.equals("")) {
            ToastUtil.showShort(UserInfoActivity.this, "姓名不能为空");
        } else if (userBron.equals("")) {
            ToastUtil.showShort(UserInfoActivity.this, "年份不能为空");
        } else if (userHeight.equals("")) {
            ToastUtil.showShort(UserInfoActivity.this, "身高不能为空");
        } else {
            userInfo.setName(userName);
            userInfo.setSex(getUserSex());
            userInfo.setBron(userBron);
            userInfo.setHeight(userHeight);
            userInfo.setGrade(getUserGrade());
            userInfo.setBabyName(babyName);
            userInfo.setBabySex(getBabySex());
            userInfo.setBabyBron(babyBron);
            UserDao.updateUser(userInfo);
            ToastUtil.showShort(UserInfoActivity.this, "个人资料保存成功");
            onBackPressed();
        }
    }

    private String getUserSex() {
        String sex = "";
        int userSex = userInfoSex.getCheckedRadioButtonId();
        if (userSex == R.id.rb_sex_male) {
            sex = "1";
        } else if (userSex == R.id.rb_sex_female) {
            sex = "2";
        }
        return sex;
    }

    private String getUserGrade() {
        String grade = "";
        int userGrade = userInfoGrade.getCheckedRadioButtonId();
        if (userGrade == R.id.rb_common_person) {
            grade = "1";
        } else if (userGrade == R.id.rb_hobby_person) {
            grade = "2";
        } else if (userGrade == R.id.rb_professional_person) {
            grade = "3";
        }
        return grade;
    }

    private String getBabySex() {
        String sex = "";
        int babySex = babyInfoSex.getCheckedRadioButtonId();
        if (babySex == R.id.rb_baby_sex_male) {
            sex = "1";
        } else if (babySex == R.id.rb_baby_sex_female) {
            sex = "2";
        }
        return sex;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
