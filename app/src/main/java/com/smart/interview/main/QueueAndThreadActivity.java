package com.smart.interview.main;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.smart.interview.R;
import com.smart.interview.utils.KLog;
import com.zp.commonlibrary.BaseActivity;

import butterknife.BindString;
import butterknife.OnClick;

public class QueueAndThreadActivity extends BaseActivity {

    @BindString(R.string.queue_and_thread)
    String mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KLog.getInstance().init();
    }

    @Override
    public boolean showActionBar() {
        return true;
    }

    @Override
    public int layoutID() {
        return R.layout.activity_queue_and_thread;
    }

    @Override
    public String title() {
        return mTitle;
    }

    @Override
    public boolean showBackArrow() {
        return true;
    }

    @OnClick({R.id.print001, R.id.print002, R.id.print003, R.id.print004})
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.print001:
                KLog.logD("输出日志001");
                break;
            case R.id.print002:
                KLog.logD("输出日志002");
                break;
            case R.id.print003:
                KLog.logD("输出日志003");
                break;
            case R.id.print004:
                KLog.logD("输出日志004");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KLog.getInstance().dispose();
        KLog.releaseInstance();
    }
}
