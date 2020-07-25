package com.moft.xfapply.activity.logic;

import android.util.Log;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.SuggestAddrInfo;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.moft.xfapply.model.common.RoutePlanInfo;

import java.util.List;

/**
 * Created by sxf on 2019-04-25.
 */

public class RouteSearch implements
        OnGetRoutePlanResultListener {
    public RouteSearch() {
    }

    private RoutePlanInfo mRoutePlanInfo = null;
    private RoutePlanSearch mSearch = null;

    private OnRouteLogicListener listener;
    private DrivingRoutePlanOption.DrivingPolicy drivingPolicy =
            DrivingRoutePlanOption.DrivingPolicy.ECAR_TIME_FIRST;
    private DrivingRoutePlanOption.DrivingTrafficPolicy trafficPolicy =
            DrivingRoutePlanOption.DrivingTrafficPolicy.ROUTE_PATH_AND_TRAFFIC;

    public void setDrivingPolicy(DrivingRoutePlanOption.DrivingPolicy drivingPolicy) {
        this.drivingPolicy = drivingPolicy;
    }
    public void setTrafficPolicy(DrivingRoutePlanOption.DrivingTrafficPolicy trafficPolicy) {
        this.trafficPolicy = trafficPolicy;
    }

    public void init(boolean isDurationPriority, boolean isHaveTraffic) {
        // 初始化搜索模块，注册事件监听
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);

        if (isDurationPriority) {
            drivingPolicy = DrivingRoutePlanOption.DrivingPolicy.ECAR_TIME_FIRST;
        } else {
            drivingPolicy = DrivingRoutePlanOption.DrivingPolicy.ECAR_DIS_FIRST;
        }
        if (isHaveTraffic) {
            trafficPolicy = DrivingRoutePlanOption.DrivingTrafficPolicy.ROUTE_PATH_AND_TRAFFIC;
        } else {
            trafficPolicy = DrivingRoutePlanOption.DrivingTrafficPolicy.ROUTE_PATH;
        }
    }

    public void planRoute(LatLng start, LatLng end, OnRouteLogicListener listener) {
        this.listener = listener;

        // 设置起终点信息
        PlanNode stNode = PlanNode.withLocation(start);
        PlanNode enNode = PlanNode.withLocation(end);

        // 规划路线
        mSearch.drivingSearch((new DrivingRoutePlanOption())
                .from(stNode).to(enNode).policy(drivingPolicy)
                .trafficPolicy(trafficPolicy));
    }

    public void destroy() {
        if (mSearch != null) {
            mSearch.destroy();
        }
    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult result) {
        if (listener == null) {
            return;
        }
        if (result == null) {
            listener.onError();
            return;
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义
            listener.onAmbiguousRoute(result.getSuggestAddrInfo());
            return;
        }
        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
            listener.onError();
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            if (result.getRouteLines() == null) {
                listener.onError();
                return;
            }
            listener.onSuccess(result.getRouteLines());
        }
    }

    public void outputRouteLinesInfo(List<DrivingRouteLine> routeLines) {
        int i = 0;
        for (DrivingRouteLine routeLine : routeLines) {
            Log.e("RouteLinesInfo", "线路" + i++);
            Log.e("RouteLinesInfo", "红绿灯数：" + routeLine.getLightNum());
            Log.e("RouteLinesInfo", "拥堵距离为：" + routeLine.getCongestionDistance() + "米");
            Log.e("RouteLinesInfo", "Title：" + routeLine.getTitle());
            Log.e("RouteLinesInfo", "describeContents：" + routeLine.describeContents());
            Log.e("RouteLinesInfo", "距离大约是：" + routeLine.getDistance() + "米");
            int time = routeLine.getDuration();
            if ( time / 3600 == 0 ) {
                Log.e("RouteLinesInfo", "大约需要：" + time / 60 + "分钟" );
            } else {
                Log.e("RouteLinesInfo", "大约需要：" + time / 3600 + "小时" + (time % 3600) / 60 + "分钟" );
            }
        }
    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult result) {
    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult result) {
    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult result) {
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult result) {
    }

    public RoutePlanInfo getRoutePlanInfo() {
        return mRoutePlanInfo;
    }

    public void setRoutePlanInfo(RoutePlanInfo mRoutePlanInfo) {
        this.mRoutePlanInfo = mRoutePlanInfo;
    }

    public interface OnRouteLogicListener{
        void onError();
        void onAmbiguousRoute(SuggestAddrInfo suggestAddrInfo);
        void onSuccess(List<DrivingRouteLine> routeLines);
    }
}
