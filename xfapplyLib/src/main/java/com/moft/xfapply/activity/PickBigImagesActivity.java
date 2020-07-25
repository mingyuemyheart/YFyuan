package com.moft.xfapply.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.moft.xfapply.R;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.logic.setting.PrefSetting;
import com.moft.xfapply.model.KeyDiagram.GeometryPlot;
import com.moft.xfapply.model.base.IMediaInfo;
import com.moft.xfapply.model.common.Point;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.utils.BitmapUtil;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.widget.ImageViewPager;
import com.moft.xfapply.widget.ZoomImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PickBigImagesActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    private TextView tv_title;
    private TextView tv_count;
    private ImageViewPager viewPager;

    private MyViewPagerAdapter adapter;

    private ArrayList<IMediaInfo> mediaList = new ArrayList<>();
    /** 当前选中的图片 */
    private int currentPic;
    private boolean isHistory;
    private GeometryPlot geometryPlot;

    /** 选择的照片文件夹 */
    public final static String EXTRA_DATA = "extra_data";
    /** 当前被选中的照片 */
    public final static String EXTRA_CURRENT_PIC = "extra_current_pic";
    /** 高亮点 */
    public final static String EXTRA_GEOMETRY_PLOT = "geometry_plot";

    public final static String EXTRA_HISTORY = "extra_history";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_big_images);
        initView();
        initData();
    }

    protected void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_count = (TextView) findViewById(R.id.tv_count);
        viewPager = (ImageViewPager) findViewById(R.id.vp_content);

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back(v);
            }
        });
    }

    protected void initData() {
        isHistory = getIntent().getBooleanExtra(EXTRA_HISTORY, false);
        currentPic = getIntent().getIntExtra(EXTRA_CURRENT_PIC, 0);

        List<IMediaInfo> afList = (List<IMediaInfo>) getIntent().getSerializableExtra(EXTRA_DATA);
        geometryPlot = (GeometryPlot)getIntent().getSerializableExtra(EXTRA_GEOMETRY_PLOT);
        int i = 0;
        for (IMediaInfo item : afList) {
            if (AppDefs.MediaFormat.IMG.toString().equals(item.getMediaFormat())) {
                mediaList.add(item);
            } else {
                if (i < currentPic) {
                    currentPic--;
                }
            }
            i++;
        }

        adapter = new MyViewPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);
        viewPager.setCurrentItem(currentPic);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        String name = mediaList.get(position).getMediaDescription();
        if (StringUtil.isEmpty(name)) {
            name = "无名称";
        }
        tv_title.setText(name);
        int pos = position + 1;
        tv_count.setText(pos + "/" + mediaList.size());
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private class MyViewPagerAdapter extends PagerAdapter {
        private int rotateAngel = 0;
//        private Bitmap targetResource = null;
        private Map<Integer, Bitmap> tagResMap = new HashMap<>();

        @Override
        public int getCount() {
            return getImagesCount();
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = LayoutInflater.from(PickBigImagesActivity.this).inflate(R.layout.widget_zoom_iamge, null);
            final ZoomImageView zoomImageView = (ZoomImageView) view.findViewById(R.id.zoom_image_view);

            ImageView iv_left = (ImageView) view.findViewById(R.id.iv_left);
            iv_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bitmap targetResource = tagResMap.get(position);
                    if (targetResource == null) {
                        Toast.makeText(PickBigImagesActivity.this, "图片正在加载，请稍后。",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    rotateAngel = -90;
                    targetResource = BitmapUtil.rotateBitmap(targetResource, rotateAngel);
                    tagResMap.put(position, targetResource);
                    if(geometryPlot != null && geometryPlot.getXys() != null && geometryPlot.getXys().size() > 0) {
                        List<Point> tempXys = getConvertXys(geometryPlot, rotateAngel);
                        int temp = geometryPlot.getBaseWidth();
                        geometryPlot.setBaseWidth(geometryPlot.getBaseHeight());
                        geometryPlot.setBaseHeight(temp);
                        zoomImageView.setOriginalSize(geometryPlot.getBaseWidth(), geometryPlot.getBaseHeight());
                        zoomImageView.setHighLightPoints(tempXys);
                    }
                    zoomImageView.setSourceImageBitmap(targetResource);
                }
            });

            ImageView iv_right = (ImageView) view.findViewById(R.id.iv_right);
            iv_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bitmap targetResource = tagResMap.get(position);
                    if (targetResource == null) {
                        Toast.makeText(PickBigImagesActivity.this, "图片正在加载，请稍后。",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    rotateAngel = 90;
                    targetResource = BitmapUtil.rotateBitmap(targetResource, rotateAngel);
                    tagResMap.put(position, targetResource);
                    if(geometryPlot != null && geometryPlot.getXys() != null && geometryPlot.getXys().size() > 0) {
                        List<Point> tempXys = getConvertXys(geometryPlot, rotateAngel);
                        int temp = geometryPlot.getBaseWidth();
                        geometryPlot.setBaseWidth(geometryPlot.getBaseHeight());
                        geometryPlot.setBaseHeight(temp);
                        zoomImageView.setOriginalSize(geometryPlot.getBaseWidth(), geometryPlot.getBaseHeight());
                        zoomImageView.setHighLightPoints(tempXys);
                    }
                    zoomImageView.setSourceImageBitmap(targetResource);
                }
            });

            IMediaInfo af = mediaList.get(position);
            String remoteFullPath = af.getMediaUrl();
            if (!remoteFullPath.toLowerCase().startsWith("http")) {
                remoteFullPath = Constant.URL_HEAD + PrefSetting.getInstance().getServerIP()
                    + Constant.URL_MIDDLE + PrefSetting.getInstance().getServerPort()
                    + Constant.SERVICE_NAME
                    + Constant.ATTACH_NAME + af.getMediaUrl();
            }

            SimpleTarget<Bitmap> simpleTarget = new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> animation) {
                        tagResMap.put(position, resource);
                    if(geometryPlot != null && geometryPlot.getXys() != null && geometryPlot.getXys().size() > 0) {
                       zoomImageView.setOriginalSize(geometryPlot.getBaseWidth(), geometryPlot.getBaseHeight());
                       zoomImageView.setHighLightPoints(geometryPlot.getXys());
                    }
                    zoomImageView.setSourceImageBitmap(resource);
                }
            };
            Glide.with(PickBigImagesActivity.this)
                    .load(remoteFullPath)
                    .asBitmap()
                    .into(simpleTarget);

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            final ZoomImageView zoomImageView = (ZoomImageView) view.findViewById(R.id.zoom_image_view);
            zoomImageView.destroy();
            container.removeView(view);
            tagResMap.remove(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    /**
     * 获得所有的图片数量
     */
    private int getImagesCount(){
        return mediaList.size();
    }

    @Override
    public void finish() {
        super.finish();
    }

    private List<Point> getConvertXys(GeometryPlot geometryPlot, int rotateAngel) {
        if(geometryPlot.getXys() != null && geometryPlot.getXys().size() > 0) {
            for (Point point : geometryPlot.getXys()) {
                if(rotateAngel == -90) {
                    float tempY = geometryPlot.getBaseWidth() - point.getX();
                    point.setX(point.getY());
                    point.setY(tempY);
                } else {
                    float tempX = geometryPlot.getBaseHeight() - point.getY();
                    point.setY(point.getX());
                    point.setX(tempX);
                }
            }
        }
        return geometryPlot.getXys();
    }
}
