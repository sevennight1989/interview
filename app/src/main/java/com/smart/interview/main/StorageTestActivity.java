package com.smart.interview.main;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blankj.utilcode.util.LogUtils;
import com.smart.interview.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class StorageTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_test);
        test001();
    }

    private void test001() {
        try {
            FileOutputStream outputStream = openFileOutput("hello.txt", Context.MODE_PRIVATE);
            outputStream.write("Hello world!".getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] files = fileList();
        for (String file : files) {
            LogUtils.d(file);
        }
        String databasePath  = getDatabasePath("data").getPath();
        LogUtils.d(databasePath);
        LogUtils.d(getExternalCacheDir().getPath());
        LogUtils.d(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
        LogUtils.d(getExternalFilesDir("apple"));

    }
}
