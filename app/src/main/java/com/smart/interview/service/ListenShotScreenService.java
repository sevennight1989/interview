package com.smart.interview.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.smart.interview.R;
import com.smart.interview.listener.ScreenShotListenManager;
import com.squareup.picasso.Picasso;

import java.io.File;

public class ListenShotScreenService extends Service {
    private ScreenShotListenManager manager;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("PengLog", "LicstenShotScreenService onCreate");
        startListenScreenShot();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // 启动 后台服务

        // 启动浮悬框


        super.onStart(intent, startId);
    }

    WindowManager windowManager;
    View view;

    private void getWindowService(String imagePath) {

        windowManager = (WindowManager) this
                .getSystemService(WINDOW_SERVICE);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        // 布局属性
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        // 同时设置多个属性：悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应
        // 不可触摸：LayoutParams.FLAG_NOT_TOUCHABLE
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        // 设置背景
        params.format = PixelFormat.RGBA_8888;
        // 调整悬浮窗口至右侧中间
        params.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
        // 设置左上角为初始值
        params.x = 200;
        params.y = 200;
        // 设置悬浮窗口长宽数据
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        // 加载View
        // LayoutInflater inflater=getLayoutInflater().from(this);
        view = View.inflate(getApplicationContext(),
                R.layout.windowmanager, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);

        Log.d("PengLog","imagePath --> " + imagePath);
        File file = new File(imagePath);
        if(!file.exists()){
            Log.d("PengLog","图片不存在");
            return;
        }
        Picasso.with(this).load(file).into(imageView);
        // 添加点击事件
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                windowManager.removeView(view);
                // 回到主界面
//                Toast.makeText(getApplicationContext(), "我是小悬浮，正在跳转。。",
//                        Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getApplicationContext(),
//                        TestActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);

            }
        });

        // 添加到管理器
        Log.d("PengLog","windowManager.addView");
        windowManager.addView(view, params);

        // 移除
        // windowManager.removeView(view);
    }


    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        manager.stopListen();
    }


    private void startListenScreenShot() {
        manager = ScreenShotListenManager.newInstance(this);
        manager.setListener(
                new ScreenShotListenManager.OnScreenShotListener() {
                    public void onShot(String imagePath) {
                        Log.d("PengLog", "imagePath " + imagePath);
                        getWindowService(imagePath);
                    }
                }
        );
        manager.startListen();

    }
}
