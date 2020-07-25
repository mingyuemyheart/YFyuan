package com.moft.xfapply.widget.route;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRouteLine.DrivingStep;
import com.moft.xfapply.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于显示一条驾车路线的overlay，自3.4.0版本起可实例化多个添加在地图中显示，当数据中包含路况数据时，则默认使用路况纹理分段绘制
 */
public class DrivingRouteOverlay extends OverlayManager {

    private DrivingRouteLine mRouteLine = null;
    boolean focus = false;
    private String name = "";

    /**
     * 构造函数
     * 
     * @param baiduMap
     *            该DrivingRouteOvelray引用的 BaiduMap
     */
    public DrivingRouteOverlay(BaiduMap baiduMap) {
        super(baiduMap);
    }

    @Override
    public final List<OverlayOptions> getOverlayOptions() {
        if (mRouteLine == null) {
            return null;
        }

        int zIndex = focus ? -4 : -5;

        List<OverlayOptions> overlayOptionses = new ArrayList<>();
        // step node
        if (mRouteLine.getAllStep() != null
                && mRouteLine.getAllStep().size() > 0) {
            for (DrivingStep step : mRouteLine.getAllStep()) {
                Bundle b = new Bundle();
                b.putInt("index", mRouteLine.getAllStep().indexOf(step));
                if (step.getEntrance() != null) {
                    overlayOptionses.add((new MarkerOptions())
                            .position(step.getEntrance().getLocation())
                            .anchor(0.5f, 0.5f)
                            .zIndex(zIndex)
                            .rotate((360 - step.getDirection()))
                            .extraInfo(b)
                            .icon(BitmapDescriptorFactory.fromAssetWithDpi("Icon_line_node.png")));
                }
                // 最后路段绘制出口点
                if (mRouteLine.getAllStep().indexOf(step) == (mRouteLine
                        .getAllStep().size() - 1) && step.getExit() != null) {
                    overlayOptionses.add((new MarkerOptions())
                    .position(step.getExit().getLocation())
                    .anchor(0.5f, 0.5f)
                    .zIndex(zIndex)
                    .icon(BitmapDescriptorFactory.fromAssetWithDpi("Icon_line_node.png")));

                }
            }
        }
        // poly line
        if (mRouteLine.getAllStep() != null
                && mRouteLine.getAllStep().size() > 0) {

            List<DrivingStep> steps = mRouteLine.getAllStep();
            int stepNum = steps.size();


            List<LatLng> points = new ArrayList<>();
            ArrayList<Integer> traffics = new ArrayList<>();
            for (int i = 0; i < stepNum ; i++) {
                DrivingStep drivingStep = steps.get(i);
                if (i == stepNum - 1) {
                    points.addAll(drivingStep.getWayPoints());
                } else {
                    points.addAll(drivingStep.getWayPoints().subList(0, drivingStep.getWayPoints().size() - 1));
                }

                int[] trafficList = drivingStep.getTrafficList();
                if (trafficList != null && trafficList.length > 0) {
                    for (int j = 0; j < trafficList.length; j++) {
                        traffics.add(trafficList[j]);
                    }
                }
            }

            boolean isDotLine = false;
            if (traffics != null && traffics.size() > 0) {
                isDotLine = true;
            }
            List<BitmapDescriptor> customTextureList = null;
            int lineColor = 0;
            if (focus) {
                customTextureList = getCustomTextureList();
                lineColor = getLineColor();
            } else {
                customTextureList = getUnfocusCustomTextureList();
                lineColor = Color.rgb(151, 160, 220);
            }
            PolylineOptions option = new PolylineOptions().points(points).textureIndex(traffics)
                    .width(20).dottedLine(isDotLine).focus(true)
                    .color(lineColor).zIndex(zIndex-1);
            if (isDotLine) {
                option.customTextureList(customTextureList);
            }
            overlayOptionses.add(option);
        }

        if (mRouteLine.getStarting() != null) {
            overlayOptionses.add((new MarkerOptions())
                    .position(mRouteLine.getStarting().getLocation())
                    .icon(getStartMarker() != null ? getStartMarker() :
                    BitmapDescriptorFactory.fromAssetWithDpi("Icon_start.png"))
                    .zIndex(zIndex));

            // 起点提示文本框
            LatLng llText = mRouteLine.getStarting().getLocation();
            String text = " " +  name + "  ";
            text += "\\";
            text += " " +  StringUtil.getDurationDesc(mRouteLine.getDuration()) + "  ";
            text += "\\";
            text += " " +  StringUtil.getDistanceDesc(mRouteLine.getDistance()) + "  ";
            overlayOptionses.add(new TextOptions()
                    .text(text)
                    .align(TextOptions.ALIGN_CENTER_HORIZONTAL, TextOptions.ALIGN_TOP)
                    .bgColor(Color.rgb(255, 255, 0))
                    .fontSize(36)
                    .fontColor(Color.rgb(127, 127, 127))
                    .position(llText)
                    .zIndex(zIndex));
        }
        if (mRouteLine.getTerminal() != null) {
            overlayOptionses
                    .add((new MarkerOptions())
                    .position(mRouteLine.getTerminal().getLocation())
                    .icon(getTerminalMarker() != null ? getTerminalMarker() :
                    BitmapDescriptorFactory.fromAssetWithDpi("Icon_end.png"))
                    .zIndex(zIndex));
        }
        return overlayOptionses;
    }

    /**
     * 设置路线数据
     * 
     * @param routeLine
     *            路线数据
     */
    public void setData(DrivingRouteLine routeLine) {
        this.mRouteLine = routeLine;
    }

    /**
     * 覆写此方法以改变默认起点图标
     * 
     * @return 起点图标
     */
    public BitmapDescriptor getStartMarker() {
        return null;
    }

    /**
     * 覆写此方法以改变默认绘制颜色
     * @return 线颜色
     */
    public int getLineColor() {
        return 0;
    }

    public List<BitmapDescriptor> getCustomTextureList() {
        ArrayList<BitmapDescriptor> list = new ArrayList<BitmapDescriptor>();
        list.add(BitmapDescriptorFactory.fromAsset("Icon_road_blue_arrow.png"));
        list.add(BitmapDescriptorFactory.fromAsset("Icon_road_green_arrow.png"));
        list.add(BitmapDescriptorFactory.fromAsset("Icon_road_yellow_arrow.png"));
        list.add(BitmapDescriptorFactory.fromAsset("Icon_road_red_arrow.png"));
        list.add(BitmapDescriptorFactory.fromAsset("Icon_road_nofocus.png"));
        return list;
    }

    public List<BitmapDescriptor> getUnfocusCustomTextureList() {
        ArrayList<BitmapDescriptor> list = new ArrayList<BitmapDescriptor>();
        list.add(BitmapDescriptorFactory.fromAsset("Icon_road_blue_arrow_unfocus.png"));
        list.add(BitmapDescriptorFactory.fromAsset("Icon_road_green_arrow_unfocus.png"));
        list.add(BitmapDescriptorFactory.fromAsset("Icon_road_yellow_arrow_unfocus.png"));
        list.add(BitmapDescriptorFactory.fromAsset("Icon_road_red_arrow_unfocus.png"));
        list.add(BitmapDescriptorFactory.fromAsset("Icon_road_nofocus_unfocus.png"));
        return list;
    }
    /**
     * 覆写此方法以改变默认终点图标
     * 
     * @return 终点图标
     */
    public BitmapDescriptor getTerminalMarker() {
        return null;
    }

    /**
     * 覆写此方法以改变默认点击处理
     * 
     * @param i
     *            线路节点的 index
     * @return 是否处理了该点击事件
     */
    public boolean onRouteNodeClick(int i) {
        if (mRouteLine.getAllStep() != null
                && mRouteLine.getAllStep().get(i) != null) {
        }
        return false;
    }

    public void setFocus(boolean flag) {
        focus = flag;
    }

    public boolean getFocus() {
        return focus;
    }

    public void setName(String name) {
        this.name = name;
    }
}
