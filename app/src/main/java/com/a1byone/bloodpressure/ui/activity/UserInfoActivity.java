package com.a1byone.bloodpressure.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.a1byone.bloodpressure.R;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

/**
 * 个人信息
 */
public class UserInfoActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    private CommonTitleBar userInfoToolBar;
    private Switch switchBaby;
    private LinearLayout babyInfoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        initView();

    }

    private void initView() {
        userInfoToolBar = findViewById(R.id.user_info_title_bar);
        switchBaby = findViewById(R.id.switch_lock);
        babyInfoView = findViewById(R.id.user_info_baby);

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
                } else {
                    babyInfoView.setVisibility(View.GONE);
                }
            }
        });
    }

}
