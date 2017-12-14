package com.smart.interview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lenovo on 17-12-12.
 * View内部包含四个子viw,分别在矩形的四个边角
 */

public class CustomImgContainer extends ViewGroup {

    public CustomImgContainer(Context context) {
        super(context);
    }

    public CustomImgContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImgContainer(Context context, AttributeSet attrs, int defStyleAttr) {
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
        int width = 0;
        int height = 0;
        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams params = null;
        //左边两个的高度
        int lHeight = 0;
        //右边两个的高度
        int rHeight = 0;
        //上面两个的宽度
        int tWidth = 0;
        //下面两个的宽度
        int bWidth = 0;
        //左上右下的宽度
        int pWidth = 0;
        //右上左下的宽度
        int qWidth = 0;
        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            params = (MarginLayoutParams) childView.getLayoutParams();
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();

            if (i == 0 || i == 1) {
                tWidth += cWidth + params.leftMargin + params.rightMargin;
            }
            if (i == 2 || i == 3) {
                bWidth += cWidth + params.leftMargin + params.rightMargin;
            }
            if (i == 0 || i == 2) {
                lHeight += cHeight + params.topMargin + params.bottomMargin;
            }
            if (i == 1 || i == 3) {
                rHeight += cHeight + params.topMargin + params.bottomMargin;
            }
            if (i == 0 || i == 3) {
                pWidth += cWidth + params.leftMargin + params.rightMargin;
            }
            if (i == 1 || i == 2) {
                qWidth += cWidth + params.leftMargin + params.rightMargin;
            }

        }
        width = Math.max(tWidth, bWidth);
        width = Math.max(width, pWidth);
        width = Math.max(width, qWidth);
        height = Math.max(lHeight, rHeight);
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth : width,
                (heightMode == MeasureSpec.EXACTLY) ? sizeHeight : height);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams params = null;
        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            params = (MarginLayoutParams) childView.getLayoutParams();
            int cl = 0;
            int ct = 0;
            int cr = 0;
            int cb = 0;
            switch (i) {
                case 0:
                    cl = params.leftMargin;
                    ct = params.topMargin;
                    break;
                case 1:
                    cl = getWidth() - cWidth - params.rightMargin;
                    ct = params.topMargin;
                    break;
                case 2:
                    cl = params.leftMargin;
                    ct = getHeight() - cHeight - params.bottomMargin;
                    break;

                case 3:
                    cl = getWidth() - cWidth - params.rightMargin;
                    ct = getHeight() - cHeight - params.bottomMargin;
                    break;
            }
            cr = cl + cWidth;
            cb = ct + cHeight;
            childView.layout(cl, ct, cr, cb);
        }

    }

}
