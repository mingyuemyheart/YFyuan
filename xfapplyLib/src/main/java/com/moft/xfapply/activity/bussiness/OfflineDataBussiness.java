package com.moft.xfapply.activity.bussiness;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.event.CommandEvent;
import com.moft.xfapply.event.UpdateIncDataEvent;
import com.moft.xfapply.logic.network.OkHttpClientManager;
import com.moft.xfapply.model.common.IncOfflineProcess;
import com.moft.xfapply.model.common.IncOfflineTypeProcess;
import com.moft.xfapply.model.entity.OfflineDBInfo;
import com.moft.xfapply.model.entity.OfflineDBUpdateDateInfo;
import com.moft.xfapply.model.external.dto.OfflineDbDTO;
import com.moft.xfapply.model.external.dto.UpdateDataDTO;
import com.moft.xfapply.model.external.params.ActualSqlForm;
import com.moft.xfapply.model.external.params.InquiryForm;
import com.moft.xfapply.model.external.params.InquiryTypeForm;
import com.moft.xfapply.model.external.result.SimpleRestResult;
import com.moft.xfapply.task.RequestGetActualSQLAsyncTask;
import com.moft.xfapply.utils.DateUtil;
import com.moft.xfapply.utils.DbUtil;
import com.moft.xfapply.utils.GsonUtil;
import com.moft.xfapply.utils.LogUtils;
import com.moft.xfapply.utils.StringUtil;

import net.tsz.afinal.FinalDb;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/*
 * @Author: 王泉
 * @Date:   2019-02-25
 * No.              Date.          Modifier    Description
 * 【T886049】      2019-02-25     王泉       终端与平台数据一致，实时同步（新建数据）
 */

public class OfflineDataBussiness {
    private final int BIG_DATA_ERROR_CODE = 17070;
    private static OfflineDataBussiness instance = null;
    private Map<String, IncOfflineProcess> processMap;

    private OfflineDataBussiness() {
        processMap = new HashMap<>();
    }

    public static OfflineDataBussiness getInstance() {
        if (instance == null) {
            instance = new OfflineDataBussiness();
        }
        return instance;
    }

    public IncOfflineProcess getIncOfflineProcess(String belongtoGroup) {
        IncOfflineProcess process = null;
        if(processMap.containsKey(belongtoGroup)) {
            process = processMap.get(belongtoGroup);
        }
        return process;
    }

    public void updateGetIncOfflineData() {
        final String districtCode = LvApplication.getInstance().getCityCode();

        if (StringUtil.isEmpty(districtCode)) {
            return;
        }
        if(!processMap.containsKey(districtCode)) {
            processMap.put(districtCode, new IncOfflineProcess());
        }

        if(processMap.get(districtCode).getStatus() == IncOfflineProcess.ProcessStatus.EXECUTING) {
            EventBus.getDefault().post(new UpdateIncDataEvent(districtCode));
            return;
        }

        List<OfflineDBUpdateDateInfo> updateDateInfos = getUpdateDateInfos();
        OkHttpClientManager http = OkHttpClientManager.getInstance();

        InquiryForm form = getInquiryForm(districtCode, updateDateInfos);
        final Gson gson = GsonUtil.create();


        final IncOfflineProcess process = processMap.get(districtCode);
        process.setStatus(IncOfflineProcess.ProcessStatus.EXECUTING);
        process.getTypeProcesses().clear();
        EventBus.getDefault().post(new UpdateIncDataEvent(districtCode));
        //设置不更新离线DB
        LvApplication.getInstance().setDownloadedOfflineDB(false);
        LogUtils.d(String.format("updateGetIncOfflineData params = %s", gson.toJson(form)));
        http.postAsyn(http.convertToURL(Constant.METHOD_GET_INQUIRY_INC_DATA), new OkHttpClientManager.ResultCallback<SimpleRestResult>() {
            @Override
            public void onError(Request request, Exception e) {
                LogUtils.d(String.format("updateGetIncOfflineData error = %s", e.getMessage()));
                process.setStatus(IncOfflineProcess.ProcessStatus.EXCEPTION);
                process.setMessage(String.format("获取变更数据异常：%s", e.getMessage()));
                EventBus.getDefault().post(new UpdateIncDataEvent(districtCode));
            }

            @Override
            public void onResponse(SimpleRestResult result) {
                if (result != null  && result.isSuccess()) {
                    Gson gson = GsonUtil.create();
                    String jsonStr = gson.toJson(result.getData());
                    Type listType = new TypeToken<UpdateDataDTO>() {
                    }.getType();
                    UpdateDataDTO updateDataDTO = gson.fromJson(jsonStr, listType);

                    List<InquiryTypeForm> inquiryTypeList = updateDataDTO.getTypeList();
                    if (inquiryTypeList != null && inquiryTypeList.size() > 0) {
                        LogUtils.d(String.format("updateGetIncOfflineData success = true, size = %d", inquiryTypeList.size()));
                        // 启动一个任务，处理实时sql数据
                        IncOfflineProcess process = processMap.get(updateDataDTO.getBelongtoGroup());
                        List<IncOfflineTypeProcess> typeProcesses = createIncOfflineTypeProcess(inquiryTypeList);
                        process.setTypeProcesses(typeProcesses);

                        if (typeProcesses.size() > 0) {
                            requestGetActualSQL(updateDataDTO.getBelongtoGroup(), typeProcesses, 0);
                        } else {
                            process.setStatus(IncOfflineProcess.ProcessStatus.IDLE);
                        }
                        EventBus.getDefault().post(new UpdateIncDataEvent(updateDataDTO.getBelongtoGroup()));
                    } else {
                        process.setStatus(IncOfflineProcess.ProcessStatus.IDLE);
                        EventBus.getDefault().post(new UpdateIncDataEvent(updateDataDTO.getBelongtoGroup()));
                    }
                } else {
                    if (result != null && result.getErrorCode() == BIG_DATA_ERROR_CODE) {
                        if(result.getData() != null) {
                            String jsonStr = gson.toJson(result.getData());
                            Type listType = new TypeToken<OfflineDbDTO>() {}.getType();
                            OfflineDbDTO dicOfflineDb = gson.fromJson(jsonStr, listType);

                            if (dicOfflineDb != null) {
                                updateDistrictDBInfo(dicOfflineDb);
                                EventBus.getDefault().post(new CommandEvent(CommandEvent.CMD_UPDATE_OFFLINE_COMPLETED));
                            }
                        }
                        process.setStatus(IncOfflineProcess.ProcessStatus.IDLE);
                        EventBus.getDefault().post(new UpdateIncDataEvent(districtCode));
                    } else {
                        LogUtils.d("updateGetIncOfflineData success = false");
                        process.setStatus(IncOfflineProcess.ProcessStatus.EXCEPTION);
                        process.setMessage("获取变更数据失败！");
                        EventBus.getDefault().post(new UpdateIncDataEvent(districtCode));
                    }
                }
            }
        }, gson.toJson(form));
    }

    private void updateDistrictDBInfo(OfflineDbDTO info) {
        try {
            FinalDb commonDb = DbUtil.getInstance().getDB(Constant.DB_NAME_COMMON);
            String strWhere = String.format(Constant.SEARCH_COND_BELONG_TO_GROUP, info.getBelongtoGroup());
            List<OfflineDBInfo> list = commonDb.findAllByWhere(OfflineDBInfo.class, strWhere, String.format(Constant.ORDER_COND_VERSION, "desc"));

            if (list.isEmpty()) {
                if(!StringUtil.isEmpty(info.getBelongtoGroup())) {
                    updateOfflineDBInfo(info);
                }
            } else {
                OfflineDBInfo district = list.get(0);
                if ((district.getVersionNo() == null) || ((info.getVersionNo() != null) && (info.getVersionNo() >= district.getVersionNo()))) {
                    if ((info.getVersionNo() != null)
                            && (district.getVersionNo() != null)
                            && (info.getVersionNo().intValue() == district.getVersionNo().intValue())) {
                        if (!"finish".equals(district.getStatus())) {   // 1004714 【移动端】后台生成离线包的过程中，手机端下载完离线包并加载完成后，还一直弹出dialog提示有离线包下载 ->期望不显示
                            LvApplication.getInstance().setDownloadedOfflineDB(true);
                        }
                    } else {
                        updateOfflineDBInfo(info);
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    private List<IncOfflineTypeProcess> createIncOfflineTypeProcess(List<InquiryTypeForm> inquiryTypeList) {
        List<IncOfflineTypeProcess> processes = new ArrayList<>();
        for (InquiryTypeForm item : inquiryTypeList) {
            if (item.getUpdate()) {
                IncOfflineTypeProcess typeProcess = new IncOfflineTypeProcess();
                typeProcess.setType(item.getType());
                typeProcess.setStatus(IncOfflineTypeProcess.ProcessStatus.IDLE);
                typeProcess.setUpdateDate(item.getStartDate());
                processes.add(typeProcess);
            }
        }
        return processes;
    }

    // ID:925904 19-04-03 大量数据同步时报内存不足异常。 smy
    private void requestGetActualSQL(final String belongtoGroup, final List<IncOfflineTypeProcess> typeProcesses, final int index) {
        // 获得参数处理
        final IncOfflineTypeProcess typeProcess = typeProcesses.get(index);
        final String type = typeProcess.getType();
        ActualSqlForm sqlForm = new ActualSqlForm();
        sqlForm.setBelongtoGroup(belongtoGroup);
        sqlForm.setType(typeProcess.getType());
        sqlForm.setStartDate(DateUtil.format(typeProcess.getUpdateDate(), "yyyy-MM-dd HH:mm:ss"));
        typeProcess.setStatus(IncOfflineTypeProcess.ProcessStatus.EXECUTING);
        // 写入日志
        final Gson gson = GsonUtil.create();
        LogUtils.d(String.format("requestGetActualSQL params = %s", gson.toJson(sqlForm)));
        RequestGetActualSQLAsyncTask task = new RequestGetActualSQLAsyncTask(new RequestGetActualSQLAsyncTask.RequestGetActualSQLListener() {
            @Override
            public void onPreExecute() {

            }
            @Override
            public void onPostExecute(List<String> exceptions, Date endDate) {
                if(exceptions != null && exceptions.size() == 0) {
                    updateOfflineDBDate(belongtoGroup, type, endDate);
                }
                requestNextGetActualSQL(belongtoGroup, index, typeProcesses, exceptions);
            }
        });
        //支持省离线数据库查询
        String dbName = CommonBussiness.getInstance().getDistrictNameByCode(belongtoGroup);
        task.execute(sqlForm, dbName);
    }

    private void requestNextGetActualSQL(String belongtoGroup, int index, List<IncOfflineTypeProcess> typeProcesses, List<String> exceptions) {
        IncOfflineTypeProcess typeProcess = typeProcesses.get(index);
        typeProcess.setStatus(IncOfflineTypeProcess.ProcessStatus.FINISH);
        typeProcess.getExceptions().clear();
        typeProcess.getExceptions().addAll(exceptions);
        if (index + 1 < typeProcesses.size()) {
            requestGetActualSQL(belongtoGroup, typeProcesses, index + 1);
        } else {
            processMap.get(belongtoGroup).setStatus(IncOfflineProcess.ProcessStatus.FINISH);
        }
        EventBus.getDefault().post(new UpdateIncDataEvent(belongtoGroup));
    }

    private OfflineDBInfo getCurrentDbInfo() {
        OfflineDBInfo retInfo = null;
        try {
            String districtCode = LvApplication.getInstance().getCityCode();
            FinalDb commonDb = DbUtil.getInstance().getDB(Constant.DB_NAME_COMMON);
            String strWhere = String.format(String.format(Constant.SEARCH_COND_BELONG_TO_GROUP, districtCode));
            List<OfflineDBInfo> list = commonDb.findAllByWhere(OfflineDBInfo.class, strWhere, "version_no desc");

            if (list.size() > 0) {
                retInfo = list.get(0);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return retInfo;
    }

    private InquiryForm getInquiryForm(String districtCode, List<OfflineDBUpdateDateInfo> list) {
        InquiryForm form = new InquiryForm();
        List<InquiryTypeForm> typeForms = new ArrayList<>();
        form.setBelongtoGroup(districtCode);
        form.setTypeList(typeForms);
        for(OfflineDBUpdateDateInfo item : list) {
            InquiryTypeForm info = new InquiryTypeForm();
            info.setType(item.getType());
            info.setStartDate(item.getUpdateDate());
            typeForms.add(info);
        }
        return form;
    }

    public void updateOfflineDBInfo(OfflineDbDTO info) {
        FinalDb commonDb = DbUtil.getInstance().getDB(Constant.DB_NAME_COMMON);
        OfflineDBInfo district = new OfflineDBInfo();

        district.setName(LvApplication.getInstance().getCityName());
        district.setBelongtoGroup(info.getBelongtoGroup());
        district.setUrl(info.getUrl());
        district.setVersionNo(info.getVersionNo());
        district.setUpdateDate(info.getStartDate());
        district.setDataType(info.getType());
        district.setPercent(0);
        commonDb.deleteByWhere(OfflineDBInfo.class, String.format(Constant.SEARCH_COND_NAME, LvApplication.getInstance().getCityName()));
        commonDb.save(district);
        //设置更新离线DB
        LvApplication.getInstance().setDownloadedOfflineDB(true);
    }

    public OfflineDBInfo getOfflineDBInfo(String name) {
        OfflineDBInfo info = null;

        FinalDb mDb = DbUtil.getInstance().getDB(Constant.DB_NAME_COMMON);

        String strWhere = String.format(Constant.SEARCH_COND_NAME, name);
        List<OfflineDBInfo> list = mDb.findAllByWhere(OfflineDBInfo.class, strWhere, String.format(Constant.ORDER_COND_VERSION, "desc"));
        if (list.size() == 0) {
            info = new OfflineDBInfo();
            info.setName(name);
            mDb.save(info);
        } else {
            info = list.get(0);
        }
        return info;
    }

    public void updateOfflineInfo(OfflineDBInfo cityInfo) {
        FinalDb mDb = DbUtil.getInstance().getDB(Constant.DB_NAME_COMMON);
        mDb.update(cityInfo);
    }

    public void updateOfflineDBDate(String belongtoGroup, String type, Date date) {
        try {
            //支持省离线数据库查询
            FinalDb mDb = DbUtil.getInstance().getDB(CommonBussiness.getInstance().getDistrictNameByCode(belongtoGroup));
            String strWhere = String.format(Constant.SEARCH_COND_TYPE, type);
            List<OfflineDBUpdateDateInfo> list = mDb.findAllByWhere(OfflineDBUpdateDateInfo.class, strWhere);

            if (list != null && list.size() > 0) {
                OfflineDBUpdateDateInfo info = list.get(0);
                if(info.getUpdateDate().compareTo(date) < 0) {
                    info.setUpdateDate(date);
                    mDb.update(info);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<OfflineDBUpdateDateInfo> getUpdateDateInfos() {
        List<OfflineDBUpdateDateInfo> list = null;
        try {
            //支持省离线数据库查询
            FinalDb mDb = DbUtil.getInstance().getDB(LvApplication.getInstance().getCityName());
            list = mDb.findAll(OfflineDBUpdateDateInfo.class, "update_date desc");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(list == null) {
            list = new ArrayList<>();
        }
        return list;
    }
}
