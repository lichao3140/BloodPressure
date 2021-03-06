package com.a1byone.bloodpressure.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a1byone.bloodpressure.Dao.RemindDao;
import com.a1byone.bloodpressure.R;
import com.a1byone.bloodpressure.bean.Remind;
import com.a1byone.bloodpressure.ui.adapter.RemindDataAdapter;
import com.a1byone.bloodpressure.ui.widget.RecyclerViewWidget;
import com.a1byone.bloodpressure.utils.ToastUtil;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.wuhenzhizao.titlebar.utils.AppUtils;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加提醒
 */
public class AddRemindActivity extends AppCompatActivity
        implements RadialTimePickerDialogFragment.OnTimeSetListener {
    private final static String TAG = AddRemindActivity.class.getSimpleName();
    private static final String FRAG_TAG_TIME_PICKER = "timePickerDialogFragment";

    private CommonTitleBar addRemindBar;
    private CommonTitleBar RemindDataBar;
    private View addRemindRightLayout;
    private View addRemindLeftLayout;
    private View addRemindView, remindDataView;
    private boolean remindDataViewLoad = false;//remindDataViewLoad是否载入过的flag
    private List<String> list;
    private RemindDataAdapter remindDataAdapter;
    private RecyclerView recyclerView;
    private String email;
    private Long userId;

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
        addRemindView = LayoutInflater.from(this).inflate(R.layout.activity_add_remind, null);
        remindDataView = LayoutInflater.from(this).inflate(R.layout.activity_add_remind_data, null);
        setContentView(addRemindView);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        userId = intent.getLongExtra("userId", 0);

        tvAddRemindTime.setText(getSysTime());
        addRemindBar = findViewById(R.id.add_remind_title_bar);
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
                addRemind(tvAddRemindTime.getText().toString());
                ToastUtil.showShort(AddRemindActivity.this, "设定成功");
            }
        });
    }

    private void addRemind(String time) {
        Remind remind = new Remind();
        remind.setUid(userId);
        remind.setTime(time);
        RemindDao.insertRemind(remind);
    }

    private String getSysTime() {
        String sysTime = null;
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        if (minute < 10) {
            sysTime = hour + ":0" + minute;
        } else {
            sysTime = hour + ":" + minute;
        }
        return sysTime;
    }

    private void setRemindDataView() {
        setContentView(remindDataView);
        if (!remindDataViewLoad) {
            RemindDataBar = (CommonTitleBar) findViewById(R.id.remind_data_title_bar);
            recyclerView = (RecyclerView) findViewById(R.id.rv_remind_data);
            initList();
            LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(manager);
            recyclerView.setHasFixedSize(true);
            remindDataAdapter = new RemindDataAdapter(list, this);
            recyclerView.setAdapter(remindDataAdapter);
            //添加分割线
            recyclerView.addItemDecoration(new RecyclerViewWidget(this, RecyclerViewWidget.VERTICAL_LIST));
            remindDataAdapter.setRecyclerViewOnItemClickListener(new RemindDataAdapter.RecyclerViewOnItemClickListener() {
                @Override
                public void onItemClickListener(View view, int position) {
                    //remindDataAdapter.setShowBox();
                    remindDataAdapter.setSelectItem(position);//设置选中的项
                    remindDataAdapter.notifyDataSetChanged();
                }

                @Override
                public boolean onItemLongClickListener(View view, int position) {
                    return false;
                }
            });

            View addRemindLeftLayout = RemindDataBar.getLeftCustomView();
            addRemindLeftLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //获取你选中的item
                    Map<Integer, Boolean> map = remindDataAdapter.getMap();
                    for (int i = 0; i < map.size(); i++) {
                        if (map.get(i)) {
                            Log.e(TAG, "你选了第：" + i + "项");
                        }
                    }
                    setContentView(addRemindView);
                }
            });
        }
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
                setRemindDataView();
//                Intent intent = new Intent(AddRemindActivity.this, RemindDataActivity.class);
//                startActivity(intent);
                break;
        }
    }

    private void initList() {
        list = new ArrayList<>();
        list.add("每天");
        list.add("星期一");
        list.add("星期二");
        list.add("星期三");
        list.add("星期四");
        list.add("星期五");
        list.add("星期六");
        list.add("星期日");
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        AppUtils.StatusBarLightMode(getWindow());
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
