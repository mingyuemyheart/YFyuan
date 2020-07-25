package com.moft.xfapply.activity.logic;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.moft.xfapply.R;
import com.moft.xfapply.base.ViewLogic;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.utils.CoordinateUtil;
import com.moft.xfapply.widget.ShaderView;
import com.moft.xfapply.widget.dialog.ListDialog;
import com.moft.xfapply.widget.dialog.ListDialog.OnSelectedListener;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MeasureLogic extends ViewLogic  {

    public MeasureLogic(View v, Activity a) {
        super(v, a);
    }

    private View mParentView = null;
    private RelativeLayout mSharderParent = null;
    private BaiduMap mBaiduMap = null;
    private OnMeasureLogicListener mListener = null;
    
    public static final int DIS_TYPE_NONE = 0;
    public static final int DIS_TYPE_DIS = 1;
    public static final int DIS_TYPE_AREA = 2;
    
    private int dis_type = DIS_TYPE_DIS;
    
    private RelativeLayout re_back = null;
    private RelativeLayout re_delete = null;
    private RelativeLayout re_rollback = null;
    private RelativeLayout re_unit = null;
    
    private TextView tv_caldis_result = null;
    private TextView tv_unit = null;    
    
    private int curDisUnitSelected = 1;   
    private int curAreaUnitSelected = 1;  
    private String curDisUnit = "千米";       
    private String curAreaUnit = "平方千米";  
    
    private ArrayList<ArrayList<LatLng>> totalPoints = new ArrayList<ArrayList<LatLng>>();
    private ArrayList<LatLng> points = null;
    
    private List<Overlay> overlayList = new ArrayList<Overlay>();
    
    private ShaderView sv = null;
    private WeakReference<Bitmap> bmp = null;
    private int widths;
    private int heights;
    private Point prevPt = null;

    public void init() { 
        tv_unit = (TextView) getView().findViewById(R.id.tv_unit);
        if (dis_type == DIS_TYPE_DIS) {
            tv_unit.setText(curDisUnit);
        } else if (dis_type == DIS_TYPE_AREA) {
            tv_unit.setText(curAreaUnit);
        }
        
        tv_caldis_result = (TextView) getView().findViewById(R.id.tv_result);
        tv_caldis_result.setText(getCalResult(tv_unit.getText().toString().trim()));
        
        re_back = (RelativeLayout) getView().findViewById(R.id.re_back);
        re_back.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onMeasureCancel();
                }
            }
        });
        
        re_delete = (RelativeLayout) getView().findViewById(R.id.re_delete);
        re_delete.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {           
                clearPoints();
                refreshResult();
            }
        });
        
        re_rollback = (RelativeLayout) getView().findViewById(R.id.re_rollback);
        re_rollback.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) { 
                rollbackOne();  
                draw();
                refreshResult();
            }
        });
        
        re_unit = (RelativeLayout) getView().findViewById(R.id.re_unit);
        re_unit.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) { 
                if (dis_type == DIS_TYPE_DIS) {
                    showDisUnitDialog();
                } else if (dis_type == DIS_TYPE_AREA) {
                    showAreaUnitDialog();
                }
            }
        });
        
        startPoints();
        
        mSharderParent = (RelativeLayout) mParentView.findViewById(R.id.map_content);
    }
    
    public void setDisType(int type) {
        dis_type = type;
    }

    public void endMeasure() {
        clearPoints();
        dis_type = DIS_TYPE_NONE;
    }
    
    private String getCalResult(String unit) {
        double temp = 0;
        DecimalFormat df = new DecimalFormat("###,##0.###");

        for (ArrayList<LatLng> pts : totalPoints) {        
            if (dis_type == DIS_TYPE_DIS) {
                temp += CoordinateUtil.getInstance().getDistance(pts);
            } else if (dis_type == DIS_TYPE_AREA) {
                temp += CoordinateUtil.getInstance().getAreaBL(pts);
            }
        }        
        
        if (dis_type == DIS_TYPE_DIS) {
            if ("千米".equals(unit)) {
                temp = temp / 1000;
            }            
        } else if (dis_type == DIS_TYPE_AREA) {
            if ("平方千米".equals(unit)) {
                temp = temp / 1000000;
            } else if ("亩".equals(unit)) {
                temp = temp * 0.0015;
            } else if ("公顷".equals(unit)) {
                temp = temp * 0.0001;
            }
        }
        
        return df.format(temp);
    }
    
    private void refreshResult() {
        tv_caldis_result.setText(getCalResult(tv_unit.getText().toString().trim())); 
    }
    
    private void startPoints() {
        points = new ArrayList<LatLng>();
        totalPoints.add(points);
    }
    
    public void addPoint(LatLng pt) {
        if(pt == null)
            return;
        //ID:B930294 19-03-27 【移动终端】测量工具点击相同点，描绘崩溃。 王泉
        if(checkPointIsExist(pt)) {
            return;
        }
        points.add(pt);
        
        draw();
        
        refreshResult();
    }

    //ID:B930294 19-03-27 【移动终端】测量工具点击相同点，描绘崩溃。 王泉
    private boolean checkPointIsExist(LatLng pt) {
        boolean ret = false;
        for(LatLng item : points) {
            if(item.longitude == pt.longitude && item.latitude == pt.latitude) {
                ret = true;
                break;
            }
        }
        return ret;
    }
    
    public void dragMarkStart(Marker marker) {
        View view = getActivity().getWindow().getDecorView();
        view.buildDrawingCache();
        
        Display display = getActivity().getWindowManager().getDefaultDisplay();

        widths = display.getWidth();
        heights = display.getHeight();

        view.setDrawingCacheEnabled(true);

        bmp = new WeakReference<Bitmap>(Bitmap.createBitmap(view.getDrawingCache(), 0, 0, widths, heights));
        
        sv = new ShaderView(getActivity());
        sv.setBitmap(bmp.get());
        mSharderParent.addView(sv, -1);

        prevPt = mBaiduMap.getProjection().toScreenLocation(marker.getPosition());
    }
    
    public void dragMark(Marker marker) {
        View view = getActivity().getWindow().getDecorView();

        bmp.clear();
        bmp = new WeakReference<Bitmap>(Bitmap.createBitmap(view.getDrawingCache(), 0, 0, widths, heights));

        Point pt = mBaiduMap.getProjection().toScreenLocation(marker.getPosition());
        sv.setBitmap(bmp.get());
        sv.setMoving(pt.x - prevPt.x, pt.y- prevPt.y);
        prevPt = pt;
    }
    
    public void dragMarkEnd(Marker marker) {
        View view = getActivity().getWindow().getDecorView();
        view.destroyDrawingCache();

        mSharderParent.removeView(sv);
        bmp.clear();
        
        prevPt = null;
    }
    
    private void draw() {
        if (dis_type != DIS_TYPE_DIS && dis_type != DIS_TYPE_AREA) {
            return;
        }

        if(totalPoints == null || totalPoints.size() == 0) {
            return;
        }
        
        for (Overlay ol : overlayList) {
            ol.remove();
        }
        overlayList.clear();
         
        for (ArrayList<LatLng> pts : totalPoints) { 
            if(pts == null || pts.size() == 0) {
                continue;
            }
            
            int index = 0;
            for (LatLng pt : pts) {
                int resId = 0;
                if (index == 0) {
                    resId = R.drawable.rg_ic_turn_start_s;
                } else if (index == (pts.size() - 1)) {
                    resId = R.drawable.rg_ic_turn_dest_s;
                } else {
                    resId = R.drawable.rg_ic_turn_mid_s;
                }
                index++;
                
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(resId);
                
                OverlayOptions option = new MarkerOptions().position(pt)
                        .icon(bitmap).anchor(0.5f, 0.5f);
                
                Overlay ol = mBaiduMap.addOverlay(option);
                overlayList.add(ol);

                if (dis_type == DIS_TYPE_DIS) {
                    if (pts.size() >= 2) {                
                        OverlayOptions polygonOption = new PolylineOptions()  
                                .points(pts)  
                                .color(0xAA00FF00).width(10);  
                        Overlay olLine = mBaiduMap.addOverlay(polygonOption);
                        overlayList.add(olLine);
                    }
                } else if (dis_type == DIS_TYPE_AREA) {
                    if (pts.size() >= 3) {                
                        OverlayOptions polygonOption = new PolygonOptions()  
                                .points(pts)  
                                .stroke(new Stroke(5, 0xAA00FF00))  
                                .fillColor(0xAAFFFF00);  
                        Overlay olLine = mBaiduMap.addOverlay(polygonOption);
                        overlayList.add(olLine);
                    }   
                }
            }
        }
    }
        
    private void clearPoints() {
        totalPoints.clear();
        
        points = new ArrayList<LatLng>();
        totalPoints.add(points);
        
        for (Overlay ol : overlayList) {
            ol.remove();
        }
        overlayList.clear();
    }
    
    private void rollbackOne() {
        int size = points.size();
        
        if (size > 0) {
            points.remove(size-1);
        } else {
            totalPoints.remove(points);
            if (totalPoints.size() > 0) {
                points = totalPoints.get(totalPoints.size() - 1);
                rollbackOne();
            } else {
                points = new ArrayList<LatLng>();
                totalPoints.add(points);
            }
        }
    }
    
    private void showDisUnitDialog() {        
        final List<Dictionary> contentList = new ArrayList<Dictionary>();
        contentList.add(new Dictionary("米", ""));
        contentList.add(new Dictionary("千米", ""));
        
        ListDialog.show(getActivity(), contentList, curDisUnitSelected, new OnSelectedListener() {
            @Override
            public void onSelected(int position) {
                curDisUnitSelected = position;
                curDisUnit = contentList.get(position).getValue();
                tv_unit.setText(curDisUnit);
                tv_caldis_result.setText(getCalResult(curDisUnit)); 
            }
        });
    }

    private void showAreaUnitDialog() {        
        final List<Dictionary> contentList = new ArrayList<Dictionary>();
        contentList.add(new Dictionary("平方米", ""));
        contentList.add(new Dictionary("平方千米", ""));
        contentList.add(new Dictionary("亩", ""));
        contentList.add(new Dictionary("公顷", ""));
        
        ListDialog.show(getActivity(), contentList, curAreaUnitSelected, new OnSelectedListener() {
            @Override
            public void onSelected(int position) {
                curAreaUnitSelected = position;
                curAreaUnit = contentList.get(position).getValue();
                tv_unit.setText(curAreaUnit);
                tv_caldis_result.setText(getCalResult(curAreaUnit)); 
            }
        });
    }

    public OnMeasureLogicListener getListener() {
        return mListener;
    }

    public void setListener(OnMeasureLogicListener mListener) {
        this.mListener = mListener;
    }

    public BaiduMap getBaiduMap() {
        return mBaiduMap;
    }

    public void setBaiduMap(BaiduMap mBaiduMap) {
        this.mBaiduMap = mBaiduMap;
    }

    public RelativeLayout getSharderParent() {
        return mSharderParent;
    }

    public void setSharderParent(RelativeLayout mSharderParent) {
        this.mSharderParent = mSharderParent;
    }

    public View getParentView() {
        return mParentView;
    }

    public void setParentView(View mParentView) {
        this.mParentView = mParentView;
    }

    public interface OnMeasureLogicListener{
        void onMeasureCancel();
    }
}
