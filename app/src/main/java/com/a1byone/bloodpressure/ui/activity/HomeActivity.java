package com.a1byone.bloodpressure.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.a1byone.bloodpressure.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.ib_register)
    ImageButton ibRegister;
    @BindView(R.id.ib_login)
    ImageButton ibLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

    }

    @OnClick({R.id.ib_register, R.id.ib_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_register:
                startActivity(new Intent(HomeActivity.this, RegisterActivity.class));
                HomeActivity.this.finish();
                break;
            case R.id.ib_login:
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                HomeActivity.this.finish();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
