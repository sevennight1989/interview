package com.smart.interview.main.explorer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ZhangPeng on 2-5-0005.
 */

public class PageInfoBean implements Parcelable {
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

    @Override
    public String toString() {
        return "PageInfoBean{" +
                "path='" + path + '\'' +
                ", position=" + position +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeInt(this.position);
    }

    public PageInfoBean() {
    }

    protected PageInfoBean(Parcel in) {
        this.path = in.readString();
        this.position = in.readInt();
    }

    public static final Parcelable.Creator<PageInfoBean> CREATOR = new Parcelable.Creator<PageInfoBean>() {
        @Override
        public PageInfoBean createFromParcel(Parcel source) {
            return new PageInfoBean(source);
        }

        @Override
        public PageInfoBean[] newArray(int size) {
            return new PageInfoBean[size];
        }
    };
}
