package com.smart.interview.utils;

/**
 * Created by lenovo on 17-12-15.
 * 数学工具
 */

public class MathUtils {

    // 获取最大值
    public static int getMax(int... args) throws Exception {
        if (args.length < 2) {
            throw new Exception("至少传入两个参数");
        }
        int max= args[0];
        for(int i=1;i<args.length;i++){
            max = Math.max(max, args[i]);
        }
        return max;
    }
}
