package com.a1byone.bloodpressure.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a1byone.bloodpressure.R;
import com.a1byone.bloodpressure.utils.ToastUtil;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.wuhenzhizao.titlebar.utils.AppUtils;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加提醒
 */
public class AddRemindActivity extends AppCompatActivity
        implements RadialTimePickerDialogFragment.OnTimeSetListener{
    private final static String TAG = MainActivity.class.getSimpleName();
    private static final String FRAG_TAG_TIME_PICKER = "timePickerDialogFragment";

    private CommonTitleBar addRemindBar;
    private View addRemindRightLayout;
    private View addRemindLeftLayout;

    @BindView(R.id.tv_add_remind_time)
    TextView tvAddRemindTime;
    @BindView(R.id.ll_add_remind_time)
    LinearLayout llAddRemindTime;
    @BindView(R.id.tv_add_remind_day)
    TextView tvAddRemindDay;
    @BindView(R.id.ll_add_remind_repeat)
    LinearLayout llAddRemindRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_remind);
        ButterKnife.bind(this);

        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        tvAddRemindTime.setText(hour + ":" + minute);

        addRemindBar = (CommonTitleBar) findViewById(R.id.add_remind_title_bar);
        addRemindLeftLayout = addRemindBar.getLeftCustomView();
        addRemindRightLayout = addRemindBar.getRightCustomView();

        addRemindLeftLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        addRemindRightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showShort(AddRemindActivity.this, "确定");
            }
        });
    }

    @OnClick({R.id.ll_add_remind_time, R.id.ll_add_remind_repeat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_add_remind_time:
                RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                        .setOnTimeSetListener(AddRemindActivity.this)
                        .setForced24hFormat();
                rtpd.show(getSupportFragmentManager(), FRAG_TAG_TIME_PICKER);
                break;
            case R.id.ll_add_remind_repeat:
                ToastUtil.showShort(AddRemindActivity.this, "重复");
                break;
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 透明化状态栏背景
        AppUtils.transparencyBar(getWindow());
    }

    @Override
    public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
        tvAddRemindTime.setText(getString(R.string.radial_time_picker_result_value, hourOfDay, minute));
    }

    @Override
    protected void onResume() {
        super.onResume();
        RadialTimePickerDialogFragment rtpd = (RadialTimePickerDialogFragment) getSupportFragmentManager().findFragmentByTag(FRAG_TAG_TIME_PICKER);
        if (rtpd != null) {
            rtpd.setOnTimeSetListener(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
