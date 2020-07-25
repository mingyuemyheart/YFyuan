/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */

package com.moft.xfapply.utils.cluster.ui;

import com.moft.xfapply.R;
import com.moft.xfapply.utils.WindowUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * IconGenerator generates icons that contain text (or custom content) within an info
 * window-like shape.
 * <p/>
 * The icon {@link Bitmap}s generated by the factory should be used in conjunction with a {@link
 * com.baidu.mapapi.map.BitmapDescriptorFactory}.
 * <p/>
 * This class is not thread safe.
 */
public class IconGenerator {
    private final Context mContext;

    private ViewGroup mContainer;
    private RelativeLayout mRotationLayout;
    private TextView mTextView;
    private ImageView mImageView;

    /**
     * Creates a new IconGenerator with the default style.
     */
    public IconGenerator(Context context) {
        mContext = context;
        mContainer = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.text_bubble, null);
        mRotationLayout = (RelativeLayout) mContainer.getChildAt(0);
        mTextView = (TextView) mRotationLayout.findViewById(R.id.text);
        mImageView = (ImageView) mRotationLayout.findViewById(R.id.image);
    }

    /**
     * Sets the text content, then creates an icon with the current style.
     *
     * @param bucket the text content to display inside the icon.
     */
    public Bitmap makeIcon(int bucket) {
        if (mTextView != null) {
            mTextView.setText("" + bucket);
        }

        int textSize = 0;
        int resId;
        int nStep = 8;
        int nSizeS = 40;
        if (bucket < 30) {
            resId = R.drawable.marker_cluster_10;
            textSize = 14;
            setImageSize(mImageView, nSizeS);
        } else if (bucket >= 30 && bucket < 100) {
            resId = R.drawable.marker_cluster_50;
            textSize = 14;
            setImageSize(mImageView, nSizeS + 1 * nStep);
        } else if (bucket >= 100 && bucket < 1000) {
            resId = R.drawable.marker_cluster_30;
            textSize = 14;
            setImageSize(mImageView, nSizeS + 2 * nStep);
        } else if (bucket >= 1000 && bucket < 10000) {
            resId = R.drawable.marker_cluster_100;
            textSize = 14;
            setImageSize(mImageView, nSizeS + 3 * nStep);
        } else {
            resId = R.drawable.marker_cluster_50;
            textSize = 14;
            setImageSize(mImageView, nSizeS + 4 * nStep);
        }
        mTextView.setTextSize(textSize);
        mImageView.setImageResource(resId);

        int measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mContainer.measure(measureSpec, measureSpec);

        Bitmap r = Bitmap.createBitmap(mContainer.getMeasuredWidth(),
                mContainer.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        r.eraseColor(Color.TRANSPARENT);
        Canvas canvas = new Canvas(r);

        mContainer.layout(0, 0, mContainer.getMeasuredWidth(), mContainer.getMeasuredHeight());
        mContainer.draw(canvas);

        return r;
    }

    private void setImageSize(ImageView img, int size) {
        ViewGroup.LayoutParams params = img.getLayoutParams();
        params.height = WindowUtil.dp2px(mContext, size);
        params.width = params.height;
        img.setLayoutParams(params);
    }
}
