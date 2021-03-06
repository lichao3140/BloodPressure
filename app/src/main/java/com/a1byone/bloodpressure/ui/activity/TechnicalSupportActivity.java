package com.a1byone.bloodpressure.ui.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.a1byone.bloodpressure.R;
import com.a1byone.bloodpressure.mail.SendMailUtil;
import com.a1byone.bloodpressure.utils.ToastUtil;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 技术支持
 */
public class TechnicalSupportActivity extends AppCompatActivity {
    private final static String TAG = TechnicalSupportActivity.class.getSimpleName();

    private CommonTitleBar technicalSupportToolBar;
    private String emailTitle;
    private String emailContent;

    @BindView(R.id.et_message_title)
    EditText etMessageTitle;
    @BindView(R.id.et_message_content)
    EditText etMessageContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technical_support);
        ButterKnife.bind(this);

        technicalSupportToolBar = findViewById(R.id.technical_support_title_bar);

        technicalSupportToolBar.getLeftCustomView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        technicalSupportToolBar.getRightTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailTitle = etMessageTitle.getText().toString().trim();
                emailContent = etMessageContent.getText().toString().trim();
                if (emailTitle.equals("")) {
                    ToastUtil.showShort(TechnicalSupportActivity.this, "主题不能为空");
                } else {
                    SendMailUtil.send("lichao3140@icloud.com", emailTitle, emailContent);
                    ToastUtil.showShort(TechnicalSupportActivity.this, "邮件发送成功");
                    onBackPressed();
                }
            }
        });
    }

    public void sendFileMail(View view) {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "test.txt");
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            String str = "hello world";
            byte[] data = str.getBytes();
            os.write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) os.close();
            } catch (IOException e) {
            }
        }
        SendMailUtil.send(file, "", "", "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
