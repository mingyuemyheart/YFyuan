package com.moft.xfapply.activity.logic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.SuggestAddrInfo;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.bussiness.GeomEleBussiness;
import com.moft.xfapply.base.ViewLogic;
import com.moft.xfapply.model.common.DisasterInfo;
import com.moft.xfapply.model.common.QueryCondition;
import com.moft.xfapply.model.common.RoutePlanInfo;
import com.moft.xfapply.model.entity.GeomElementDBInfo;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.utils.Utils;
import com.moft.xfapply.widget.route.RouteLineAdapter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by sxf on 2019-04-26.
 */

public class RoutePlanLogic extends ViewLogic {
    public RoutePlanLogic(View v, Activity a) {
        super(v, a);
    }

    private OnRoutePlanLogicListener mListener = null;
    private DisasterInfo disasterInfo = null;
    private List<RouteSearch> mRouteSearchList = new ArrayList<>();
    private List<RoutePlanInfo> mRoutePlanInfoList = new ArrayList<>();

    private PopupWindow popInfo = null;
    private View popInfoView = null;
    private LinearLayout popup_content = null;
    private LinearLayout popInfoCon = null;

    private View re_disaster = null;
    private View re_map = null;
    private View re_process = null;
    private View re_avatar = null;
    private TextView tv_name = null;
    private TextView tv_address = null;
    private ListView list = null;
    private View re_time = null;
    private TextView tv_time = null;
    private ImageView iv_time_radio = null;
    private View re_dis = null;
    private TextView tv_dis = null;
    private ImageView iv_dis_radio = null;
    private View re_lukuang = null;
    private TextView tv_lukuang = null;
    private ImageView iv_lukuang_radio = null;

    private RouteLineAdapter adapter = null;
    private boolean isDurationPriority = true;
    private boolean isHaveTraffic = true;
    private boolean isLoadData = false;
    private int nCount = 0;

    public List<RoutePlanInfo> getRoutePlanInfoList() {
        return mRoutePlanInfoList;
    }

    public void init() {
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);

        popInfoView = getActivity().getLayoutInflater().inflate(R.layout.popup_info, null);
        popup_content = (LinearLayout) popInfoView.findViewById(R.id.popup_content);

        popInfo = new PopupWindow(popInfoView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popInfo.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popInfo.setOutsideTouchable(false);
        popInfo.setTouchable(true);
        popInfo.setAnimationStyle(R.style.AnimBottom);
        popInfo.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popInfo.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        popInfo.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });

        popInfoCon = (LinearLayout) getActivity().getLayoutInflater().inflate(
                R.layout.popup_route_plan, null);
        popup_content.addView(popInfoCon);

        try {
            Method method = PopupWindow.class.getDeclaredMethod("setTouchModal", boolean.class);
            method.setAccessible(true);
            method.invoke(popInfo, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadData();
    }

    public void show() {
        if (disasterInfo == null) {
            Toast.makeText(getActivity(), "灾害对象未设定！",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isLoadData) {
            re_map = popInfoCon.findViewById(R.id.re_map);
            re_process = popInfoCon.findViewById(R.id.re_process);
            re_avatar = popInfoCon.findViewById(R.id.re_avatar);
            tv_name = (TextView) popInfoCon.findViewById(R.id.tv_name);
            tv_address = (TextView) popInfoCon.findViewById(R.id.tv_address);

            re_process.setVisibility(View.VISIBLE);
            re_avatar.setVisibility(View.GONE);
            re_map.setVisibility(View.GONE);
            re_map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectNumber();
                }
            });
            if (!StringUtil.isEmpty(disasterInfo.getName())) {
                tv_name.setText(disasterInfo.getName());
            } else {
                tv_name.setText("未知名称");
            }
            tv_address.setText(disasterInfo.getAddress());

            re_disaster = popInfoCon.findViewById(R.id.re_disaster);
            re_disaster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (nCount >= mRoutePlanInfoList.size()) {
                        for (RoutePlanInfo rpi : mRoutePlanInfoList) {
                            rpi.iniRouteInfo();
                        }
                        planStart();
                    } else {
                        Toast.makeText(getActivity(), "行车路线规划中...",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

            list = (ListView) popInfoCon.findViewById(R.id.list);
            adapter = new RouteLineAdapter(getActivity(), mRoutePlanInfoList);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (nCount < mRoutePlanInfoList.size()) {
                        Toast.makeText(getActivity(), "行车路线规划中...",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    boolean isLoadFailure = false;
                    RoutePlanInfo rpi = mRoutePlanInfoList.get(i);
                    if (isDurationPriority) {
                        if (rpi.getDuration() == -1) {
                            isLoadFailure = true;
                        }
                    } else {
                        if (rpi.getDistance() == -1) {
                            isLoadFailure = true;
                        }
                    }

                    if (isLoadFailure) {
                        planStart(rpi);
                    } else {
                        hide();
                        if (mListener != null) {
                            mListener.onChangeToMap(i);
                        }
                    }
                }
            });

            tv_time = (TextView) popInfoCon.findViewById(R.id.tv_time);
            iv_time_radio = (ImageView) popInfoCon.findViewById(R.id.iv_time_radio);
            re_time = popInfoCon.findViewById(R.id.re_time);
            re_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (nCount < mRoutePlanInfoList.size()) {
                        Toast.makeText(getActivity(), "行车路线规划中...",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (isDurationPriority) {
                        return;
                    }
                    isDurationPriority = true;
                    refreshPriorityView();
                    planPriority();
                }
            });

            tv_dis = (TextView) popInfoCon.findViewById(R.id.tv_dis);
            iv_dis_radio = (ImageView) popInfoCon.findViewById(R.id.iv_dis_radio);
            re_dis = popInfoCon.findViewById(R.id.re_dis);
            re_dis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (nCount < mRoutePlanInfoList.size()) {
                        Toast.makeText(getActivity(), "行车路线规划中...",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!isDurationPriority) {
                        return;
                    }
                    isDurationPriority = false;
                    refreshPriorityView();
                    planPriority();
                }
            });

            tv_lukuang = (TextView) popInfoCon.findViewById(R.id.tv_lukuang);
            iv_lukuang_radio = (ImageView) popInfoCon.findViewById(R.id.iv_lukuang_radio);
            re_lukuang = popInfoCon.findViewById(R.id.re_lukuang);
            re_lukuang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (nCount < mRoutePlanInfoList.size()) {
                        Toast.makeText(getActivity(), "行车路线规划中...",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    isHaveTraffic = !isHaveTraffic;
                    refreshHaveTrafficView();
                    re_process.setVisibility(View.VISIBLE);
                    re_avatar.setVisibility(View.GONE);
                    re_map.setVisibility(View.GONE);

                    for (RoutePlanInfo rpi : mRoutePlanInfoList) {
                        rpi.iniRouteInfo();
                    }
                    planStart();
                }
            });
            refreshPriorityView();
            refreshHaveTrafficView();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    planStart();
                }
            }, 100);
        }

        hide();
        popInfo.showAtLocation(getView(), Gravity.TOP, 0, 0);
    }

    private void refreshPriorityView() {
        if (isDurationPriority) {
            tv_time.setTextColor(Color.rgb(0, 116, 199));
            iv_time_radio.setImageResource(R.drawable.icon_radio_sel);
            tv_dis.setTextColor(Color.rgb(133, 133, 133));
            iv_dis_radio.setImageResource(R.drawable.icon_radio_unsel);
        } else {
            tv_dis.setTextColor(Color.rgb(0, 116, 199));
            iv_dis_radio.setImageResource(R.drawable.icon_radio_sel);
            tv_time.setTextColor(Color.rgb(133, 133, 133));
            iv_time_radio.setImageResource(R.drawable.icon_radio_unsel);
        }
    }

    private void refreshHaveTrafficView() {
        if (isHaveTraffic) {
            tv_lukuang.setText("含路况");
            tv_lukuang.setTextColor(Color.rgb(0, 116, 199));
            iv_lukuang_radio.setImageResource(R.drawable.icon_checkbox_sel);
        } else {
            tv_lukuang.setText("不含路况");
            tv_lukuang.setTextColor(Color.rgb(133, 133, 133));
            iv_lukuang_radio.setImageResource(R.drawable.icon_checkbox_unsel);
        }
    }

    private void loadData() {
        GeomEleBussiness gb = GeomEleBussiness.getInstance();
        QueryCondition qc = new QueryCondition();
        qc.setType(QueryCondition.TYPE_SYLX);
        qc.setSylxCode(AppDefs.CompEleType.SQUADRON.toString());
        List<GeomElementDBInfo> geoList = gb.loadGeomElement(qc);
        if (geoList == null) {
            Toast.makeText(getActivity(), "未取得队站位置信息！",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        mRoutePlanInfoList.clear();
        for (GeomElementDBInfo geo : geoList) {
            if (!geo.isGeoPosValid()) {
                continue;
            }
            RoutePlanInfo routePlanInfo = new RoutePlanInfo();
            routePlanInfo.setGeoElement(geo);
            mRoutePlanInfoList.add(routePlanInfo);
        }
    }

    private void refreshResult() {
        nCount++;
        if (nCount >= mRoutePlanInfoList.size()) {
            re_process.setVisibility(View.GONE);
            re_avatar.setVisibility(View.VISIBLE);
            re_map.setVisibility(View.VISIBLE);

            if (mListener != null) {
                mListener.onRoutePlanOk(mRoutePlanInfoList);
            }
        } else {
            re_process.setVisibility(View.VISIBLE);
            re_avatar.setVisibility(View.GONE);
            re_map.setVisibility(View.GONE);
        }
    }

    private void planPriority() {
        for (RoutePlanInfo rpi : mRoutePlanInfoList) {
            rpi.setDurationPriority(isDurationPriority);
        }
        Collections.sort(mRoutePlanInfoList, new RoutePlanInfoComparetor());
        adapter.notifyDataSetChanged();
    }

    private void planStart(RoutePlanInfo rpi) {
        double geoLat = Utils.convertToDouble(rpi.getGeoElement().getLatitude());
        double geoLng = Utils.convertToDouble(rpi.getGeoElement().getLongitude());
        final RouteSearch routeSearch = new RouteSearch();
        routeSearch.setRoutePlanInfo(rpi);
        routeSearch.init(isDurationPriority, isHaveTraffic);
        routeSearch.planRoute(new LatLng(geoLat, geoLng),
                new LatLng(disasterInfo.getLat(), disasterInfo.getLng()),
                new RouteSearch.OnRouteLogicListener() {
                    @Override
                    public void onError() {
                        Toast.makeText(getActivity(), "路线获取失败！",
                                Toast.LENGTH_SHORT).show();
                        refreshResult();
                    }

                    @Override
                    public void onAmbiguousRoute(SuggestAddrInfo suggestAddrInfo) {
                        Toast.makeText(getActivity(), "不能确定灾害地点准确位置！",
                                Toast.LENGTH_SHORT).show();
                        refreshResult();
                    }

                    @Override
                    public void onSuccess(List<DrivingRouteLine> routeLines) {
                        refreshResult();
                        RoutePlanInfo routePlanInfo = routeSearch.getRoutePlanInfo();
                        routePlanInfo.setRouteLines(routeLines);
                        routePlanInfo.setDurationPriority(isDurationPriority);
                        Collections.sort(mRoutePlanInfoList, new RoutePlanInfoComparetor());
                        adapter.notifyDataSetChanged();
                    }
                });
        mRouteSearchList.add(routeSearch);
    }

    private void planStart() {
        nCount = 0;
        isLoadData = true;
        adapter.notifyDataSetChanged();
        for (RoutePlanInfo rpi : mRoutePlanInfoList) {
            planStart(rpi);
        }
    }

    public void hide() {
        if (popInfo != null && popInfo.isShowing()) {
            popInfo.dismiss();
        }
    }

    public void destroy() {
        for (RouteSearch rs : mRouteSearchList) {
            rs.destroy();
        }
        mRouteSearchList.clear();
    }

    public void setDisasterInfo(final DisasterInfo disasterInfo) {
        this.isLoadData = false;
        this.disasterInfo = disasterInfo;
        for (RoutePlanInfo rpi : mRoutePlanInfoList) {
            rpi.iniRouteInfo();
        }
        Collections.sort(mRoutePlanInfoList, new FirstRoutePlanInfoComparetor());
    }

    private int selectNumber = 1;
    private void selectNumber() {
        selectNumber = 1;
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View fourView = inflater.inflate(R.layout.pop_route_number_select, null);
        final AlertDialog dlg = new AlertDialog.Builder(getActivity()).create();
        dlg .setView((getActivity().getLayoutInflater().inflate(
                R.layout.pop_route_number_select, null)));
        dlg .show();
        dlg .getWindow().setContentView(fourView);

        final TextView tv_number = (TextView) fourView.findViewById(R.id.tv_number);
        tv_number.setText("" + selectNumber);

        final ImageView iv_sub = (ImageView)fourView.findViewById(R.id.iv_sub);
        final ImageView iv_plus = (ImageView)fourView.findViewById(R.id.iv_plus);

        View re_sub = fourView.findViewById(R.id.re_sub);
        re_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectNumber -= 1;
                if (selectNumber <= 0) {
                    selectNumber = 0;
                    iv_sub.setImageResource(R.drawable.icon_sub_no);
                } else {
                    iv_sub.setImageResource(R.drawable.icon_sub);
                }
                if (selectNumber >= mRoutePlanInfoList.size()) {
                    iv_plus.setImageResource(R.drawable.icon_plus_no);
                } else {
                    iv_plus.setImageResource(R.drawable.icon_plus);
                }
                tv_number.setText("" + selectNumber);
            }
        });
        View re_plus = fourView.findViewById(R.id.re_plus);
        re_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectNumber += 1;
                if (selectNumber >= mRoutePlanInfoList.size()) {
                    selectNumber = mRoutePlanInfoList.size();
                    iv_plus.setImageResource(R.drawable.icon_plus_no);
                } else {
                    iv_plus.setImageResource(R.drawable.icon_plus);
                }
                if (selectNumber <= 0) {
                    iv_sub.setImageResource(R.drawable.icon_sub_no);
                } else {
                    iv_sub.setImageResource(R.drawable.icon_sub);
                }
                tv_number.setText("" + selectNumber);
            }
        });

        View re_ok = fourView.findViewById(R.id.re_ok);
        re_ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dlg.dismiss();
                hide();
                if (mListener != null) {
                    mListener.onChangeToMap((-1)*selectNumber);
                }
            }
        });

        View re_cancel = fourView.findViewById(R.id.re_cancel);
        re_cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });
    }

    public void setListener(OnRoutePlanLogicListener mListener) {
        this.mListener = mListener;
    }

    public interface OnRoutePlanLogicListener{
        void onRoutePlanOk(List<RoutePlanInfo> dataList);
        void onChangeToMap(int pos);
    }

    class RoutePlanInfoComparetor implements Comparator<RoutePlanInfo> {
        @Override
        public int compare(RoutePlanInfo rpi1, RoutePlanInfo rpi2) {
            if (isDurationPriority) {
                return rpi1.getDurationForSort() - rpi2.getDurationForSort();
            } else {
                return rpi1.getDistanceForSort() - rpi2.getDistanceForSort();
            }
        }
    }

    class FirstRoutePlanInfoComparetor implements Comparator<RoutePlanInfo> {
        @Override
        public int compare(RoutePlanInfo rpi1, RoutePlanInfo rpi2) {
            return (int) (rpi1.getDistance(disasterInfo.getLng(), disasterInfo.getLat()) -
                    rpi2.getDistance(disasterInfo.getLng(), disasterInfo.getLat()));
        }
    }
}
