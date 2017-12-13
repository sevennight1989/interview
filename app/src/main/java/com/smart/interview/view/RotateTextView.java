package com.smart.interview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by lenovo on 17-12-13.
 */

@SuppressLint("AppCompatCustomView")
public class RotateTextView extends TextView {
    public RotateTextView(Context context) {
        this(context,null);
    }

    public RotateTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RotateTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(getCompoundPaddingLeft(),getCompoundPaddingTop());
        canvas.rotate(-45,getWidth()/2f,getHeight()/2f);
        super.onDraw(canvas);
        canvas.restore();
    }
}
