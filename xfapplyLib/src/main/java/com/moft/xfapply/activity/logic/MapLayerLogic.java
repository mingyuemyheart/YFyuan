package com.moft.xfapply.activity.logic;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.adapter.MapLayerAdapter;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.base.ViewLogic;
import com.moft.xfapply.logic.setting.PrefSetting;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.common.LayerSelect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MapLayerLogic extends ViewLogic {
    public MapLayerLogic(View v, Activity a) {
        super(v, a);
    }

    private OnMapLayerLogicListener mListener = null;

    private BaiduMap baiduMap;
    private PopupWindow popOption;
    private View popupView;

    private ImageView iv_satelite;
    private ImageView iv_plain;
    private ImageView iv_3d;

    private MapLayerGroupLogic mapLayerGroupLogic = null;
    private MapLayerAdapter adapter = null;

    private List<LayerSelect> list = new ArrayList<LayerSelect>();

    public void setListener(OnMapLayerLogicListener l) {
        mListener = l;
    }

    public void setBaiduMap(BaiduMap bm) {
        baiduMap = bm;
    }

    public void init() {
        popupView = getActivity().getLayoutInflater().inflate(R.layout.map_popup_option, null);
        popOption = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popOption.setAnimationStyle(R.style.AnimationRightFade);
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        popOption.setBackgroundDrawable(dw);
        popOption.setOutsideTouchable(true);
        popOption.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        WindowManager wm = (WindowManager) getActivity()
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        updatePopup(popOption, width *2 /3);

        View rl_measure_dis = popupView.findViewById(R.id.rl_measure_dis);
        rl_measure_dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onMeasureDis();
                }
                popOption.dismiss();
            }
        });

        View rl_measure_area = popupView.findViewById(R.id.rl_measure_area);
        rl_measure_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onMeasureArea();
                }
                popOption.dismiss();
            }
        });
    }

    public void show() {
        if (popOption == null || !popOption.isShowing()) {
            backgroundAlpha(0.5f);
            popOption.showAtLocation(popupView, Gravity.RIGHT, 0, 0);

            RelativeLayout rl_satelite = (RelativeLayout) popupView.findViewById(R.id.rl_satelite);
            iv_satelite = (ImageView) popupView.findViewById(R.id.iv_satelite);
            rl_satelite.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (PrefSetting.getInstance().getMapType() == PrefSetting.MAP_SATELITE) {
                        baiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);
                        PrefSetting.getInstance().setMapType(PrefSetting.MAP_NONE);
                        iv_satelite.setImageResource(R.drawable.main_map_mode_satellite_normal);
                    } else {
                        MapStatus overStatus = new MapStatus.Builder().overlook(0).build();
                        baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(overStatus));

                        baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                        PrefSetting.getInstance().setMapType(PrefSetting.MAP_SATELITE);
                        iv_satelite.setImageResource(R.drawable.main_map_mode_satellite_press);
                        iv_plain.setImageResource(R.drawable.main_map_mode_plain_normal);
                        iv_3d.setImageResource(R.drawable.main_map_mode_3d_normal);
                    }
                }
            });

            RelativeLayout rl_plain = (RelativeLayout) popupView.findViewById(R.id.rl_plain);
            iv_plain = (ImageView) popupView.findViewById(R.id.iv_plain);
            rl_plain.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (PrefSetting.getInstance().getMapType() == PrefSetting.MAP_NORMAL) {
                        baiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);
                        PrefSetting.getInstance().setMapType(PrefSetting.MAP_NONE);
                        iv_plain.setImageResource(R.drawable.main_map_mode_plain_normal);
                    } else {
                        MapStatus overStatus = new MapStatus.Builder().overlook(0).build();
                        baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(overStatus));

                        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                        PrefSetting.getInstance().setMapType(PrefSetting.MAP_NORMAL);
                        iv_plain.setImageResource(R.drawable.main_map_mode_plain_press);
                        iv_satelite.setImageResource(R.drawable.main_map_mode_satellite_normal);
                        iv_3d.setImageResource(R.drawable.main_map_mode_3d_normal);
                    }
                }
            });

            RelativeLayout rl_3d = (RelativeLayout) popupView.findViewById(R.id.rl_3d);
            iv_3d = (ImageView) popupView.findViewById(R.id.iv_3d);
            rl_3d.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (PrefSetting.getInstance().getMapType() == PrefSetting.MAP_3D) {
                        baiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);
                        PrefSetting.getInstance().setMapType(PrefSetting.MAP_NONE);
                        iv_3d.setImageResource(R.drawable.main_map_mode_3d_normal);
                    } else {
                        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                        float overlook = baiduMap.getMapStatus().overlook;
                        MapStatus overStatus = new MapStatus.Builder().overlook(overlook-45).build();
                        baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(overStatus));
                        PrefSetting.getInstance().setMapType(PrefSetting.MAP_3D);
                        iv_plain.setImageResource(R.drawable.main_map_mode_plain_normal);
                        iv_satelite.setImageResource(R.drawable.main_map_mode_satellite_normal);
                        iv_3d.setImageResource(R.drawable.main_map_mode_3d_press);
                    }
                }
            });

            int mapType = PrefSetting.getInstance().getMapType();
            switch (mapType) {
                case PrefSetting.MAP_NONE:
                    iv_plain.setImageResource(R.drawable.main_map_mode_plain_normal);
                    iv_satelite.setImageResource(R.drawable.main_map_mode_satellite_normal);
                    iv_3d.setImageResource(R.drawable.main_map_mode_3d_normal);
                    break;
                case PrefSetting.MAP_SATELITE:
                    iv_plain.setImageResource(R.drawable.main_map_mode_plain_normal);
                    iv_satelite.setImageResource(R.drawable.main_map_mode_satellite_press);
                    iv_3d.setImageResource(R.drawable.main_map_mode_3d_normal);
                    break;
                case PrefSetting.MAP_NORMAL:
                    iv_plain.setImageResource(R.drawable.main_map_mode_plain_press);
                    iv_satelite.setImageResource(R.drawable.main_map_mode_satellite_normal);
                    iv_3d.setImageResource(R.drawable.main_map_mode_3d_normal);
                    break;
                case PrefSetting.MAP_3D:;
                    iv_plain.setImageResource(R.drawable.main_map_mode_plain_normal);
                    iv_satelite.setImageResource(R.drawable.main_map_mode_satellite_normal);
                    iv_3d.setImageResource(R.drawable.main_map_mode_3d_press);
                    break;
            }

            final ImageView iv_bdlk_option_open = (ImageView) popupView.findViewById(R.id.iv_bdlk_option_open);
            final ImageView iv_bdlk_option_close = (ImageView) popupView.findViewById(R.id.iv_bdlk_option_close);
            if (baiduMap.isTrafficEnabled()) {
                iv_bdlk_option_open.setVisibility(View.VISIBLE);
                iv_bdlk_option_close.setVisibility(View.INVISIBLE);
            } else {
                iv_bdlk_option_open.setVisibility(View.INVISIBLE);
                iv_bdlk_option_close.setVisibility(View.VISIBLE);
            }
            iv_bdlk_option_open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iv_bdlk_option_open.setVisibility(View.INVISIBLE);
                    iv_bdlk_option_close.setVisibility(View.VISIBLE);
                    baiduMap.setTrafficEnabled(false);
                    PrefSetting.getInstance().setBaiduLuKuang(false);
                }
            });
            iv_bdlk_option_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iv_bdlk_option_open.setVisibility(View.VISIBLE);
                    iv_bdlk_option_close.setVisibility(View.INVISIBLE);
                    baiduMap.setTrafficEnabled(true);
                    PrefSetting.getInstance().setBaiduLuKuang(true);
                }
            });

            final ImageView iv_mark_option_open = (ImageView) popupView.findViewById(R.id.iv_mark_option_open);
            final ImageView iv_mark_option_close = (ImageView) popupView.findViewById(R.id.iv_mark_option_close);
            if (PrefSetting.getInstance().getMapShowPoi()) {
                iv_mark_option_open.setVisibility(View.VISIBLE);
                iv_mark_option_close.setVisibility(View.INVISIBLE);
            } else {
                iv_mark_option_open.setVisibility(View.INVISIBLE);
                iv_mark_option_close.setVisibility(View.VISIBLE);
            }
            iv_mark_option_open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iv_mark_option_open.setVisibility(View.INVISIBLE);
                    iv_mark_option_close.setVisibility(View.VISIBLE);
                    baiduMap.showMapPoi(false);
                    PrefSetting.getInstance().setMapShowPoi(false);
                }
            });
            iv_mark_option_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iv_mark_option_open.setVisibility(View.VISIBLE);
                    iv_mark_option_close.setVisibility(View.INVISIBLE);
                    baiduMap.showMapPoi(true);
                    PrefSetting.getInstance().setMapShowPoi(true);
                }
            });

            list.clear();

            List<Dictionary> dicList = LvApplication.getInstance().getEleTypeGeoDicList();
            for (Dictionary dic : dicList) {
                list.add(new LayerSelect(dic.getResMapId(), dic.getValue(), dic.getCode()));
            }

            ListView listView = (ListView) popupView.findViewById(R.id.list_layer);
            adapter = new MapLayerAdapter(getActivity(), list,
                    new MapLayerAdapter.OnItemSelectedListener() {
                @Override
                public void onItemClick(LayerSelect dto) {
                    doLayerSelected(dto);
                }
            });
            listView.setAdapter(adapter);

            mapLayerGroupLogic = new MapLayerGroupLogic(popupView, getActivity());
            mapLayerGroupLogic.setMapLayerLogic(this);
            mapLayerGroupLogic.setListener(mListener);
            mapLayerGroupLogic.setLayerList(list);
            mapLayerGroupLogic.init();
        } else {
            hide();
        }
    }

    public void hide() {
        if (popOption != null && popOption.isShowing()) {
            popOption.dismiss();
        }
    }

    public void refreshLayer() {
        adapter.notifyDataSetChanged();
    }

    private void doLayerSelected(LayerSelect dto) {
        if (mapLayerGroupLogic != null) {
            mapLayerGroupLogic.refresh();
        }

        if (mListener == null) {
            return;
        }

        mListener.onLayerChanged(dto);
    }

    private void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    private void updatePopup(PopupWindow pw, int width) {
        if (pw == null) {
            return;
        }

        if (Build.VERSION.SDK_INT < 24) {
            pw.update(0, 0, width, ViewGroup.LayoutParams.MATCH_PARENT);
        } else {
            pw.setWidth(width);

            Object obj = null;
            try {
                Field field = PopupWindow.class.getDeclaredField("mDecorView");
                field.setAccessible(true);
                obj = field.get(pw);
            } catch (Exception e) {
                e.printStackTrace();
            }

            View mDecorView = null;
            if (obj instanceof View) {
                mDecorView = (View) obj;
            } else {
                return;
            }

            WindowManager.LayoutParams params =
                    (WindowManager.LayoutParams) mDecorView.getLayoutParams();

            WindowManager wManager = getActivity().getWindowManager();
            wManager.updateViewLayout(mDecorView, params);
        }
    }

    public interface OnMapLayerLogicListener{
        public void onLayerChanged(LayerSelect dto);
        public void onLayerGroupChanged(List<LayerSelect> selectedList);
        public void onMeasureDis();
        public void onMeasureArea();
    }
}
