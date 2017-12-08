package com.a1byone.bloodpressure.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.a1byone.bloodpressure.R;
import com.a1byone.bloodpressure.ui.fragment.HistoryFragment;
import com.a1byone.bloodpressure.ui.fragment.MeasureFragment;
import com.a1byone.bloodpressure.ui.fragment.RemindFragment;
import java.util.ArrayList;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    private ArrayList<Fragment> fragmentList;

    LinearLayout mainBottomSwitcherContainer;
    FrameLayout mainFragmentContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
        mainBottomSwitcherContainer = (LinearLayout) findViewById(R.id.main_bottom_switcher_container);

        initFragment();

        toolbar.setTitle("1ByOne");
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        initClick();
        View childView = mainBottomSwitcherContainer.getChildAt(0);
        onClick(childView);
    }

    private void initClick() {
        //通过获取mainBottomSwitcherContainer容器中的每一个孩子节点,即选项卡那个FrameLayout,注册点击事件
        int childCount = mainBottomSwitcherContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            FrameLayout frameLayout = (FrameLayout) mainBottomSwitcherContainer.getChildAt(i);
            frameLayout.setOnClickListener(this);
        }
    }

    private void initFragment() {
        fragmentList = new ArrayList<>();

        fragmentList.add(new MeasureFragment());
        fragmentList.add(new HistoryFragment());
        fragmentList.add(new RemindFragment());
    }

    @Override
    public void onClick(View view) {
        int index = mainBottomSwitcherContainer.indexOfChild(view);
        changeUI(index);
        changeFragment(index);
    }

    private void changeFragment(int index) {
        //获取fragment的管理者对象
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragmentList.get(index)).commit();
    }

    private void changeUI(int index) {
        for (int i = 0; i < mainBottomSwitcherContainer.getChildCount(); i++) {
            View view = mainBottomSwitcherContainer.getChildAt(i);
            if (i == index){
                //循环遍历到的i,选中条目
                setEnable(view,false);
            }else{
                setEnable(view,true);
            }
        }
    }

    private void setEnable(View view, boolean b) {
        //1.将view设置为不可用
        view.setEnabled(b);
        //2.处理view的孩子结点状态,ViewGroup容器,只有容器才有孩子结点
        if (view instanceof ViewGroup){
            int childCount = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = ((ViewGroup) view).getChildAt(i);
                setEnable(childView,b);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        return false;
    }
}
