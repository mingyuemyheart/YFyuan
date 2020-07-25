package com.moft.xfapply.task;

import android.os.AsyncTask;

import com.moft.xfapply.app.Constant;
import com.moft.xfapply.model.entity.SysDictionaryDBInfo;
import com.moft.xfapply.model.external.dto.SysDictionaryDTO;
import com.moft.xfapply.utils.DbUtil;
import com.moft.xfapply.utils.LogUtils;
import com.moft.xfapply.utils.ReflectionUtil;
import com.moft.xfapply.utils.StringUtil;

import net.tsz.afinal.FinalDb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangquan on 2017/4/25.
 */

public class UpdateDictionaryAsyncTask extends AsyncTask<Object, Integer, String> {
    private OnUpdateDictionaryListener listener;

    public UpdateDictionaryAsyncTask(OnUpdateDictionaryListener listener) {
        super();
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(listener != null) {
            listener.onPreExecute();
        }
    }

    @Override
    protected String doInBackground(Object... params) {
        LogUtils.d("UpdateDictionaryAsyncTask update start");
        List<SysDictionaryDTO> dicCompList = (List<SysDictionaryDTO>)params[0];
        try {
            FinalDb commonDb = DbUtil.getInstance().getDB(Constant.DB_NAME_COMMON);
            if (commonDb != null) {
                List<SysDictionaryDBInfo> oldDicCompList = commonDb.findAll(SysDictionaryDBInfo.class);
                List<SysDictionaryDBInfo> delList = new ArrayList<>();
                if (oldDicCompList != null && oldDicCompList.size() > 0) {
                    for (SysDictionaryDBInfo item : oldDicCompList) {
                        SysDictionaryDTO info = findDicCompItem(dicCompList, item);
                        if (info == null) {
                            delList.add(item);
                        } else {
                            if(!checkEqualsDictionary(item, info)) {
                                ReflectionUtil.convertToObj(item, info);
                                commonDb.update(item);
                            }
                            dicCompList.remove(info);
                        }
                    }
                }
                if (delList.size() > 0) {
                    for (SysDictionaryDBInfo item : delList) {
                        commonDb.delete(item);
                    }
                }

                for (Object item : dicCompList) {
                    SysDictionaryDBInfo dicDBInfo = new SysDictionaryDBInfo();
                    ReflectionUtil.convertToObj(dicDBInfo, item);
                    commonDb.save(dicDBInfo);
                }
            }
        }catch (Exception e) {
            LogUtils.d(String.format("UpdateDictionaryAsyncTask exception = %s", e.getMessage()));
        }
        LogUtils.d("UpdateDictionaryAsyncTask update end");
        return null;
    }

    private SysDictionaryDTO findDicCompItem(List<SysDictionaryDTO> dicCompList, SysDictionaryDBInfo info) {
        SysDictionaryDTO retInfo = null;
        if(dicCompList != null && dicCompList.size() > 0) {
            for(SysDictionaryDTO item : dicCompList) {
                if(!StringUtil.isEmpty(info.getUuid()) && info.getUuid().equals(item.getUuid())) {
                    retInfo = item;
                    break;
                }
            }
        }
        return retInfo;
    }

    private boolean checkEqualsDictionary(SysDictionaryDBInfo dbInfo, SysDictionaryDTO info) {
        boolean ret = false;

        if(dbInfo.getWeight() == info.getWeight() && dbInfo.getDepth() == info.getDepth() &&
                equalsStringValue(dbInfo.getCode(), info.getCode()) && equalsStringValue(dbInfo.getConfigUuid(), info.getConfigUuid()) &&
                equalsStringValue(dbInfo.getName(), info.getName()) && equalsStringValue(dbInfo.getParentUuid(), info.getParentUuid())) {
            ret = true;
        }
        return ret;
    }

    private boolean equalsStringValue(String value, String value2) {
        value = StringUtil.get(value);
        value2 = StringUtil.get(value2);
        return value.equals(value2);
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
    }

    @Override
    protected void onPostExecute(String result) {
        if(listener != null) {
            listener.onPostExecute();
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    public interface OnUpdateDictionaryListener {
        public void onPreExecute();
        public void onPostExecute();
    }
}
