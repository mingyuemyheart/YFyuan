package com.moft.xfapply.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.model.BaiduPanoData;
import com.baidu.lbsapi.model.BaiduPoiPanoData;
import com.baidu.lbsapi.panoramaview.PanoramaRequest;
import com.baidu.lbsapi.panoramaview.PanoramaView;
import com.baidu.lbsapi.panoramaview.PanoramaViewListener;
import com.baidu.pano.platform.plugin.indooralbum.IndoorAlbumCallback;
import com.baidu.pano.platform.plugin.indooralbum.IndoorAlbumPlugin;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.indoor.AlbumContainer;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.utils.StringUtil;

public class PanoActivity extends BaseActivity {

    private PanoramaView mPanoView;
    private TextView textTitle;

    private String mTitleText = "全景";
    private int mType;
    private String mUid = "";
    private double mLat;
    private double mLng;

    public static final int PID = 0;// PID方式
    public static final int GEO = 1;// 经纬度方式
    public static final int UID_STREET = 2;// UID方式展示外景
    public static final int UID_INTERIOR = 3;// UID方式展示内景
    public static final int UID_STREET_CUSTOMALBUM = 4;// UID方式展示外景(使用自定义相册)

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 先初始化BMapManager
        initBMapManager();
        setContentView(R.layout.panodemo_main);

        Intent intent = getIntent();
        if (intent != null) {
            mType = intent.getIntExtra("type", -1);
            mTitleText = intent.getStringExtra("title");
            if (StringUtil.isEmpty(mTitleText)) {
                mTitleText = "未知名称";
            }

            if (intent.hasExtra("uid")) {
                mUid = intent.getStringExtra("uid");
            }

            if (intent.hasExtra("lat")) {
                mLat = intent.getDoubleExtra("lat", 0);
                mLng = intent.getDoubleExtra("lng", 0);
            }
        } else {
            finish();
        }

        PanoramaRequest request = PanoramaRequest.getInstance(this);
        if (!StringUtil.isEmpty(mUid)) {
            BaiduPoiPanoData poiPanoData = request.getPanoramaInfoByUid(mUid);
            if (mType == UID_INTERIOR) {
                if (!poiPanoData.hasInnerPano()) {
                    mUid = "7c5e480b109e67adacb22aae";
                    mTitleText = "内景演示";
                }
            } else if (mType == UID_STREET) {
                if (!poiPanoData.hasStreetPano()) {
                    mType = GEO;
                }
            }
        } else {
            if (mType == UID_INTERIOR) {
                mUid = "7c5e480b109e67adacb22aae";
                mTitleText = "内景演示";
            }
        }

        initView();
        doPanoByType(mType);
    }

    private void initBMapManager() {
        LvApplication app = (LvApplication) this.getApplication();
        if (app.mBMapManager == null) {
            app.mBMapManager = new BMapManager(app);
            app.mBMapManager.init(new LvApplication.MyGeneralListener());
        }
    }

    private void initView() {
        textTitle = (TextView) findViewById(R.id.tv_title);
        textTitle.setText(mTitleText);

        mPanoView = (PanoramaView) findViewById(R.id.panorama);
    }

    private void doPanoByType(int type) {
        mPanoView.setShowTopoLink(true);

        mPanoView.setPanoramaViewListener(new PanoramaViewListener() {

            @Override
            public void onLoadPanoramaBegin() {
            }

            @Override
            public void onLoadPanoramaEnd(String json) {
            }

            @Override
            public void onLoadPanoramaError(String error) {
            }

            @Override
            public void onDescriptionLoadEnd(String json) {
            }

            @Override
            public void onMessage(String msgName, int msgType) {
            }

            @Override
            public void onCustomMarkerClick(String key) {

            }

            @Override
            public void onMoveStart() {

            }

            @Override
            public void onMoveEnd() {

            }
        });

        mPanoView.setPanoramaZoomLevel(5);
        mPanoView.setPanoramaImageLevel(PanoramaView.ImageDefinition.ImageDefinitionHigh);
        if (type == PID) {
            String pid = "0900220000141205144547300IN";
            mPanoView.setPanorama(pid);
        } else if (type == GEO) {
            mPanoView.setPanorama(mLng, mLat);
        } else if (type == UID_STREET) {
            // 默认相册
            IndoorAlbumPlugin.getInstance().init();

            mPanoView.setPanoramaByUid(mUid, PanoramaView.PANOTYPE_STREET);
        } else if (type == UID_INTERIOR) {
            // 默认相册
            IndoorAlbumPlugin.getInstance().init();
            mPanoView.setPanoramaByUid(mUid, PanoramaView.PANOTYPE_INTERIOR);
        } else if (type == UID_STREET_CUSTOMALBUM) {
            // 自定义相册
            IndoorAlbumPlugin.getInstance().init(new IndoorAlbumCallback() {

                @Override
                public View loadAlbumView(PanoramaView panoramaView, EntryInfo info) {
                    if (panoramaView != null && info != null) {
                        View albumView = LayoutInflater.from(panoramaView.getContext())
                                .inflate(R.layout.baidupano_photoalbum_container, null);
                        if (albumView != null) {
                            AlbumContainer mAlbumContainer =
                                    (AlbumContainer) albumView.findViewById(R.id.page_pano_album_view);
                            TextView mTvAddress = (TextView) albumView.findViewById(R.id.page_pano_album_address);
                            mAlbumContainer.setControlView(panoramaView, mTvAddress);
                        }
                        LayoutParams lp = (LayoutParams) albumView.getLayoutParams();
                        if (lp == null) {
                            lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                        }
                        lp.gravity = Gravity.BOTTOM;
                        albumView.setLayoutParams(lp);
                        AlbumContainer albumContainer =
                                (AlbumContainer) albumView.findViewById(R.id.page_pano_album_view);
                        albumContainer.startLoad(panoramaView.getContext(), info);
                        return albumView;
                    } else {
                        return null;
                    }
                }
            });

            mPanoView.setPanoramaByUid(mUid, PanoramaView.PANOTYPE_STREET);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mPanoView.destroy();
        super.onDestroy();
    }

}
