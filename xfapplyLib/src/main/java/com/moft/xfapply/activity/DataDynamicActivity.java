package com.moft.xfapply.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.adapter.DataDynamicAdapter;
import com.moft.xfapply.activity.bussiness.CommonBussiness;
import com.moft.xfapply.activity.bussiness.GeomEleBussiness;
import com.moft.xfapply.activity.logic.CheckBoxLogic;
import com.moft.xfapply.activity.logic.DzMutilSelLogic;
import com.moft.xfapply.activity.logic.RadioLogic;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.logic.network.OkHttpClientManager;
import com.moft.xfapply.logic.setting.PrefUserInfo;
import com.moft.xfapply.model.base.IGeomElementInfo;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.entity.SysDepartmentDBInfo;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.dto.EntityMaintainTaskDTO;
import com.moft.xfapply.model.external.result.ArrayRestResult;
import com.moft.xfapply.utils.DateUtil;
import com.moft.xfapply.utils.DictionaryUtil;
import com.moft.xfapply.utils.GsonUtil;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.widget.XListView;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class DataDynamicActivity extends BaseActivity {

    private static final int REQUEST_DATA_DYNAMIC_RESULT = 0;

    private XListView listView;
    private List<EntityMaintainTaskDTO> dataDynamicList = null;
    private DataDynamicAdapter adapter;
    private int page = 0;
    private int pageItem = 100;

    private View ll_station_filter;
    private TextView tv_duizhan_filter;
    private TextView tv_operation_filter;
    private TextView tv_date_filter;
    private TextView tv_type_filter;
    private TextView tv_result;
    private View re_result;


    private DzMutilSelLogic dzMutilSelLogic = null;

    private RadioLogic dateRadioLogic = null;

    private CheckBoxLogic typeCheckBoxLogic = null;
    private List<Dictionary> typeFilterList = new ArrayList<>();

    private CheckBoxLogic zdSelCheckBoxLogic = null;
    private List<Dictionary> zdFilterList = new ArrayList<>();

    private CheckBoxLogic operationCheckBoxLogic = null;
    private List<Dictionary> operationFilterList = new ArrayList<>();

    private List<String> operateList = new ArrayList<>();
    private String startDate = null;
    private String endDate = null;
    private List<String> stationList = new ArrayList<>();
    private List<String> typeList = new ArrayList<>();
    private Integer grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_dynamic);
        initView();
        initData();
    }

    private void initView() {
        listView = (XListView)findViewById(R.id.list);
        ll_station_filter = (View) findViewById(R.id.ll_station_filter);
        tv_duizhan_filter = (TextView)findViewById(R.id.tv_duizhan_filter);
        tv_operation_filter = (TextView)findViewById(R.id.tv_operation_filter);
        tv_date_filter = (TextView)findViewById(R.id.tv_date_filter);
        tv_type_filter = (TextView)findViewById(R.id.tv_type_filter);
        tv_result = (TextView)findViewById(R.id.tv_result);
        re_result = findViewById(R.id.re_result);
        re_result.setVisibility(View.GONE);
        tv_result.setText("");

        dataDynamicList = new ArrayList<>();
        adapter = new DataDynamicAdapter(this, dataDynamicList, new DataDynamicAdapter.OnItemSelectedListener() {
            @Override
            public void onItemClick(EntityMaintainTaskDTO dto) {
                Intent intent = new Intent(DataDynamicActivity.this, WsDetailActivity.class);
                intent.putExtra("type", dto.getEntityType());
                intent.putExtra("key", dto.getEntityUuid());
                intent.putExtra("city", LvApplication.getInstance().getCityCode());
                startActivityForResult(intent, REQUEST_DATA_DYNAMIC_RESULT);
            }

            @Override
            public void onItemLongClick(EntityMaintainTaskDTO dto) {

            }
        });
        listView.setAdapter(adapter);
        listView.setPullLoadEnable(true);
        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                resetListView();
                page = 0;
                requestDataDynamic(true);
            }

            @Override
            public void onLoadMore() {
                resetListView();
                page++;
                requestDataDynamic(false);
            }
        });

        typeCheckBoxLogic = new CheckBoxLogic(getWindow().getDecorView(), this);
        typeCheckBoxLogic.setDefaultCheckValue(true);
        typeCheckBoxLogic.setListener(new CheckBoxLogic.OnCheckBoxLogicListener() {
            @Override
            public void onLoadListData(List<Dictionary> dataList) {
                dataList.clear();
                dataList.add(new Dictionary("重点单位", AppDefs.CompEleType.KEY_UNIT.toString()));
                dataList.add(new Dictionary("消火栓", AppDefs.CompEleType.WATERSOURCE_FIRE_HYDRANT.toString()));
                dataList.add(new Dictionary("消防水池", AppDefs.CompEleType.WATERSOURCE_FIRE_POOL.toString()));
                dataList.add(new Dictionary("消防水鹤", AppDefs.CompEleType.WATERSOURCE_CRANE.toString()));
                dataList.add(new Dictionary("天然取水点", AppDefs.CompEleType.WATERSOURCE_NATURE_INTAKE.toString()));
                dataList.add(new Dictionary("消防车辆", AppDefs.CompEleType.FIRE_VEHICLE.toString()));
                dataList.add(new Dictionary("消防器材", AppDefs.CompEleType.EQUIPMENT.toString()));
                dataList.add(new Dictionary("灭火剂", AppDefs.CompEleType.EXTINGUISHING_AGENT.toString()));
                dataList.add(new Dictionary("消防支队", AppDefs.CompEleType.DETACHMENT.toString()));
                dataList.add(new Dictionary("消防大队", AppDefs.CompEleType.BATTALION.toString()));
                dataList.add(new Dictionary("消防中队", AppDefs.CompEleType.SQUADRON.toString()));
                dataList.add(new Dictionary("其他消防队伍", AppDefs.CompEleType.ENTERPRISE_FOURCE.toString()));
                dataList.add(new Dictionary("联动力量", AppDefs.CompEleType.JOINT_FORCE.toString()));
                typeFilterList = dataList;
            }

            @Override
            public int onGetCurrentPos() {
                return -1;
            }

            @Override
            public void onConfirmResult(List<Boolean> dataListCheck) {
                tv_type_filter.setTextColor(Color.rgb(53, 53, 53));
                typeList.clear();
                for(int i = 0; i < dataListCheck.size(); i++) {
                    if (dataListCheck.get(i)) {
                        typeList.add(typeFilterList.get(i).getCode());
                    }
                }
                if (typeList.size() != dataListCheck.size()) {
                    tv_type_filter.setText("数据类别...");
                } else {
                    tv_type_filter.setText("数据类别");
                }

                resetListView();
                page = 0;
                requestDataDynamic(true);

            }

            @Override
            public void onUnChanged() {
                tv_type_filter.setTextColor(Color.rgb(53, 53, 53));
            }
        });

        operationCheckBoxLogic = new CheckBoxLogic(getWindow().getDecorView(), this);
        operationCheckBoxLogic.setDefaultCheckValue(true);
        operationCheckBoxLogic.setListener(new CheckBoxLogic.OnCheckBoxLogicListener() {
            @Override
            public void onLoadListData(List<Dictionary> dataList) {
                dataList.clear();
                dataList.add(new Dictionary("新建", "NEW"));
                dataList.add(new Dictionary("更新", "MODIFY"));
                dataList.add(new Dictionary("删除", "DELETE"));
                operationFilterList = dataList;
            }

            @Override
            public int onGetCurrentPos() {
                return -1;
            }

            @Override
            public void onConfirmResult(List<Boolean> dataListCheck) {
                tv_operation_filter.setTextColor(Color.rgb(53, 53, 53));
                operateList.clear();
                for(int i = 0; i < dataListCheck.size(); i++) {
                    if (dataListCheck.get(i)) {
                        operateList.add(operationFilterList.get(i).getCode());
                    }
                }

                if (operateList.size() == 3 || operateList.size() == 0) {
                    tv_operation_filter.setText("操作类型");
                } else {
                    String operText = "";
                    for (int i = 0; i < operateList.size(); i++) {
                        String name = "";
                        if ("NEW".equals(operateList.get(i))) {
                            name = "新建";
                        } else if ("MODIFY".equals(operateList.get(i))) {
                            name = "更新";
                        } else if ("DELETE".equals(operateList.get(i))) {
                            name = "删除";
                        }
                        operText += name;
                        if (i != (operateList.size()-1)) {
                            operText += "/";
                        }
                    }
                    tv_operation_filter.setText(operText);
                }

                resetListView();
                page = 0;
                requestDataDynamic(true);

            }

            @Override
            public void onUnChanged() {
                tv_operation_filter.setTextColor(Color.rgb(53, 53, 53));
            }
        });
        dateRadioLogic = new RadioLogic(getWindow().getDecorView(), this);
        dateRadioLogic.setListener(new RadioLogic.OnRadioLogicListener() {
            @Override
            public void onLoadListData(List<Dictionary> dataList) {
                dataList.clear();
                dataList.add(new Dictionary("今日", "00"));
                dataList.add(new Dictionary("昨日", "01"));
                dataList.add(new Dictionary("本周", "02"));
                dataList.add(new Dictionary("上周", "03"));
                dataList.add(new Dictionary("本月", "04"));
                dataList.add(new Dictionary("上月", "05"));
                dataList.add(new Dictionary("本年度", "06"));
            }

            @Override
            public int onGetCurrentPos() {
                return 0;
            }

            @Override
            public void onConfirmResult(Dictionary dic, int pos) {
                tv_date_filter.setTextColor(Color.rgb(53, 53, 53));
                processDate(dic);
                resetListView();
                page = 0;
                requestDataDynamic(true);
            }

            @Override
            public void onUnChanged() {
                tv_date_filter.setTextColor(Color.rgb(53, 53, 53));
            }
        });

        initStationFilterView();
    }

    private void initStationFilterView() {
        PrefUserInfo pui = PrefUserInfo.getInstance();
        grade = Integer.valueOf(pui.getUserInfo("department_grade"));
        if(grade != null) {
            if(grade.intValue() == 0 || grade.intValue() == 1) {
                dzMutilSelLogic = new DzMutilSelLogic(getWindow().getDecorView(), this);
                dzMutilSelLogic.setDefaultCheckValue(true);
                dzMutilSelLogic.setOnDzMutilSelLogicListener(new DzMutilSelLogic.OnDzMutilSelLogicListener() {
                    @Override
                    public void onConfirmClick(Map<String, Dictionary> items) {
                        tv_duizhan_filter.setTextColor(Color.rgb(53, 53, 53));
                        stationList.clear();
                        Map<String, Dictionary> stationItems = filterStationItems(items);
                        for (Dictionary item : stationItems.values()) {
                            stationList.add(item.getCode());
                        }
                        tv_duizhan_filter.setText("队站...");

                        resetListView();
                        page = 0;
                        requestDataDynamic(true);
                    }

                    @Override
                    public void onUnChanged() {
                        tv_duizhan_filter.setTextColor(Color.rgb(53, 53, 53));
                    }
                });
            } else if(grade.intValue() == 2) {
                zdSelCheckBoxLogic = new CheckBoxLogic(getWindow().getDecorView(), this);
                zdSelCheckBoxLogic.setDefaultCheckValue(true);
                zdSelCheckBoxLogic.setListener(new CheckBoxLogic.OnCheckBoxLogicListener() {
                    @Override
                    public void onLoadListData(List<Dictionary> dataList) {
                        if(dataList == null || dataList.size() == 0) {
                            PrefUserInfo pui = PrefUserInfo.getInstance();
                            String departmentUuid = pui.getUserInfo("department_uuid");
                            if(!StringUtil.isEmpty(departmentUuid)) {
                                List<Dictionary> tempList = CommonBussiness.getInstance().getChildOrgList(departmentUuid);
                                if(tempList != null && tempList.size() > 0) {
                                    dataList.addAll(tempList);
                                }
                            }
                        }
                        zdFilterList = dataList;
                    }

                    @Override
                    public int onGetCurrentPos() {
                        return -1;
                    }

                    @Override
                    public void onConfirmResult(List<Boolean> dataListCheck) {
                        tv_duizhan_filter.setTextColor(Color.rgb(53, 53, 53));
                        stationList.clear();
                        for(int i = 0; i < dataListCheck.size(); i++) {
                            if (dataListCheck.get(i)) {
                                stationList.add(zdFilterList.get(i).getCode());
                            }
                        }
                        if (stationList.size() != dataListCheck.size()) {
                            tv_duizhan_filter.setText("队站...");
                        } else {
                            tv_duizhan_filter.setText("队站");
                        }

                        resetListView();
                        page = 0;
                        requestDataDynamic(true);
                    }

                    @Override
                    public void onUnChanged() {
                        tv_duizhan_filter.setTextColor(Color.rgb(53, 53, 53));
                    }
                });
            } else {
                ll_station_filter.setVisibility(View.GONE);
            }
        } else {
            ll_station_filter.setVisibility(View.GONE);
        }
    }

    private Map<String, Dictionary> filterStationItems(Map<String, Dictionary> items) {
        Map<String, Dictionary> retItems = new LinkedHashMap<>();
        if(items != null && items.size() > 0) {
            for(Map.Entry<String, Dictionary> item : items.entrySet()) {
                if(item.getKey().endsWith("0000")) {
                    if(containsAllSubStation(item.getKey(), 4, items)) {
                        retItems.put(item.getKey(), item.getValue());
                    }
                } else if(item.getKey().endsWith("00")) {
                    if(containsAllSubStation(item.getKey(), 6, items)) {
                        retItems.put(item.getKey(), item.getValue());
                    }
                } else {
                    retItems.put(item.getKey(), item.getValue());
                }
            }
        }
        return retItems;
    }

    private boolean containsAllSubStation(String stationCode, int lastIndex, Map<String, Dictionary> items) {
        boolean ret = false;
        int count = 0;
        Dictionary dic = DictionaryUtil.getDictionaryById(stationCode, LvApplication.getInstance().getCurrentOrgList());
        if(dic != null && dic.getSubDictionary() != null && dic.getSubDictionary().size() > 0) {
            for(Dictionary item : dic.getSubDictionary()) {
                if(items.containsKey(item.getId())) {
                    count++;
                }
            }

            if(count == 0 || count == dic.getSubDictionary().size()) {
                ret = true;
            }
        } else {
            ret = true;
        }

        return ret;
    }

    private void processDate(Dictionary dic) {

        if ("00".equals(dic.getCode())) {
            // "今日"
            startDate = DateUtil.getToday();
            endDate = null;
            tv_date_filter.setText("今日");
        } else if ("01".equals(dic.getCode())) {
            // 昨日
            startDate = DateUtil.getYestoday();
            endDate = DateUtil.getToday();
            tv_date_filter.setText("昨日");
        } else if ("02".equals(dic.getCode())) {
            // 本周
            startDate = DateUtil.getWeekStart();
            endDate = null;
            tv_date_filter.setText("本周");
        } else if ("03".equals(dic.getCode())) {
            // 上周
            startDate = DateUtil.getPreWeekStart();
            endDate = DateUtil.getPreWeekEnd();
            tv_date_filter.setText("上周");
        } else if ("04".equals(dic.getCode())) {
            // 本月
            startDate = DateUtil.getMonthStart();
            endDate = null;
            tv_date_filter.setText("本月");
        } else if ("05".equals(dic.getCode())) {
            // 上月
            startDate = DateUtil.getPreMonthStart();
            endDate = DateUtil.getPreMonthEnd();
            tv_date_filter.setText("上月");
        } else if ("06".equals(dic.getCode())) {
            // 本年度
            startDate = DateUtil.getYearStart();
            endDate = null;
            tv_date_filter.setText("本年度");
        } else {}

    }

    private void initData() {
        // 默认时间取今日的
        startDate = DateUtil.getToday();
        endDate = null;
        requestDataDynamic(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onTypeFilterClick(View view) {
        tv_operation_filter.setTextColor(Color.rgb(53, 53, 53));
        tv_date_filter.setTextColor(Color.rgb(53, 53, 53));
        tv_duizhan_filter.setTextColor(Color.rgb(53, 53, 53));
        tv_type_filter.setTextColor(Color.rgb(255, 152, 0));

        View re_con = this.findViewById(R.id.re_con);
        typeCheckBoxLogic.show(re_con);
        dateRadioLogic.hide();
        operationCheckBoxLogic.hide();
        if(dzMutilSelLogic != null) {
            dzMutilSelLogic.hide();
        }
        if(zdSelCheckBoxLogic != null) {
            zdSelCheckBoxLogic.hide();
        }
    }

    public void onDuizhanFilterClick(View view) {
        tv_operation_filter.setTextColor(Color.rgb(53, 53, 53));
        tv_date_filter.setTextColor(Color.rgb(53, 53, 53));
        tv_duizhan_filter.setTextColor(Color.rgb(255, 152, 0));
        tv_type_filter.setTextColor(Color.rgb(53, 53, 53));

        View re_con = this.findViewById(R.id.re_con);
        if(dzMutilSelLogic != null) {
            dzMutilSelLogic.show(re_con, DzMutilSelLogic.TYPE_LEVEL3);
        }
        if(zdSelCheckBoxLogic != null) {
            zdSelCheckBoxLogic.show(re_con);
        }
        typeCheckBoxLogic.hide();
        dateRadioLogic.hide();
        operationCheckBoxLogic.hide();
    }

    public void onDateFilterClick(View view) {
        tv_operation_filter.setTextColor(Color.rgb(53, 53, 53));
        tv_date_filter.setTextColor(Color.rgb(255, 152, 0));
        tv_duizhan_filter.setTextColor(Color.rgb(53, 53, 53));
        tv_type_filter.setTextColor(Color.rgb(53, 53, 53));

        View re_con = this.findViewById(R.id.re_con);
        dateRadioLogic.show(re_con);
        typeCheckBoxLogic.hide();
        operationCheckBoxLogic.hide();
        if(dzMutilSelLogic != null) {
            dzMutilSelLogic.hide();
        }
        if(zdSelCheckBoxLogic != null) {
            zdSelCheckBoxLogic.hide();
        }
    }

    public void onOperationFilterClick(View view) {
        tv_operation_filter.setTextColor(Color.rgb(255, 152, 0));
        tv_date_filter.setTextColor(Color.rgb(53, 53, 53));
        tv_duizhan_filter.setTextColor(Color.rgb(53, 53, 53));
        tv_type_filter.setTextColor(Color.rgb(53, 53, 53));

        View re_con = this.findViewById(R.id.re_con);
        operationCheckBoxLogic.show(re_con);
        typeCheckBoxLogic.hide();
        dateRadioLogic.hide();
        if(dzMutilSelLogic != null) {
            dzMutilSelLogic.hide();
        }
        if(zdSelCheckBoxLogic != null) {
            zdSelCheckBoxLogic.hide();
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void resetListView() {
        listView.stopRefresh();
        listView.stopLoadMore();
        SimpleDateFormat sDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String date = sDateFormat.format(new Date());
        listView.setRefreshTime(date);
    }

    private void requestDataDynamic(final boolean reload) {
        if (reload) {
            re_result.setVisibility(View.VISIBLE);
            tv_result.setText("正在查询中...");
        }

        OkHttpClientManager http = OkHttpClientManager.getInstance();
        final Gson gson = GsonUtil.create();
        Map<String, String> params = getDataDynamicParams();
        if(params != null) {
            http.postAsyn(http.convertToURL(Constant.METHOD_GET_DATA_DYNAMIC_V2),
                    new OkHttpClientManager.ResultCallback<ArrayRestResult>() {
                @Override
                public void onError(Request request, Exception e) {

                }

                @Override
                public void onResponse(ArrayRestResult result) {
                    if (result != null && result.isSuccess()) {
                        String jsonStr = gson.toJson(result.getContents());
                        tv_result.setText("共查询到" + result.getTotal() + "条动态数据");

                        Type listType = new TypeToken<List<EntityMaintainTaskDTO>>() {
                        }.getType();
                        List<EntityMaintainTaskDTO> tempList = gson.fromJson(jsonStr, listType);
                        updateDataDynamic(tempList, reload);
                    }
                }
            }, params);
        }
    }

    private void updateDataDynamic(List<EntityMaintainTaskDTO> list, boolean reload) {
        if(reload) {
            dataDynamicList.clear();
        }
        if(list != null) {
            if (list.size() < pageItem) {
                listView.finishLoadMore();
            } else {
                listView.setLoadMoreNormal();
            }
            dataDynamicList.addAll(list);
        }
        adapter.notifyDataSetChanged();
    }

    private Map<String, String> getDataDynamicParams() {
        Map<String, String> params = null;

        PrefUserInfo pui = PrefUserInfo.getInstance();
        String departmentUuid = pui.getUserInfo("department_uuid");
        if(!StringUtil.isEmpty(departmentUuid)) {
            Gson gson2 = new Gson();
            params =  new HashMap<>();
            if (stationList != null && stationList.size() > 0) {
                params.put("departmentUuid", gson2.toJson(stationList));
            } else {
                params.put("departmentUuid", gson2.toJson(new ArrayList<>(Arrays.asList(departmentUuid))));
            }
            if (!operateList.isEmpty()) {
                params.put("operateType", gson2.toJson(operateList));
            }
            if (startDate != null) {
                params.put("startDate", startDate);
            }

            if (endDate != null) {
                params.put("endDate", endDate);
            }

            if (!typeList.isEmpty()) {
                params.put("entityType", gson2.toJson(typeList));
            }

            params.put("state", gson2.toJson(new ArrayList<String>(Arrays.asList("YSH"))));
            params.put("orderBy", "updateDate");
            params.put("page", StringUtil.get(page + 1));
            params.put("start", StringUtil.get(page * pageItem));
            params.put("limit", StringUtil.get(pageItem));
        }
        return params;
    }

    private String getDefaultDepartmentUuid() {
        // 如果用户没有选择任意一个队站，则根据账号传递department，以确保显示正确的数据范围
        PrefUserInfo pui = PrefUserInfo.getInstance();
        Integer grade = Integer.valueOf(pui.getUserInfo("department_grade"));
        Gson gson2 = new Gson();
        String defaultDepartment = gson2.toJson(new ArrayList<>(Arrays.asList(pui.getUserInfo("department_uuid"))));

        if (grade > 1) {
            // 当当前登录人账号为大中队时,传递支队department_uuid
            SysDepartmentDBInfo tmpDepartment = GeomEleBussiness.getInstance().getDetachmentDepartment(
                    LvApplication.getInstance().getCityCode(),
                    pui.getUserInfo("district_code"),
                    "1");
            if (tmpDepartment != null) {
                defaultDepartment = gson2.toJson(new ArrayList<>(Arrays.asList(tmpDepartment.getUuid())));
            }
        }
        return defaultDepartment;
    }

    @Override
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

            this.setResult(Activity.RESULT_OK, intent);

            this.finish();
        }
    }

}
