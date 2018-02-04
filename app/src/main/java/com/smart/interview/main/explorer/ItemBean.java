package com.smart.interview.main.explorer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ZhangPeng on 2-4-0004.
 */

public class ItemBean implements Parcelable {

    private FileType fileType;
    private String name;
    private long size;
    private String path;
    private int resId;
    private String fileTypeName;

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getFileTypeName() {
        return fileTypeName;
    }

    public void setFileTypeName(String fileTypeName) {
        this.fileTypeName = fileTypeName;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.fileType == null ? -1 : this.fileType.ordinal());
        dest.writeString(this.name);
        dest.writeLong(this.size);
        dest.writeString(this.path);
        dest.writeInt(this.resId);
        dest.writeString(this.fileTypeName);
    }

    public ItemBean() {
    }

    protected ItemBean(Parcel in) {
        int tmpFileType = in.readInt();
        this.fileType = tmpFileType == -1 ? null : FileType.values()[tmpFileType];
        this.name = in.readString();
        this.size = in.readLong();
        this.path = in.readString();
        this.resId = in.readInt();
        this.fileTypeName = in.readString();
    }

    public static final Parcelable.Creator<ItemBean> CREATOR = new Parcelable.Creator<ItemBean>() {
        @Override
        public ItemBean createFromParcel(Parcel source) {
            return new ItemBean(source);
        }

        @Override
        public ItemBean[] newArray(int size) {
            return new ItemBean[size];
        }
    };
}
