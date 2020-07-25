package com.moft.xfapply.activity.logic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.GpsDetailActivity;
import com.moft.xfapply.activity.KeyUnitDisposalPointActivity;
import com.moft.xfapply.activity.PanoramaActivity;
import com.moft.xfapply.activity.PickOrTakeImageActivity;
import com.moft.xfapply.activity.bussiness.GeomEleBussiness;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.base.ViewLogic;
import com.moft.xfapply.logic.network.OkHttpClientManager;
import com.moft.xfapply.logic.setting.PrefSetting;
import com.moft.xfapply.model.base.IMediaInfo;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.common.EleCondition;
import com.moft.xfapply.model.common.SearchSummaryInfo;
import com.moft.xfapply.model.entity.MapSearchInfo;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.dto.KeyUnitDisposalPointDTO;
import com.moft.xfapply.model.external.dto.VehicleRealDataDTO;
import com.moft.xfapply.model.external.result.ArrayRestResult;
import com.moft.xfapply.utils.GsonUtil;
import com.moft.xfapply.utils.StringUtil;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class OutlineLogic extends ViewLogic  implements
        OnGetPoiSearchResultListener {
    public OutlineLogic(View v, Activity a) {
        super(v, a);
    }

    private OnOutlinLogicListener mListener = null;

    private MapSearchInfo mapSearchInfo = null;
    private PopupWindow popInfo = null;
    private View popInfoView = null;
    private PoiSearch mPoiSearch = null;
    private GeoCoder mGeoCoder = null;

    private LinearLayout popup_content = null;
    private LinearLayout popInfoCon = null;
    private List<View> conItemViewListSy = new ArrayList<>();
    private List<View> conItemViewListDw = new ArrayList<>();

    private View re_parent = null;
    private View rl_info1 = null;
    private View rl_info2 = null;
    private View rl_info3 = null;
    private View rl_info33 = null;
    private ImageView iv_avatar = null;
    private TextView tv_name = null;
    private TextView tv_address = null;
    private TextView[] tv_extra = new TextView[4];
    private Map<String, Integer> resMap;

    private boolean isShowExtra = false;

    public void init() {
        resMap = new HashMap<>();

        for (Dictionary dic : LvApplication.getInstance().getEleTypeGeoDicList()) {
            resMap.put(dic.getCode(), dic.getResCurMapId());
        }

        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);

        mGeoCoder = GeoCoder.newInstance();

        createPopinfo();
    }

    public boolean isShowing() {
        return popInfo != null && popInfo.isShowing();
    }

    private void createPopinfo() {
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);

        popInfoView = getActivity().getLayoutInflater().inflate(R.layout.popup_info, null);
        popup_content = (LinearLayout) popInfoView.findViewById(R.id.popup_content);

        popInfo = new PopupWindow(popInfoView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popInfo.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popInfo.setOutsideTouchable(false);
        popInfo.setTouchable(true);
        popInfo.setAnimationStyle(R.style.AnimBottom);
        popInfo.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popInfo.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        popInfo.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (mListener != null) {
                    mListener.onPopDismiss();
                }
            }
        });

        try {
            Method method = PopupWindow.class.getDeclaredMethod("setTouchModal", boolean.class);
            method.setAccessible(true);
            method.invoke(popInfo, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateExtra(String content, int color) {
        if (popInfoCon == null) {
            return;
        }
        View rl_extra = popInfoCon.findViewById(R.id.rl_extra);
        TextView tv_extra = (TextView)popInfoCon.findViewById(R.id.tv_extra);

        if (StringUtil.isEmpty(content)) {
            rl_extra.setVisibility(View.GONE);
            return;
        }

        rl_extra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onExtra(mapSearchInfo);
                }
            }
        });
        rl_extra.setVisibility(View.VISIBLE);
        tv_extra.setText(content);
        tv_extra.setTextColor(color);
    }

    public void showPopInfo(MapSearchInfo msi) {
        if (msi == null) {
            return;
        }
        mapSearchInfo = msi;

        popup_content.removeAllViews();
        if (Constant.MAP_SEARCH_FIRE.equals(msi.getType())) {
            popInfoCon = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.popup_info_fire, null);

            tv_extra[0] = (TextView) popInfoCon.findViewById(R.id.tv_outline1);
            tv_extra[1] = (TextView) popInfoCon.findViewById(R.id.tv_outline2);
            tv_extra[2] = (TextView) popInfoCon.findViewById(R.id.tv_outline3);
            tv_extra[3] = (TextView) popInfoCon.findViewById(R.id.tv_outline4);

            View rl_detail = popInfoCon.findViewById(R.id.rl_detail);
            rl_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null && mapSearchInfo != null) {
                        String extra = mapSearchInfo.getExtra();
                        if (!StringUtil.isEmpty(extra)) {
                            String[] exs = extra.split(Constant.OUTLINE_DIVIDER);
                            if (exs.length >= 3) {
                                mListener.onViewDetail(exs[0],exs[1],exs[2]);
                            }
                        }
                    }
                }
            });

            View rl_phone = popInfoCon.findViewById(R.id.rl_phone);
            rl_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        String dialInfo = v.getTag().toString();
                        if (!StringUtil.isEmpty(dialInfo)) {
                            mListener.onDial(dialInfo);
                        }
                    }
                }
            });

            String dialInfo = "";
            if (mapSearchInfo != null) {
                String extra = mapSearchInfo.getExtra();
                if (!StringUtil.isEmpty(extra)) {
                    String[] exs = extra.split(Constant.OUTLINE_DIVIDER);
                    if (exs.length >= 5 && !StringUtil.isEmpty(exs[4])) {
                        for (int i = 4; i < exs.length; i++) {
                            if (exs[i].startsWith("&")) {
                                dialInfo = exs[i].substring(1);
                                break;
                            }
                        }
                    }
                }
            }
            if (!StringUtil.isEmpty(dialInfo)) {
                rl_phone.setTag(dialInfo);
                rl_phone.setVisibility(View.VISIBLE);
            } else {
                rl_phone.setVisibility(View.GONE);
            }

            iv_avatar = (ImageView) popInfoCon.findViewById(R.id.iv_avatar);
            String extra = mapSearchInfo.getExtra();
            if (!StringUtil.isEmpty(extra)) {
                String[] exs = extra.split(Constant.OUTLINE_DIVIDER);
                if (exs.length >= 3) {
                    Integer res = resMap.get(exs[3]);
                    if (res != null) {
                        iv_avatar.setImageResource(res);
                    }
                }
            }
        } else if(Constant.MAP_VEHICLE_GPS.equals(msi.getType())) {
            popInfoCon = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.popup_info_fire, null);
            final VehicleRealDataDTO vehicle = (VehicleRealDataDTO)msi.getTag();
            tv_extra[0] = (TextView) popInfoCon.findViewById(R.id.tv_outline1);
            tv_extra[1] = (TextView) popInfoCon.findViewById(R.id.tv_outline2);
            tv_extra[2] = (TextView) popInfoCon.findViewById(R.id.tv_outline3);
            tv_extra[3] = (TextView) popInfoCon.findViewById(R.id.tv_outline4);

            View rl_detail = popInfoCon.findViewById(R.id.rl_detail);
            rl_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (vehicle != null) {
                        Intent intent = new Intent(getActivity(), GpsDetailActivity.class);
                        intent.putExtra(Constant.GEO_TYPE_VEHICLE_GPS, vehicle);
                        getActivity().startActivity(intent);
                    }
                }
            });
            iv_avatar = (ImageView) popInfoCon.findViewById(R.id.iv_avatar);
            iv_avatar.setImageResource(R.drawable.marker_vehicle_map);

            String extra = mapSearchInfo.getExtra();
            if (!StringUtil.isEmpty(extra)) {
                String[] exs = extra.split(Constant.OUTLINE_DIVIDER);
                for (int i = 0; i < tv_extra.length; i++) {
                    if(i < exs.length) {
                        String content = exs[i];
                        if (content.indexOf("</font>") == -1) {
                            tv_extra[i].setText(content);
                        } else {
                            Spanned temp = Html.fromHtml(content);
                            tv_extra[i].setText(temp);
                        }
                        tv_extra[i].setVisibility(View.VISIBLE);
                    } else {
                        tv_extra[i].setVisibility(View.GONE);
                    }
                }
            }
            View rl_zddw = popInfoCon.findViewById(R.id.rl_zddw);
            rl_zddw.setVisibility(View.GONE);
        } else  {
            popInfoCon = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.popup_info_poi, null);
            if (StringUtil.isEmpty(mapSearchInfo.getAddress()) &&
                    !StringUtil.isEmpty(mapSearchInfo.getUid())) {
                mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
                        .poiUid(mapSearchInfo.getUid()));
            }

            if ("near".equals(mapSearchInfo.getExtra())) {
                if (mListener != null) {
                    mListener.onSearchNear(mapSearchInfo, "");
                }
            }
        }
        popup_content.addView(popInfoCon);

        if (popInfo != null && !popInfo.isShowing()) {
            popInfo.showAtLocation(getView(), Gravity.BOTTOM, 0, 0);
        }

        View rl_detail = popInfoCon.findViewById(R.id.rl_detail);
        if (rl_detail != null) {
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            rl_detail.measure(w, h);
            LinearLayout rl_phone = (LinearLayout) popInfoCon.findViewById(R.id.rl_phone);
            if (rl_phone != null) {
                ViewGroup.LayoutParams param = rl_phone.getLayoutParams();
                param.height = rl_detail.getMeasuredHeight();
                rl_phone.setLayoutParams(param);
            }
        }

        tv_name = (TextView) popInfoCon.findViewById(R.id.tv_name);
        tv_address = (TextView) popInfoCon.findViewById(R.id.tv_address);

        if (StringUtil.isEmpty(mapSearchInfo.getName())) {
            tv_name.setText("未知名称");
        } else {
            tv_name.setText(mapSearchInfo.getName());
        }
        tv_address.setText(mapSearchInfo.getAddress());
        setExtraInfo(msi);

        re_parent = popInfoCon.findViewById(R.id.re_parent);
        re_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onMoveMap(mapSearchInfo.getLat()/1e6, mapSearchInfo.getLng()/1e6);
                }
                if (Constant.MAP_SEARCH_FIRE.equals(mapSearchInfo.getType())) {
                    showPopInfo(mapSearchInfo);
                }
            }
        });

        rl_info1 = popInfoCon.findViewById(R.id.rl_info1);
        rl_info1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onSearchNear(mapSearchInfo, "");
                }
            }
        });

        rl_info2 = popInfoCon.findViewById(R.id.rl_info2);
        rl_info2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    if (StringUtil.isEmpty(mapSearchInfo.getUid())) {
                        mListener.onViewPano(
                                mapSearchInfo.getLat()/1e6, mapSearchInfo.getLng()/1e6,
                                mapSearchInfo.getName());
                    } else {
                        mListener.onViewPano(
                                mapSearchInfo.getUid(),
                                mapSearchInfo.getLat()/1e6,
                                mapSearchInfo.getLng()/1e6,
                                mapSearchInfo.getName());
                    }
                }
            }
        });

        rl_info3 = popInfoCon.findViewById(R.id.rl_info3);
        rl_info3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onStartNav(
                            mapSearchInfo.getLat()/1e6, mapSearchInfo.getLng()/1e6,
                            mapSearchInfo.getName());
                }
            }
        });

        rl_info33 = popInfoCon.findViewById(R.id.rl_info33);
        rl_info33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    String dialInfo = v.getTag().toString();
                    if (!StringUtil.isEmpty(dialInfo)) {
                        mListener.onDial(dialInfo);
                    }
                }
            }
        });
    }

    public void hidePopInfo() {
        if (popInfo != null && popInfo.isShowing()) {
            popInfo.dismiss();
        }
    }

    public void destroy() {
        if (mPoiSearch != null) {
            mPoiSearch.destroy();
        }
        if (mGeoCoder != null) {
            mGeoCoder.destroy();
        }
    }

    private void setExtraInfo(MapSearchInfo msi) {
        if (!Constant.MAP_SEARCH_FIRE.equals(msi.getType())) {
            return;
        }

        String eleType = "";
        String extra = mapSearchInfo.getExtra();
        if (!StringUtil.isEmpty(extra)) {
            String[] exs = extra.split(Constant.OUTLINE_DIVIDER);
            //对应其他消防队伍分为4类
            int olCnt = exs.length - 4;
            eleType = exs[0];
            for (int i = 0; i < tv_extra.length; i++) {
                if (i < olCnt && !exs[4 + i].startsWith("&")) {
                    String content = exs[4 + i];
                    if (content.indexOf("</font>") == -1) {
                        tv_extra[i].setText(content);
                    } else {
                        Spanned temp = Html.fromHtml(content);
                        tv_extra[i].setText(temp);
                    }
                    tv_extra[i].setVisibility(View.VISIBLE);
                } else {
                    tv_extra[i].setVisibility(View.GONE);
                }
            }
        }

        View rl_qrcode = popInfoCon.findViewById(R.id.rl_qrcode);
        View rl_zddw = popInfoCon.findViewById(R.id.rl_zddw);
        if (AppDefs.CompEleType.KEY_UNIT.toString().equals(eleType)) {
            rl_qrcode.setVisibility(View.VISIBLE);
            rl_qrcode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null && mapSearchInfo != null) {
                        String extra = mapSearchInfo.getExtra();
                        if (!StringUtil.isEmpty(extra)) {
                            String[] exs = extra.split(";;");
                            if (exs.length >= 3) {
                                mListener.onViewQRCode(exs[0], exs[1], exs[2]);
                            }
                        }
                    }
                }
            });
            rl_zddw.setVisibility(View.VISIBLE);
            View rl_info4 = popInfoCon.findViewById(R.id.rl_info4);
            rl_info4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null && mapSearchInfo != null) {
                        String extra = mapSearchInfo.getExtra();
                        if (!StringUtil.isEmpty(extra)) {
                            String[] exs = extra.split(Constant.OUTLINE_DIVIDER);
                            if (exs.length >= 3) {
                                mListener.onViewDetail(exs[0],exs[1],exs[2]);
                            }
                        }
                    }
                }
            });

            View rl_info5 = popInfoCon.findViewById(R.id.rl_info5);
            rl_info5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), PickOrTakeImageActivity.class);

                    List<IMediaInfo> mediaInfos = getPickMediaInfos(mapSearchInfo.toEleCondition().getUuid(), mapSearchInfo.toEleCondition().getCityCode());
                    intent.putExtra("EleCondition", mapSearchInfo.toEleCondition());
                    intent.putExtra("pick_media_infos", (Serializable) mediaInfos);
                    getActivity().startActivity(intent);
                }
            });

            View rl_info6 = popInfoCon.findViewById(R.id.rl_info6);
            rl_info6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), PanoramaActivity.class);
                    IMediaInfo info = getPanoMediaInfo(mapSearchInfo.toEleCondition().getUuid(), mapSearchInfo.toEleCondition().getCityCode());
                    if(info != null) {
                        intent.putExtra("pano_url", info.getMediaUrl());
                        getActivity().startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "无全景漫游！", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            View rl_info7 = popInfoCon.findViewById(R.id.rl_info7);
            rl_info7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                    }
                }
            });

            // 对应重点单位处置要点
            View rl_info8 = popInfoCon.findViewById(R.id.rl_info8);
            rl_info8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reuestDisposalPointByKeyUnit(mapSearchInfo.toEleCondition().getUuid());
                }
            });
        } else {
            rl_zddw.setVisibility(View.GONE);
            rl_qrcode.setVisibility(View.GONE);
        }
    }

    private List<IMediaInfo> getPickMediaInfos(String uuid, String group) {
        String condition = String.format("%s and %s and %s and %s and %s",
                String.format(Constant.SEARCH_COND_KEY_UNIT_UUID, uuid),
//                String.format(Constant.SEARCH_COND_BELONG_TO_UUID, uuid),
                String.format(Constant.SEARCH_COND_CLASSIFICATION_NOT_EQUALS, AppDefs.KeyUnitMediaType.KU_MEDIA_IMAGE.toString()),
                String.format(Constant.SEARCH_COND_CLASSIFICATION_NOT_EQUALS, AppDefs.KeyUnitMediaType.KU_MEDIA_PANO.toString()),
                String.format(Constant.SEARCH_COND_CLASSIFICATION_NOT_EQUALS, AppDefs.KeyUnitMediaType.KU_MEDIA_MX.toString()),
                String.format(Constant.SEARCH_COND_CLASSIFICATION_NOT_EQUALS, AppDefs.KeyUnitMediaType.KU_MEDIA_PRE_PALN.toString()));

        List<IMediaInfo> mediaInfos = GeomEleBussiness.getInstance().getKeyUnitMediaInfosByCondition(group, condition, false);
        return  mediaInfos;
    }

    private IMediaInfo getPanoMediaInfo(String uuid, String group) {
        IMediaInfo mediaInfo = null;
        String condition = String.format("%s and %s and %s",
                String.format(Constant.SEARCH_COND_KEY_UNIT_UUID, uuid),
                String.format(Constant.SEARCH_COND_BELONG_TO_UUID, uuid),
                String.format(Constant.SEARCH_COND_CLASSIFICATION, AppDefs.KeyUnitMediaType.KU_MEDIA_PANO.toString()));

        List<IMediaInfo> mediaInfos = GeomEleBussiness.getInstance().getKeyUnitMediaInfosByCondition(group, condition, false);
        if(mediaInfos != null && mediaInfos.size() > 0) {
            mediaInfo = mediaInfos.get(0);
        }

        return  mediaInfo;
    }

    private String getNearDis() {
        String disDesp = "(约200米)";

        String dis = PrefSetting.getInstance().getLoadDis();
        Double disD = Double.valueOf(dis);
        if (disD < 1) {
            disD *= 1000;
            disDesp = "(约" + disD.intValue() + "米)";
        } else {
            disDesp = "(约" + disD.intValue() + "千米)";
        }

        return disDesp;
    }

    public void updateFireNear(HashMap<String, SearchSummaryInfo> summaryMap) {
        ViewGroup ll_fire_info = (ViewGroup)popInfoCon.findViewById(R.id.ll_fire_info);
        ll_fire_info.removeAllViews();

        updateFireNearWs(summaryMap, ll_fire_info);
        updateFireNearZddw(summaryMap, ll_fire_info);
    }

    private void updateFireNearWs(HashMap<String, SearchSummaryInfo> summaryMap, ViewGroup ll_fire_info) {
        int totalCountWs = 0;
        conItemViewListSy.clear();
        for (Dictionary wsDic : LvApplication.getInstance().getEleTypeWsDicList()) {
            SearchSummaryInfo ssi = summaryMap.get(wsDic.getCode());
            if (ssi == null || ssi.getCount() == 0) {
                continue;
            }
            totalCountWs += ssi.getCount();

            String value = "" + ssi.getCount();
            View conItem = getConItemView(wsDic.getValue(), value, wsDic.getResMapId());
            conItem.setTag(ssi);
            conItemViewListSy.add(conItem);
        }

        View water_info = getActivity().getLayoutInflater().inflate(
                R.layout.popup_info_item_near_title, null);

        LinearLayout propertyConView = (LinearLayout)water_info.findViewById(R.id.ll_sub_content);
        initAutoLL(propertyConView, conItemViewListSy);

        ll_fire_info.addView(water_info);

        TextView tv_name = (TextView)water_info.findViewById(R.id.tv_name);
        tv_name.setText("周边水源" + getNearDis());

        TextView tv_value = (TextView)water_info.findViewById(R.id.tv_value);
        tv_value.setText("共" + totalCountWs + "条");
    }

    private void updateFireNearZddw(HashMap<String, SearchSummaryInfo> summaryMap, ViewGroup ll_fire_info) {
        int totalCountWs = 0;
        conItemViewListDw.clear();
        for (Dictionary wsDic : LvApplication.getInstance().getEleTypeZddwDicList()) {
            SearchSummaryInfo ssi = summaryMap.get(wsDic.getCode());
            if (ssi == null || ssi.getCount() == 0) {
                continue;
            }
            totalCountWs += ssi.getCount();

            String value = "" + ssi.getCount();
            View conItem = getConItemView(wsDic.getValue(), value, wsDic.getResMapId());
            conItem.setTag(ssi);
            conItemViewListDw.add(conItem);
        }

        View water_info = getActivity().getLayoutInflater().inflate(
                R.layout.popup_info_item_near_title, null);

        LinearLayout propertyConView = (LinearLayout)water_info.findViewById(R.id.ll_sub_content);
        initAutoLL(propertyConView, conItemViewListDw);

        ll_fire_info.addView(water_info);

        TextView tv_name = (TextView)water_info.findViewById(R.id.tv_name);
        tv_name.setText("周边重点单位" + getNearDis());

        TextView tv_value = (TextView)water_info.findViewById(R.id.tv_value);
        tv_value.setText("共" + totalCountWs + "条");
    }

    private View getConItemView(String name, String count, int resId) {
        View conItem = getActivity().getLayoutInflater().inflate(R.layout.popup_info_item_near_item, null);

        ImageView iv_type = (ImageView)conItem.findViewById(R.id.iv_type);
        iv_type.setImageResource(resId);

        final TextView tv_name = (TextView)conItem.findViewById(R.id.tv_name);
        tv_name.setText(name);

        final TextView tv_value = (TextView)conItem.findViewById(R.id.tv_value);
        tv_value.setText("(" + count + ")");

        conItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchSummaryInfo ssi = (SearchSummaryInfo)v.getTag();
                if (ssi == null) {
                    return;
                }

                for (View view : conItemViewListSy) {
                    TextView tv_name_other = (TextView)view.findViewById(R.id.tv_name);
                    TextView tv_value_other = (TextView)view.findViewById(R.id.tv_value);
                    tv_name_other.setTextColor(Color.rgb(33, 33, 33));
                    tv_value_other.setTextColor(Color.rgb(53, 53, 53));
                }
                for (View view : conItemViewListDw) {
                    TextView tv_name_other = (TextView)view.findViewById(R.id.tv_name);
                    TextView tv_value_other = (TextView)view.findViewById(R.id.tv_value);
                    tv_name_other.setTextColor(Color.rgb(33, 33, 33));
                    tv_value_other.setTextColor(Color.rgb(53, 53, 53));
                }
                tv_name.setTextColor(Color.rgb(0, 116, 199));
                tv_value.setTextColor(Color.rgb(0, 116, 199));

                if (mListener != null) {
                    mListener.onSearchNear(mapSearchInfo, ssi.getType());
                }
            }
        });

        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        conItem.measure(w, h);
        int height = conItem.getMeasuredHeight();
        int width = conItem.getMeasuredWidth();
        conItem.setLayoutParams(new ViewGroup.LayoutParams(width, height));

        return conItem;
    }

    private OnGetGeoCoderResultListener mOnGetGeoCoderResultListener =
            new OnGetGeoCoderResultListener() {

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                return;
            }

            mapSearchInfo.setAddress(StringUtil.getSimpleAddress(result.getAddress()));
            tv_address.setText(mapSearchInfo.getAddress());
        }

        @Override
        public void onGetGeoCodeResult(GeoCodeResult result) {
        }
    };

    private void getAddress(double latitude, double lontitude) {
        LatLng mLatLng = new LatLng(latitude, lontitude);

        ReverseGeoCodeOption mReverseGeoCodeOption = new ReverseGeoCodeOption();
        mReverseGeoCodeOption.location(mLatLng);

        mGeoCoder.reverseGeoCode(mReverseGeoCodeOption);

        mGeoCoder.setOnGetGeoCodeResultListener(mOnGetGeoCoderResultListener);
    }

    public void setListener(OnOutlinLogicListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult result) {
        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
            getAddress(mapSearchInfo.getLat()/1e6, mapSearchInfo.getLng()/1e6);
            return;
        }

        String address = "";
        if (!StringUtil.isEmpty(result.getAddress())) {
            address += result.getAddress() + " ";
        }
        if (!StringUtil.isEmpty(result.getTelephone())) {
            address += result.getTelephone() + " ";
            rl_info33.setTag("来自互联网搜索：" + result.getTelephone());
            rl_info33.setVisibility(View.VISIBLE);
        } else {
            rl_info33.setVisibility(View.GONE);
        }

        address = StringUtil.getSimpleAddress(address);
        tv_address.setText(address);

        mapSearchInfo.setAddress(address);
        if (mListener != null) {
            mListener.onUpdateAddress(mapSearchInfo);
        }
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }

    public void setShowExtra(boolean showExtra) {
        isShowExtra = showExtra;
    }

    public interface OnOutlinLogicListener{
        void onSearchNear(MapSearchInfo msi, String type);
        void onViewPano(String uid, double lat, double lng, String name);
        void onViewPano(double lat, double lng, String name);
        void onViewPanoIndoor(String uid, double lat, double lng, String name);
        void onStartNav(double lat, double lng, String endName);
        void onPopDismiss();
        void onViewDetail(String type, String key, String city);
        void onViewQRCode(String type, String key, String city);
        void onMoveMap(double lat, double lng);
        void onDial(String dialInfo);
        void onExtra(MapSearchInfo msi);
        void onUpdateAddress(MapSearchInfo msi);
    }

    // 绘制自动换行的线性布局
    private void initAutoLL(LinearLayout ll_parent, List<View> childViewList) {
        //每一行的布局，初始化第一行布局
        LinearLayout rowLL = new LinearLayout(getActivity());
        LinearLayout.LayoutParams rowLP =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        rowLP.setMargins(0, 0, 0, 0);
        rowLL.setLayoutParams(rowLP);
        boolean isNewLayout = false;
        float maxWidth = getScreenWidth() - dipToPx(10);
        // 剩下的宽度
        float elseWidth = maxWidth;
        for (int i = 0; i < childViewList.size(); i++) {
            //若当前为新起的一行，先添加旧的那行
            // 然后重新创建布局对象，设置参数，将isNewLayout判断重置为false
            if (isNewLayout) {
                ll_parent.addView(rowLL);
                rowLL = new LinearLayout(getActivity());
                rowLL.setLayoutParams(rowLP);
                isNewLayout = false;
            }
            // 计算是否需要换行
            childViewList.get(i).measure(0, 0);
            // 若是一整行都放不下这个文本框，添加旧的那行，新起一行添加这个文本框
            if (maxWidth < childViewList.get(i).getMeasuredWidth()) {
                ll_parent.addView(rowLL);
                rowLL = new LinearLayout(getActivity());
                rowLL.setLayoutParams(rowLP);
                rowLL.addView(childViewList.get(i));
                isNewLayout = true;
                continue;
            }
            //若是剩下的宽度小于文本框的宽度（放不下了）
            //添加旧的那行，新起一行，但是i要-1，因为当前的文本框还未添加
            if (elseWidth < childViewList.get(i).getMeasuredWidth()) {
                isNewLayout = true;
                i--;
                // 重置剩余宽度
                elseWidth = maxWidth;
                continue;
            } else {
                // 剩余宽度减去文本框的宽度=新的剩余宽度
                elseWidth -= childViewList.get(i).getMeasuredWidth();
                if (rowLL.getChildCount() == 0) {
                    rowLL.addView(childViewList.get(i));
                } else {
                    rowLL.addView(childViewList.get(i));
                }
            }
        }
        // 添加最后一行，但要防止重复添加
        ll_parent.addView(rowLL);
    }

    private float dipToPx(int dipValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dipValue,
                getActivity().getResources().getDisplayMetrics());
    }

    private float getScreenWidth() {
        return getActivity().getResources().getDisplayMetrics().widthPixels;
    }

    // 重点单位 处置要点
    private void reuestDisposalPointByKeyUnit(final String uuid) {
        if (StringUtil.isEmpty(uuid)) {
            return;
        }
        OkHttpClientManager http = OkHttpClientManager.getInstance();
        http.getAsyn(http.convertToURL(String.format(Constant.METHOD_GET_KEY_UNIT_DISPOSAL_POINTS, uuid)), new OkHttpClientManager.ResultCallback<ArrayRestResult>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getActivity(), "网络异常，请检查网络是否正常！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(ArrayRestResult result) {
                if (result != null && result.isSuccess()) {
                    Gson gson = GsonUtil.create();
                    if (result.getContents() != null) {
                        String jsonStr = gson.toJson(result.getContents());
                        Type listType = new TypeToken<List<KeyUnitDisposalPointDTO>>(){}.getType();
                        List<KeyUnitDisposalPointDTO> kuPoints = gson.fromJson(jsonStr, listType);
                        if (kuPoints.isEmpty()) {
                            Toast.makeText(getActivity(), "暂无处置要点！", Toast.LENGTH_SHORT).show();
                        } else {
                            EleCondition ec = new EleCondition();
                            ec.setUuid(uuid);
                            ec.setName(mapSearchInfo.toEleCondition().getName());
                            Intent intent = new Intent(getActivity(), KeyUnitDisposalPointActivity.class);
                            intent.putExtra("EleCondition", ec);
                            intent.putExtra("key_unit_disposal_points", (Serializable) kuPoints);
                            intent.putExtra("key_unit_uuid", uuid);
                            getActivity().startActivity(intent);
                        }

                    } else {
                        Toast.makeText(getActivity(), "暂无处置要点！", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "网络异常，请检查网络是否正常！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
