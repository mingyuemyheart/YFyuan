package com.moft.xfapply.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class TouchLinearLayout extends LinearLayout{
	private static final String TAG = TouchLinearLayout.class.getName();  
	
    private float mPosX, mPosY, mCurPosX, mCurPosY, mOffsetX, mOffsetY;
    private boolean mScrolling;
  
    public TouchLinearLayout(Context context) {  
        super(context);  
    }  
  
    public TouchLinearLayout(Context context, AttributeSet attributeSet) {  
        super(context, attributeSet);  
    }  
  
    @Override  
    public boolean onInterceptTouchEvent(MotionEvent event) {  
        mScrolling = false;
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            mPosX = event.getX();
            mPosY = event.getY();
            break;
        case MotionEvent.ACTION_MOVE:
            mCurPosX = event.getX();
            mCurPosY = event.getY();
            if (mCurPosY - mPosY > 0
                    && (Math.abs(mCurPosY - mPosY) > 25)) {
                mScrolling = true;
            } else if (mCurPosY - mPosY < 0
                    && (Math.abs(mCurPosY - mPosY) > 25)) {
                mScrolling = true;
            }
            break;
        case MotionEvent.ACTION_UP:            
            break;
        }
        return mScrolling;
    }  
  
    @Override  
    public boolean onTouchEvent(MotionEvent event) { 
        return super.onTouchEvent(event);  
    }  

}
