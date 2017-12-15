package com.a1byone.bloodpressure.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.a1byone.bloodpressure.R;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

public class MenuActivity extends AppCompatActivity {

    CommonTitleBar titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        titleBar = (CommonTitleBar) findViewById(R.id.menu_title_bar);
        View leftLayout = titleBar.getLeftCustomView();

        leftLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


}
