package com.moft.xfapply.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.logic.MapLogicForPos;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.model.common.ComLocation;
import com.moft.xfapply.model.common.PropertyDes;

public class MapPosActivity extends BaseActivity {
    private MapLogicForPos mMapLogic = null;
    private PropertyDes mPropertyDes = null;
    private ComLocation mComLocation = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_pos);

        Intent intent = getIntent();
        if (intent.hasExtra("PropertyDes")) {
            mPropertyDes = (PropertyDes)intent.getSerializableExtra("PropertyDes");
        }
        if (intent.hasExtra("ComLocation")) {
            mComLocation = (ComLocation)intent.getSerializableExtra("ComLocation");
        }
        boolean isEditable = false;
        if (intent.hasExtra("isEditable")) {
            View rl_save = findViewById(R.id.rl_save);
            isEditable = intent.getBooleanExtra("isEditable", false);
            if (!isEditable) {
                rl_save.setVisibility(View.GONE);
            } else {
                rl_save.setVisibility(View.VISIBLE);
            }
        }

        mMapLogic = new MapLogicForPos(getWindow().getDecorView(), this);
        mMapLogic.init(isEditable);

        if (mComLocation != null && !mComLocation.isInvalid()) {
            mMapLogic.setMapCenter(mComLocation.getLat(), mComLocation.getLng());
            mMapLogic.getAddressAync(mComLocation.getLat(), mComLocation.getLng());
        } else {
            mMapLogic.doLocation();
        }
    }

    public void doSave(View v) {
        Intent intent = new Intent();

        ComLocation cl = mMapLogic.getComLocation();
        cl.setEleUuid(mPropertyDes.getEntityUuid());
        intent.putExtra("ComLocation", cl);

        setResult(RESULT_OK, intent);
        back(null);
    }

    public void doChangeMap(View v) {
        mMapLogic.changeBaseMap();

        ImageView iv_maptype = (ImageView)findViewById(R.id.iv_maptype);
        if (mMapLogic.isMapNormal()) {
            iv_maptype.setImageResource(R.drawable.main_map_mode_satellite_normal);
        } else {
            iv_maptype.setImageResource(R.drawable.main_map_mode_plain_normal);
        }
    }

    public void doZoomIn(View v) {
        mMapLogic.zoomIn();
    }

    public void doZoomOut(View v) {
        mMapLogic.zoomOut();
    }

    public void doLocation(View v) {
        mMapLogic.doLocation();
    }

    @Override
    public void onDestroy() {
        mMapLogic.destroy();

        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back(null);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
    public void back(View view) { 
        finish();
    }
}
