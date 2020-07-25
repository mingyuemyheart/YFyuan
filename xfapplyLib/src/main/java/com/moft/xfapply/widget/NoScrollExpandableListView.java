package com.moft.xfapply.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Created by Administrator on 2019/3/27 0027.
 */

public class NoScrollExpandableListView extends ExpandableListView {

    public NoScrollExpandableListView(Context context) {
        super(context);
    }

    public NoScrollExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollExpandableListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 设置listView不可滑动
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}