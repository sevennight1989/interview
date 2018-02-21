package com.smart.interview.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.smart.interview.R;
import com.zp.commonlibrary.BaseActivity;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class ThreadTestActivity extends BaseActivity {

    @BindView(R.id.thread_info)
    TextView mThreadInfo;
    @BindString(R.string.title_thread_local)
    String mTitle;
    private StringBuffer sb;

    private ThreadLocal<String> mThreadLocal = new InheritableThreadLocal<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean showActionBar() {
        return true;
    }

    @Override
    public int layoutID() {
        return R.layout.activity_thread_test;
    }

    @Override
    public String title() {
        return mTitle;
    }

    @Override
    public boolean showBackArrow() {
        return true;
    }

    @OnClick({R.id.print_thread_info})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.print_thread_info:
                sb = new StringBuffer();
                testThreadLocal();
                break;
        }
    }

    private void testThreadLocal() {
        mThreadLocal.set("我是主线程");
        LogUtils.v(mThreadLocal.get());
        sb.append(mThreadLocal.get()).append("\n");
        new Thread(new Runnable() {
            @Override
            public void run() {
                mThreadLocal.set("我是子线程");
                LogUtils.v(mThreadLocal.get());
                sb.append(mThreadLocal.get()).append("\n");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                mThreadLocal.set("我是子线程2");
                LogUtils.v(mThreadLocal.get());
                sb.append(mThreadLocal.get()).append("\n");
            }
        }).start();

        try {
            Thread.sleep(2000);
            LogUtils.v(mThreadLocal.get());
            sb.append(mThreadLocal.get());
            mThreadInfo.setText(sb);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
