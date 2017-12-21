package com.smart.interview;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.splash_content)
    TextView mContentTv;
    private static final long DELAY = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //修复当应用安装完成之后，通过打开按钮启动页面，如果立即按home键，在启动应用会导致应用不能后台
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
            finish();
            return;
        }
        mCountDownTimer.start();

    }

    @Override
    public boolean showActionBar() {
        return false;
    }

    @Override
    public int layoutID() {
        return R.layout.activity_splash;
    }

    @Override
    public String title() {
        return "";
    }

    @Override
    public boolean showBackArrow() {
        return false;
    }

    private CountDownTimer mCountDownTimer = new CountDownTimer(DELAY * 1000, 1000) {
        @Override
        public void onTick(long l) {
            mContentTv.setText(l / 1000 + "");
        }

        @Override
        public void onFinish() {
            startFilterUI();
        }
    };

    private void startFilterUI() {
        Intent uiIntent = new Intent(this, FilterActivity.class);
        uiIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(uiIntent);
        finish();
    }
}
