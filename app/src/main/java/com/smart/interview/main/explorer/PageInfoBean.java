package com.smart.interview.main.explorer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ZhangPeng on 2-5-0005.
 */

public class PageInfoBean  {
    //当前页面的文件夹路径
    private String path;
    //当前页面的滑动位置
    private int position;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


}
