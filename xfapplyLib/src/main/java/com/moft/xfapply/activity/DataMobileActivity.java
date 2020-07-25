package com.moft.xfapply.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.adapter.DataDeviceAdapter;
import com.moft.xfapply.activity.logic.DzMutilSelLogic;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.logic.network.OkHttpClientManager;
import com.moft.xfapply.logic.setting.PrefUserInfo;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.external.dto.AppDeviceUsageSurveyChildDTO;
import com.moft.xfapply.model.external.dto.AppDeviceUsageSurveyDTO;
import com.moft.xfapply.model.external.result.SimpleRestResult;
import com.moft.xfapply.utils.CharacterParserUtil;
import com.moft.xfapply.utils.GsonUtil;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class DataMobileActivity extends BaseActivity {
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public enum SortType {
        NAME,
        COUNT,
        TIME
    }
    private ListView listView;
    private LinearLayout ll_station;
    private LinearLayout ll_count;
    private LinearLayout ll_value;
    private View re_con = null;
    private View re_zhidui = null;
    private View re_dadui = null;
    private View re_zhongdui = null;
    private TextView tv_zhidui = null;
    private TextView tv_dadui = null;
    private TextView tv_zhongdui = null;

    private TextView tv_station = null;
    private ImageView iv_station = null;
    private TextView tv_count = null;
    private ImageView iv_count = null;
    private TextView tv_value = null;
    private ImageView iv_value = null;

    private DzMutilSelLogic dzMutilSelLogic = null;
    private AppDeviceUsageSurveyDTO deviceUsageInfo;
    private List<AppDeviceUsageSurveyChildDTO> appDeviceUsageList = null;
    private DataDeviceAdapter adapter;
    private int currentGrade;
    private int selPreviewGrade;
    private SortType sortType;
    private boolean isAsc;
    private Map<String, Dictionary> selStationItems;

    private CharacterParserUtil characterParserUtil = null;
    private ProgressDialog dialog;
    private boolean isAverageTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_mobile);

        initData();
        initView();
    }

    private void initView() {
        listView = (ListView)findViewById(R.id.list);
        ll_station = (LinearLayout)findViewById(R.id.ll_station);
        ll_count = (LinearLayout)findViewById(R.id.ll_count);
        ll_value = (LinearLayout)findViewById(R.id.ll_value);
        re_con = this.findViewById(R.id.re_con);
        re_zhidui = this.findViewById(R.id.re_zhidui);
        re_dadui = this.findViewById(R.id.re_dadui);
        re_zhongdui = this.findViewById(R.id.re_zhongdui);
        tv_zhidui = (TextView)findViewById(R.id.tv_zhidui);
        tv_dadui = (TextView)findViewById(R.id.tv_dadui);
        tv_zhongdui = (TextView)findViewById(R.id.tv_zhongdui);
        tv_station = (TextView)findViewById(R.id.tv_station);
        iv_station = (ImageView) findViewById(R.id.iv_station);
        tv_count = (TextView)findViewById(R.id.tv_count);
        iv_count = (ImageView) findViewById(R.id.iv_count);
        tv_value = (TextView)findViewById(R.id.tv_value);
        iv_value = (ImageView) findViewById(R.id.iv_value);

        initSearchConditionView();
        ll_station.setTag(false);
        ll_count.setTag(false);
        ll_value.setTag(false);

        ll_station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sortType != SortType.NAME) {
                    sortType = SortType.NAME;
                    isAsc = false;
                } else {
                    boolean asc = (boolean)view.getTag();
                    isAsc = !asc;
                }
                view.setTag(isAsc);
                updateDeviceUseListView();

                tv_station.setTextColor(Color.rgb(255, 152, 0));
                tv_count.setTextColor(Color.rgb(53, 53, 53));
                tv_value.setTextColor(Color.rgb(53, 53, 53));
                if (isAsc) {
                    iv_station.setImageResource(R.drawable.up_cur);
                } else {
                    iv_station.setImageResource(R.drawable.down_cur);
                }
                iv_count.setImageResource(R.drawable.updown);
                iv_value.setImageResource(R.drawable.updown);
            }
        });

        ll_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sortType != SortType.COUNT) {
                    sortType = SortType.COUNT;
                    isAsc = false;
                } else {
                    boolean asc = (boolean)view.getTag();
                    isAsc = !asc;
                }
                view.setTag(isAsc);
                updateDeviceUseListView();

                tv_station.setTextColor(Color.rgb(53, 53, 53));
                tv_count.setTextColor(Color.rgb(255, 152, 0));
                tv_value.setTextColor(Color.rgb(53, 53, 53));
                iv_station.setImageResource(R.drawable.updown);
                if (isAsc) {
                    iv_count.setImageResource(R.drawable.up_cur);
                } else {
                    iv_count.setImageResource(R.drawable.down_cur);
                }
                iv_value.setImageResource(R.drawable.updown);
            }
        });

        ll_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sortType != SortType.TIME) {
                    sortType = SortType.TIME;
                    isAsc = false;
                } else {
                    boolean asc = (boolean)view.getTag();
                    isAsc = !asc;
                }
                view.setTag(isAsc);
                updateDeviceUseListView();

                tv_station.setTextColor(Color.rgb(53, 53, 53));
                tv_count.setTextColor(Color.rgb(53, 53, 530));
                tv_value.setTextColor(Color.rgb(255, 152, 0));
                iv_station.setImageResource(R.drawable.updown);
                iv_count.setImageResource(R.drawable.updown);
                if (isAsc) {
                    iv_value.setImageResource(R.drawable.up_cur);
                } else {
                    iv_value.setImageResource(R.drawable.down_cur);
                }
            }
        });

        re_zhidui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_zhidui.setTextColor(Color.rgb(255, 152, 0));
                tv_dadui.setTextColor(Color.rgb(53, 53, 53));
                tv_zhongdui.setTextColor(Color.rgb(53, 53, 53));
                showDuizhanSel(DzMutilSelLogic.TYPE_LEVEL1);
            }
        });


        re_dadui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_zhidui.setTextColor(Color.rgb(53, 53, 53));
                tv_dadui.setTextColor(Color.rgb(255, 152, 0));
                tv_zhongdui.setTextColor(Color.rgb(53, 53, 53));
                showDuizhanSel(DzMutilSelLogic.TYPE_LEVEL2);
            }
        });

        re_zhongdui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_zhidui.setTextColor(Color.rgb(53, 53, 53));
                tv_dadui.setTextColor(Color.rgb(53, 53, 53));
                tv_zhongdui.setTextColor(Color.rgb(255, 152, 0));
                showDuizhanSel(DzMutilSelLogic.TYPE_LEVEL3);
            }
        });

        dzMutilSelLogic = new DzMutilSelLogic(getWindow().getDecorView(), this);
        dzMutilSelLogic.setDefaultCheckValue(true);
        dzMutilSelLogic.setOnDzMutilSelLogicListener(new DzMutilSelLogic.OnDzMutilSelLogicListener() {
            @Override
            public void onConfirmClick(Map<String, Dictionary> items) {
                selStationItems = filterStationItems(items);
                if(selPreviewGrade != dzMutilSelLogic.getType()) {
                    selPreviewGrade = dzMutilSelLogic.getType();
                    loadData();
                } else {
                    updateDeviceUseListView();
                }
            }

            @Override
            public void onUnChanged() {
                if (selPreviewGrade == DzMutilSelLogic.TYPE_LEVEL1) {
                    tv_zhidui.setTextColor(Color.rgb(255, 152, 0));
                    tv_dadui.setTextColor(Color.rgb(53, 53, 53));
                    tv_zhongdui.setTextColor(Color.rgb(53, 53, 53));
                } else if (selPreviewGrade == DzMutilSelLogic.TYPE_LEVEL2) {
                    tv_zhidui.setTextColor(Color.rgb(53, 53, 53));
                    tv_dadui.setTextColor(Color.rgb(255, 152, 0));
                    tv_zhongdui.setTextColor(Color.rgb(53, 53, 53));
                } else if (selPreviewGrade == DzMutilSelLogic.TYPE_LEVEL3) {
                    tv_zhidui.setTextColor(Color.rgb(53, 53, 53));
                    tv_dadui.setTextColor(Color.rgb(53, 53, 53));
                    tv_zhongdui.setTextColor(Color.rgb(255, 152, 0));
                }
            }
        });
        Integer dayCount = 0;
        if(deviceUsageInfo != null) {
            dayCount = deviceUsageInfo.getTotalDay();
        }
        adapter = new DataDeviceAdapter(this, appDeviceUsageList, isAverageTime, dayCount);
        listView.setAdapter(adapter);
    }

    private Map<String, Dictionary> filterStationItems(Map<String, Dictionary> items) {
        Map<String, Dictionary> retItems = new LinkedHashMap<>();
        if(items != null && items.size() > 0) {
            for(Map.Entry<String, Dictionary> item : items.entrySet()) {
                if(item.getKey().endsWith("0000")) {
                    if(!containsSubStation(item.getKey(), 4, items)) {
                        retItems.put(item.getKey(), item.getValue());
                    }
                } else if(item.getKey().endsWith("00")) {
                    if(!containsSubStation(item.getKey(), 6, items)) {
                        retItems.put(item.getKey(), item.getValue());
                    }
                } else {
                    retItems.put(item.getKey(), item.getValue());
                }
            }
        }
        return retItems;
    }

    private boolean containsSubStation(String stationCode, int lastIndex, Map<String, Dictionary> items) {
        boolean ret = false;
        String code = stationCode.substring(0, lastIndex);
        for(String item : items.keySet()) {
            if(item.startsWith(code) && !item.equals(stationCode)) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    private void initSearchConditionView() {
        if (selPreviewGrade == DzMutilSelLogic.TYPE_LEVEL1) {
            tv_zhidui.setTextColor(Color.rgb(255, 152, 0));
            tv_dadui.setTextColor(Color.rgb(53, 53, 53));
            tv_zhongdui.setTextColor(Color.rgb(53, 53, 53));
        } else if (selPreviewGrade == DzMutilSelLogic.TYPE_LEVEL2) {
            tv_zhidui.setTextColor(Color.rgb(53, 53, 53));
            tv_dadui.setTextColor(Color.rgb(255, 152, 0));
            tv_zhongdui.setTextColor(Color.rgb(53, 53, 53));
        } else if (selPreviewGrade == DzMutilSelLogic.TYPE_LEVEL3) {
            tv_zhidui.setTextColor(Color.rgb(53, 53, 53));
            tv_dadui.setTextColor(Color.rgb(53, 53, 53));
            tv_zhongdui.setTextColor(Color.rgb(255, 152, 0));
        }

        if(currentGrade == 0) {
            re_zhidui.setVisibility(View.VISIBLE);
            re_dadui.setVisibility(View.VISIBLE);
            re_zhongdui.setVisibility(View.VISIBLE);
            re_con.setVisibility(View.VISIBLE);
        } else if(currentGrade == 1) {
            re_zhidui.setVisibility(View.GONE);
            re_dadui.setVisibility(View.VISIBLE);
            re_zhongdui.setVisibility(View.VISIBLE);
            re_con.setVisibility(View.VISIBLE);
        } else if(currentGrade == 2 || currentGrade == 3) {
            re_zhidui.setVisibility(View.GONE);
            re_dadui.setVisibility(View.GONE);
            re_zhongdui.setVisibility(View.GONE);
            re_con.setVisibility(View.GONE);
        }

        if(SortType.NAME == sortType) {
            tv_station.setTextColor(Color.rgb(255, 152, 0));
            tv_count.setTextColor(Color.rgb(53, 53, 53));
            tv_value.setTextColor(Color.rgb(53, 53, 53));
            if (isAsc) {
                iv_station.setImageResource(R.drawable.up_cur);
            } else {
                iv_station.setImageResource(R.drawable.down_cur);
            }
            iv_count.setImageResource(R.drawable.updown);
            iv_value.setImageResource(R.drawable.updown);
        } else if(SortType.COUNT == sortType) {
            tv_station.setTextColor(Color.rgb(53, 53, 53));
            tv_count.setTextColor(Color.rgb(255, 152, 0));
            tv_value.setTextColor(Color.rgb(53, 53, 53));
            iv_station.setImageResource(R.drawable.updown);
            if (isAsc) {
                iv_count.setImageResource(R.drawable.up_cur);
            } else {
                iv_count.setImageResource(R.drawable.down_cur);
            }
            iv_value.setImageResource(R.drawable.updown);
        } else if(SortType.TIME == sortType) {
            tv_station.setTextColor(Color.rgb(53, 53, 53));
            tv_count.setTextColor(Color.rgb(53, 53, 530));
            tv_value.setTextColor(Color.rgb(255, 152, 0));
            iv_station.setImageResource(R.drawable.updown);
            iv_count.setImageResource(R.drawable.updown);
            if (isAsc) {
                iv_value.setImageResource(R.drawable.up_cur);
            } else {
                iv_value.setImageResource(R.drawable.down_cur);
            }
        }
        if(!isAverageTime) {
            tv_count.setText("设备总数");
            tv_value.setText("总时长");
        } else {
            tv_count.setText("应用日均时长");
            tv_value.setText("采集日均时长");
        }
    }

    private void initData() {
        characterParserUtil = CharacterParserUtil.getInstance();
        Intent intent = getIntent();
        deviceUsageInfo = (AppDeviceUsageSurveyDTO)intent.getSerializableExtra("device_usage_info");
        if(deviceUsageInfo == null || deviceUsageInfo.getAppDeviceUsageSurveyChildDTO() == null) {
            appDeviceUsageList = new ArrayList<>();
        } else {
            appDeviceUsageList = deviceUsageInfo.getAppDeviceUsageSurveyChildDTO();
        }
        initPreviewGrade();
        sortType = SortType.values()[intent.getIntExtra("sort", SortType.TIME.ordinal())];
        isAverageTime = intent.getBooleanExtra("is_average_time", false);
        isAsc = false;
        dataDeviceSort(appDeviceUsageList, sortType, isAsc);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在加载数据...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.show();

        requestOperateTimeOverview();
    }

    private boolean checkStation(AppDeviceUsageSurveyChildDTO info) {
        boolean ret = false;
        if(selPreviewGrade == DzMutilSelLogic.TYPE_LEVEL1) {
            ret = checkLevel1Station(info);
        } else if(selPreviewGrade == DzMutilSelLogic.TYPE_LEVEL2) {
            ret = checkLevel2Station(info);
        } else if(selPreviewGrade == DzMutilSelLogic.TYPE_LEVEL3) {
            ret = checkLevel3Station(info);
        }
        return ret;
    }

    private boolean checkLevel1Station(AppDeviceUsageSurveyChildDTO info) {
        boolean ret = false;
        if (selStationItems != null && selStationItems.size() > 0) {
            if (selStationItems.containsKey(info.getDepartmentCode())) {
                ret = true;
            }
        } else {
            ret = true;
        }
        return ret;
    }

    private boolean checkLevel2Station(AppDeviceUsageSurveyChildDTO info) {
        boolean ret = false;
        String departmentCode = info.getDepartmentCode();
        if (selStationItems != null && selStationItems.size() > 0) {
            if (selStationItems.containsKey(departmentCode)) {
                ret = true;
            } else {
                if (departmentCode.length() > 4) {
                    String zdCode = departmentCode.substring(0, 4) + "0000";
                    if (selStationItems.containsKey(zdCode)) {
                        ret = true;
                    }
                }
            }
        } else {
            ret = true;
        }
        return ret;
    }

    private boolean checkLevel3Station(AppDeviceUsageSurveyChildDTO info) {
        boolean ret = false;
        String departmentCode = info.getDepartmentCode();
        if (selStationItems != null && selStationItems.size() > 0) {
            if (selStationItems.containsKey(departmentCode)) {
                ret = true;
            } else {
                if (departmentCode.length() > 6) {
                    String ddCode = departmentCode.substring(0, 6) + "00";
                    if (selStationItems.containsKey(ddCode)) {
                        ret = true;
                    } else {
                        if (departmentCode.length() > 4) {
                            String zdCode = departmentCode.substring(0, 4) + "0000";
                            if (selStationItems.containsKey(zdCode)) {
                                ret = true;
                            }
                        }
                    }
                }
            }
        } else {
            ret = true;
        }
        return ret;
    }

    private void updateDeviceUseListView() {
        if(deviceUsageInfo == null && deviceUsageInfo.getAppDeviceUsageSurveyChildDTO() == null) {
            appDeviceUsageList.clear();
            adapter.notifyDataSetChanged();
            return;
        }
        List<AppDeviceUsageSurveyChildDTO> tempDeviceUsageList = getDeviceUsageList(deviceUsageInfo.getAppDeviceUsageSurveyChildDTO());

        if(tempDeviceUsageList.size() > 0) {
            appDeviceUsageList.clear();
            dataDeviceSort(tempDeviceUsageList, sortType, isAsc);
            appDeviceUsageList.addAll(tempDeviceUsageList);
            adapter.notifyDataSetChanged();
        } else {
            appDeviceUsageList.clear();
            adapter.notifyDataSetChanged();
        }
    }

    private List<AppDeviceUsageSurveyChildDTO> getDeviceUsageList(List<AppDeviceUsageSurveyChildDTO> usageList) {
        List<AppDeviceUsageSurveyChildDTO> list = new ArrayList<>();
        if(usageList.size() > 0) {
            for(AppDeviceUsageSurveyChildDTO item : usageList) {
                if(checkStation(item)) {
                    list.add(item);
                }
            }
        }
        return list;
    }

    private void dataDeviceSort(List<AppDeviceUsageSurveyChildDTO> integrityList, final SortType type, final boolean asc) {
        Collections.sort(integrityList, new Comparator<AppDeviceUsageSurveyChildDTO>() {
            @Override
            public int compare(AppDeviceUsageSurveyChildDTO t1, AppDeviceUsageSurveyChildDTO t2) {
                int ret = 0;
                if(SortType.NAME == type) {
                    ret = compareName(asc, t1, t2);
                } else if(SortType.COUNT == type) {
                    if(!isAverageTime) {
                        ret = compareCount(asc, t1, t2);
                    } else {
                        ret = compareYYAverageTime(asc, t1, t2);
                    }
                } else if(SortType.TIME == type) {
                    if(!isAverageTime) {
                        ret = compareTime(asc, t1, t2);
                    } else {
                        ret = compareCJAverageTime(asc, t1, t2);
                    }
                }
                return ret;
            }
        });
    }

    private int compareName(boolean asc, AppDeviceUsageSurveyChildDTO t1, AppDeviceUsageSurveyChildDTO t2) {
        int ret = 0;
        if(asc) {
            if(t1.getDepartmentName() == null) {
                ret = -1;
            } else {
                ret = characterParserUtil.getSelling(t1.getDepartmentName()).compareTo(characterParserUtil.getSelling(t2.getDepartmentName()));
            }
        } else {
            if(t2.getDepartmentName() == null) {
                ret = -1;
            } else {
                ret = characterParserUtil.getSelling(t2.getDepartmentName()).compareTo(characterParserUtil.getSelling(t1.getDepartmentName()));
            }
        }
        return ret;
    }

    private int compareCount(boolean asc, AppDeviceUsageSurveyChildDTO t1, AppDeviceUsageSurveyChildDTO t2) {
        int ret = 0;
        if(asc) {
            Integer t1Count = getUseCount(t1);
            Integer t2Count = getUseCount(t2);
            ret = t1Count.compareTo(t2Count);
        } else {
            Integer t1Count = getUseCount(t1);
            Integer t2Count = getUseCount(t2);
            ret = t2Count.compareTo(t1Count);
        }
        return ret;
    }

    private int compareTime(boolean asc, AppDeviceUsageSurveyChildDTO t1, AppDeviceUsageSurveyChildDTO t2) {
        int ret = 0;
        if(asc) {
            Float t1Time = getUseTime(t1);
            Float t2Time = getUseTime(t2);
            ret = t1Time.compareTo(t2Time);
        } else {
            Float t1Time = getUseTime(t1);
            Float t2Time = getUseTime(t2);
            ret = t2Time.compareTo(t1Time);
        }
        return ret;
    }

    private Integer getUseCount(AppDeviceUsageSurveyChildDTO info) {
        int count = info.getTotalCnt() == null ? 0 : info.getTotalCnt();
        return count;
    }

    private Float getUseTime(AppDeviceUsageSurveyChildDTO info) {
        Float operateTime = 0f;
        if(info.getCjOperateTime() != null) {
            operateTime += info.getCjOperateTime();
        }
        if(info.getYyOperateTime() != null) {
            operateTime += info.getYyOperateTime();
        }
        return operateTime;
    }

    private int compareYYAverageTime(boolean asc, AppDeviceUsageSurveyChildDTO t1, AppDeviceUsageSurveyChildDTO t2) {
        int ret = 0;
        if(asc) {
            Float t1Time = getAverageTime(t1.getYyOperateTime(), t1.getYyUseCnt());
            Float t2Time = getAverageTime(t2.getYyOperateTime(), t2.getYyUseCnt());
            ret = t1Time.compareTo(t2Time);
        } else {
            Float t1Time = getAverageTime(t1.getYyOperateTime(), t1.getYyUseCnt());
            Float t2Time = getAverageTime(t2.getYyOperateTime(), t2.getYyUseCnt());
            ret = t2Time.compareTo(t1Time);
        }
        return ret;
    }

    private int compareCJAverageTime(boolean asc, AppDeviceUsageSurveyChildDTO t1, AppDeviceUsageSurveyChildDTO t2) {
        int ret = 0;
        if(asc) {
            Float t1Time = getAverageTime(t1.getCjOperateTime(), t1.getCjUseCnt());
            Float t2Time = getAverageTime(t2.getCjOperateTime(), t2.getCjUseCnt());
            ret = t1Time.compareTo(t2Time);
        } else {
            Float t1Time = getAverageTime(t1.getCjOperateTime(), t1.getCjUseCnt());
            Float t2Time = getAverageTime(t2.getCjOperateTime(), t2.getCjUseCnt());
            ret = t2Time.compareTo(t1Time);
        }
        return ret;
    }

    private Float getAverageTime(Float operateTime, Integer useCount) {
        Integer dayCount = 0;
        if(deviceUsageInfo != null) {
            dayCount = deviceUsageInfo.getTotalDay();
        }
        if (operateTime == null || operateTime == 0 ||
                useCount == null || useCount == 0 ||
                dayCount == null || dayCount == 0) {
            return 0f;
        }
        return operateTime / dayCount / useCount;
    }

    private void requestOperateTimeOverview() {
        OkHttpClientManager http = OkHttpClientManager.getInstance();
        final Gson gson = GsonUtil.create();
        Map<String, String> params = getDeviceUsageParams();
        if(params != null) {
            http.postAsyn(http.convertToURL(Constant.METHOD_GET_DEVICE_OVERVIEW), new OkHttpClientManager.ResultCallback<SimpleRestResult>() {
                @Override
                public void onError(Request request, Exception e) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(DataMobileActivity.this, "数据加载失败！", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(SimpleRestResult result) {
                    if (result != null && result.isSuccess()) {
                        String jsonStr = gson.toJson(result.getData());
                        deviceUsageInfo = gson.fromJson(jsonStr, AppDeviceUsageSurveyDTO.class);
                        updateDeviceUseListView();
                    }
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }, gson.toJson(params));
        }
    }

    private Map<String, String> getDeviceUsageParams() {
        Map<String, String> params = null;
        PrefUserInfo pui = PrefUserInfo.getInstance();
        String departmentCode = pui.getUserInfo("department_code");
        if(!StringUtil.isEmpty(departmentCode)) {
            params = new HashMap<>();
            params.put("departmentCode", departmentCode);
            int grade = currentGrade;
            if (currentGrade == 3 && departmentCode.length() == 8) {
                params.put("departmentCode", departmentCode.substring(0, 6) + "00");
                grade = 2;
            } else {
                params.put("departmentCode", departmentCode);
            }
            params.put("districtCode", pui.getUserInfo("district_code"));
            params.put("grade", StringUtil.get(grade));
            params.put("previewGrade", StringUtil.get(selPreviewGrade));

            Calendar curDate = Calendar.getInstance();
            curDate.set(Calendar.DAY_OF_MONTH, 1);
            String firstDay = format.format(curDate.getTime());
            params.put("startDate", String.format("%s 00:00:00", firstDay));

            curDate = Calendar.getInstance();
            String lastDay = timeFormat.format(curDate.getTime());
            params.put("endDate", String.format("%s 23:59:59", lastDay));
        }
        return params;
    }

    private void initPreviewGrade() {
        currentGrade = 0;
        selPreviewGrade = 0;
        PrefUserInfo pui = PrefUserInfo.getInstance();
        String grade = pui.getUserInfo("department_grade");
        if(StringUtil.isEmpty(grade)) {
            return;
        }
        Integer intGrade = Utils.ToInteger(grade);
        if(intGrade == null) {
            return;
        }
        currentGrade = intGrade.intValue();
        if (currentGrade == 0) {
            selPreviewGrade = DzMutilSelLogic.TYPE_LEVEL1;
        } else if (currentGrade == 1) {
            selPreviewGrade = DzMutilSelLogic.TYPE_LEVEL2;
        } else if (currentGrade == 2 || currentGrade == 3) {
            selPreviewGrade = DzMutilSelLogic.TYPE_LEVEL3;
        }
    }

    private void showDuizhanSel(int type) {
        View re_con = this.findViewById(R.id.re_con);
        dzMutilSelLogic.show(re_con, type);
    }
}
