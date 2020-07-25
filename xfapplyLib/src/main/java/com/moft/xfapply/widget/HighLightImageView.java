package com.moft.xfapply.widget;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.moft.xfapply.app.LvApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 自定义的ImageView控制，可对图片进行多点触控缩放和拖动
 */
public class HighLightImageView extends ImageView {

    /**
     * 初始化状态常量
     */
    public static final int STATUS_NONE = 0;
    /**
     * 初始化状态常量
     */
    public static final int STATUS_INIT = 1;

    /**
     * 图片放大状态常量
     */
    public static final int STATUS_ZOOM_OUT = 2;

    /**
     * 图片缩小状态常量
     */
    public static final int STATUS_ZOOM_IN = 3;

    /**
     * 图片拖动状态常量
     */
    public static final int STATUS_MOVE = 4;

    /**
     * 用于对图片进行移动和缩放变换的矩阵
     */
    private Matrix matrix = new Matrix();

    /**
     * 待展示的Bitmap对象
     */
    private Bitmap sourceBitmap;

    /**
     * 记录当前操作的状态，可选值为STATUS_INIT、STATUS_ZOOM_OUT、STATUS_ZOOM_IN和STATUS_MOVE
     */
    private int currentStatus;

    /**
     * ZoomImageView控件的宽度
     */
    private int width;

    /**
     * ZoomImageView控件的高度
     */
    private int height;

    /**
     * 记录两指同时放在屏幕上时，中心点的横坐标值
     */
    private float centerPointX;

    /**
     * 记录两指同时放在屏幕上时，中心点的纵坐标值
     */
    private float centerPointY;

    /**
     * 记录当前图片的宽度，图片被缩放时，这个值会一起变动
     */
    private float currentBitmapWidth;

    /**
     * 记录当前图片的高度，图片被缩放时，这个值会一起变动
     */
    private float currentBitmapHeight;

    /**
     * 记录上次手指移动时的横坐标
     */
    private float lastXMove = -1;

    /**
     * 记录上次手指移动时的纵坐标
     */
    private float lastYMove = -1;

    /**
     * 记录手指在横坐标方向上的移动距离
     */
    private float movedDistanceX;

    /**
     * 记录手指在纵坐标方向上的移动距离
     */
    private float movedDistanceY;

    /**
     * 记录图片在矩阵上的横向偏移值
     */
    private float totalTranslateX;

    /**
     * 记录图片在矩阵上的纵向偏移值
     */
    private float totalTranslateY;

    /**
     * 记录图片在矩阵上的总缩放比例
     */
    private float totalRatio;

    /**
     * 记录手指移动的距离所造成的缩放比例
     */
    private float scaledRatio;

    /**
     * 记录图片初始化时的缩放比例
     */
    private float initRatio;

    /**
     * 记录上次两指之间的距离
     */
    private double lastFingerDis;

    private boolean isIntected;
    private float lx = -1, ly = -1;

    /** 是否初始化完成 */
    private boolean isInited = false;

    //高亮点
    List<com.moft.xfapply.model.common.Point> highLightPoints = new ArrayList<>();

    public static final int MSG_UPDATE_HIGH_LIGTH_EVENT = 0;
    private int originalWidth;
    private int originalHeight;
    private float highLightRatio;
    private int circleColor;


    private Timer timer = null;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_UPDATE_HIGH_LIGTH_EVENT:
                    invalidate();
                    break;
            }
        }
    };

    /**
     * ZoomImageView构造函数，将当前操作状态设为STATUS_INIT。
     *
     * @param context
     * @param attrs
     */

    private Context context;

    public HighLightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        currentStatus = STATUS_INIT;
        this.context = context;
        // 初始化默认图
        // sourceBitmap = BitmapFactory.decodeResource(getResources(),
        // R.drawable.ic_product);
    }

    public void setOriginalSize(int originalWidth, int originalHeight) {
        this.originalWidth = originalWidth;
        this.originalHeight = originalHeight;
    }

    public void setHighLightPoints(List<com.moft.xfapply.model.common.Point> highLightPoints) {
        this.highLightPoints.clear();
        for(com.moft.xfapply.model.common.Point point : highLightPoints) {
            com.moft.xfapply.model.common.Point info = new com.moft.xfapply.model.common.Point(point.getX(), point.getY());
            this.highLightPoints.add(info);
        }
    }
    /**
     * 将待展示的图片设置进来。
     *
     * @param bitmap 待展示的Bitmap对象
     */
    public void setSourceImageBitmap(Bitmap bitmap, int width, int height) {
        totalTranslateX = 0f;
        totalTranslateY = 0f;
        totalRatio = 0f;
        sourceBitmap = bitmap;
        sourceBitmap = resizeImage(sourceBitmap, width, height);
        resizePoints(sourceBitmap);
        highLightRatio = calchighLightRatio(sourceBitmap);
        currentStatus = STATUS_INIT;
        invalidate();
        setHighLightPointAnimation();
    }

    private float calchighLightRatio(Bitmap bitmap) {
        int screenWidth = ((WindowManager) (LvApplication.getInstance().getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay().getWidth();
        float ret = screenWidth > 1000 ? 1.5f : 1f;
        if(bitmap != null && bitmap.getWidth() < bitmap.getHeight()) {
            ret = screenWidth > 1000 ? 2f : 1.5f;
        }
        return ret;
    }

    public Bitmap resizeImage(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth();//144
        int height = bitmap.getHeight();//144

        if (width <= 0 || height <= 0) return null;

        int newWidth = w;//1080
        int newHeight = (w * height) / width;//1920

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);
        Bitmap resizedBitmap = null;
        try {
            resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        } catch (Exception e) {
            resizedBitmap = bitmap;
            Log.d("ZoomImageView", e.getMessage());
        }

        return resizedBitmap;
    }

    public void destroy () {
        highLightPoints = null;
        if(sourceBitmap != null) {
            sourceBitmap.recycle();
            sourceBitmap = null;
        }
        if(timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void resizePoints(Bitmap sourceBitmap) {
        if(highLightPoints != null && sourceBitmap != null) {
            for(com.moft.xfapply.model.common.Point point : highLightPoints) {
                point.setX((int)(point.getX() * 1.0 * sourceBitmap.getWidth() / originalWidth));
                point.setY((int)(point.getY() * 1.0 * sourceBitmap.getHeight() / originalHeight));
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            // 分别获取到ZoomImageView的宽度和高度
            width = getWidth();
            height = getHeight();
        }
    }

    /**
     * 根据currentStatus的值来决定对图片进行什么样的绘制操作。
     */
    @Override
    protected void onDraw(Canvas canvas) {
        switch (currentStatus) {
            case STATUS_INIT:
                initBitmap(canvas);
                break;
            default:
                if (sourceBitmap != null) {
                    canvas.drawBitmap(sourceBitmap, matrix, null);
                    drawHighLightPoint(canvas);
                }
                break;
        }
    }

    private void drawHighLightPoint(Canvas canvas) {
        if(highLightPoints != null) {
            Paint paint = new Paint();
            paint.setColor(circleColor);
            paint.setStyle(Paint.Style.STROKE);
            //设置边框宽度
            paint.setStrokeWidth(5);
            for(com.moft.xfapply.model.common.Point point : highLightPoints) {
                canvas.drawCircle((point.getX()  * totalRatio + totalTranslateX), (point.getY() * totalRatio + totalTranslateY), 14 * totalRatio * highLightRatio, paint);
            }
        }
    }

    private void setHighLightPointAnimation(){
        if(highLightPoints != null) {
            final int color1 = 0xEEFEC004;
            final int color2 = 0x44FEC004;
            circleColor = color1;
            if(timer == null) {
                timer= new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        circleColor = (circleColor == color1) ? color2 : color1;
                        handler.sendEmptyMessage(MSG_UPDATE_HIGH_LIGTH_EVENT);
                    }
                }, 0, 500);
            }
        } else {
            if(timer != null) {
                timer.cancel();
                timer = null;
            }
        }
    }
    /**
     * 对图片进行初始化操作，包括让图片居中，以及当图片大于屏幕宽高时对图片进行压缩。
     *
     * @param canvas
     */
    private void initBitmap(Canvas canvas) {
        if (sourceBitmap != null) {
            isInited  = true;
            matrix.reset();
            int bitmapWidth = sourceBitmap.getWidth();
            int bitmapHeight = sourceBitmap.getHeight();
            if (bitmapWidth > width || bitmapHeight > height) {
                if (bitmapWidth - width > bitmapHeight - height) {
                    // 当图片宽度大于屏幕宽度时，将图片等比例压缩，使它可以完全显示出来
                    float ratio = width / (bitmapWidth * 1.0f);
                    matrix.postScale(ratio, ratio);
                    float translateY = (height - (bitmapHeight * ratio)) / 2f;
                    // 在纵坐标方向上进行偏移，以保证图片居中显示
                    matrix.postTranslate(0, translateY);
                    totalTranslateY = translateY;
                    totalRatio = initRatio = ratio;
                } else {
                    // 当图片高度大于屏幕高度时，将图片等比例压缩，使它可以完全显示出来
                    float ratio = height / (bitmapHeight * 1.0f);
                    matrix.postScale(ratio, ratio);
                    float translateX = (width - (bitmapWidth * ratio)) / 2f;
                    // 在横坐标方向上进行偏移，以保证图片居中显示
                    matrix.postTranslate(translateX, 0);
                    totalTranslateX = translateX;
                    totalRatio = initRatio = ratio;
                }
                currentBitmapWidth = bitmapWidth * initRatio;
                currentBitmapHeight = bitmapHeight * initRatio;
            } else {
                // 当图片的宽高都小于屏幕宽高时，直接让图片居中显示
                float translateX = (width - sourceBitmap.getWidth()) / 2f;
                float translateY = (height - sourceBitmap.getHeight()) / 2f;
                matrix.postTranslate(translateX, translateY);
                totalTranslateX = translateX;
                totalTranslateY = translateY;
                totalRatio = initRatio = 1f;
                currentBitmapWidth = bitmapWidth;
                currentBitmapHeight = bitmapHeight;
            }
            canvas.drawBitmap(sourceBitmap, matrix, null);
            drawHighLightPoint(canvas);
        }
    }
}
