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

import com.moft.xfapply.R;
import com.moft.xfapply.activity.WsDetailActivity;
import com.moft.xfapply.activity.adapter.WsRecordAdapter;
import com.moft.xfapply.activity.bussiness.GeomEleBussiness;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.base.ViewLogic;
import com.moft.xfapply.logic.setting.PrefSetting;
import com.moft.xfapply.model.base.IGeomElementInfo;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.common.PropertyConditon;
import com.moft.xfapply.model.common.QueryCondition;
import com.moft.xfapply.model.entity.EnterpriseFourceDBInfo;
import com.moft.xfapply.model.entity.GeomElementDBInfo;
import com.moft.xfapply.model.entity.JointForceDBInfo;
import com.moft.xfapply.model.entity.KeyUnitDBInfo;
import com.moft.xfapply.model.entity.MaterialEquipmentDBInfo;
import com.moft.xfapply.model.entity.MaterialExtinguishingAgentDBInfo;
import com.moft.xfapply.model.entity.MaterialFireVehicleDBInfo;
import com.moft.xfapply.model.entity.StationBattalionDBInfo;
import com.moft.xfapply.model.entity.StationBrigadeDBInfo;
import com.moft.xfapply.model.entity.StationDetachmentDBInfo;
import com.moft.xfapply.model.entity.StationSquadronDBInfo;
import com.moft.xfapply.model.entity.WatersourceCraneDBInfo;
import com.moft.xfapply.model.entity.WatersourceFireHydrantDBInfo;
import com.moft.xfapply.model.entity.WatersourceFirePoolDBInfo;
import com.moft.xfapply.model.entity.WatersourceNatureIntakeDBInfo;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.widget.XListView;
import com.moft.xfapply.widget.XListView.IXListViewListener;
import com.moft.xfapply.widget.dialog.CustomAlertDialog;
import com.moft.xfapply.widget.dialog.ListDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class WsRecordLogic extends ViewLogic implements
        IXListViewListener,
        DuizhanListLogic.OnDuizhanListLogicListener{

    public WsRecordLogic(View v, Activity a) {
        super(v, a);
    }

    private static final int REQUEST_OPERATION_RESULT = 0;

    private OnWsRecordLogicListener mListener = null;
    private QueryCondition mCondition = null;

    private TextView tv_title = null;
    private TextView tv_select = null;
    private View re_select_con = null;
    private TextView tv_result = null;
    private TextView tv_eletype_con = null;
    private RelativeLayout re_eletype_con = null;
    private RelativeLayout re_clear = null;
    private XListView listView;
    private WsRecordAdapter adapter = null;
    private WsRecordAdapter.OnItemSelectedListener mOnItemSelectedListener = null;

    private LinearLayout ll_tongji = null;
    private TextView tv_zongliang = null;  // 总量
    private TextView tv_keyong = null;     // 可用
    private TextView tv_sunhuai = null;    // 损坏
    private TextView tv_chezai = null;     // 车载

    private Handler mHandler;

    private int page = 0;
    private int pageItem = 100;
    private List<GeomElementDBInfo> vrList = null;
    private List<GeomElementDBInfo> allGeoListForNear = null;
    private int totalCount = 0;

    private List<Dictionary> eleTypeList = new ArrayList<>();
    private int curSelectedType = -1;

    private DuizhanListLogic mDzListLogic = null;

    private String eletype_con = "";
    private List<String> geoUuidList;

    public void init() {
        mHandler = new Handler();
        pageItem = PrefSetting.getInstance().getPageNum();

        View xs_record = getActivity().getLayoutInflater().inflate(R.layout.xs_record, null);

        LinearLayout ll_content = (LinearLayout)getView();
        ll_content.addView(xs_record);

        loadEleType();

        tv_select = (TextView) xs_record.findViewById(R.id.tv_select);

        if (mCondition.getType() == QueryCondition.TYPE_SYLX) {
            mDzListLogic = new DuizhanListLogic(xs_record, getActivity());
            mDzListLogic.addListener(this);
            mDzListLogic.init();
            mDzListLogic.initData(mCondition.getZqzdCode(), mCondition.getZqzd());
            mDzListLogic.refreshView();

            tv_select.setText(mDzListLogic.getName());
        } else {
            tv_select.setText(mCondition.getDescribe());
        }

        tv_result = (TextView) xs_record.findViewById(R.id.tv_result);
        tv_result.setText("(共?条)");

        re_select_con = getView().findViewById(R.id.re_select_con);
        re_select_con.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                doFilter();
            }
        });
        if (isHaveFilter()) {
            re_select_con.setVisibility(View.VISIBLE);
        } else {
            re_select_con.setVisibility(View.GONE);
        }

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
                eletype_con = null;
                View conItem = getActivity().getLayoutInflater().inflate(R.layout.condition_content, null);
                hidePrevCon(conItem);

                mCondition.setFilterDesc("");
                mCondition.setFilterSQL("");

                reloadData();
            }
        });

        ll_tongji = (LinearLayout) xs_record.findViewById(R.id.ll_tongji);
        tv_zongliang = (TextView) xs_record.findViewById(R.id.tv_zongliang);
        tv_keyong = (TextView) xs_record.findViewById(R.id.tv_keyong);
        tv_sunhuai = (TextView) xs_record.findViewById(R.id.tv_sunhuai);
        tv_chezai = (TextView) xs_record.findViewById(R.id.tv_chezai);

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

        vrList = loadData();
        adapter = new WsRecordAdapter(getActivity(), vrList, new WsRecordAdapter.OnItemSelectedListener() {
            @Override
            public void onItemClick(GeomElementDBInfo dto) {
                if (mListener != null && mListener.onPreStartDetailActivity(dto)) {
                    return;
                }

                Intent intent = new Intent(getActivity(), WsDetailActivity.class);
                intent.putExtra("type", dto.getEleType());
                intent.putExtra("key", dto.getUuid());
                intent.putExtra("city", dto.getBelongtoGroup());
                getActivity().startActivityForResult(intent,
                        REQUEST_OPERATION_RESULT);
            }

            @Override
            public void onItemLongClick(final GeomElementDBInfo dto) {
            }
        });
        adapter.setCondition(mCondition);
        listView.setAdapter(adapter);

        loadStatisticsData();
    }

    public void setCondition(QueryCondition con) {
        mCondition = con;
    }

    public void setGeoUuidList(List<String> geoUuidList) {
        this.geoUuidList = geoUuidList;
    }

    private List<GeomElementDBInfo> loadData() {
        GeomEleBussiness xb = GeomEleBussiness.getInstance();
        List<GeomElementDBInfo> list = null;

        if (mCondition.getType() == QueryCondition.TYPE_POSITION) {
            if (allGeoListForNear == null) {
                allGeoListForNear = xb.loadGeomElement(mCondition);
                totalCount = allGeoListForNear.size();
                class GeomElementDBInfoComparetor implements Comparator<GeomElementDBInfo> {
                    @Override
                    public int compare(GeomElementDBInfo geo1, GeomElementDBInfo geo2) {
                        return (int) (geo1.getDistance(mCondition.getLng(), mCondition.getLat()) -
                                geo2.getDistance(mCondition.getLng(), mCondition.getLat()));
                    }
                }
                Collections.sort(allGeoListForNear, new GeomElementDBInfoComparetor());
            }

            list = new ArrayList<>();
            int indexStart = page;
            int indexEnd = indexStart + pageItem;
            if (indexEnd > allGeoListForNear.size()) {
                indexEnd = allGeoListForNear.size();
            }

            for (int i = indexStart; i < indexEnd; i++) {
                list.add(allGeoListForNear.get(i));
            }
        } else {
            if(geoUuidList != null && geoUuidList.size() > 0) {
                List<String> departmentUuids = getDepartmentUuidsByGeoUuid();
                list = xb.loadGeomElementByUuids(mCondition, departmentUuids, pageItem, page);
            } else {
                list = xb.loadGeomElement(mCondition, pageItem, page);
            }
            totalCount = xb.getGeomElementCount(mCondition);
        }
        
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

        tv_result.setText("(共" + totalCount + "条)");

        return list;
    }

    private List<String> getDepartmentUuidsByGeoUuid() {
        List<String> uuidList = new ArrayList<>();
        Map<String, GeomElementDBInfo> geomMap = GeomEleBussiness.getInstance().getGeomElementInfoByUuids(geoUuidList);
        for(String uuid : geoUuidList) {
            GeomElementDBInfo info = geomMap.get(uuid);
            uuidList.add(info.getDepartmentUuid());
        }
        return uuidList;
    }

    private void loadEleType() {
        GeomEleBussiness xb = GeomEleBussiness.getInstance();
        eleTypeList = xb.loadGeoElementTypeList(mCondition);

        if(tv_title != null) {
            tv_title.setClickable(true);
            tv_title.setText(mCondition.getTitle());
            tv_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (eleTypeList.size() > 1) {
                        doFilterType();
                    }
                }
            });
        }
    }

    private String getLoadSQL() {
        String filterSQL = null;
        if (AppDefs.CompEleType.KEY_UNIT.toString().equals(mCondition.getSylxCode())) {
            filterSQL = getKeyUnitSQL();
        } else {
            filterSQL = getSQL();
        }
        return filterSQL;
    }

    public void reloadData() {
        page = 0;
        allGeoListForNear = null;

        if (vrList == null) { // 增加这个if语句纯粹是为了容错 edit by wangx 2019-09-03
            vrList = new ArrayList<GeomElementDBInfo>();
        }
        vrList.clear();
        List<GeomElementDBInfo> list = loadData();
        vrList.addAll(list);

        loadStatisticsData();
        
        adapter.notifyDataSetChanged();
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (data.hasExtra("neededMapChange")) {
            IGeomElementInfo dto = (IGeomElementInfo)data.getSerializableExtra("CompElement");
            Boolean neededMapChange = (Boolean)data.getSerializableExtra("neededMapChange");
            String city = (String) data.getSerializableExtra("city");

            Intent intent = new Intent();
            intent.putExtra("neededMapChange", neededMapChange);
            intent.putExtra("CompElement", dto);
            intent.putExtra("city", city);
            getActivity().setResult(Activity.RESULT_OK, intent);

            getActivity().finish();
        }
    }

    /*
    * @Author 王旭
    * @Date 2019-03-05
    * @Description ID:900358 【移动应用端】也添加关于器材的统计，和前台一致
    * */
    private void loadStatisticsData() {
        if("EQUIPMENT".equals(mCondition.getSylxCode())) {
            Map<String, String> ret = GeomEleBussiness.getInstance().getEquipmentCount(mCondition);
            tv_zongliang.setText("总量:" + ret.get("zl"));
            tv_keyong.setText("可用:" + ret.get("ky"));
            tv_sunhuai.setText("损坏:" + ret.get("sh"));
            tv_chezai.setText("车载:" + ret.get("cz"));
            ll_tongji.setVisibility(View.VISIBLE);
        } else {
            ll_tongji.setVisibility(View.GONE);
        }
    }

    private boolean isHaveFilter() {
        boolean result = false;
        String eleType = mCondition.getSylxCode();

        if (AppDefs.CompEleType.KEY_UNIT.toString().equals(eleType)) {
            result = true;
        } else if (AppDefs.CompEleType.WATERSOURCE_FIRE_HYDRANT.toString().equals(eleType)) {
            result = true;
        } else if (AppDefs.CompEleType.WATERSOURCE_CRANE.toString().equals(eleType)) {
            result = true;
        } else if (AppDefs.CompEleType.WATERSOURCE_FIRE_POOL.toString().equals(eleType)) {
            result = true;
        } else if (AppDefs.CompEleType.WATERSOURCE_NATURE_INTAKE.toString().equals(eleType)) {
            result = true;
        } else if (AppDefs.CompEleType.FIRE_VEHICLE.toString().equals(eleType)) {
            result = true;
        } else if (AppDefs.CompEleType.EXTINGUISHING_AGENT.toString().equals(eleType)) {
            result = true;
        } else if (AppDefs.CompEleType.EQUIPMENT.toString().equals(eleType)) {
            result = true;
        } else if (AppDefs.CompEleType.BRIGADE.toString().equals(eleType)) {
            result = false;
        } else if (AppDefs.CompEleType.DETACHMENT.toString().equals(eleType)) {
            result = false;
        } else if (AppDefs.CompEleType.BATTALION.toString().equals(eleType)) {
            result = true;
        } else if (AppDefs.CompEleType.SQUADRON.toString().equals(eleType)) {
            result = true;
        } else if (AppDefs.CompEleType.ENTERPRISE_FOURCE.toString().equals(eleType)) {
            result = true;
        } else if (AppDefs.CompEleType.JOINT_FORCE.toString().equals(eleType)) {
            result = true;
        } else {}
        return result;
    }

    private void doFilterType() {
        ListDialog.show(getActivity(), eleTypeList, curSelectedType,
                new ListDialog.OnSelectedListener() {
            @Override
            public void onSelected(int position) {
                curSelectedType = position;
                Dictionary curDic = eleTypeList.get(position);
                tv_title.setText(curDic.getValue());

                mCondition.setSylx(curDic.getValue());
                //对应其他消防队伍分为4类
                if(curDic.getCode().startsWith(AppDefs.CompEleType.ENTERPRISE_FOURCE.toString())) {
                    mCondition.setLxSubCode(curDic.getCode().replace(AppDefs.CompEleType.ENTERPRISE_FOURCE.toString(), ""));
                    mCondition.setSylxCode(AppDefs.CompEleType.ENTERPRISE_FOURCE.toString());
                } else {
                    mCondition.setSylxCode(curDic.getCode());
                    mCondition.setLxSubCode("");
                }

                if (isHaveFilter()) {
                    re_select_con.setVisibility(View.VISIBLE);
                } else {
                    re_select_con.setVisibility(View.GONE);
                }

                reloadData();
            }
        });
    }

    private void doFilter() {
        final String eleType = mCondition.getSylxCode();
        String tableNameTemp = "";
        List<PropertyConditon> pcList = new ArrayList<>();

        List<PropertyConditon> pcListTemp = null;
        if (AppDefs.CompEleType.KEY_UNIT.toString().equals(eleType)) {
            pcListTemp = KeyUnitDBInfo.getPdListDetailForFilter();
            tableNameTemp = Constant.TABLE_NAME_KEY_UNIT;
        } else if (AppDefs.CompEleType.WATERSOURCE_FIRE_HYDRANT.toString().equals(eleType)) {
            pcListTemp = WatersourceFireHydrantDBInfo.getPdListDetailForFilter();
            tableNameTemp = Constant.TABLE_NAME_WS_FIRE_HYDRANT;
        } else if (AppDefs.CompEleType.WATERSOURCE_CRANE.toString().equals(eleType)) {
            pcListTemp = WatersourceCraneDBInfo.getPdListDetailForFilter();
            tableNameTemp = Constant.TABLE_NAME_WS_CRANE;
        } else if (AppDefs.CompEleType.WATERSOURCE_FIRE_POOL.toString().equals(eleType)) {
            pcListTemp = WatersourceFirePoolDBInfo.getPdListDetailForFilter();
            tableNameTemp = Constant.TABLE_NAME_WS_FIRE_POOL;
        } else if (AppDefs.CompEleType.WATERSOURCE_NATURE_INTAKE.toString().equals(eleType)) {
            pcListTemp = WatersourceNatureIntakeDBInfo.getPdListDetailForFilter();
            tableNameTemp = Constant.TABLE_NAME_WS_NATURE_INTAKE;
        } else if (AppDefs.CompEleType.FIRE_VEHICLE.toString().equals(eleType)) {
            pcListTemp = MaterialFireVehicleDBInfo.getPdListDetailForFilter();
            tableNameTemp = Constant.TABLE_NAME_FIRE_VEHICLE;
        } else if (AppDefs.CompEleType.EXTINGUISHING_AGENT.toString().equals(eleType)) {
            pcListTemp = MaterialExtinguishingAgentDBInfo.getPdListDetailForFilter();
            tableNameTemp = Constant.TABLE_NAME_EXTINGUISHING_AGENT;
        } else if (AppDefs.CompEleType.EQUIPMENT.toString().equals(eleType)) {
            pcListTemp = MaterialEquipmentDBInfo.getPdListDetailForFilter();
            tableNameTemp = Constant.TABLE_NAME_EQUIPMENT;
        } else if (AppDefs.CompEleType.BRIGADE.toString().equals(eleType)) {
            pcListTemp = StationBrigadeDBInfo.getPdListDetailForFilter();
            tableNameTemp = Constant.TABLE_NAME_BRIGADE;
        } else if (AppDefs.CompEleType.DETACHMENT.toString().equals(eleType)) {
            pcListTemp = StationDetachmentDBInfo.getPdListDetailForFilter();
            tableNameTemp = Constant.TABLE_NAME_DETACHMENT;
        } else if (AppDefs.CompEleType.BATTALION.toString().equals(eleType)) {
            pcListTemp = StationBattalionDBInfo.getPdListDetailForFilter();
            tableNameTemp = Constant.TABLE_NAME_BATTALION;
        } else if (AppDefs.CompEleType.SQUADRON.toString().equals(eleType)) {
            pcListTemp = StationSquadronDBInfo.getPdListDetailForFilter();
            tableNameTemp = Constant.TABLE_NAME_SQUADRON;
        } else if (AppDefs.CompEleType.ENTERPRISE_FOURCE.toString().equals(eleType)) {
            pcListTemp = EnterpriseFourceDBInfo.getPdListDetailForFilter();
            tableNameTemp = Constant.TABLE_NAME_ENTERPRISE_FOURCE;
        } else if (AppDefs.CompEleType.JOINT_FORCE.toString().equals(eleType)) {
            pcListTemp = JointForceDBInfo.getPdListDetailForFilter();
            tableNameTemp = Constant.TABLE_NAME_JOINT_FORCE;
        } else {
            Toast.makeText(getActivity(), "无可选择条件！",
                    Toast.LENGTH_SHORT).show();
        }

        if (pcListTemp != null && !pcListTemp.isEmpty()) {
            pcList.addAll(pcListTemp);
        }

        View conItem = getActivity().getLayoutInflater().inflate(R.layout.condition_content, null);
        LinearLayout ll_content = (LinearLayout) conItem.findViewById(R.id.ll_content);

        final PropertyConditionLogic pcl = new PropertyConditionLogic(ll_content, getActivity());
        pcl.setPcListDetail(pcList);
        pcl.init();

        hidePrevCon(conItem);

        final ImageView iv_prev_con_in = (ImageView) conItem.findViewById(R.id.iv_prev_con_in);
        final ImageView iv_prev_con_out = (ImageView) conItem.findViewById(R.id.iv_prev_con_out);
        iv_prev_con_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_prev_con_in.setVisibility(View.INVISIBLE);
                iv_prev_con_out.setVisibility(View.VISIBLE);
            }
        });
        iv_prev_con_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_prev_con_in.setVisibility(View.VISIBLE);
                iv_prev_con_out.setVisibility(View.INVISIBLE);
            }
        });

        final TextView tv_add_option = (TextView) conItem.findViewById(R.id.tv_add_option);
        final ImageView iv_add_option_open = (ImageView) conItem.findViewById(R.id.iv_add_option_open);
        final ImageView iv_add_option_close = (ImageView) conItem.findViewById(R.id.iv_add_option_close);
        iv_add_option_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_add_option_open.setVisibility(View.INVISIBLE);
                iv_add_option_close.setVisibility(View.VISIBLE);
                tv_add_option.setText("或");
            }
        });
        iv_add_option_close.setOnClickListener(new View.OnClickListener() {
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
        iv_bdlk_option_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_bdlk_option_open.setVisibility(View.INVISIBLE);
                iv_bdlk_option_close.setVisibility(View.VISIBLE);
                tv_bdlk_option.setText("或");
            }
        });
        iv_bdlk_option_close.setOnClickListener(new View.OnClickListener() {
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
                        boolean isOr = false;
                        if ("或".equals(tv_bdlk_option.getText().toString())) {
                            isOr = true;
                        }
                        String strSQL = pcl.getSQL(isOr);
                        if (StringUtil.isEmpty(strSQL)) {
                            reloadData();
                            return;
                        }
                        String strSQLDesc = pcl.getSQLDesc(isOr);
                        String desc = tv_eletype_con.getText().toString();
                        if (iv_prev_con_in.getVisibility() == View.VISIBLE) {
                            String linkMark = "and";
                            if ("或".equals(tv_add_option.getText().toString())) {
                                linkMark = "or";
                            }
                            eletype_con = "(" + eletype_con + ") " + linkMark + " (" + strSQL + ")";
                            desc = "(" + desc + ") " + tv_add_option.getText().toString() + " (" + strSQLDesc + ")";
                        } else {
                            eletype_con = strSQL;
                            desc = strSQLDesc;
                        }

                        tv_eletype_con.setText(desc);
                        if (re_eletype_con.getVisibility() != View.VISIBLE) {
                            re_eletype_con.setVisibility(View.VISIBLE);
                        }

                        String filterSQL = getLoadSQL();
                        mCondition.setFilterSQL(filterSQL);
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
        if (StringUtil.isEmpty(eletype_con)) {
            rl_prev_con.setVisibility(View.GONE);
        } else {
            rl_prev_con.setVisibility(View.VISIBLE);
        }
    }

    private String getSQL() {
        String tmpTableName = getTableName();
        if (StringUtil.isEmpty(tmpTableName) || StringUtil.isEmpty(eletype_con)) {
            return null;
        }
        //支持省离线数据库查询
        String strSQL = "SELECT uuid "  +
                " FROM " + tmpTableName +
                " WHERE " + Constant.SEARCH_COND_NOT_DELETED +
                " AND " + eletype_con;

        return strSQL;
    }

    private String getKeyUnitSQL() {
        if (StringUtil.isEmpty(eletype_con)) {
            return null;
        }
        String keyUnitSQL = "( SELECT " + KeyUnitDBInfo.getSelectColumnForFilter() +
                " FROM " + KeyUnitDBInfo.getTableNameForFilter() +
                " WHERE " + eletype_con +
                " ) as k";
        //支持省离线数据库查询
        String strSQL = "SELECT uuid "  +
                " FROM " + keyUnitSQL +
                " WHERE " + Constant.SEARCH_COND_NOT_DELETED;

        return strSQL;
    }


    private String getTableName() {
        String tableName = null;
        String eleType = mCondition.getSylxCode();
        if (AppDefs.CompEleType.KEY_UNIT.toString().equals(eleType)) {
            tableName = Constant.TABLE_NAME_KEY_UNIT;
        } else if (AppDefs.CompEleType.WATERSOURCE_FIRE_HYDRANT.toString().equals(eleType)) {
            tableName = Constant.TABLE_NAME_WS_FIRE_HYDRANT;
        } else if (AppDefs.CompEleType.WATERSOURCE_CRANE.toString().equals(eleType)) {
            tableName = Constant.TABLE_NAME_WS_CRANE;
        } else if (AppDefs.CompEleType.WATERSOURCE_FIRE_POOL.toString().equals(eleType)) {
            tableName = Constant.TABLE_NAME_WS_FIRE_POOL;
        } else if (AppDefs.CompEleType.WATERSOURCE_NATURE_INTAKE.toString().equals(eleType)) {
            tableName = Constant.TABLE_NAME_WS_NATURE_INTAKE;
        } else if (AppDefs.CompEleType.FIRE_VEHICLE.toString().equals(eleType)) {
            tableName = Constant.TABLE_NAME_FIRE_VEHICLE;
        } else if (AppDefs.CompEleType.EXTINGUISHING_AGENT.toString().equals(eleType)) {
            tableName = Constant.TABLE_NAME_EXTINGUISHING_AGENT;
        } else if (AppDefs.CompEleType.EQUIPMENT.toString().equals(eleType)) {
            tableName = Constant.TABLE_NAME_EQUIPMENT;
        } else if (AppDefs.CompEleType.BRIGADE.toString().equals(eleType)) {
            tableName = Constant.TABLE_NAME_BRIGADE;
        } else if (AppDefs.CompEleType.DETACHMENT.toString().equals(eleType)) {
            tableName = Constant.TABLE_NAME_DETACHMENT;
        } else if (AppDefs.CompEleType.BATTALION.toString().equals(eleType)) {
            tableName = Constant.TABLE_NAME_BATTALION;
        } else if (AppDefs.CompEleType.SQUADRON.toString().equals(eleType)) {
            tableName = Constant.TABLE_NAME_SQUADRON;
        } else if (AppDefs.CompEleType.ENTERPRISE_FOURCE.toString().equals(eleType)) {
            tableName = Constant.TABLE_NAME_ENTERPRISE_FOURCE;
        } else if (AppDefs.CompEleType.JOINT_FORCE.toString().equals(eleType)) {
            tableName = Constant.TABLE_NAME_JOINT_FORCE;
        } else {
            // nothing
        }
        return tableName;
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

    public OnWsRecordLogicListener getListener() {
        return mListener;
    }

    public void setListener(OnWsRecordLogicListener mListener) {
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
    }

    public interface OnWsRecordLogicListener{
        boolean onPreStartDetailActivity(GeomElementDBInfo dto);
        void onKeyBoardClose();
    }
}
