package com.smart.interview.main.camera;

/**
 * Created by Percy on 1-10 0010.
 */

public class Convert {

    public native void convert(int[] colors, byte[] data,int width,int height);

    public native void convertByteToColor(byte[] data,int []colors,int width,int height);
}
