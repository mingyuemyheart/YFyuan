package com.moft.xfapply.activity.logic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.HcDetailActivity;
import com.moft.xfapply.activity.WsDetailActivity;
import com.moft.xfapply.activity.adapter.WsRecordAdapter;
import com.moft.xfapply.activity.bussiness.GeomEleBussiness;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.base.ViewLogic;
import com.moft.xfapply.logic.setting.PrefSetting;
import com.moft.xfapply.model.common.QueryCondition;
import com.moft.xfapply.model.common.WhpViewInfo;
import com.moft.xfapply.model.entity.GeomElementDBInfo;
import com.moft.xfapply.widget.XListView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sxf on 2019-04-04.
 */

public class SearchRecordLogic extends ViewLogic implements
        XListView.IXListViewListener {
    public SearchRecordLogic(View v, Activity a) {
        super(v, a);
    }

    private View mViewSelf = null;
    private View re_select_con = null;
    private TextView tv_result = null;

    private XListView listView;
    private WsRecordAdapter adapter = null;
    private WsRecordAdapter.OnItemSelectedListener mOnItemSelectedListener = null;
    private static final int REQUEST_OPERATION_RESULT = 0;

    private Handler mHandler;
    private int page = 0;
    private int pageItem = 20;
    private String mKey = "";

    private ArrayList<GeomElementDBInfo> vrList = new ArrayList<GeomElementDBInfo>();
    private int totalCount = 0;

    private QueryCondition mCondition = null;
    private OnWsRecordSearchLogicListener mListener = null;

    public void init() {
        pageItem = PrefSetting.getInstance().getPageNum();

        mViewSelf = getActivity().getLayoutInflater().inflate(R.layout.xs_record_search, null);

        re_select_con = (View) mViewSelf.findViewById(R.id.re_select_con);
        re_select_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onSearchChanged(1);
                }
            }
        });

        tv_result = (TextView) mViewSelf.findViewById(R.id.tv_result);
        tv_result.setText("(共?条)");

        LinearLayout ll_content = (LinearLayout) getView();
        ll_content.addView(mViewSelf);

        listView = (XListView) mViewSelf.findViewById(R.id.list);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mListener != null) {
                    mListener.onKeyBoardClose();
                }
                return false;
            }
        });
        listView.setPullLoadEnable(true);
        listView.setXListViewListener(this);
        mHandler = new Handler();

        mOnItemSelectedListener = new WsRecordAdapter.OnItemSelectedListener() {
            @Override
            public void onItemClick(GeomElementDBInfo dto) {
                if (mListener != null) {
                    boolean isStop = mListener.onPreStartDetailActivity(dto);
                    if (isStop) {
                        return;
                    }
                }

                if (Constant.HAZARD_CHEMICAL.equals(dto.getEleType())) {
                    WhpViewInfo info = new WhpViewInfo();
                    info.setUuid(dto.getUuid());
                    info.setType(Integer.valueOf(dto.getCode()));

                    Intent intent = new Intent(getActivity(), HcDetailActivity.class);
                    intent.putExtra("WhpViewInfo", (Serializable)info);
                    getActivity().startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), WsDetailActivity.class);
                    intent.putExtra("type", dto.getEleType());
                    intent.putExtra("key", dto.getUuid());
                    intent.putExtra("city", dto.getBelongtoGroup());
                    getActivity().startActivityForResult(intent,
                            REQUEST_OPERATION_RESULT);
                }
            }

            @Override
            public void onItemLongClick(final GeomElementDBInfo dto) {
            }
        };
    }

    public void setSearchKey(String key) {
        tv_result.setText("正在搜索\"" + key + "\"");
        mKey = key;

        mCondition.setType(QueryCondition.TYPE_KEY);
        mCondition.setKey(key);

        page = 0;
        vrList.clear();
    }

    public void searchByKey() {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("正在查找...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();

        mCondition.setType(QueryCondition.TYPE_KEY);
        mCondition.setKey(mKey);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                vrList = loadData();
                if (totalCount == 0) {
                    re_select_con.setVisibility(View.GONE);
                    if (mListener != null) {
                        Toast.makeText(getActivity(), "消防库中未找到数据，已切换至地图库！",
                                Toast.LENGTH_SHORT).show();
                        mListener.onSearchChanged(1);
                        return;
                    }
                } else {
                    re_select_con.setVisibility(View.VISIBLE);
                }

                adapter = new WsRecordAdapter(getActivity(), vrList, mOnItemSelectedListener);
                adapter.setCondition(mCondition);
                listView.setAdapter(adapter);

                dialog.dismiss();
            }
        }, 100);
    }

    private ArrayList<GeomElementDBInfo> loadData() {
        GeomEleBussiness xb = GeomEleBussiness.getInstance();
        ArrayList<GeomElementDBInfo> list = xb.loadGeomElement(mCondition, pageItem, page);

        totalCount = xb.getGeomElementCount(mCondition);

        resetListView();
        if (pageItem == -1) {
            listView.finishLoadMore();
        } else {
            if (list.size() < pageItem) {
                listView.finishLoadMore();
            } else {
                listView.setLoadMoreNormal();
            }
        }

        tv_result.setText("共找到" + totalCount + "相关个结果");

        return list;
    }

    private void reloadData() {
        page = 0;

        vrList.clear();
        List<GeomElementDBInfo> list = loadData();
        vrList.addAll(list);

        adapter.notifyDataSetChanged();
    }

    public void doMap() {
        if (totalCount == 0) {
            Toast.makeText(getActivity(), "无数据！", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("neededMapChange", true);
        intent.putExtra("QueryCondition", mCondition);
        getActivity().setResult(Activity.RESULT_OK, intent);

        getActivity().finish();
    }

    public void hide() {
        if (mViewSelf != null) {
            mViewSelf.setVisibility(View.GONE);
        }
    }

    public void unhide() {
        if (mViewSelf != null) {
            mViewSelf.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                reloadData();
            }
        }, 500);
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                page += pageItem;

                List<GeomElementDBInfo> list = loadData();
                vrList.addAll(list);

                adapter.notifyDataSetChanged();
            }
        }, 500);
    }

    @SuppressLint("SimpleDateFormat")
    private void resetListView() {
        listView.stopRefresh();
        listView.stopLoadMore();
        SimpleDateFormat sDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        listView.setRefreshTime(date);
    }

    public void setCondition(QueryCondition qc) {
        this.mCondition = qc;
    }

    public void setListener(OnWsRecordSearchLogicListener mListener) {
        this.mListener = mListener;
    }

    public interface OnWsRecordSearchLogicListener{
        boolean onPreStartDetailActivity(GeomElementDBInfo dto);
        void onKeyBoardClose();
        void onSearchChanged(int type);
    }
}
