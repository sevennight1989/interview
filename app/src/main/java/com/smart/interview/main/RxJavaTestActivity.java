package com.smart.interview.main;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blankj.utilcode.util.LogUtils;
import com.smart.interview.R;

import java.io.File;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxJavaTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_test);
        test001();
    }

    private void test001() {
        String picDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();
        String picDir2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath();
        File folder = new File(picDir + File.separator + "Screenshots");
        File folder2 = new File(picDir2);
        File[] folders = new File[2];
        folders[0] = folder;
        folders[1] = folder2;
        Observable.from(folders).map(new Func1<File, String>() {
            @Override
            public String call(File file) {
                return file.getPath();
            }
        }).filter(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                return s.contains("Screenshots");
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        LogUtils.d("---> " + s);
                    }
                });

       /* Observable.just(folder,folder2).flatMap(new Func1<File, Observable<File>>() {
            @Override
            public Observable<File> call(File file) {
                return Observable.from(file.listFiles());
            }
        }).filter(new Func1<File, Boolean>() {
            @Override
            public Boolean call(File file) {
                String fileName = file.getName();
                return fileName.endsWith(".jpg")||fileName.endsWith(".png");
            }
        }).map(new Func1<File, String>() {
            @Override
            public String call(File file) {
                return file.getName();
            }
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                LogUtils.d(s);
            }
        });
        */
    }
}
