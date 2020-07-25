package com.moft.xfapply.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.adapter.WsTypeRecordAdapter;
import com.moft.xfapply.activity.adapter.WsTypeRecordAdapter.OnItemSelectedListener;
import com.moft.xfapply.activity.bussiness.GeomEleBussiness;
import com.moft.xfapply.activity.bussiness.StandardBussiness;
import com.moft.xfapply.activity.logic.DownloadLogic;
import com.moft.xfapply.activity.logic.DownloadLogic.OnDownloadLogicListener;
import com.moft.xfapply.activity.logic.DuizhanListLogic;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.event.CommandEvent;
import com.moft.xfapply.logic.network.OkHttpClientManager;
import com.moft.xfapply.logic.setting.PrefUserInfo;
import com.moft.xfapply.model.common.GeomElementType;
import com.moft.xfapply.model.common.QueryCondition;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.dto.DisposalPointDTO;
import com.moft.xfapply.model.external.dto.FileNodeDTO;
import com.moft.xfapply.model.external.dto.KnowledgeBaseDTO;
import com.moft.xfapply.model.external.dto.PlanLibraryDTO;
import com.moft.xfapply.model.external.result.ArrayRestResult;
import com.moft.xfapply.utils.GsonUtil;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.widget.XListView;
import com.moft.xfapply.widget.XListView.IXListViewListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class FragmentData extends Fragment implements
        IXListViewListener,
        OnDownloadLogicListener,
        DuizhanListLogic.OnDuizhanListLogicListener {
    private boolean hidden;
    
    private DownloadLogic mDownloadLogic = null;
    private DuizhanListLogic mDzListLogic = null;
    
    private TextView tv_result = null;  
    private TextView tv_cont = null;

    private XListView listView = null;
    private WsTypeRecordAdapter adapter = null;
    private OnItemSelectedListener mOnItemSelectedListener = null;
    
    private List<GeomElementType> wsTypeList = new ArrayList<>();
    private List<DisposalPointDTO> dealProgramList = new ArrayList<DisposalPointDTO>();
    private List<KnowledgeBaseDTO> knowledgeList = new ArrayList<KnowledgeBaseDTO>();
    private List<FileNodeDTO> lawRegulation = new ArrayList<FileNodeDTO>(); // 法律法规

    private Handler mHandler;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater
                .inflate(R.layout.fragment_data, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.builder().logNoSubscriberMessages(false).sendNoSubscriberEvent(false).build();
        EventBus.getDefault().register(this);
        tv_result = (TextView) getView().findViewById(R.id.tv_result);
        tv_cont = (TextView) getView().findViewById(R.id.tv_cont);
        
        listView = (XListView) getView().findViewById(R.id.list);
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(true);
        listView.setXListViewListener(this);
        mHandler = new Handler();
        
        mOnItemSelectedListener = new OnItemSelectedListener() {
            @Override
            public void onItemClick(GeomElementType dto) {
                QueryCondition qc = LvApplication.getInstance().getDataQueryCondition();
                qc.setType(QueryCondition.TYPE_SYLX);
                qc.setSylx(dto.getType());

                //对应其他消防队伍分为4类
                if(!StringUtil.isEmpty(dto.getTypeCode()) && dto.getTypeCode().startsWith(AppDefs.CompEleType.ENTERPRISE_FOURCE.toString())) {
                    qc.setLxSubCode(dto.getTypeCode().replace(AppDefs.CompEleType.ENTERPRISE_FOURCE.toString(), ""));
                    qc.setSylxCode(AppDefs.CompEleType.ENTERPRISE_FOURCE.toString());
                } else {
                    qc.setSylxCode(dto.getTypeCode());
                }
                qc.setZqzd(mDzListLogic.getName());
                qc.setZqzdCode(mDzListLogic.getCode());

                if (Constant.HAZARD_CHEMICAL.equals(dto.getTypeCode())) {
                    Intent intent = new Intent(getActivity(), HcSearchActivity.class);
                    getActivity().startActivity(intent);
                } else if (Constant.DEAL_PROGRAM.equals(dto.getTypeCode())) {
                    Intent intent = new Intent(getActivity(), DealProgramActivity.class);
                    intent.putExtra("deal_program_list", (Serializable)dealProgramList);
                    getActivity().startActivity(intent);
                } else if (Constant.KNOWLEDGE.equals(dto.getTypeCode())) {
                    Intent intent = new Intent(getActivity(), KnowledgeActivity.class);
                    intent.putExtra("knowledge_list", (Serializable)knowledgeList);
                    getActivity().startActivity(intent);
                }  else if (Constant.PLAN_LIBRARY.equals(dto.getTypeCode())) {
                    Intent intent = new Intent(getActivity(), PlanLibraryCatalogsActivity.class);
                    getActivity().startActivity(intent);
                }   else if (Constant.LAW_REGULATION.equals(dto.getTypeCode())) {
                    Intent intent = new Intent(getActivity(), LawRegulationActivity.class);
                    intent.putExtra("lawRegulation_list", (Serializable)lawRegulation);
                    getActivity().startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), WsRecordActivity.class);
                    intent.putExtra("FromMap", false);
                    getActivity().startActivityForResult(intent, Constant.LIST_INFO);
                }
            }

            @Override
            public void onItemLongClick(final GeomElementType dto) {
            }            
        };
        
        mDownloadLogic = new DownloadLogic(getView(), getActivity());
        mDownloadLogic.addListener(this);
        mDownloadLogic.init();
        mDzListLogic = new DuizhanListLogic(getView(), getActivity());
        mDzListLogic.addListener(this);
        mDzListLogic.init();
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
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    
    private List<GeomElementType> loadData() {
        GeomEleBussiness xb = GeomEleBussiness.getInstance();
        List<GeomElementType> list = xb.getGeomElementSummary(
                xb.getWhereDepartments(mDzListLogic.getCode()));
        List<GeomElementType> weightList = getWeightList(list);
        resetListView();
        updateResult(weightList);

        getDealProgram(); // 处置要点
        getKnowledge();  // 知识库
        getLawRegulations(); // 法律法规
        getPlanLibraryCatelogs(); // 预案库

        return weightList;
    }

    private List<GeomElementType> getWeightList(List<GeomElementType> list) {
        List<GeomElementType> retList = new ArrayList<>();
        List<Map.Entry<Integer, GeomElementType>> weightList = new ArrayList<>();

        for (GeomElementType get : list) {
            Integer index = LvApplication.getInstance().getGeoEleWeight(get.getTypeCode());
            if (index == null) {
                continue;
            }
            weightList.add(new AbstractMap.SimpleEntry(index, get));
        }
        if(weightList.size() > 0) {
            Collections.sort(weightList, new Comparator<Map.Entry<Integer, GeomElementType>>() {
                @Override
                public int compare(Map.Entry<Integer, GeomElementType> t1, Map.Entry<Integer, GeomElementType> t2) {
                    return t1.getKey().compareTo(t2.getKey());
                }
            });
            for (Map.Entry<Integer, GeomElementType> item : weightList) {
                retList.add(item.getValue());
            }
        }
        // 追加 危化品
        GeomElementType whp = new GeomElementType();
        whp.setTypeCode(Constant.HAZARD_CHEMICAL);
        whp.setCount(StandardBussiness.getInstance().getHazardChemicalCount("") + StandardBussiness.getInstance().getMsdsCount(""));
        retList.add(whp);

        // 追加 处置程序
        GeomElementType pGeo = new GeomElementType();
        pGeo.setTypeCode(Constant.DEAL_PROGRAM);
        pGeo.setCount(0);
        retList.add(pGeo);

        // 追加 知识库
        GeomElementType knowledge = new GeomElementType();
        knowledge.setTypeCode(Constant.KNOWLEDGE);
        knowledge.setCount(0);
        retList.add(knowledge);

        // 追加 预案库
        GeomElementType planLibrary = new GeomElementType();
        planLibrary.setTypeCode(Constant.PLAN_LIBRARY);
        planLibrary.setCount(0);
        retList.add(planLibrary);

        // 追加 法律法规
        GeomElementType lawRegulation = new GeomElementType();
        lawRegulation.setTypeCode(Constant.LAW_REGULATION);
        lawRegulation.setCount(0);
        retList.add(lawRegulation);

        return retList;
    }
    
    private void reloadData() {
        wsTypeList.clear();     
        List<GeomElementType> list = loadData();
        wsTypeList.addAll(list);
        
        adapter.notifyDataSetChanged();
    }
    
    private void updateResult(List<GeomElementType> list) {
        int totalCnt = 0;
        
        for (GeomElementType wsType : list) {
            totalCnt += wsType.getCount();
        }
        
        String result = "(共" + totalCnt + "条)";

        tv_cont.setText(mDzListLogic.getName());
        tv_result.setText(result);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CommandEvent event) {
        if(CommandEvent.CMD_UPDATE_OFFLINE_COMPLETED.equals(event.command)) {
            if(mDownloadLogic != null) {
                mDownloadLogic.confirmOfflineDB();
            }
        }
    }

    //ID:T886049 19-02-26 【移动终端】终端与平台数据一致，实时同步（新建数据）。 王泉
    public void stopDownload() {
        mDownloadLogic.stopDownload();
    }

    // 刷新ui
    public void refresh() {
        if (mDownloadLogic.isDealing()) {
            return;
        }
        mDzListLogic.refreshView();
        wsTypeList = loadData();
        adapter = new WsTypeRecordAdapter(getActivity(), wsTypeList, mOnItemSelectedListener);
        listView.setAdapter(adapter);

        mDownloadLogic.init();
    }

    private void getKnowledge() {
        OkHttpClientManager http = OkHttpClientManager.getInstance();
        http.postAsyn(http.convertToURL( String.format(Constant.METHOD_GET_KNOWLEDGE_LIST)),   // 后台没有按分页进行处理，也没有使用前台所传递的值，所以此处传递的值都是固定的
                new OkHttpClientManager.ResultCallback<ArrayRestResult>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Toast.makeText(getActivity(), "知识库获取失败，请检查网络连接!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(ArrayRestResult result) {
                        if (result != null && result.isSuccess()) {
                            Gson gson = GsonUtil.create();
                            if (result.getContents() != null) {
                                String jsonStr = gson.toJson(result.getContents());
                                Type listType = new TypeToken<List<KnowledgeBaseDTO>>(){}.getType();
                                knowledgeList = gson.fromJson(jsonStr, listType);
                                updateKnowledgeData(knowledgeList.size());
                            }
                        } else {
                            Toast.makeText(getActivity(), "知识库获取失败，请检查网络连接!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new HashMap<String, String>());
    }

    // 获取法律法规
    private void getLawRegulations() {
        OkHttpClientManager http = OkHttpClientManager.getInstance();
        http.getAsyn(http.convertToURL( String.format(Constant.METHOD_GET_RAW_REGULATION_LIST)),   // 后台没有按分页进行处理，也没有使用前台所传递的值，所以此处传递的值都是固定的
                new OkHttpClientManager.ResultCallback<ArrayRestResult>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Toast.makeText(getActivity(), "法律法规获取失败，请检查网络连接!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(ArrayRestResult result) {
                        if (result != null && result.isSuccess()) {
                            Gson gson = GsonUtil.create();
                            if (result.getContents() != null) {
                                String jsonStr = gson.toJson(result.getContents());
                                Type listType = new TypeToken<List<FileNodeDTO>>(){}.getType();
                                lawRegulation = gson.fromJson(jsonStr, listType);
                                updateLawRegulationData(lawRegulation.size());
                            }
                        } else {
                            Toast.makeText(getActivity(), "法律法规获取失败，请检查网络连接!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // 获取预案库总数量
    private void getPlanLibraryCatelogs() {
        OkHttpClientManager http = OkHttpClientManager.getInstance();
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constant.PARAM_DEPARTMENT_UUID, PrefUserInfo.getInstance().getUserInfo("department_uuid"));

        http.postAsyn(http.convertToURL( String.format(Constant.METHOD_GET_ALL_LIBRARY_LIST)),   // 后台没有按分页进行处理，也没有使用前台所传递的值，所以此处传递的值都是固定的
                new OkHttpClientManager.ResultCallback<ArrayRestResult>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Toast.makeText(getActivity(), "法律法规获取失败，请检查网络连接!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(ArrayRestResult result) {
                        if (result != null && result.isSuccess()) {
                            Gson gson = GsonUtil.create();
                            if (result.getContents() != null) {
                                String jsonStr = gson.toJson(result.getContents());
                                Type listType = new TypeToken<List<PlanLibraryDTO>>(){}.getType();
                                List<PlanLibraryDTO> tmp = gson.fromJson(jsonStr, listType);
                                updatePlanLibraryData(tmp.size());
                            }
                        } else {
                            Toast.makeText(getActivity(), "法律法规获取失败，请检查网络连接!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, params);
    }

    private void getDealProgram() {
        OkHttpClientManager http = OkHttpClientManager.getInstance();
        http.postAsyn(http.convertToURL( String.format(Constant.METHOD_GET_DISPOSAL_POINTS_LIST)),   // 后台没有按分页进行处理，也没有使用前台所传递的值，所以此处传递的值都是固定的
                new OkHttpClientManager.ResultCallback<ArrayRestResult>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Toast.makeText(getActivity(), "处置要点获取失败，请检查网络连接!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(ArrayRestResult result) {
                        if (result != null && result.isSuccess()) {
                            Gson gson = GsonUtil.create();
                            if (result.getContents() != null) {
                                String jsonStr = gson.toJson(result.getContents());
                                Type listType = new TypeToken<List<DisposalPointDTO>>(){}.getType();
                                dealProgramList = gson.fromJson(jsonStr, listType);
                                updateDealProgramData(dealProgramList.size());
                            }
                        } else {
                            Toast.makeText(getActivity(), "处置要点获取失败，请检查网络连接!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new HashMap<String, String>());
    }

    private void updateKnowledgeData(int count) {
        for (GeomElementType g : wsTypeList) {
            if (Constant.KNOWLEDGE.equals(g.getTypeCode())) {
                g.setCount(count);
                adapter.notifyDataSetChanged();
                updateResult(wsTypeList);
                break;
            }
        }
    }

    private void updateLawRegulationData(int count) {
        for (GeomElementType g : wsTypeList) {
            if (Constant.LAW_REGULATION.equals(g.getTypeCode())) {
                g.setCount(count);
                adapter.notifyDataSetChanged();
                updateResult(wsTypeList);
                break;
            }
        }
    }

    private void updatePlanLibraryData(int count) {
        for (GeomElementType g : wsTypeList) {
            if (Constant.PLAN_LIBRARY.equals(g.getTypeCode())) {
                g.setCount(count);
                adapter.notifyDataSetChanged();
                updateResult(wsTypeList);
                break;
            }
        }
    }

    private void updateDealProgramData(int count) {
        for (GeomElementType g : wsTypeList) {
            if (Constant.DEAL_PROGRAM.equals(g.getTypeCode())) {
                g.setCount(count);
                adapter.notifyDataSetChanged();
                updateResult(wsTypeList);
                break;
            }
        }
    }

    @Override
    public void onDownloadFinish() {
    }

    @Override
    public void onLoadOfflineFinish() {
        mDzListLogic.reloadData();
        refresh();
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

    
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.hidden = hidden;
        if (!hidden) {
            refresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!hidden) {
            refresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onSelectedDuizhan(String code, String name) {
        reloadData();
    }
}
