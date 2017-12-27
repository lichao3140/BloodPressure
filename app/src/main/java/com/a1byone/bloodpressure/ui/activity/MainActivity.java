package com.a1byone.bloodpressure.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.a1byone.bloodpressure.R;
import com.a1byone.bloodpressure.ui.fragment.HistoryFragment;
import com.a1byone.bloodpressure.ui.fragment.MeasureFragment;
import com.a1byone.bloodpressure.ui.fragment.RemindFragment;
import com.a1byone.bloodpressure.ui.fragment.WeightFragment;
import com.a1byone.bloodpressure.utils.ToastUtil;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 主页
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CommonTitleBar mainTitleBar;//https://github.com/wuhenzhizao/android-titlebar
    private CommonTitleBar menuTitleBar;
    private View mainLeftLayout;
    private View mainRightLayout;
    private ImageButton ibBloodPressure;
    private ImageButton ibWeightMeasurement;
    private ImageButton ibUserInformation;
    private ImageButton ibTechnicalSupport;
    private ImageButton ibSignOut;
    private ArrayList<Fragment> fragmentList;

    private LinearLayout mainBottomSwitcherContainer;
    private FrameLayout mainFragmentContainer;
    private View mainView, menuView;
    private boolean menuViewLoad = false;//menuView是否载入过的flag
    private boolean isExit;//是否退出
    private String email;
    private Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainView = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        menuView = LayoutInflater.from(this).inflate(R.layout.activity_menu, null);
        setMainView();

        mainBottomSwitcherContainer = findViewById(R.id.main_bottom_switcher_container);
        mainTitleBar = findViewById(R.id.main_title_bar);
        mainLeftLayout = mainTitleBar.getLeftCustomView();
        mainRightLayout = mainTitleBar.getRightCustomView();

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        userId = intent.getLongExtra("userId", 0);

        initFragment();
        initClick();
        View childView = mainBottomSwitcherContainer.getChildAt(0);//默认第一页被选中
        onClick(childView);

        mainLeftLayout.setOnClickListener(new View.OnClickListener() { //自定义布局事件
            @Override
            public void onClick(View view) {
                setMenuView();
            }
        });

        mainRightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddRemindActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
    }

    private void setMainView() {
        setContentView(mainView);
    }

    private void setMenuView() {
        setContentView(menuView);
        initMenuView();
    }

    private void initMenuView() {
        if (!menuViewLoad) { //如果首次显示menuView,查找menuView里的控件并绑定监听器
            menuTitleBar = findViewById(R.id.menu_title_bar);
            ibBloodPressure = findViewById(R.id.ib_blood_pressure);
            ibWeightMeasurement = findViewById(R.id.ib_weight_measurement);
            ibUserInformation = findViewById(R.id.ib_user_information);
            ibTechnicalSupport = findViewById(R.id.ib_technical_support);
            ibSignOut = findViewById(R.id.ib_sign_out);

            ibBloodPressure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setContentView(mainView);
                    getSupportFragmentManager().beginTransaction().remove(fragmentList.get(0)).commit();
                    fragmentList.remove(0);
                    fragmentList.add(0,new MeasureFragment());
                    changeUI(0);
                    changeFragment(0);
                }
            });

            ibWeightMeasurement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setContentView(mainView);
                    getSupportFragmentManager().beginTransaction().remove(fragmentList.get(0)).commit();
                    fragmentList.remove(0);
                    fragmentList.add(0,new WeightFragment());
                    changeUI(0);
                    changeFragment(0);
                }
            });

            ibUserInformation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }
            });

            ibTechnicalSupport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, TechnicalSupportActivity.class);
                    startActivity(intent);
                }
            });

            ibSignOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });

            View menuLeftLayout = menuTitleBar.getLeftCustomView();
            menuLeftLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setMainView();
                }
            });
        }
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
        changeToolBar(index);
    }

    private void changeFragment(int index) {
        //获取fragment的管理者对象
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragmentList.get(index)).commit();
        Bundle bundle = new Bundle();
        bundle.putString("email", email);
        bundle.putLong("userId", userId);
        fragmentList.get(index).setArguments(bundle);
    }

    private void changeToolBar(int index) {
        switch (index) {
            case 0:
                mainTitleBar.getCenterTextView().setText("测量");
                mainRightLayout.setVisibility(View.GONE);
                break;
            case 1:
                mainTitleBar.getCenterTextView().setText("历史");
                mainRightLayout.setVisibility(View.GONE);
                break;
            case 2:
                mainTitleBar.getCenterTextView().setText("提醒");
                mainRightLayout.setVisibility(View.VISIBLE);
                break;
            default:
                mainTitleBar.getCenterTextView().setText(R.string.app_name);
                break;
        }
    }

    private void changeUI(int index) {
        for (int i = 0; i < mainBottomSwitcherContainer.getChildCount(); i++) {
            View view = mainBottomSwitcherContainer.getChildAt(i);
            if (i == index) {
                //循环遍历到的i,选中条目
                setEnable(view, false);
            } else {
                setEnable(view, true);
            }
        }
    }

    private void setEnable(View view, boolean b) {
        //1.将view设置为不可用
        view.setEnabled(b);
        //2.处理view的孩子结点状态,ViewGroup容器,只有容器才有孩子结点
        if (view instanceof ViewGroup) {
            int childCount = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = ((ViewGroup) view).getChildAt(i);
                setEnable(childView, b);
            }
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
        }
        return false;
    }

    private void exitBy2Click() {
        if (!isExit) {
            isExit = true;
            ToastUtil.showShort(MainActivity.this, "再按一次退出");
            new Timer().schedule(new TimerTask() {
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {
            onDestroy();
            finish();
            System.exit(0);
        }
    }
}
