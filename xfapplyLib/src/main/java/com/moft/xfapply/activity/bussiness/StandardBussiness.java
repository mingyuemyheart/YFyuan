package com.moft.xfapply.activity.bussiness;

import android.content.Context;

import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.model.common.QueryCondition;
import com.moft.xfapply.model.common.WhpViewInfo;
import com.moft.xfapply.model.entity.FileNodeDBInfo;
import com.moft.xfapply.model.entity.HazardChemicalDBInfo;
import com.moft.xfapply.model.entity.MsdsDBInfo;
import com.moft.xfapply.utils.DbUtil;
import com.moft.xfapply.utils.StringUtil;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.db.sqlite.DbModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangquan on 2017/4/20.
 */

public class StandardBussiness {
    private static StandardBussiness instance = null;
    private Context context = null;

    private StandardBussiness() {
        context = LvApplication.getContext();
    }

    public static StandardBussiness getInstance() {
        if (instance == null) {
            instance = new StandardBussiness();
        }
        return instance;
    }

    public HazardChemicalDBInfo getHazardChemicalById(String uuid) {
        HazardChemicalDBInfo ret = null;
        FinalDb mDb = DbUtil.getInstance().getDB(Constant.DB_NAME_EXTRA);
        if (mDb != null) {
            String condition = Constant.SEARCH_COND_NOT_DELETED;
            condition += " and ";
            condition += String.format(Constant.SEARCH_COND_UUID, uuid);
            List<HazardChemicalDBInfo> list = mDb.findAllByWhere(HazardChemicalDBInfo.class, condition);
            if (list != null && list.size() > 0) {
                ret = list.get(0);
            }
        }
        return ret;
    }

    public List<WhpViewInfo> getHazardChemicalList(QueryCondition sc, int limit, int offset) {
        List<WhpViewInfo> whpList = new ArrayList<>();
        FinalDb mDb = DbUtil.getInstance().getDB(Constant.DB_NAME_EXTRA);

        String condition = Constant.SEARCH_COND_NOT_DELETED;
        if(!StringUtil.isEmpty(sc.getKey())) {
            String keywords = StringUtil.handleTransferChar(sc.getKey());  // 对应字符转换
            condition += String.format(Constant.SEARCH_COND_UN_NAME_CODE, keywords, keywords, keywords);
        }
        if (!StringUtil.isEmpty(sc.getFilterSQL())) {
            condition = sc.getFilterSQL() + " AND " + condition;
        }
        String orderBy = String.format(Constant.ORDER_BY_NAME_LIMIT, getLimitCon(limit, offset));
        List<HazardChemicalDBInfo> hcList = mDb.findAllByWhere(HazardChemicalDBInfo.class, condition, orderBy);

        int nReadyCount = 0;
        if (hcList != null && hcList.size() > 0) {
            for (HazardChemicalDBInfo hc : hcList) {
                WhpViewInfo whp = new WhpViewInfo();
                whp.setType(0);
                whp.setUuid(hc.getUuid());
                whp.setName(hc.getName());
                whp.setProperty1(hc.getToxicity());
                whp.setProperty2(hc.getMainUse());
                whpList.add(whp);
                nReadyCount++;
            }
        }

        if (nReadyCount < limit) {
            List<MsdsDBInfo> msdsDBInfoList =  getMsdsList(sc, limit-nReadyCount, 0);
            for (MsdsDBInfo msds : msdsDBInfoList) {
                WhpViewInfo whp = new WhpViewInfo();
                whp.setType(1);
                whp.setUuid(msds.getPid());
                whp.setName(msds.getCname());
                whp.setProperty2("来自于MSDS库");
                whpList.add(whp);
            }
        }

        return whpList;
    }

    public MsdsDBInfo getMsdsById(String uuid) {
        MsdsDBInfo ret = null;
        FinalDb mDb = DbUtil.getInstance().getDB(Constant.DB_NAME_EXTRA);
        if (mDb != null) {
            String condition = "pid = '" + uuid + "'";
            List<MsdsDBInfo> list = mDb.findAllByWhere(MsdsDBInfo.class, condition);
            if (list != null && list.size() > 0) {
                ret = list.get(0);
            }
        }
        return ret;
    }

    private List<MsdsDBInfo> getMsdsList(QueryCondition sc, int limit, int offset) {
        FinalDb mDb = DbUtil.getInstance().getDB(Constant.DB_NAME_EXTRA);
        String condition = "1=1";
        if (!StringUtil.isEmpty(sc.getKey())) {
            String keywords = StringUtil.handleTransferChar(sc.getKey());  // 对应字符转换
            condition += String.format(Constant.SEARCH_COND_CNAME, keywords);
        }

        String orderBy = String.format(Constant.ORDER_BY_CNAME_LIMIT, getLimitCon(limit, offset));
        List<MsdsDBInfo> msdsList = mDb.findAllByWhere(MsdsDBInfo.class, condition, orderBy);
        return msdsList;
    }

    public int getHazardChemicalCount(String key) {
        FinalDb mDb = DbUtil.getInstance().getDB(Constant.DB_NAME_EXTRA);
        String condition = Constant.SEARCH_COND_NOT_DELETED;
        if (!StringUtil.isEmpty(key)) {
            condition += String.format(Constant.SEARCH_COND_UN_NAME_CODE, key, key, key);
        }
        String strSQL = String.format("select count(*) as cnt from %s where %s", Constant.TABLE_NAME_HAZARD_CHEMICAL, condition);
        DbModel dm = mDb.findDbModelBySQL(strSQL);

        return Integer.valueOf((String)dm.get("cnt"));
    }

    public int getMsdsCount(String key) {
        FinalDb mDb = DbUtil.getInstance().getDB(Constant.DB_NAME_EXTRA);
        String condition = "1=1";
        if (!StringUtil.isEmpty(key)) {
            condition += String.format(Constant.SEARCH_COND_CNAME, key);
        }
        String strSQL = String.format("select count(*) as cnt from %s where %s", Constant.TABLE_NAME_MSDS, condition);
        DbModel dm = mDb.findDbModelBySQL(strSQL);

        return Integer.valueOf((String)dm.get("cnt"));
    }

    public void updateFileNode(FileNodeDBInfo mediaInfo) {
        String strWhere = String.format(Constant.SEARCH_COND_PUBLISH_URL, mediaInfo.getPublishUrl());

        FinalDb mDb = DbUtil.getInstance().getDB(Constant.DB_NAME_EXTRA);

        List<FileNodeDBInfo> dtos = (List<FileNodeDBInfo>)mDb.findAllByWhere(mediaInfo.getClass(), strWhere);
        if (dtos.size() > 0) {
            FileNodeDBInfo info = dtos.get(0);
            info.setName(mediaInfo.getName());
            info.setDirectory(mediaInfo.getDirectory());
            info.setFormat(mediaInfo.getFormat());
            info.setDestination(mediaInfo.getDestination());
            info.setChildren(mediaInfo.getChildren());
            info.setPublishUrl(mediaInfo.getPublishUrl());
            info.setRelativePath(mediaInfo.getRelativePath());
            info.setLocalPath(mediaInfo.getLocalPath());
            mDb.update(info);
        }
    }

    private String getLimitCon(int limit, int offset) {
        String strLimit = "";

        if (limit > 0) {
            strLimit = "limit " + limit + " offset " + offset;
        }

        return strLimit;
    }

}
