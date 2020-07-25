package com.moft.xfapply.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;
import android.graphics.Path.Direction;
import android.view.MotionEvent;
import android.view.View;

public class ShaderView extends View {
    private Path mPath = new Path();
    private Matrix matrix = new Matrix();
    private Bitmap bitmap;
    private static final int RADIUS = 120;
    private static final int FACTOR = 2;
    private int mCurrentX, mCurrentY;

    public ShaderView(Context context) {
        super(context);
        mPath.addCircle(RADIUS, RADIUS, RADIUS, Direction.CW);
        matrix.setScale(FACTOR, FACTOR);
    }
    
    public void setBitmap(Bitmap bp) {        
        bitmap = bp;
    }
    
    public void setMoving(MotionEvent event) {
        mCurrentX = (int) event.getX();
        mCurrentY = (int) event.getY();
        
        invalidate();
    }
    
    public void setMoving(int x, int y) {
        mCurrentX = x;
        mCurrentY = y;
        
        invalidate();
    }
    
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return true;
//    }
    
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(30, 100);
        Path path = new Path();
        path.addCircle(RADIUS, RADIUS, RADIUS+15, Direction.CW);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(8);
        paint.setColor(Color.rgb(225, 251, 4));
        paint.setStyle(Style.STROKE);
        canvas.drawPath(path, paint);
        
        //剪切
        canvas.clipPath(mPath); 
        
        //画放大后的图
        canvas.translate(RADIUS-mCurrentX*FACTOR, -RADIUS-mCurrentY*FACTOR);
        canvas.drawBitmap(bitmap, matrix, null);        
    }
}
