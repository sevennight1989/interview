package com.smart.interview.utils;

import com.blankj.utilcode.util.LogUtils;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by vettel on 18-2-1.
 */

public class KLog implements Runnable {

    private static KLog mInstance;
    private boolean isExited = false;
    private boolean isInit = false;
    private Queue<String> mQueue;

    private Thread mThread = null;

    private KLog() {
        mQueue = new LinkedList<>();
    }

    public synchronized static KLog getInstance() {

        if (mInstance == null) {
            mInstance = new KLog();
        }
        return mInstance;
    }

    public synchronized static void releaseInstance() {
        if (mInstance != null) {
            mInstance = null;
        }
    }

    public synchronized boolean init() {
        if (isExited) {
            return true;
        }
        isExited = false;
        startThread();
        isInit = true;
        return true;
    }

    private void startThread() {
        mThread = new Thread(this);
        mThread.start();
    }

    public synchronized void dispose() {
        if (!isExited) {
            return;
        }
        isExited = true;
        isInit = false;
        synchronized (mThread) {
            if (mThread.isAlive()) {
                mThread.notifyAll();
            }
        }
        try {
            mThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mThread = null;
    }

    public static void logD(String msg) {
        mInstance.add(msg);
    }

    private void add(String msg) {
        if (!isInit) {
            return;
        }
        synchronized (mQueue) {
            mQueue.add(msg);
        }
        synchronized (mThread) {
            mThread.notifyAll();
        }
    }

    @Override
    public void run() {
        while (!isExited) {
            if (isEmpty()) {
                synchronized (mThread) {
                    try {
                        mThread.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            while (!isEmpty()) {
                String log = get();
                LogUtils.d(log);
            }
        }
    }

    private String get() {
        String result = null;
        synchronized (mQueue) {
            if (!mQueue.isEmpty()) {
                result = mQueue.poll();
            }
        }
        return result;
    }

    private boolean isEmpty() {
        boolean result = true;
        synchronized (mQueue) {
            result = mQueue.isEmpty();
        }
        return result;
    }
}
