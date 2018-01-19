package com.a1byone.bloodpressure.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.a1byone.bloodpressure.R;
import com.a1byone.bloodpressure.ui.adapter.RemindDataAdapter;
import com.a1byone.bloodpressure.ui.widget.RecyclerViewWidget;
import com.a1byone.bloodpressure.utils.ToastUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 提醒重复星期设定
 */

public class RemindDataActivity extends AppCompatActivity implements View.OnClickListener{
    private final static String TAG = RemindDataActivity.class.getSimpleName();

    private RemindDataAdapter remindDataAdapter;
    private List<String> list;
    private RecyclerView rvRemindData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_data);

        initList();
        rvRemindData = (RecyclerView) findViewById(R.id.rv_remind_data);
        findViewById(R.id.commit).setOnClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvRemindData.setLayoutManager(manager);
        rvRemindData.setHasFixedSize(true);
        remindDataAdapter = new RemindDataAdapter(list, this);
        rvRemindData.setAdapter(remindDataAdapter);
        //添加分割线
        rvRemindData.addItemDecoration(new RecyclerViewWidget(this, RecyclerViewWidget.VERTICAL_LIST));
        remindDataAdapter.setRecyclerViewOnItemClickListener(new RemindDataAdapter.RecyclerViewOnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                remindDataAdapter.setSelectItem(position);//设置选中的项
            }

            @Override
            public boolean onItemLongClickListener(View view, int position) {
                remindDataAdapter.setShowBox();
                remindDataAdapter.setSelectItem(position);//设置选中的项
                remindDataAdapter.notifyDataSetChanged();
                return true;
            }
        });
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
    public void onClick(View view) {
        //获取你选中的item
        Map<Integer, Boolean> map = remindDataAdapter.getMap();
        for (int i = 0; i < map.size(); i++) {
            if (map.get(i)) {
                ToastUtil.showShort(RemindDataActivity.this, "你选了第：" + i + "项");
                Log.e("TAG", "你选了第：" + i + "项");
            }
        }
//        switch (view.getId()) {
//            case R.id.rv_remind_data:
//                //获取你选中的item
//                Map<Integer, Boolean> map = remindDataAdapter.getMap();
//                for (int i = 0; i < map.size(); i++) {
//                    if (map.get(i)) {
//                        ToastUtil.showShort(RemindDataActivity.this, "你选了第：" + i + "项");
//                        Log.e("TAG", "你选了第：" + i + "项");
//                    }
//                }
//                break;
//        }
    }
}
