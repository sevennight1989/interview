package com.smart.interview.network;

import rx.Subscriber;

/**
 * Created by ZhangPeng on 2-8-0008.
 */

public class BaseSubscriber<T> extends Subscriber<T> {


    @Override
    public void onStart() {
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(T o) {

    }
}
