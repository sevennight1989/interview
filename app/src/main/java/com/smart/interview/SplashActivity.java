package com.smart.interview;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.splash_content)
    TextView mContentTv;
    private static final long DELAY = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        mCountDownTimer.start();

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
