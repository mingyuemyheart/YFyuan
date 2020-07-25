package com.moft.xfapply.activity.logic;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.moft.xfapply.R;
import com.moft.xfapply.base.ViewLogic;
import com.moft.xfapply.model.common.LayerSelect;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sxf on 2019-04-02.
 */

public class MapLayerGroupLogic extends ViewLogic {
    private MapLayerLogic.OnMapLayerLogicListener mListener = null;
    private List<LayerSelect> mLayerList = null;
    private MapLayerLogic mMapLayerLogic = null;

    ImageView iv_dz_option_open = null;
    ImageView iv_dz_option_close = null;
    ImageView iv_sy_option_open = null;
    ImageView iv_sy_option_close = null;

    public MapLayerGroupLogic(View v, Activity a) {
        super(v, a);
    }

    public static int GROUP_DUIZHAN = 0;
    public static int GROUP_SHUIYUAN = 1;

    public void init() {
        iv_sy_option_open = (ImageView) getView().findViewById(R.id.iv_sy_option_open);
        iv_sy_option_close = (ImageView) getView().findViewById(R.id.iv_sy_option_close);
        iv_sy_option_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_sy_option_open.setVisibility(View.INVISIBLE);
                iv_sy_option_close.setVisibility(View.VISIBLE);
                setShuiyuanChecked(false);
            }
        });
        iv_sy_option_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_sy_option_open.setVisibility(View.VISIBLE);
                iv_sy_option_close.setVisibility(View.INVISIBLE);
                setShuiyuanChecked(true);
            }
        });

        iv_dz_option_open = (ImageView) getView().findViewById(R.id.iv_dz_option_open);
        iv_dz_option_close = (ImageView) getView().findViewById(R.id.iv_dz_option_close);
        iv_dz_option_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_dz_option_open.setVisibility(View.INVISIBLE);
                iv_dz_option_close.setVisibility(View.VISIBLE);
                setDuizhanChecked(false);
            }
        });
        iv_dz_option_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_dz_option_open.setVisibility(View.VISIBLE);
                iv_dz_option_close.setVisibility(View.INVISIBLE);
                setDuizhanChecked(true);
            }
        });
        refresh();
    }

    public void refresh() {
        if (isShuiyuanChecked()) {
            iv_sy_option_open.setVisibility(View.VISIBLE);
            iv_sy_option_close.setVisibility(View.INVISIBLE);
        } else {
            iv_sy_option_open.setVisibility(View.INVISIBLE);
            iv_sy_option_close.setVisibility(View.VISIBLE);
        }
        if (isDuizhanChecked()) {
            iv_dz_option_open.setVisibility(View.VISIBLE);
            iv_dz_option_close.setVisibility(View.INVISIBLE);
        } else {
            iv_dz_option_open.setVisibility(View.INVISIBLE);
            iv_dz_option_close.setVisibility(View.VISIBLE);
        }
    }

    private boolean isDuizhanChecked() {
        boolean isChecked = false;
        if (mLayerList == null) {
            return isChecked;
        }
        for (LayerSelect item : mLayerList) {
            if (AppDefs.CompEleType.DETACHMENT.toString().equals(item.getKey()) ||
                    AppDefs.CompEleType.BATTALION.toString().equals(item.getKey()) ||
                    AppDefs.CompEleType.SQUADRON.toString().equals(item.getKey()) ||
                    isEnterPriseFource(item.getKey()) ||
                    AppDefs.CompEleType.JOINT_FORCE.toString().equals(item.getKey())) {
                if (item.isSelected()) {
                    isChecked = true;
                    break;
                }
            }
        }
        return isChecked;
    }

    private Boolean isEnterPriseFource(String key) {
        if (StringUtil.isEmpty(key)) {
            return false;
        }
        return key.startsWith(AppDefs.CompEleType.ENTERPRISE_FOURCE.toString());
    }


    private boolean isShuiyuanChecked() {
        boolean isChecked = false;
        if (mLayerList == null) {
            return isChecked;
        }
        for (LayerSelect item : mLayerList) {
            if (AppDefs.CompEleType.WATERSOURCE_FIRE_HYDRANT.toString().equals(item.getKey()) ||
                    AppDefs.CompEleType.WATERSOURCE_CRANE.toString().equals(item.getKey()) ||
                    AppDefs.CompEleType.WATERSOURCE_FIRE_POOL.toString().equals(item.getKey()) ||
                    AppDefs.CompEleType.WATERSOURCE_NATURE_INTAKE.toString().equals(item.getKey())) {
                if (item.isSelected()) {
                    isChecked = true;
                    break;
                }
            }
        }
        return isChecked;
    }

    private void setDuizhanChecked(boolean checked) {
        if (mLayerList == null) {
            return;
        }

        List<LayerSelect> selectedList = new ArrayList<>();
        for (LayerSelect item : mLayerList) {
            if (AppDefs.CompEleType.DETACHMENT.toString().equals(item.getKey()) ||
                    AppDefs.CompEleType.BATTALION.toString().equals(item.getKey()) ||
                    AppDefs.CompEleType.SQUADRON.toString().equals(item.getKey()) ||
                    isEnterPriseFource(item.getKey()) ||
                    AppDefs.CompEleType.JOINT_FORCE.toString().equals(item.getKey())) {
                item.setSelected(checked);
                selectedList.add(item);
            }
        }
        if (selectedList.size() > 0 && mListener != null) {
            mListener.onLayerGroupChanged(selectedList);
        }
        if (mMapLayerLogic != null) {
            mMapLayerLogic.refreshLayer();
        }
    }

    private void setShuiyuanChecked(boolean checked) {
        if (mLayerList == null) {
            return;
        }

        List<LayerSelect> selectedList = new ArrayList<>();
        for (LayerSelect item : mLayerList) {
            if (AppDefs.CompEleType.WATERSOURCE_FIRE_HYDRANT.toString().equals(item.getKey()) ||
                    AppDefs.CompEleType.WATERSOURCE_CRANE.toString().equals(item.getKey()) ||
                    AppDefs.CompEleType.WATERSOURCE_FIRE_POOL.toString().equals(item.getKey()) ||
                    AppDefs.CompEleType.WATERSOURCE_NATURE_INTAKE.toString().equals(item.getKey())) {
                item.setSelected(checked);
                selectedList.add(item);
            }
        }
        if (selectedList.size() > 0 && mListener != null) {
            mListener.onLayerGroupChanged(selectedList);
        }
        if (mMapLayerLogic != null) {
            mMapLayerLogic.refreshLayer();
        }
    }

    public void setListener(MapLayerLogic.OnMapLayerLogicListener mListener) {
        this.mListener = mListener;
    }

    public void setLayerList(List<LayerSelect> list) {
        this.mLayerList = list;
    }

    public void setMapLayerLogic(MapLayerLogic mMapLayerLogic) {
        this.mMapLayerLogic = mMapLayerLogic;
    }
}
