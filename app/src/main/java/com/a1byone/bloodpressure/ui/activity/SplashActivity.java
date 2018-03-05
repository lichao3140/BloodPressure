package com.a1byone.bloodpressure.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import com.a1byone.bloodpressure.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;

public class SplashActivity extends AppCompatActivity {

    private ImageView img;
    private ImageView imgMark;

    ViewPropertyAnimation.Animator animator = new ViewPropertyAnimation.Animator(){

        @Override
        public void animate(View view) {
            view.setAlpha(0f);
            ObjectAnimator objAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
            objAnimator.setDuration(1000);
            objAnimator.start();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setBackgroundDrawable(null);
        initStatus();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        img = findViewById(R.id.img_id);
        imgMark = findViewById(R.id.icon_mark);
        imgMark.post(new Runnable() {
            @Override
            public void run() {
                Glide.with(SplashActivity.this).load(R.drawable.beijing).animate(animator).into(img);
                startAnimate();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    private void initStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decoderView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decoderView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    private void startAnimate() {
        int imgHeight = imgMark.getHeight() / 5;
        int height = getWindowManager().getDefaultDisplay().getHeight();
        int curY = height/2 + imgHeight / 2;
        int dy = (height - imgHeight) / 2;
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorTranslate = ObjectAnimator.ofFloat(imgMark,"translationY",0,dy);
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(imgMark,"ScaleX",1f,0.2f);
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(imgMark,"ScaleY",1f,0.2f);
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(imgMark,"alpha",1f,0.5f);
        animatorSet.play(animatorTranslate).with(animatorScaleX).with(animatorScaleY).with(animatorAlpha);
        animatorSet.setDuration(1200);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                imgMark.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        SplashActivity.this.finish();
                    }
                },1000);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
}
