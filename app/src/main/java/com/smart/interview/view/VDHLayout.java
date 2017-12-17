package com.smart.interview.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by ZhangPeng on 12-17-0017.
 */

public class VDHLayout extends LinearLayout {

    private ViewDragHelper mDragHelper;
    private View mDragView;


    public VDHLayout(Context context) {
        this(context, null);
    }

    public VDHLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VDHLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - getPaddingRight() - mDragView.getWidth();
                final int newLeft = Math.min(Math.max(leftBound, left), rightBound);
                return newLeft;
            }

            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                final int topBound = getPaddingTop();
                final int bottomBound = getHeight() - getPaddingBottom() - mDragView.getHeight();
                int newTop;
                if (top >= topBound && top <= bottomBound) {
                    newTop = top;
                } else if (top < topBound) {
                    newTop = topBound;
                } else {
                    newTop = bottomBound;
                }
                return newTop;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDragView = getChildAt(0);
    }
}
