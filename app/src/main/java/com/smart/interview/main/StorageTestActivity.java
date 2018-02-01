package com.smart.interview.main;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.smart.interview.BaseActivity;
import com.smart.interview.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindString;
import butterknife.OnClick;

public class StorageTestActivity extends BaseActivity {

    @BindString(R.string.storage_use)
    String mTitle;

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
        return R.layout.activity_storage_test;
    }

    @Override
    public String title() {
        return mTitle;
    }

    @Override
    public boolean showBackArrow() {
        return true;
    }

    @OnClick({R.id.addFile, R.id.listFile, R.id.deleteFile})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addFile:
                try {
                    FileOutputStream outputStream = openFileOutput("hello.txt", Context.MODE_PRIVATE);
                    outputStream.write("Hello world!".getBytes());
                    outputStream.close();
                    outputStream = openFileOutput("yyy.log", Context.MODE_PRIVATE);
                    outputStream.write("1111.xxx".getBytes());
                    outputStream.close();
                    LogUtils.d("添加文件");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.listFile:
                LogUtils.d("文件列表：");
                String[] files = fileList();
                if (files == null || files.length == 0) {
                    LogUtils.d("为空");
                    return;
                }
                for (String file : files) {
                    LogUtils.d(file);
                }
                break;

            case R.id.deleteFile:
                ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                activityManager.clearApplicationUserData();
                LogUtils.d("删除文件");
                break;

        }
    }

    private void test001() {

        String databasePath = getDatabasePath("data").getPath();
        LogUtils.d(databasePath);
        LogUtils.d(getExternalCacheDir().getPath());
        LogUtils.d(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
        LogUtils.d(getExternalFilesDir("apple"));

    }
}
