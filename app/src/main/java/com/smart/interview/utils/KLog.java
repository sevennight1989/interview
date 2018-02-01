package com.smart.interview.utils;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by vettel on 18-2-1.
 */

public class KLog implements Runnable {

    private KLog mInstance;
    private boolean isExited = false;
    private boolean isInit = false;
    private Queue mQueue;

    private Thread mThread;

    private KLog() {
        mQueue = new LinkedList();
    }

    public KLog getInstance(){
        if(mInstance == null){
            mInstance = new KLog();
        }
        return mInstance;
    }

    private void init(){

    }


    @Override
    public void run() {

    }
}
