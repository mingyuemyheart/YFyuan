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
import com.google.gson.reflect.TypeToken;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.adapter.DataQualityAdapter;
import com.moft.xfapply.activity.bussiness.CommonBussiness;
import com.moft.xfapply.activity.logic.DzMutilSelLogic;
import com.moft.xfapply.activity.logic.RadioLogic;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.logic.network.OkHttpClientManager;
import com.moft.xfapply.logic.setting.PrefUserInfo;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.dto.FireIntegrityStatisticsDTO;
import com.moft.xfapply.model.external.dto.FireIntegrityWholeDTO;
import com.moft.xfapply.model.external.result.ArrayRestResult;
import com.moft.xfapply.utils.CharacterParserUtil;
import com.moft.xfapply.utils.GsonUtil;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.utils.Utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class DataQualityActivity extends BaseActivity {
    public enum SortType {
        NAME,
        COUNT,
        VALUE
    }
    private ListView listView;
    private LinearLayout ll_station;
    private LinearLayout ll_count;
    private LinearLayout ll_value;
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

    private TextView tv_type_filter;

    private RadioLogic radioLogic = null;
    private DzMutilSelLogic dzMutilSelLogic = null;
    private List<FireIntegrityWholeDTO> integrityList;
    private List<FireIntegrityStatisticsDTO> typeIntegrityList;
    private DataQualityAdapter adapter;
    private int currentGrade;
    private int selPreviewGrade;
    private String selEleType;
    private SortType sortType;
    private boolean isAsc;
    private Map<String, Dictionary> selStationItems;

    private CharacterParserUtil characterParserUtil = null;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_quality);

        initData();
        initView();
    }

    private void initView() {
        listView = (ListView)findViewById(R.id.list);
        tv_type_filter = (TextView)findViewById(R.id.tv_type_filter);
        ll_station = (LinearLayout)findViewById(R.id.ll_station);
        ll_count = (LinearLayout)findViewById(R.id.ll_count);
        ll_value = (LinearLayout)findViewById(R.id.ll_value);
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
                updateIntegrityListView();

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
                updateIntegrityListView();

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
                if(sortType != SortType.VALUE) {
                    sortType = SortType.VALUE;
                    isAsc = false;
                } else {
                    boolean asc = (boolean)view.getTag();
                    isAsc = !asc;
                }
                view.setTag(isAsc);
                updateIntegrityListView();

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
        radioLogic = new RadioLogic(getWindow().getDecorView(), this);
        radioLogic.setListener(new RadioLogic.OnRadioLogicListener() {
            @Override
            public void onLoadListData(List<Dictionary> dataList) {
                dataList.clear();
                dataList.add(new Dictionary("一二级重点单位", AppDefs.CompEleType.KEY_UNIT.toString()));
                dataList.add(new Dictionary("消火栓", AppDefs.CompEleType.WATERSOURCE_FIRE_HYDRANT.toString()));
                dataList.add(new Dictionary("消防水池", AppDefs.CompEleType.WATERSOURCE_FIRE_POOL.toString()));
                dataList.add(new Dictionary("消防水鹤", AppDefs.CompEleType.WATERSOURCE_CRANE.toString()));
                dataList.add(new Dictionary("天然取水点", AppDefs.CompEleType.WATERSOURCE_NATURE_INTAKE.toString()));
                dataList.add(new Dictionary("消防车辆", AppDefs.CompEleType.FIRE_VEHICLE.toString()));
                dataList.add(new Dictionary("消防器材", AppDefs.CompEleType.EQUIPMENT.toString()));
                dataList.add(new Dictionary("灭火剂", AppDefs.CompEleType.EXTINGUISHING_AGENT.toString()));
                if(currentGrade == 0) {
                    dataList.add(new Dictionary("消防支队", AppDefs.CompEleType.DETACHMENT.toString()));
                    dataList.add(new Dictionary("消防大队", AppDefs.CompEleType.BATTALION.toString()));
                    dataList.add(new Dictionary("消防中队", AppDefs.CompEleType.SQUADRON.toString()));
                } else if(currentGrade == 1) {
                    dataList.add(new Dictionary("消防大队", AppDefs.CompEleType.BATTALION.toString()));
                    dataList.add(new Dictionary("消防中队", AppDefs.CompEleType.SQUADRON.toString()));
                } else if(currentGrade == 2 || currentGrade == 3) {
                    dataList.add(new Dictionary("消防中队", AppDefs.CompEleType.SQUADRON.toString()));
                }
            }

            @Override
            public int onGetCurrentPos() {
                return 0;
            }

            @Override
            public void onConfirmResult(Dictionary dic, int pos) {
                tv_type_filter.setText(dic.getValue());
                selEleType = dic.getCode();
                updateIntegrityListView();
            }

            @Override
            public void onUnChanged() {

            }
        });

        dzMutilSelLogic = new DzMutilSelLogic(getWindow().getDecorView(), this);
        dzMutilSelLogic.setType(selPreviewGrade);
        dzMutilSelLogic.setDefaultCheckValue(true);
        dzMutilSelLogic.setOnDzMutilSelLogicListener(new DzMutilSelLogic.OnDzMutilSelLogicListener() {
            @Override
            public void onConfirmClick(Map<String, Dictionary> items) {
                selStationItems = filterStationItems(items);
                if(selPreviewGrade != dzMutilSelLogic.getType()) {
                    selPreviewGrade = dzMutilSelLogic.getType();
                    loadData();
                } else {
                    updateIntegrityListView();
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

        adapter = new DataQualityAdapter(this, typeIntegrityList);
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
        } else if(currentGrade == 1) {
            re_zhidui.setVisibility(View.GONE);
            re_dadui.setVisibility(View.VISIBLE);
            re_zhongdui.setVisibility(View.VISIBLE);
        } else if(currentGrade == 2 || currentGrade == 3) {
            re_zhidui.setVisibility(View.GONE);
            re_dadui.setVisibility(View.GONE);
            re_zhongdui.setVisibility(View.GONE);
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
        } else if(SortType.VALUE == sortType) {
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
    }

    private void initData() {
        characterParserUtil = CharacterParserUtil.getInstance();

        Intent intent = getIntent();
        integrityList = (List<FireIntegrityWholeDTO>)intent.getSerializableExtra("data_quality_list");
        if(integrityList == null) {
            integrityList = new ArrayList<>();
        }
        initPreviewGrade(intent);
        selEleType = AppDefs.CompEleType.KEY_UNIT.toString();
        typeIntegrityList = getTypeIntegrityList(selEleType, integrityList);
        sortType = SortType.values()[intent.getIntExtra("sort", SortType.VALUE.ordinal())];
        isAsc = false;
        dataQualitySort(typeIntegrityList, sortType, isAsc);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    public void onTypeFilterClick(View view) {
        showTypeSel();
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

        requestIntegrityOverview();
    }

    private void dataQualitySort(List<FireIntegrityStatisticsDTO> integrityList, final SortType type, final boolean asc) {
        Collections.sort(integrityList, new Comparator<FireIntegrityStatisticsDTO>() {
            @Override
            public int compare(FireIntegrityStatisticsDTO t1, FireIntegrityStatisticsDTO t2) {
                int ret = 0;
                if(SortType.NAME == type) {
                    ret = compareName(asc, t1, t2);
                } else if(SortType.COUNT == type) {
                    ret = compareCount(asc, t1, t2);
                } else if(SortType.VALUE == type) {
                    ret = compareValue(asc, t1, t2);
                }
                return ret;
            }
        });
    }

    private int compareName(boolean asc, FireIntegrityStatisticsDTO t1, FireIntegrityStatisticsDTO t2) {
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

    private int compareCount(boolean asc, FireIntegrityStatisticsDTO t1, FireIntegrityStatisticsDTO t2) {
        int ret = 0;
        if(asc) {
            if(t1.getTotalCnt() == null) {
                ret = -1;
            } else {
                ret = t1.getTotalCnt().compareTo(t2.getTotalCnt());
            }
        } else {
            if(t2.getTotalCnt() == null) {
                ret = -1;
            } else {
                ret = t2.getTotalCnt().compareTo(t1.getTotalCnt());
            }
        }
        return ret;
    }

    private int compareValue(boolean asc, FireIntegrityStatisticsDTO t1, FireIntegrityStatisticsDTO t2) {
        if(asc) {
            if(t1.getTotalQuality() == null || t1.getTotalCnt() == null || t1.getTotalCnt() == 0) {
                return -1;
            }
            if(t2.getTotalQuality() == null || t2.getTotalCnt() == null || t2.getTotalCnt() == 0) {
                return 1;
            }
            return ((Double)(t1.getTotalQuality() / t1.getTotalCnt())).compareTo(t2.getTotalQuality() / t2.getTotalCnt());
        } else {
            if(t2.getTotalQuality() == null || t2.getTotalCnt() == null || t2.getTotalCnt() == 0) {
                return -1;
            }
            if(t1.getTotalQuality() == null || t1.getTotalCnt() == null || t1.getTotalCnt() == 0) {
                return 1;
            }
            return ((Double)(t2.getTotalQuality() / t2.getTotalCnt())).compareTo(t1.getTotalQuality() / t1.getTotalCnt());
        }
    }

    private void requestIntegrityOverview() {
        OkHttpClientManager http = OkHttpClientManager.getInstance();
        final Gson gson = GsonUtil.create();
        Map<String, String> params = getIntegrityParams();
        if(params != null) {
            http.postAsyn(http.convertToURL(Constant.METHOD_GET_INTEGRITY_OVERVIEW), new OkHttpClientManager.ResultCallback<ArrayRestResult>() {
                @Override
                public void onError(Request request, Exception e) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(DataQualityActivity.this, "数据加载失败！", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(ArrayRestResult result) {
                    if (result != null && result.isSuccess()) {
                        String jsonStr = gson.toJson(result.getContents());

                        Type listType = new TypeToken<List<FireIntegrityWholeDTO>>() {
                        }.getType();
                        integrityList = gson.fromJson(jsonStr, listType);
                        updateIntegrityListView();
                    }
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }, gson.toJson(params));
        }
    }

    private void updateIntegrityListView() {
        List<FireIntegrityStatisticsDTO> tempIntegrityList = getTypeIntegrityList(selEleType, integrityList);
        if(tempIntegrityList.size() > 0) {
            typeIntegrityList.clear();
            dataQualitySort(tempIntegrityList, sortType, isAsc);
            typeIntegrityList.addAll(tempIntegrityList);
            adapter.notifyDataSetChanged();
        } else {
            typeIntegrityList.clear();
            adapter.notifyDataSetChanged();
        }
    }

    private List<FireIntegrityStatisticsDTO> getTypeIntegrityList(String type, List<FireIntegrityWholeDTO> integrityList) {
        List<FireIntegrityStatisticsDTO> typeList = new ArrayList<>();
        if(integrityList != null && integrityList.size() > 0) {
            for(FireIntegrityWholeDTO stationIntegrity : integrityList) {
                List<FireIntegrityStatisticsDTO> typeIntegrityList = stationIntegrity.getFireIntegrityStatisticsDTOS();
                if(typeIntegrityList != null && typeIntegrityList.size() > 0) {
                    for(FireIntegrityStatisticsDTO item : typeIntegrityList) {
                        if(type.equals(item.getEntityType()) && checkStation(item)) {
                            typeList.add(item);
                            break;
                        }
                    }
                }
            }
        }
        return typeList;
    }

    private boolean checkStation(FireIntegrityStatisticsDTO info) {
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

    private boolean checkLevel1Station(FireIntegrityStatisticsDTO info) {
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

    private boolean checkLevel2Station(FireIntegrityStatisticsDTO info) {
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

    private boolean checkLevel3Station(FireIntegrityStatisticsDTO info) {
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

    private Map<String, String> getIntegrityParams() {
        Map<String, String> params = null;
        PrefUserInfo pui = PrefUserInfo.getInstance();
        String departmentCode = pui.getUserInfo("department_code");
        if (!StringUtil.isEmpty(departmentCode)) {
            int grade = currentGrade;
            params = new HashMap<>();
            params.put("departmentCode", departmentCode);
            if (currentGrade == 3 && departmentCode.length() == 8) {
                params.put("departmentCode", departmentCode.substring(0, 6) + "00");
                grade = 2;
            } else {
                params.put("departmentCode", departmentCode);
            }
            params.put("districtCode", pui.getUserInfo("district_code"));
            params.put("grade", StringUtil.get(grade));
            params.put("previewGrade", StringUtil.get(selPreviewGrade));
        }
        return params;
    }

    private void initPreviewGrade(Intent intent) {
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

        String orgCode = intent.getStringExtra("org_code");
        if(!StringUtil.isEmpty(orgCode)) {
            if(AppDefs.CompEleType.DETACHMENT.toString().equals(checkDepartmentCode(orgCode))) {
                selPreviewGrade = DzMutilSelLogic.TYPE_LEVEL2;
                selStationItems = getStationItemsByOrgCode(CommonBussiness.getInstance().getChildOrgListByCode(orgCode));
            } else if(AppDefs.CompEleType.BATTALION.toString().equals(checkDepartmentCode(orgCode))) {
                selPreviewGrade = DzMutilSelLogic.TYPE_LEVEL3;
                selStationItems = getStationItemsByOrgCode(CommonBussiness.getInstance().getChildOrgListByCode(orgCode));
            } else {
                if (currentGrade == 0) {
                    selPreviewGrade = DzMutilSelLogic.TYPE_LEVEL1;
                } else if (currentGrade == 1) {
                    selPreviewGrade = DzMutilSelLogic.TYPE_LEVEL2;
                } else if (currentGrade == 2 || currentGrade == 3) {
                    selPreviewGrade = DzMutilSelLogic.TYPE_LEVEL3;
                }
            }
        } else {
            if (currentGrade == 0) {
                selPreviewGrade = DzMutilSelLogic.TYPE_LEVEL1;
            } else if (currentGrade == 1) {
                selPreviewGrade = DzMutilSelLogic.TYPE_LEVEL2;
            } else if (currentGrade == 2 || currentGrade == 3) {
                selPreviewGrade = DzMutilSelLogic.TYPE_LEVEL3;
            }
        }
    }

    private Map<String, Dictionary> getStationItemsByOrgCode(List<Dictionary> list) {
        Map<String, Dictionary> ret = new LinkedHashMap<>();
        if(list != null && list.size() > 0) {
            for(Dictionary item : list) {
                ret.put(item.getId(), item);
            }
        }
        return ret;
    }

    private String checkDepartmentCode(String departmentCode) {
        String station = AppDefs.CompEleType.SQUADRON.toString();
        if(!StringUtil.isEmpty(departmentCode)) {
            if(departmentCode.length() >= 8) {
                String str = departmentCode.substring(departmentCode.length() - 4);
                if("0000".equals(str)) {
                    station = AppDefs.CompEleType.DETACHMENT.toString();
                    return station;
                }
                str = departmentCode.substring(departmentCode.length() - 2);
                if("00".equals(str)) {
                    station = AppDefs.CompEleType.BATTALION.toString();
                    return station;
                }
            }
        }
        return station;
    }

    private void showTypeSel() {
        dzMutilSelLogic.hide();
        View re_con = this.findViewById(R.id.re_con);
        radioLogic.show(re_con);
    }

    private void showDuizhanSel(int type) {
        radioLogic.hide();
        View re_con = this.findViewById(R.id.re_con);
        dzMutilSelLogic.show(re_con, type);
    }
}
