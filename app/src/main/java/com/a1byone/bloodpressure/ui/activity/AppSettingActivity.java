package com.a1byone.bloodpressure.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.a1byone.bloodpressure.R;
import com.a1byone.bloodpressure.utils.ToastUtil;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

public class AppSettingActivity extends AppCompatActivity {
    private final static String TAG = AppSettingActivity.class.getSimpleName();

    private CommonTitleBar appSettingToolBar;
    private Switch switchSyncGoogleFit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_setting);

        initView();
    }

    private void initView() {
        appSettingToolBar = findViewById(R.id.app_setting_title_bar);
        switchSyncGoogleFit = findViewById(R.id.switch_sync_google_fit);

        appSettingToolBar.getLeftCustomView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        switchSyncGoogleFit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    ToastUtil.showShort(AppSettingActivity.this, "Sync Google Fit");
                } else {

                }
            }
        });
    }
}
