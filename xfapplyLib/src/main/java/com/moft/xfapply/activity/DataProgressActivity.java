package com.moft.xfapply.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.adapter.DataProcessAdapter;
import com.moft.xfapply.activity.bussiness.CommonBussiness;
import com.moft.xfapply.activity.logic.DzMutilSelLogic;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.logic.network.OkHttpClientManager;
import com.moft.xfapply.logic.setting.PrefUserInfo;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.dto.VerifyDepDTO;
import com.moft.xfapply.model.external.dto.VerifyStatDTO;
import com.moft.xfapply.model.external.result.ArrayRestResult;
import com.moft.xfapply.utils.CharacterParserUtil;
import com.moft.xfapply.utils.DictionaryUtil;
import com.moft.xfapply.utils.GsonUtil;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.utils.Utils;

import java.lang.reflect.Type;
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

public class DataProgressActivity extends BaseActivity {
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    public enum SortType {
        NAME,
        COUNT,
        NEW_COUNT
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
    private List<VerifyDepDTO> verifyList = null;
    private List<VerifyStatDTO> verifyStationList = null;
    private DataProcessAdapter adapter;
    private int currentGrade;
    private int selPreviewGrade;
    private SortType sortType;
    private boolean isAsc;
    private Map<String, Dictionary> selStationItems;

    private CharacterParserUtil characterParserUtil= null;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_progress);

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
                updateDataProcessListView();

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
                updateDataProcessListView();

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
                if(sortType != SortType.NEW_COUNT) {
                    sortType = SortType.NEW_COUNT;
                    isAsc = false;
                } else {
                    boolean asc = (boolean)view.getTag();
                    isAsc = !asc;
                }
                view.setTag(isAsc);
                updateDataProcessListView();

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
                    updateDataProcessListView();
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
        adapter = new DataProcessAdapter(this, verifyStationList);
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
        } else if(SortType.NEW_COUNT == sortType) {
            tv_station.setTextColor(Color.rgb(53, 53, 53));
            tv_count.setTextColor(Color.rgb(53, 53, 53));
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
        characterParserUtil= CharacterParserUtil.getInstance();

        Intent intent = getIntent();
        verifyList = (List<VerifyDepDTO>)intent.getSerializableExtra("data_verify_list");
        if(verifyList == null) {
            verifyList = new ArrayList<>();
        }
        initPreviewGrade(intent);
        verifyStationList = getWholeVerifyList(verifyList);
        sortType = SortType.values()[intent.getIntExtra("sort", SortType.NEW_COUNT.ordinal())];
        isAsc = false;
        dataProcessSort(verifyStationList, sortType, isAsc);
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

        requestVerifyOverview();
    }

    private List<VerifyStatDTO> getWholeVerifyList(List<VerifyDepDTO> verifyList) {
        List<VerifyStatDTO> wholeList = new ArrayList<>();
        if(verifyList != null && verifyList.size() > 0) {
            for(VerifyDepDTO stationVerify : verifyList) {
                if (checkStation(stationVerify)) {
                    List<VerifyStatDTO> typeVerifyList = stationVerify.getActionStats();
                    VerifyStatDTO info = new VerifyStatDTO();
                    info.setDepartmentCode(stationVerify.getDepartmentCode());
                    info.setDepartmentName(stationVerify.getDepartmentName());
                    info.setDetachmentCode(stationVerify.getDetachmentCode());
                    info.setDetachmentName(stationVerify.getDetachmentName());
                    int newCount = 0;
                    int modifyCount = 0;
                    int deleteCount = 0;
                    int verifyCount = 0;
                    int updateCount = 0;
                    if (typeVerifyList != null && typeVerifyList.size() > 0) {
                        for (VerifyStatDTO item : typeVerifyList) {

                            if (selPreviewGrade == DzMutilSelLogic.TYPE_LEVEL1) {
                                if (AppDefs.CompEleType.DETACHMENT.name().equals(item.getEntityType())) {
                                    continue;
                                }
                            } else if (selPreviewGrade == DzMutilSelLogic.TYPE_LEVEL2) {
                                if (AppDefs.CompEleType.BATTALION.name().equals(item.getEntityType())) {
                                    continue;
                                }
                            } else if (selPreviewGrade == DzMutilSelLogic.TYPE_LEVEL3) {
                                if (AppDefs.CompEleType.SQUADRON.name().equals(item.getEntityType())) {
                                    continue;
                                }
                            } else {}

                            if("WATERSOURCE".equals(item.getEntityType())) {
                                continue;
                            }
                            if (item.getNewCount() != null) {
                                newCount += item.getNewCount();
                            }
                            if (item.getDeleteCount() != null) {
                                deleteCount += item.getDeleteCount();
                            }
                            if (item.getModifyCount() != null) {
                                modifyCount += item.getModifyCount();
                            }
                            if (item.getUpdateCount() != null) {
                                updateCount += item.getUpdateCount();
                            }
                        }
                        verifyCount += newCount + deleteCount + modifyCount;
                    }
                    info.setNewCount(newCount);
                    info.setDeleteCount(deleteCount);
                    info.setModifyCount(modifyCount);
                    info.setVerifyCount(verifyCount);
                    info.setUpdateCount(updateCount);
                    wholeList.add(info);
                }
            }
        }
        return wholeList;
    }

    private boolean checkStation(VerifyDepDTO info) {
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

    private boolean checkLevel1Station(VerifyDepDTO info) {
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

    private boolean checkLevel2Station(VerifyDepDTO info) {
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

    private boolean checkLevel3Station(VerifyDepDTO info) {
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

    private void updateDataProcessListView() {
        List<VerifyStatDTO> tempVerifyStationList = getWholeVerifyList(verifyList);
        if(tempVerifyStationList.size() > 0) {
            verifyStationList.clear();
            dataProcessSort(tempVerifyStationList, sortType, isAsc);
            verifyStationList.addAll(tempVerifyStationList);
            adapter.notifyDataSetChanged();
        } else {
            verifyStationList.clear();
            adapter.notifyDataSetChanged();
        }
    }

    private void dataProcessSort(List<VerifyStatDTO> integrityList, final DataProgressActivity.SortType type, final boolean asc) {
        Collections.sort(integrityList, new Comparator<VerifyStatDTO>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public int compare(VerifyStatDTO t1, VerifyStatDTO t2) {
                int ret = 0;
                if(SortType.NAME == type) {
                    ret = compareName(asc, t1, t2);
                } else if(SortType.COUNT == type) {
                    ret = compareCount(asc, t1, t2);
                } else if(SortType.NEW_COUNT == type) {
                    ret = compareNewCount(asc, t1, t2);
                }
                return ret;
            }
        });
    }

    private int compareName(boolean asc, VerifyStatDTO t1, VerifyStatDTO t2) {
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

    private int compareCount(boolean asc, VerifyStatDTO t1, VerifyStatDTO t2) {
        int ret = 0;
        if(asc) {
            if(t1.getUpdateCount() == null) {
                ret = -1;
            } else {
                ret = t1.getUpdateCount().compareTo(t2.getUpdateCount());
            }
        } else {
            if(t2.getUpdateCount() == null) {
                ret = -1;
            } else {
                ret = t2.getUpdateCount().compareTo(t1.getUpdateCount());
            }
        }
        return ret;
    }

    private int compareNewCount(boolean asc, VerifyStatDTO t1, VerifyStatDTO t2) {
        int ret = 0;
        if(asc) {
            if(t1.getNewCount() == null) {
                ret = -1;
            } else {
                ret = t1.getNewCount().compareTo(t2.getNewCount());
            }
        } else {
            if(t2.getNewCount() == null) {
                ret = -1;
            } else {
                ret = t2.getNewCount().compareTo(t1.getNewCount());
            }
        }
        return ret;
    }

    private void requestVerifyOverview() {
        OkHttpClientManager http = OkHttpClientManager.getInstance();
        final Gson gson = GsonUtil.create();
        Map<String, String> params = getVerifyParams();
        if(params != null) {
            http.postAsyn(http.convertToURL(Constant.METHOD_GET_VERIFY_OVERVIEW), new OkHttpClientManager.ResultCallback<ArrayRestResult>() {
                @Override
                public void onError(Request request, Exception e) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(DataProgressActivity.this, "数据加载失败！", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(ArrayRestResult result) {
                    if (result != null && result.isSuccess()) {
                        String jsonStr = gson.toJson(result.getContents());

                        Type listType = new TypeToken<List<VerifyDepDTO>>() {
                        }.getType();
                        verifyList = gson.fromJson(jsonStr, listType);
                        updateDataProcessListView();
                    }
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }, params);
        }
    }

    private Map<String, String> getVerifyParams() {
        Map<String, String> params = null;
        PrefUserInfo pui = PrefUserInfo.getInstance();
        String departmentUuid = pui.getUserInfo("department_uuid");
        if(!StringUtil.isEmpty(departmentUuid)) {
            int grade = currentGrade;
            params = new HashMap<>();
            if (currentGrade == 3) {
                Dictionary dic = DictionaryUtil.getDictionaryByCode(departmentUuid, LvApplication.getInstance().getCurrentOrgList());
                if (dic != null) {
                    params.put("departmentUuid", dic.getParentCode());
                    grade = 2;
                } else {
                    params.put("departmentUuid", departmentUuid);
                }
            } else {
                params.put("departmentUuid", departmentUuid);
            }
            params.put("districtCode", pui.getUserInfo("district_code"));
            params.put("grade", StringUtil.get(grade));
            params.put("previewGrade", StringUtil.get(selPreviewGrade));
            Calendar curDate = Calendar.getInstance();
            curDate.set(Calendar.DAY_OF_MONTH, 1);
            String firstDay = format.format(curDate.getTime());
            params.put("startDate", String.format("%s 00:00:00", firstDay));

            curDate = Calendar.getInstance();
            curDate.add(Calendar.MONTH, 1);
            curDate.set(Calendar.DAY_OF_MONTH, 0);
            String lastDay = format.format(curDate.getTime());
            params.put("endDate", String.format("%s 23:59:59", lastDay));
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

    private void showDuizhanSel(int type) {
        dzMutilSelLogic.show(re_con, type);
    }
}
