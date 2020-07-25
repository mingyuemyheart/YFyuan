package com.moft.xfapply.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.adapter.DataDynamicAdapter;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.logic.network.OkHttpClientManager;
import com.moft.xfapply.logic.setting.PrefUserInfo;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.dto.AppDeviceUsageSurveyChildDTO;
import com.moft.xfapply.model.external.dto.AppDeviceUsageSurveyDTO;
import com.moft.xfapply.model.external.dto.EntityMaintainTaskDTO;
import com.moft.xfapply.model.external.dto.FireIntegrityStatisticsDTO;
import com.moft.xfapply.model.external.dto.FireIntegrityWholeDTO;
import com.moft.xfapply.model.external.dto.VerifyDepDTO;
import com.moft.xfapply.model.external.dto.VerifyStatDTO;
import com.moft.xfapply.model.external.result.ArrayRestResult;
import com.moft.xfapply.model.external.result.SimpleRestResult;
import com.moft.xfapply.utils.DateUtil;
import com.moft.xfapply.utils.DictionaryUtil;
import com.moft.xfapply.utils.GsonUtil;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.utils.Utils;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Request;

public class FragmentZqsl extends Fragment {
    private int REQUEST_FRAGMENT_ZQSL_RESULT = 0;

    private DecimalFormat decimalFormat = new DecimalFormat("###################.#");
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private boolean hidden;

    private View ll_content = null;
    private View re_data_quality = null;
    private View re_pjfz = null;
    private View re_zsl = null;

    private View re_data_progress = null;
    private View ll_verify_count = null;
    private View ll_verify_new_count = null;

    private View re_data_mobile = null;
    private View ll_device_count = null;
    private View ll_device_time = null;
    private View ll_device_average_time = null;
    private RelativeLayout rl_data_dynamic = null;

    private TextView tv_today_commit_num = null;

    private TextView tv_data_quality_average;
    private TextView tv_data_quality_count;
    private RelativeLayout rl_data_quality_l1;
    private RelativeLayout rl_data_quality_l2;
    private RelativeLayout rl_data_quality_l3;
    private RelativeLayout rl_data_quality_r1;
    private RelativeLayout rl_data_quality_r2;
    private RelativeLayout rl_data_quality_r3;
    private TextView tv_data_quality_l1;
    private TextView tv_data_quality_l2;
    private TextView tv_data_quality_l3;
    private TextView tv_data_quality_r1;
    private TextView tv_data_quality_r2;
    private TextView tv_data_quality_r3;
    private TextView tv_data_quality_station_l1;
    private TextView tv_data_quality_station_l2;
    private TextView tv_data_quality_station_l3;
    private TextView tv_data_quality_station_r1;
    private TextView tv_data_quality_station_r2;
    private TextView tv_data_quality_station_r3;
    private TextView tv_data_quality_count_l1;
    private TextView tv_data_quality_count_l2;
    private TextView tv_data_quality_count_l3;
    private TextView tv_data_quality_count_r1;
    private TextView tv_data_quality_count_r2;
    private TextView tv_data_quality_count_r3;

    private TextView tv_verify_count;
    private TextView tv_verify_new_count;
    private RelativeLayout rl_verify_station_l1;
    private RelativeLayout rl_verify_station_l2;
    private RelativeLayout rl_verify_station_l3;
    private RelativeLayout rl_verify_station_r1;
    private RelativeLayout rl_verify_station_r2;
    private RelativeLayout rl_verify_station_r3;
    private TextView tv_verify_station_l1;
    private TextView tv_verify_station_l2;
    private TextView tv_verify_station_l3;
    private TextView tv_verify_station_r1;
    private TextView tv_verify_station_r2;
    private TextView tv_verify_station_r3;

    private TextView tv_device_count;
    private TextView tv_device_cj_count;
    private TextView tv_device_yy_count;

    private TextView tv_device_time;
    private TextView tv_device_cj_time;
    private TextView tv_device_yy_time;
    private TextView tv_device_yy_average_time;
    private TextView tv_device_cj_average_time;

    private ListView listView;
    private DataDynamicAdapter adapter = null;
    private List<FireIntegrityWholeDTO> integrityList = null;
    private List<VerifyDepDTO> verifyList = null;
    private AppDeviceUsageSurveyDTO deviceUsageSurveyInfo;
    private List<EntityMaintainTaskDTO> dataDynamicList = null;

    private Timer timer;
    private static final int INTERVAL = 60 * 1000;
    private static final int MSG_UPDATE_EVENT = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_UPDATE_EVENT:
                    requestDataDynamic(true);  // ID:975744, 【移动应用端】App今日提交数据，与数据动态数不一致。
                    break;
            }
        }
    };

    @Override
    public void onDestroy() {
        stopTimer();
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater
                .inflate(R.layout.fragment_zqsl, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        loadData();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("hidden", hidden);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            hidden = savedInstanceState.getBoolean("hidden");
        }
    }

    private void initView() {
        ll_content = getView().findViewById(R.id.ll_content);
        ll_content.setFocusable(true);
        ll_content.setFocusableInTouchMode(true);
        ll_content.requestFocus();
        tv_today_commit_num = (TextView)getView().findViewById(R.id.tv_today_commit_num);
        re_data_quality = getView().findViewById(R.id.re_data_quality);
        re_data_quality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DataQualityActivity.class);
                intent.putExtra("data_quality_list", (Serializable) integrityList);
                intent.putExtra("sort", DataQualityActivity.SortType.VALUE);
                startActivity(intent);
            }
        });
        re_pjfz = getView().findViewById(R.id.re_pjfz);
        re_pjfz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DataQualityActivity.class);
                intent.putExtra("data_quality_list", (Serializable) integrityList);
                intent.putExtra("sort", DataQualityActivity.SortType.VALUE.ordinal());
                startActivity(intent);
            }
        });
        re_zsl = getView().findViewById(R.id.re_zsl);
        re_zsl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DataQualityActivity.class);
                intent.putExtra("data_quality_list", (Serializable) integrityList);
                intent.putExtra("sort", DataQualityActivity.SortType.COUNT.ordinal());
                startActivity(intent);
            }
        });


        re_data_progress = getView().findViewById(R.id.re_data_progress);
        re_data_progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DataProgressActivity.class);
                intent.putExtra("data_verify_list", (Serializable) verifyList);
                intent.putExtra("sort", DataProgressActivity.SortType.NEW_COUNT.ordinal());
                startActivity(intent);
            }
        });
        ll_verify_count = getView().findViewById(R.id.ll_verify_count);

        ll_verify_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DataProgressActivity.class);
                intent.putExtra("data_verify_list", (Serializable) verifyList);
                intent.putExtra("sort", DataProgressActivity.SortType.COUNT.ordinal());
                startActivity(intent);
            }
        });
        ll_verify_new_count = getView().findViewById(R.id.ll_verify_new_count);
        ll_verify_new_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DataProgressActivity.class);
                intent.putExtra("data_verify_list", (Serializable) verifyList);
                intent.putExtra("sort", DataProgressActivity.SortType.NEW_COUNT.ordinal());
                startActivity(intent);
            }
        });

        re_data_mobile = getView().findViewById(R.id.re_data_mobile);
        re_data_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DataMobileActivity.class);
                intent.putExtra("device_usage_info", deviceUsageSurveyInfo);
                intent.putExtra("sort", DataMobileActivity.SortType.TIME.ordinal());
                startActivity(intent);
            }
        });
        ll_device_count = getView().findViewById(R.id.ll_device_count);
        ll_device_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DataMobileActivity.class);
                intent.putExtra("device_usage_info", deviceUsageSurveyInfo);
                intent.putExtra("sort", DataMobileActivity.SortType.COUNT.ordinal());
                startActivity(intent);
            }
        });
        ll_device_time = getView().findViewById(R.id.ll_device_time);
        ll_device_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DataMobileActivity.class);
                intent.putExtra("device_usage_info", deviceUsageSurveyInfo);
                intent.putExtra("sort", DataMobileActivity.SortType.TIME.ordinal());
                startActivity(intent);
            }
        });
        ll_device_average_time = getView().findViewById(R.id.ll_device_average_time);
        ll_device_average_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DataMobileActivity.class);
                intent.putExtra("device_usage_info", deviceUsageSurveyInfo);
                intent.putExtra("sort", DataMobileActivity.SortType.TIME.ordinal());
                intent.putExtra("is_average_time", true);
                startActivity(intent);
            }
        });

        rl_data_dynamic = (RelativeLayout) getView().findViewById(R.id.rl_data_dynamic);
        rl_data_dynamic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DataDynamicActivity.class);
                startActivityForResult(intent, Constant.LIST_INFO);
            }
        });

        tv_data_quality_average = (TextView) getView().findViewById(R.id.tv_data_quality_average);
        tv_data_quality_count = (TextView) getView().findViewById(R.id.tv_data_quality_count);

        rl_data_quality_l1 = (RelativeLayout)getView().findViewById(R.id.rl_data_quality_l1);
        rl_data_quality_l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DataQualityActivity.class);
                intent.putExtra("data_quality_list", (Serializable) integrityList);
                intent.putExtra("sort", DataQualityActivity.SortType.VALUE.ordinal());
                FireIntegrityStatisticsDTO info = (FireIntegrityStatisticsDTO)view.getTag();
                if(info != null) {
                    intent.putExtra("org_code", info.getDepartmentCode());
                }
                startActivity(intent);
            }
        });

        rl_data_quality_l2 = (RelativeLayout)getView().findViewById(R.id.rl_data_quality_l2);
        rl_data_quality_l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DataQualityActivity.class);
                intent.putExtra("data_quality_list", (Serializable) integrityList);
                intent.putExtra("sort", DataQualityActivity.SortType.VALUE.ordinal());
                FireIntegrityStatisticsDTO info = (FireIntegrityStatisticsDTO)view.getTag();
                if(info != null) {
                    intent.putExtra("org_code", info.getDepartmentCode());
                }
                startActivity(intent);
            }
        });

        rl_data_quality_l3 = (RelativeLayout)getView().findViewById(R.id.rl_data_quality_l3);
        rl_data_quality_l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DataQualityActivity.class);
                intent.putExtra("data_quality_list", (Serializable) integrityList);
                intent.putExtra("sort", DataQualityActivity.SortType.VALUE.ordinal());
                FireIntegrityStatisticsDTO info = (FireIntegrityStatisticsDTO)view.getTag();
                if(info != null) {
                    intent.putExtra("org_code", info.getDepartmentCode());
                }
                startActivity(intent);
            }
        });

        rl_data_quality_r1 = (RelativeLayout)getView().findViewById(R.id.rl_data_quality_r1);
        rl_data_quality_r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DataQualityActivity.class);
                intent.putExtra("data_quality_list", (Serializable) integrityList);
                intent.putExtra("sort", DataQualityActivity.SortType.COUNT.ordinal());
                FireIntegrityStatisticsDTO info = (FireIntegrityStatisticsDTO)view.getTag();
                if(info != null) {
                    intent.putExtra("org_code", info.getDepartmentCode());
                }
                startActivity(intent);
            }
        });

        rl_data_quality_r2 = (RelativeLayout)getView().findViewById(R.id.rl_data_quality_r2);
        rl_data_quality_r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DataQualityActivity.class);
                intent.putExtra("data_quality_list", (Serializable) integrityList);
                intent.putExtra("sort", DataQualityActivity.SortType.COUNT.ordinal());
                FireIntegrityStatisticsDTO info = (FireIntegrityStatisticsDTO)view.getTag();
                if(info != null) {
                    intent.putExtra("org_code", info.getDepartmentCode());
                }
                startActivity(intent);
            }
        });

        rl_data_quality_r3 = (RelativeLayout)getView().findViewById(R.id.rl_data_quality_r3);
        rl_data_quality_r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DataQualityActivity.class);
                intent.putExtra("data_quality_list", (Serializable) integrityList);
                intent.putExtra("sort", DataQualityActivity.SortType.COUNT.ordinal());
                FireIntegrityStatisticsDTO info = (FireIntegrityStatisticsDTO)view.getTag();
                if(info != null) {
                    intent.putExtra("org_code", info.getDepartmentCode());
                }
                startActivity(intent);
            }
        });

        rl_verify_station_l1 = (RelativeLayout) getView().findViewById(R.id.rl_verify_station_l1);
        rl_verify_station_l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DataProgressActivity.class);
                intent.putExtra("data_verify_list", (Serializable) verifyList);
                intent.putExtra("sort", DataProgressActivity.SortType.COUNT.ordinal());
                VerifyStatDTO info = (VerifyStatDTO)view.getTag();
                if(info != null) {
                    intent.putExtra("org_code", info.getDepartmentCode());
                }
                startActivity(intent);
            }
        });

        rl_verify_station_l2 = (RelativeLayout) getView().findViewById(R.id.rl_verify_station_l2);
        rl_verify_station_l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DataProgressActivity.class);
                intent.putExtra("data_verify_list", (Serializable) verifyList);
                intent.putExtra("sort", DataProgressActivity.SortType.COUNT.ordinal());
                VerifyStatDTO info = (VerifyStatDTO)view.getTag();
                if(info != null) {
                    intent.putExtra("org_code", info.getDepartmentCode());
                }
                startActivity(intent);
            }
        });

        rl_verify_station_l3 = (RelativeLayout) getView().findViewById(R.id.rl_verify_station_l3);
        rl_verify_station_l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DataProgressActivity.class);
                intent.putExtra("data_verify_list", (Serializable) verifyList);
                intent.putExtra("sort", DataProgressActivity.SortType.COUNT.ordinal());
                VerifyStatDTO info = (VerifyStatDTO)view.getTag();
                if(info != null) {
                    intent.putExtra("org_code", info.getDepartmentCode());
                }
                startActivity(intent);
            }
        });

        rl_verify_station_r1 = (RelativeLayout) getView().findViewById(R.id.rl_verify_station_r1);
        rl_verify_station_r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DataProgressActivity.class);
                intent.putExtra("data_verify_list", (Serializable) verifyList);
                intent.putExtra("sort", DataProgressActivity.SortType.NEW_COUNT.ordinal());
                VerifyStatDTO info = (VerifyStatDTO)view.getTag();
                if(info != null) {
                    intent.putExtra("org_code", info.getDepartmentCode());
                }
                startActivity(intent);
            }
        });
        rl_verify_station_r2 = (RelativeLayout) getView().findViewById(R.id.rl_verify_station_r2);
        rl_verify_station_r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DataProgressActivity.class);
                intent.putExtra("data_verify_list", (Serializable) verifyList);
                intent.putExtra("sort", DataProgressActivity.SortType.NEW_COUNT.ordinal());
                VerifyStatDTO info = (VerifyStatDTO)view.getTag();
                if(info != null) {
                    intent.putExtra("org_code", info.getDepartmentCode());
                }
                startActivity(intent);
            }
        });
        rl_verify_station_r3 = (RelativeLayout) getView().findViewById(R.id.rl_verify_station_r3);
        rl_verify_station_r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DataProgressActivity.class);
                intent.putExtra("data_verify_list", (Serializable) verifyList);
                intent.putExtra("sort", DataProgressActivity.SortType.NEW_COUNT.ordinal());
                VerifyStatDTO info = (VerifyStatDTO)view.getTag();
                if(info != null) {
                    intent.putExtra("org_code", info.getDepartmentCode());
                }
                startActivity(intent);
            }
        });

        tv_data_quality_l1 = (TextView) getView().findViewById(R.id.tv_data_quality_l1);
        tv_data_quality_l2 = (TextView) getView().findViewById(R.id.tv_data_quality_l2);
        tv_data_quality_l3 = (TextView) getView().findViewById(R.id.tv_data_quality_l3);
        tv_data_quality_r1 = (TextView) getView().findViewById(R.id.tv_data_quality_r1);
        tv_data_quality_r2 = (TextView) getView().findViewById(R.id.tv_data_quality_r2);
        tv_data_quality_r3 = (TextView) getView().findViewById(R.id.tv_data_quality_r3);
        tv_data_quality_station_l1 = (TextView) getView().findViewById(R.id.tv_data_quality_station_l1);
        tv_data_quality_station_l2 = (TextView) getView().findViewById(R.id.tv_data_quality_station_l2);
        tv_data_quality_station_l3 = (TextView) getView().findViewById(R.id.tv_data_quality_station_l3);
        tv_data_quality_station_r1 = (TextView) getView().findViewById(R.id.tv_data_quality_station_r1);
        tv_data_quality_station_r2 = (TextView) getView().findViewById(R.id.tv_data_quality_station_r2);
        tv_data_quality_station_r3 = (TextView) getView().findViewById(R.id.tv_data_quality_station_r3);
        tv_data_quality_count_l1 = (TextView) getView().findViewById(R.id.tv_data_quality_count_l1);
        tv_data_quality_count_l2 = (TextView) getView().findViewById(R.id.tv_data_quality_count_l2);
        tv_data_quality_count_l3 = (TextView) getView().findViewById(R.id.tv_data_quality_count_l3);
        tv_data_quality_count_r1 = (TextView) getView().findViewById(R.id.tv_data_quality_count_r1);
        tv_data_quality_count_r2 = (TextView) getView().findViewById(R.id.tv_data_quality_count_r2);
        tv_data_quality_count_r3 = (TextView) getView().findViewById(R.id.tv_data_quality_count_r3);

        tv_verify_count = (TextView) getView().findViewById(R.id.tv_verify_count);
        tv_verify_new_count = (TextView) getView().findViewById(R.id.tv_verify_new_count);
        tv_verify_station_l1 = (TextView) getView().findViewById(R.id.tv_verify_station_l1);
        tv_verify_station_l2 = (TextView) getView().findViewById(R.id.tv_verify_station_l2);
        tv_verify_station_l3 = (TextView) getView().findViewById(R.id.tv_verify_station_l3);
        tv_verify_station_r1 = (TextView) getView().findViewById(R.id.tv_verify_station_r1);
        tv_verify_station_r2 = (TextView) getView().findViewById(R.id.tv_verify_station_r2);
        tv_verify_station_r3 = (TextView) getView().findViewById(R.id.tv_verify_station_r3);

        tv_device_count = (TextView) getView().findViewById(R.id.tv_device_count);
        tv_device_cj_count = (TextView) getView().findViewById(R.id.tv_device_cj_count);
        tv_device_yy_count = (TextView) getView().findViewById(R.id.tv_device_yy_count);

        tv_device_time = (TextView) getView().findViewById(R.id.tv_device_time);
        tv_device_cj_time = (TextView) getView().findViewById(R.id.tv_device_cj_time);
        tv_device_yy_time = (TextView) getView().findViewById(R.id.tv_device_yy_time);
        tv_device_cj_average_time = (TextView) getView().findViewById(R.id.tv_device_cj_average_time);
        tv_device_yy_average_time = (TextView) getView().findViewById(R.id.tv_device_yy_average_time);

        listView = (ListView) getView().findViewById(R.id.list);

        dataDynamicList = new ArrayList<>();

        adapter = new DataDynamicAdapter(getActivity(), dataDynamicList, new DataDynamicAdapter.OnItemSelectedListener() {
            @Override
            public void onItemClick(EntityMaintainTaskDTO dto) {
                Intent intent = new Intent(getActivity(), WsDetailActivity.class);
                intent.putExtra("type", dto.getEntityType());
                intent.putExtra("key", dto.getEntityUuid());
                intent.putExtra("city", LvApplication.getInstance().getCityCode());
                getActivity().startActivityForResult(intent, Constant.LIST_INFO);
            }

            @Override
            public void onItemLongClick(EntityMaintainTaskDTO dto) {

            }
        });
        listView.setAdapter(adapter);
    }

    private void loadData() {
        requestDataDynamic(true);
        requestIntegrityOverview();
        requestVerifyOverview();
        requestOperateTimeOverview();
        requestDataDynamic(false);
    }

    private void requestIntegrityOverview() {
        OkHttpClientManager http = OkHttpClientManager.getInstance();
        final Gson gson = GsonUtil.create();
        Map<String, String> params = getIntegrityParams();
        if(params != null) {
            http.postAsyn(http.convertToURL(Constant.METHOD_GET_INTEGRITY_OVERVIEW), new OkHttpClientManager.ResultCallback<ArrayRestResult>() {
                @Override
                public void onError(Request request, Exception e) {

                }

                @Override
                public void onResponse(ArrayRestResult result) {
                    if (result != null && result.isSuccess()) {
                        String jsonStr = gson.toJson(result.getContents());

                        Type listType = new TypeToken<List<FireIntegrityWholeDTO>>() {
                        }.getType();
                        integrityList = gson.fromJson(jsonStr, listType);
                        updateDataQualityView(integrityList);
                    }
                }
            }, gson.toJson(params));
        }
    }

    private void requestVerifyOverview() {
        OkHttpClientManager http = OkHttpClientManager.getInstance();
        final Gson gson = GsonUtil.create();
        Map<String, String> params = getVerifyParams();
        if(params != null) {
            http.postAsyn(http.convertToURL(Constant.METHOD_GET_VERIFY_OVERVIEW), new OkHttpClientManager.ResultCallback<ArrayRestResult>() {
                @Override
                public void onError(Request request, Exception e) {

                }

                @Override
                public void onResponse(ArrayRestResult result) {
                    if (result != null && result.isSuccess()) {
                        String jsonStr = gson.toJson(result.getContents());

                        Type listType = new TypeToken<List<VerifyDepDTO>>() {
                        }.getType();
                        verifyList = gson.fromJson(jsonStr, listType);
                        updateVerifyView(verifyList);
                    }
                }
            }, params);
        }
    }

    private void requestOperateTimeOverview() {
        OkHttpClientManager http = OkHttpClientManager.getInstance();
        final Gson gson = GsonUtil.create();
        Map<String, String> params = getDeviceUsageParams();
        if(params != null) {
            http.postAsyn(http.convertToURL(Constant.METHOD_GET_DEVICE_OVERVIEW), new OkHttpClientManager.ResultCallback<SimpleRestResult>() {
                @Override
                public void onError(Request request, Exception e) {

                }

                @Override
                public void onResponse(SimpleRestResult result) {
                    if (result != null && result.isSuccess()) {

                        String jsonStr = gson.toJson(result.getData());
                        deviceUsageSurveyInfo = gson.fromJson(jsonStr, AppDeviceUsageSurveyDTO.class);
                        updateOperateTimeView(deviceUsageSurveyInfo);
                    }
                }
            }, gson.toJson(params));
        }
    }

    private void requestDataDynamic(final boolean today) {
        OkHttpClientManager http = OkHttpClientManager.getInstance();
        final Gson gson = GsonUtil.create();
        Map<String, String> params = getDataDynamicParams(today);
        if(params != null) {
            http.postAsyn(http.convertToURL(Constant.METHOD_GET_DATA_DYNAMIC), new OkHttpClientManager.ResultCallback<ArrayRestResult>() {
                @Override
                public void onError(Request request, Exception e) {

                }

                @Override
                public void onResponse(ArrayRestResult result) {
                    if (result != null && result.isSuccess()) {
                        String jsonStr = gson.toJson(result.getContents());

                        Type listType = new TypeToken<List<EntityMaintainTaskDTO>>() {
                        }.getType();
                        if (today) {
                            updateTodayCommitNum(result.getTotal());
                        } else {
                            List<EntityMaintainTaskDTO> tempList = gson.fromJson(jsonStr, listType);
                            updateDataDynamic(tempList);
                        }

                    }
                }
            }, params);
        }
    }

    private Map<String, String> getIntegrityParams() {
        Map<String, String> params = null;
        PrefUserInfo pui = PrefUserInfo.getInstance();
        String departmentCode = pui.getUserInfo("department_code");
        if(!StringUtil.isEmpty(departmentCode)) {
            Integer grade = Utils.ToInteger(pui.getUserInfo("department_grade"));
            if(grade != null) {
                params = new HashMap<>();
                if (grade.intValue() == 3 && departmentCode.length() == 8) {
                    params.put("departmentCode", departmentCode.substring(0, 6) + "00");
                    grade = 2;
                } else {
                    params.put("departmentCode", departmentCode);
                }
                params.put("districtCode", pui.getUserInfo("district_code"));
                params.put("grade", StringUtil.get(grade));
                Integer previewGrade = grade;
                if (previewGrade.intValue() < 3) {  // 中队的预览等级不需要加1
                    previewGrade += 1;
                }
                params.put("previewGrade", StringUtil.get(previewGrade));
            }
        }
        return params;
    }

    private Map<String, String> getVerifyParams() {
        Map<String, String> params = null;
        PrefUserInfo pui = PrefUserInfo.getInstance();
        String departmentUuid = pui.getUserInfo("department_uuid");
        if(!StringUtil.isEmpty(departmentUuid)) {
            Integer grade = Utils.ToInteger(pui.getUserInfo("department_grade"));
            if(grade != null) {
                params = new HashMap<>();
                if(grade.intValue() == 3) {
                    Dictionary dic = DictionaryUtil.getDictionaryByCode(departmentUuid, LvApplication.getInstance().getCurrentOrgList());
                    if(dic != null) {
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
                Integer previewGrade = grade;
                if(previewGrade.intValue() < 3) {  // 中队的预览等级不需要加1
                    previewGrade += 1;
                }
                params.put("previewGrade", StringUtil.get(previewGrade));

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
        }
        return params;
    }

    private Map<String, String> getDataDynamicParams(boolean today) {
        Map<String, String> params = null;
        PrefUserInfo pui = PrefUserInfo.getInstance();
        String departmentUuid = pui.getUserInfo("department_uuid");
        if(!StringUtil.isEmpty(departmentUuid)) {
            params = new HashMap<>();
            params.put("departmentUuid", departmentUuid);
            params.put("state", "YSH");
            params.put("orderBy", "updateDate");
            params.put("page", "1");
            params.put("start", "0");
            params.put("limit", "10");
            if (today) {
                params.put("startDate", DateUtil.getToday());
            }
        }
        return params;
    }

    private Map<String, String> getDeviceUsageParams() {
        Map<String, String> params = null;
        PrefUserInfo pui = PrefUserInfo.getInstance();
        String departmentCode = pui.getUserInfo("department_code");
        if(!StringUtil.isEmpty(departmentCode)) {
            Integer grade = Utils.ToInteger(pui.getUserInfo("department_grade"));
            if(grade != null) {
                params = new HashMap<>();
                if(grade.intValue() == 3 && departmentCode.length() == 8) {
                    params.put("departmentCode", departmentCode.substring(0, 6) + "00");
                    grade = 2;
                } else {
                    params.put("departmentCode", departmentCode);
                }
                params.put("districtCode", pui.getUserInfo("district_code"));
                params.put("grade", StringUtil.get(grade));
                Integer previewGrade = grade;
                if(previewGrade.intValue() < 3) {  // 中队的预览等级不需要加1
                    previewGrade += 1;
                }
                params.put("previewGrade", StringUtil.get(previewGrade));

                Calendar curDate = Calendar.getInstance();
                curDate.set(Calendar.DAY_OF_MONTH, 1);
                String firstDay = format.format(curDate.getTime());
                params.put("startDate", String.format("%s 00:00:00", firstDay));

                curDate = Calendar.getInstance();
                String lastDay = timeFormat.format(curDate.getTime());
                params.put("endDate", String.format("%s 23:59:59", lastDay));
            }
        }
        return params;
    }

    private void updateDataQualityView(List<FireIntegrityWholeDTO> integrityList) {
        List<FireIntegrityStatisticsDTO> keyUnitIntegrityList = getTypeIntegrityList(AppDefs.CompEleType.KEY_UNIT.toString(), integrityList);
        updateLeftDataQualityView(keyUnitIntegrityList);
        updateRightDataQualityView(keyUnitIntegrityList);
    }

    private void updateLeftDataQualityView(List<FireIntegrityStatisticsDTO> keyUnitIntegrityList) {
        Collections.sort(keyUnitIntegrityList, new Comparator<FireIntegrityStatisticsDTO>() {
            @Override
            public int compare(FireIntegrityStatisticsDTO t1, FireIntegrityStatisticsDTO t2) {
                if(t2.getTotalQuality() == null || t2.getTotalCnt() == null || t2.getTotalCnt() == 0) {
                    return -1;
                }
                if(t1.getTotalQuality() == null || t1.getTotalCnt() == null || t1.getTotalCnt() == 0) {
                    return 1;
                }
                return ((Double)(t2.getTotalQuality() / t2.getTotalCnt())).compareTo(t1.getTotalQuality() / t1.getTotalCnt());
            }
        });
        tv_data_quality_average.setText(getIntegrityAverageText(keyUnitIntegrityList));

        rl_data_quality_l1.setVisibility(View.GONE);
        rl_data_quality_l2.setVisibility(View.GONE);
        rl_data_quality_l3.setVisibility(View.GONE);

        if(keyUnitIntegrityList.size() > 0) {
            rl_data_quality_l1.setVisibility(View.VISIBLE);
            FireIntegrityStatisticsDTO info = keyUnitIntegrityList.get(0);
            rl_data_quality_l1.setTag(info);
            tv_data_quality_station_l1.setText(info.getDepartmentName());
            tv_data_quality_l1.setText(getIntegrityText(info.getTotalQuality(), info.getTotalCnt()));
            tv_data_quality_count_l1.setText(String.format("(%s个)", StringUtil.get(info.getTotalCnt())));
        }

        if(keyUnitIntegrityList.size() > 1) {
            rl_data_quality_l2.setVisibility(View.VISIBLE);
            FireIntegrityStatisticsDTO info = keyUnitIntegrityList.get(1);
            rl_data_quality_l2.setTag(info);
            tv_data_quality_station_l2.setText(info.getDepartmentName());
            tv_data_quality_l2.setText(getIntegrityText(info.getTotalQuality(), info.getTotalCnt()));
            tv_data_quality_count_l2.setText(String.format("(%s个)", StringUtil.get(info.getTotalCnt())));
        }

        if(keyUnitIntegrityList.size() > 2) {
            rl_data_quality_l3.setVisibility(View.VISIBLE);
            FireIntegrityStatisticsDTO info = keyUnitIntegrityList.get(2);
            rl_data_quality_l3.setTag(info);
            tv_data_quality_station_l3.setText(info.getDepartmentName());
            tv_data_quality_l3.setText(getIntegrityText(info.getTotalQuality(), info.getTotalCnt()));
            tv_data_quality_count_l3.setText(String.format("(%s个)", StringUtil.get(info.getTotalCnt())));
        }
    }

    private void updateRightDataQualityView(List<FireIntegrityStatisticsDTO> keyUnitIntegrityList) {
        Collections.sort(keyUnitIntegrityList, new Comparator<FireIntegrityStatisticsDTO>() {
            @Override
            public int compare(FireIntegrityStatisticsDTO t1, FireIntegrityStatisticsDTO t2) {
                if(t2.getTotalCnt() == null) {
                    return -1;
                }
                return t2.getTotalCnt().compareTo(t1.getTotalCnt());
            }
        });
        tv_data_quality_count.setText(getdata_qualityCountText(keyUnitIntegrityList));

        rl_data_quality_r1.setVisibility(View.GONE);
        rl_data_quality_r2.setVisibility(View.GONE);
        rl_data_quality_r3.setVisibility(View.GONE);

        if(keyUnitIntegrityList.size() > 0) {
            rl_data_quality_r1.setVisibility(View.VISIBLE);
            FireIntegrityStatisticsDTO info = keyUnitIntegrityList.get(0);
            rl_data_quality_r1.setTag(info);
            tv_data_quality_station_r1.setText(info.getDepartmentName());
            tv_data_quality_r1.setText(String.format("(%s%%)", getIntegrityText(info.getTotalQuality(), info.getTotalCnt())));
            tv_data_quality_count_r1.setText(StringUtil.get(info.getTotalCnt()));
        }

        if(keyUnitIntegrityList.size() > 1) {
            rl_data_quality_r2.setVisibility(View.VISIBLE);
            FireIntegrityStatisticsDTO info = keyUnitIntegrityList.get(1);
            rl_data_quality_r2.setTag(info);
            tv_data_quality_station_r2.setText(info.getDepartmentName());
            tv_data_quality_r2.setText(String.format("(%s%%)", getIntegrityText(info.getTotalQuality(), info.getTotalCnt())));
            tv_data_quality_count_r2.setText(StringUtil.get(info.getTotalCnt()));
        }

        if(keyUnitIntegrityList.size() > 2) {
            rl_data_quality_r3.setVisibility(View.VISIBLE);
            FireIntegrityStatisticsDTO info = keyUnitIntegrityList.get(2);
            rl_data_quality_r3.setTag(info);
            tv_data_quality_station_r3.setText(info.getDepartmentName());
            tv_data_quality_r3.setText(String.format("(%s%%)", getIntegrityText(info.getTotalQuality(), info.getTotalCnt())));
            tv_data_quality_count_r3.setText(StringUtil.get(info.getTotalCnt()));
        }
    }

    private String getIntegrityAverageText(List<FireIntegrityStatisticsDTO> keyUnitIntegrityList) {
        String text = "";

        if(keyUnitIntegrityList.size() > 0) {
            double totalQuality = 0;
            int totalCnt = 0;
            for(FireIntegrityStatisticsDTO item : keyUnitIntegrityList) {
                if(item.getTotalQuality() != null) {
                    totalQuality += item.getTotalQuality();
                }
                if(item.getTotalCnt() != null) {
                    totalCnt += item.getTotalCnt();
                }
            }
            if(totalCnt > 0) {
                double value = totalQuality * 100 / totalCnt;
                text = decimalFormat.format(value);
            }
        }
        return text;
    }

    private String getdata_qualityCountText(List<FireIntegrityStatisticsDTO> keyUnitIntegrityList) {
        String text = "";

        if(keyUnitIntegrityList.size() > 0) {
            int totalCnt = 0;
            for(FireIntegrityStatisticsDTO item : keyUnitIntegrityList) {
                if(item.getTotalCnt() != null) {
                    totalCnt += item.getTotalCnt();
                }
            }
            text = StringUtil.get(totalCnt);
        }
        return text;
    }

    private String getIntegrityText(Double totalQuality, Integer count) {
        String text = "0";
        if(totalQuality == null || count == null) {
            return text;
        }
        if(count > 0) {
            double value = totalQuality * 100 / count;
            value = value > 100.0 ? 100.0 : value;
            text = decimalFormat.format(value);
        }
        return text;
    }

    private List<FireIntegrityStatisticsDTO> getTypeIntegrityList(String type, List<FireIntegrityWholeDTO> integrityList) {
        List<FireIntegrityStatisticsDTO> typeList = new ArrayList<>();
        if(integrityList != null && integrityList.size() > 0) {
            for(FireIntegrityWholeDTO stationIntegrity : integrityList) {
                List<FireIntegrityStatisticsDTO> typeIntegrityList = stationIntegrity.getFireIntegrityStatisticsDTOS();
                if(typeIntegrityList != null && typeIntegrityList.size() > 0) {
                    for(FireIntegrityStatisticsDTO item : typeIntegrityList) {
                        if(type.equals(item.getEntityType())) {
                            typeList.add(item);
                        }
                    }
                }
            }
        }
        return typeList;
    }
    private void updateTodayCommitNum(int count) {
        tv_today_commit_num.setText(StringUtil.get(count));
    }

    private void updateVerifyView(List<VerifyDepDTO> verifyList) {
        List<VerifyStatDTO> wholeVerifyList = getWholeVerifyList(verifyList);
        updateLeftVerifyView(wholeVerifyList);
        updateRightVerifyView(wholeVerifyList);
    }

    private void updateLeftVerifyView(List<VerifyStatDTO> wholeVerifyList) {
        Collections.sort(wholeVerifyList, new Comparator<VerifyStatDTO>() {
            @Override
            public int compare(VerifyStatDTO t1, VerifyStatDTO t2) {
                if(t2.getUpdateCount() == null) {
                    return -1;
                }
                return t2.getUpdateCount().compareTo(t1.getUpdateCount());
            }
        });
        tv_verify_count.setText(getUpdateCountText(wholeVerifyList));
        rl_verify_station_l1.setVisibility(View.GONE);
        rl_verify_station_l2.setVisibility(View.GONE);
        rl_verify_station_l3.setVisibility(View.GONE);

        if(wholeVerifyList.size() > 0) {
            rl_verify_station_l1.setVisibility(View.VISIBLE);
            VerifyStatDTO info = wholeVerifyList.get(0);
            rl_verify_station_l1.setTag(info);
            tv_verify_station_l1.setText(info.getDepartmentName());
        }

        if(wholeVerifyList.size() > 1) {
            rl_verify_station_l2.setVisibility(View.VISIBLE);
            VerifyStatDTO info = wholeVerifyList.get(1);
            rl_verify_station_l2.setTag(info);
            tv_verify_station_l2.setText(info.getDepartmentName());
        }

        if(wholeVerifyList.size() > 2) {
            rl_verify_station_l3.setVisibility(View.VISIBLE);
            VerifyStatDTO info = wholeVerifyList.get(2);
            rl_verify_station_l3.setTag(info);
            tv_verify_station_l3.setText(info.getDepartmentName());
        }
    }

    private void updateRightVerifyView(List<VerifyStatDTO> wholeVerifyList) {
        Collections.sort(wholeVerifyList, new Comparator<VerifyStatDTO>() {
            @Override
            public int compare(VerifyStatDTO t1, VerifyStatDTO t2) {
                if(t2.getNewCount() == null) {
                    return -1;
                }
                return t2.getNewCount().compareTo(t1.getNewCount());
            }
        });
        tv_verify_new_count.setText(getNewCountText(wholeVerifyList));
        rl_verify_station_r1.setVisibility(View.GONE);
        rl_verify_station_r2.setVisibility(View.GONE);
        rl_verify_station_r3.setVisibility(View.GONE);

        if(wholeVerifyList.size() > 0) {
            rl_verify_station_r1.setVisibility(View.VISIBLE);
            VerifyStatDTO info = wholeVerifyList.get(0);
            rl_verify_station_r1.setTag(info);
            tv_verify_station_r1.setText(info.getDepartmentName());
        }

        if(wholeVerifyList.size() > 1) {
            rl_verify_station_r2.setVisibility(View.VISIBLE);
            VerifyStatDTO info = wholeVerifyList.get(1);
            rl_verify_station_r2.setTag(info);
            tv_verify_station_r2.setText(info.getDepartmentName());
        }

        if(wholeVerifyList.size() > 2) {
            rl_verify_station_r3.setVisibility(View.VISIBLE);
            VerifyStatDTO info = wholeVerifyList.get(2);
            rl_verify_station_r3.setTag(info);
            tv_verify_station_r3.setText(info.getDepartmentName());
        }
    }

    private List<VerifyStatDTO> getWholeVerifyList(List<VerifyDepDTO> verifyList) {
        List<VerifyStatDTO> wholeList = new ArrayList<>();
        if(verifyList != null && verifyList.size() > 0) {
            for(VerifyDepDTO stationVerify : verifyList) {
                List<VerifyStatDTO> typeVerifyList = stationVerify.getActionStats();
                VerifyStatDTO info = new VerifyStatDTO();
                info.setDepartmentCode(stationVerify.getDepartmentCode());
                info.setDepartmentName(stationVerify.getDepartmentName());
                int newCount = 0;
                int modifyCount = 0;
                int deleteCount = 0;
                int verifyCount = 0;
                int updateCount = 0;
                PrefUserInfo pui = PrefUserInfo.getInstance();
                String grade = pui.getUserInfo("department_grade");
                if (StringUtil.isEmpty(grade)) {
                    return wholeList;
                }
                int g = Integer.parseInt(grade) + 1;
                if(typeVerifyList != null && typeVerifyList.size() > 0) {
                    for(VerifyStatDTO item : typeVerifyList) {

                        // 以总队账号登录为例，总队账号登录时 总数不包含总队及支队的数量，即只统计支队以下数量
                        if (g >= 0) {
                            if (AppDefs.CompEleType.BRIGADE.name().equals(item.getEntityType())) {
                                continue;
                            }
                        }

                        if (g >= 1) {
                            if (AppDefs.CompEleType.DETACHMENT.name().equals(item.getEntityType())) {
                                continue;
                            }
                        }

                        if (g >= 2) {
                            if (AppDefs.CompEleType.BATTALION.name().equals(item.getEntityType())) {
                                continue;
                            }
                        }

                        if (g >= 3) {
                            if (AppDefs.CompEleType.SQUADRON.name().equals(item.getEntityType())) {
                                continue;
                            }
                        }

                        if("WATERSOURCE".equals(item.getEntityType())) {
                            continue;
                        }

                        if(item.getNewCount() != null) {
                            newCount += item.getNewCount();
                        }
                        if(item.getDeleteCount() != null) {
                            deleteCount += item.getDeleteCount();
                        }
                        if(item.getModifyCount() != null) {
                            modifyCount += item.getModifyCount();
                        }
                        if(item.getUpdateCount() != null) {
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
        return wholeList;
    }

    private String getUpdateCountText(List<VerifyStatDTO> wholeVerifyList) {
        String text = "";
        if(wholeVerifyList.size() > 0) {
            int count = 0;
            for(VerifyStatDTO item : wholeVerifyList) {
                if(item.getUpdateCount() != null) {
                    count += item.getUpdateCount();
                }
            }
            text = StringUtil.get(count);
        }
        return text;
    }

    private String getNewCountText(List<VerifyStatDTO> wholeVerifyList) {
        String text = "";
        if(wholeVerifyList.size() > 0) {
            int count = 0;
            for(VerifyStatDTO item : wholeVerifyList) {
                if(item.getNewCount() != null) {
                    count += item.getNewCount();
                }
            }
            text = StringUtil.get(count);
        }
        return text;
    }

    private void updateOperateTimeView(AppDeviceUsageSurveyDTO deviceUsageSurveyInfo) {
        if(deviceUsageSurveyInfo != null) {
            List<AppDeviceUsageSurveyChildDTO> appDeviceUsageList = deviceUsageSurveyInfo.getAppDeviceUsageSurveyChildDTO();
            if(appDeviceUsageList != null && appDeviceUsageList.size() > 0) {
                tv_device_count.setText(getDeviceCountText(appDeviceUsageList));
                tv_device_cj_count.setText(getDeviceCJCountText(appDeviceUsageList));
                tv_device_yy_count.setText(getDeviceYYCountText(appDeviceUsageList));
                tv_device_time.setText(getDeviceOperateTimeText(appDeviceUsageList));
                tv_device_cj_time.setText(getDeviceCJOperateTimeText(appDeviceUsageList));
                tv_device_yy_time.setText(getDeviceYYOperateTimeText(appDeviceUsageList));
                tv_device_cj_average_time.setText(getDeviceCJOperateAverageTimeText(deviceUsageSurveyInfo.getTotalDay(), appDeviceUsageList));
                tv_device_yy_average_time.setText(getDeviceYYOperateAverageTimeText(deviceUsageSurveyInfo.getTotalDay(), appDeviceUsageList));
            } else {
                tv_device_count.setText("0");
                tv_device_cj_count.setText("0");
                tv_device_yy_count.setText("0");
                tv_device_time.setText("0");
                tv_device_cj_time.setText("0");
                tv_device_yy_time.setText("0");
                tv_device_cj_average_time.setText("0");
                tv_device_yy_average_time.setText("0");
            }
        }
    }

    private String getDeviceCountText(List<AppDeviceUsageSurveyChildDTO> appDeviceUsageList) {
        int count = 0;
        for(AppDeviceUsageSurveyChildDTO item : appDeviceUsageList) {
            count += item.getTotalCnt() == null ? 0 : item.getTotalCnt();
        }
        return StringUtil.get(count);
    }

    private String getDeviceCJCountText(List<AppDeviceUsageSurveyChildDTO> appDeviceUsageList) {
        int count = 0;
        for(AppDeviceUsageSurveyChildDTO item : appDeviceUsageList) {
            if(item.getCjTotleCnt() != null) {
                count += item.getCjTotleCnt();
            }
        }
        return StringUtil.get(count);
    }

    private String getDeviceYYCountText(List<AppDeviceUsageSurveyChildDTO> appDeviceUsageList) {
        int count = 0;
        for(AppDeviceUsageSurveyChildDTO item : appDeviceUsageList) {
            if(item.getYyTotalCnt() != null) {
                count += item.getYyTotalCnt();
            }
        }
        return StringUtil.get(count);
    }

    private String getDeviceOperateTimeText(List<AppDeviceUsageSurveyChildDTO> appDeviceUsageList) {
        Float operateTime = 0f;
        for(AppDeviceUsageSurveyChildDTO item : appDeviceUsageList) {
            if(item.getCjOperateTime() != null) {
                operateTime += item.getCjOperateTime();
            }
            if(item.getYyOperateTime() != null) {
                operateTime += item.getYyOperateTime();
            }
        }
        return decimalFormat.format(operateTime);
    }

    private String getDeviceCJOperateTimeText(List<AppDeviceUsageSurveyChildDTO> appDeviceUsageList) {
        Float operateTime = 0f;
        for(AppDeviceUsageSurveyChildDTO item : appDeviceUsageList) {
            if(item.getCjOperateTime() != null) {
                operateTime += item.getCjOperateTime();
            }
        }
        return decimalFormat.format(operateTime);
    }

    private String getDeviceYYOperateTimeText(List<AppDeviceUsageSurveyChildDTO> appDeviceUsageList) {
        Float operateTime = 0f;
        for(AppDeviceUsageSurveyChildDTO item : appDeviceUsageList) {
            if(item.getYyOperateTime() != null) {
                operateTime += item.getYyOperateTime();
            }
        }
        return decimalFormat.format(operateTime);
    }

    private String getDeviceCJOperateAverageTimeText(Integer day, List<AppDeviceUsageSurveyChildDTO> appDeviceUsageList) {
        Float operateTime = 0f;
        int totalCjCnt = 0;
        int dayCount = 1;
        if(day != null) {
            dayCount = day.intValue();
        }
        for(AppDeviceUsageSurveyChildDTO item : appDeviceUsageList) {
            if(item.getCjOperateTime() != null) {
                operateTime += item.getCjOperateTime();
            }
            totalCjCnt += item.getCjUseCnt() == null ? 0 : item.getCjUseCnt();
        }

        if (totalCjCnt == 0) {
            return "--";
        }

        return decimalFormat.format(operateTime / dayCount / totalCjCnt);
    }

    private String getDeviceYYOperateAverageTimeText(Integer day, List<AppDeviceUsageSurveyChildDTO> appDeviceUsageList) {
        Float operateTime = 0f;
        int totalYyCnt = 0;
        int dayCount = 1;

        if(day != null) {
            dayCount = day.intValue();
        }
        for(AppDeviceUsageSurveyChildDTO item : appDeviceUsageList) {
            if(item.getYyOperateTime() != null) {
                operateTime += item.getYyOperateTime();
            }
            totalYyCnt += item.getYyUseCnt() == null ? 0 : item.getYyUseCnt();
        }
        if (totalYyCnt == 0) {
            return "--";
        }
        return decimalFormat.format(operateTime / dayCount / totalYyCnt);
    }

    private void updateDataDynamic(List<EntityMaintainTaskDTO> list) {
        dataDynamicList.clear();
        if(list != null && list.size() > 0) {
            dataDynamicList.addAll(list);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    // 刷新ui
    public void refresh() {
        loadData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.hidden = hidden;
        if (!hidden) {
            refresh();
            startTimer();
        } else {
            stopTimer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!hidden) {
            refresh();
            startTimer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopTimer();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void startTimer() {
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(MSG_UPDATE_EVENT);
            }
        }, INTERVAL, INTERVAL);
    }
}
