package com.smart.interview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ZhangPeng on 12-16-0016.
 * <p>
 * 每行均等布局
 * 子view的宽度* 子view的数量 最好不超过PartView的宽度，否则会有覆盖问题
 */

public class PartView extends ViewGroup {

    private int width;
    private int height;
    private int childMaxWidth;
    private static final int COUNT = 3;
    private int deliverHeight = 20;

    public PartView(Context context) {
        this(context, null);
    }

    public PartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;

        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            //默认item的高度是一样的
            cParams = (MarginLayoutParams) childView.getLayoutParams();
        }
        //获取行数
        int line = cCount / COUNT;
        //获取最后一个在第几列
        int row = cCount % COUNT;

        if (row == 0) {
            height = (cParams.topMargin + cHeight + cParams.bottomMargin + deliverHeight) * line + deliverHeight;
        } else {
            height = (cParams.topMargin + cHeight + cParams.bottomMargin + deliverHeight) * (line + 1) + deliverHeight;
        }
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY ? sizeWidth : cWidth * 3), (heightMode == MeasureSpec.EXACTLY ? sizeHeight : height));

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        childMaxWidth = width / COUNT;
        int cCount = getChildCount();
        int cWidth;
        int cHeight;
//        MarginLayoutParams cParams;
        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
//            cParams = (MarginLayoutParams) childView.getLayoutParams();
            int cl;
            int ct;
            int cr;
            int cb;
            int line = i / COUNT;
            int row = i % COUNT;
//            cl = cParams.leftMargin + childMaxWidth * row;
//            ct = cParams.topMargin + cHeight * line;
            //设置中心对齐后，margin值无效,默认所有child的高度一致
            cl = (childMaxWidth - cWidth) / 2 + childMaxWidth * row;
            ct = (cHeight + deliverHeight) * line + deliverHeight;
            cr = cl + cWidth;
            cb = ct + cHeight;
            childView.layout(cl, ct, cr, cb);
        }
    }
}
