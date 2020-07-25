package com.moft.xfapply.activity.logic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.DisposalPointDetailActivity;
import com.moft.xfapply.activity.adapter.DealProgreamAdapter;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.base.ViewLogic;
import com.moft.xfapply.logic.network.OkHttpClientManager;
import com.moft.xfapply.logic.setting.PrefSetting;
import com.moft.xfapply.model.common.PropertyConditon;
import com.moft.xfapply.model.common.QueryCondition;
import com.moft.xfapply.model.external.dto.DisposalPointDTO;
import com.moft.xfapply.model.external.result.ArrayRestResult;
import com.moft.xfapply.utils.GsonUtil;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.widget.XListView;
import com.moft.xfapply.widget.XListView.IXListViewListener;
import com.moft.xfapply.widget.dialog.CustomAlertDialog;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class DisposalPointLogic extends ViewLogic implements
        IXListViewListener,
        DuizhanListLogic.OnDuizhanListLogicListener{

    public DisposalPointLogic(View v, Activity a) {
        super(v, a);
    }

    private OnDisposalPointLogicListener mListener = null;
    private QueryCondition mCondition = null;

    private TextView tv_title = null;
    private TextView tv_select = null;
    private View re_select_con = null;
    private TextView tv_result = null;
    private TextView tv_eletype_con = null;
    private RelativeLayout re_eletype_con = null;
    private RelativeLayout re_clear = null;
    private XListView listView;
    private DealProgreamAdapter adapter = null;

    private Handler mHandler;

    private int page = 0;
    private int pageItem = 100;
    private List<DisposalPointDTO> fileList = new ArrayList<>();
    private int totalCount = 0;
    private Map<String, List<String>> filterValue = new HashMap<>();


    List<PropertyConditon> pcListTemp = new ArrayList<PropertyConditon>() {{
        this.add(new PropertyConditon("单位性质", "key_unit", Constant.PARAM_CHARACTER, String.class, PropertyConditon.TYPE_LIST, LvApplication.getInstance().getCompDicMap().get("ZDDW_DWXZ"), true));
        this.add(new PropertyConditon("使用性质", "key_unit", Constant.PARAM_USAGE, String.class, PropertyConditon.TYPE_LIST, LvApplication.getInstance().getCompDicMap().get("ZDDW_SYXZ"), true));
        this.add(new PropertyConditon("建筑结构", "key_unit", Constant.PARAM_STRUCTURE, String.class, PropertyConditon.TYPE_LIST, LvApplication.getInstance().getCompDicMap().get("ZDDW_JZJG"), true));
    }};

    public void init() {
        mHandler = new Handler();
        pageItem = PrefSetting.getInstance().getPageNum();

        View xs_record = getActivity().getLayoutInflater().inflate(R.layout.disposal_point_logic, null);

        LinearLayout ll_content = (LinearLayout)getView();
        ll_content.addView(xs_record);

        tv_select = (TextView) xs_record.findViewById(R.id.tv_select);

        tv_select.setText("查询结果");

        tv_result = (TextView) xs_record.findViewById(R.id.tv_result);
        tv_result.setText("(共?条)");

        re_select_con = getView().findViewById(R.id.re_select_con);
        re_select_con.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                doFilter();
            }
        });
        re_select_con.setVisibility(View.VISIBLE);

        // 筛选条件的文言描述
        re_eletype_con = (RelativeLayout) xs_record.findViewById(R.id.re_eletype_con);
        tv_eletype_con = (TextView) xs_record.findViewById(R.id.tv_eletype_con);
        re_eletype_con.setVisibility(View.GONE);
        if (!StringUtil.isEmpty(mCondition.getFilterDesc())) {
            tv_eletype_con.setText(mCondition.getFilterDesc());
            re_eletype_con.setVisibility(View.VISIBLE);
        } else {
            tv_eletype_con.setText("");
            re_eletype_con.setVisibility(View.GONE);
        }

        re_clear = (RelativeLayout)xs_record.findViewById(R.id.re_clear);
        re_clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_eletype_con.setText("");
                re_eletype_con.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "条件已清除", Toast.LENGTH_SHORT).show();
                View conItem = getActivity().getLayoutInflater().inflate(R.layout.condition_content, null);
                hidePrevCon(conItem);

                mCondition.setFilterDesc("");
                filterValue.clear();

                reloadData();
            }
        });


        listView = (XListView) xs_record.findViewById(R.id.list);
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

        loadData();

        adapter = new DealProgreamAdapter(getActivity(), fileList, new DealProgreamAdapter.OnItemSelectedListener() {
            @Override
            public void onItemClick(DisposalPointDTO info) {

                Intent intent = new Intent(getActivity(), DisposalPointDetailActivity.class);
                if (!StringUtil.isEmpty(info.getPublishUrl())) {
                    intent.putExtra("url", (Serializable)info.getPublishUrl());
                    intent.putExtra("name", (Serializable)info.getName());
                    getActivity().startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "无处置要点数据！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onItemLongClick(DisposalPointDTO info) {

            }
        });
//        adapter.setCondition(mCondition);
        listView.setAdapter(adapter);
    }

    public void setCondition(QueryCondition con) {
        mCondition = con;
    }


    private void loadData() {
        Map<String, String> params = new HashMap<String, String>();

        Gson gson = GsonUtil.create();
        params.put(Constant.PARAM_KEY_WORD, mCondition.getKey());
        params.put(Constant.PARAM_CHARACTER, gson.toJson(filterValue.get(Constant.PARAM_CHARACTER)));
        params.put(Constant.PARAM_STRUCTURE, gson.toJson(filterValue.get(Constant.PARAM_STRUCTURE)));
        params.put(Constant.PARAM_USAGE, gson.toJson(filterValue.get(Constant.PARAM_USAGE)));

        OkHttpClientManager http = OkHttpClientManager.getInstance();
        http.postAsyn(http.convertToURL(String.format(Constant.METHOD_GET_DISPOSAL_POINTS_LIST)),
                new OkHttpClientManager.ResultCallback<ArrayRestResult>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Toast.makeText(getActivity(), "网络请求失败，请检查网络连接！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(ArrayRestResult result) {
                        if (result != null && result.isSuccess()) {
                            if (result.getContents() != null) {
                                Gson gson = GsonUtil.create();
                                String jsonStr = gson.toJson(result.getContents());

                                Type listType = new TypeToken<List<DisposalPointDTO>>(){}.getType();
                                List<DisposalPointDTO> list = gson.fromJson(jsonStr, listType);
                                totalCount = list.size();
                                fileList.addAll(list);
                            }

                            onRequestSuccess();
                        } else {
                            Toast.makeText(getActivity(), "数据请求失败！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, params);
    }

    private void onRequestSuccess() {
        resetListView();
        listView.finishLoadMore();

        tv_result.setText("(共" + totalCount + "条)");
        adapter.notifyDataSetChanged();
    }

    public void reloadData() {
        page = 0;

        totalCount = 0;
        if (fileList == null) {
            fileList = new ArrayList<>();
        }
        fileList.clear();
        loadData();

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

                loadData();
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
    }

    private void doFilter() {

        View conItem = getActivity().getLayoutInflater().inflate(R.layout.condition_content, null);
        LinearLayout ll_content = (LinearLayout) conItem.findViewById(R.id.ll_content);

        final PropertyConditionLogic pcl = new PropertyConditionLogic(ll_content, getActivity());
        pcl.setPcListDetail(pcListTemp);
        pcl.init();

        hidePrevCon(conItem);

        final ImageView iv_prev_con_in = (ImageView) conItem.findViewById(R.id.iv_prev_con_in);
        final ImageView iv_prev_con_out = (ImageView) conItem.findViewById(R.id.iv_prev_con_out);
        iv_prev_con_in.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_prev_con_in.setVisibility(View.INVISIBLE);
                iv_prev_con_out.setVisibility(View.VISIBLE);
            }
        });
        iv_prev_con_out.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_prev_con_in.setVisibility(View.VISIBLE);
                iv_prev_con_out.setVisibility(View.INVISIBLE);
            }
        });

        final TextView tv_add_option = (TextView) conItem.findViewById(R.id.tv_add_option);
        final ImageView iv_add_option_open = (ImageView) conItem.findViewById(R.id.iv_add_option_open);
        final ImageView iv_add_option_close = (ImageView) conItem.findViewById(R.id.iv_add_option_close);
        iv_add_option_open.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_add_option_open.setVisibility(View.INVISIBLE);
                iv_add_option_close.setVisibility(View.VISIBLE);
                tv_add_option.setText("或");
            }
        });
        iv_add_option_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_add_option_open.setVisibility(View.VISIBLE);
                iv_add_option_close.setVisibility(View.INVISIBLE);
                tv_add_option.setText("且");
            }
        });

        final TextView tv_bdlk_option = (TextView) conItem.findViewById(R.id.tv_bdlk_option);
        final ImageView iv_bdlk_option_open = (ImageView) conItem.findViewById(R.id.iv_bdlk_option_open);
        final ImageView iv_bdlk_option_close = (ImageView) conItem.findViewById(R.id.iv_bdlk_option_close);
        iv_bdlk_option_open.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_bdlk_option_open.setVisibility(View.INVISIBLE);
                iv_bdlk_option_close.setVisibility(View.VISIBLE);
                tv_bdlk_option.setText("或");
            }
        });
        iv_bdlk_option_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_bdlk_option_open.setVisibility(View.VISIBLE);
                iv_bdlk_option_close.setVisibility(View.INVISIBLE);
                tv_bdlk_option.setText("且");
            }
        });

        CustomAlertDialog.show(getActivity(), "请设置条件",
                R.layout.condition_filter, conItem, new CustomAlertDialog.OnDoingListener() {

                    @Override
                    public void onOK() {
                        filterValue = pcl.getColumnValue();
                        if (filterValue.isEmpty()) {
                            reloadData();
                            return;
                        }
                        String desc = pcl.getSQLDesc(false);  // false:且；true:或

                        tv_eletype_con.setText(desc);
                        if (re_eletype_con.getVisibility() != View.VISIBLE) {
                            re_eletype_con.setVisibility(View.VISIBLE);
                        }
                        mCondition.setFilterDesc(desc);

                        reloadData();
                    }

                    @Override
                    public void onNG() {

                    }
                });
    }

    private void hidePrevCon(View conItem) {
        View rl_prev_con = conItem.findViewById(R.id.rl_prev_con);
        View rl_current_con = conItem.findViewById(R.id.rl_current_con);
        rl_current_con.setVisibility(View.GONE);
        rl_prev_con.setVisibility(View.GONE);
    }

    public OnDisposalPointLogicListener getListener() {
        return mListener;
    }

    public void setListener(OnDisposalPointLogicListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onSelectedDuizhan(String code, String name) {
        mCondition.setZqzd(name);
        mCondition.setZqzdCode(code);
        tv_select.setText(name);
        reloadData();
    }

    public void setTitleView(TextView tv_title) {
        this.tv_title = tv_title;
        if (this.tv_title != null) {
            this.tv_title.setClickable(false);
            this.tv_title.setText("处置要点");
        }

    }

    public interface OnDisposalPointLogicListener{
        boolean onPreStartDetailActivity(DisposalPointDTO dto);
        void onKeyBoardClose();
    }
}
