package com.moft.xfapply.logic.download;

import java.util.ArrayList;  
import java.util.List;

import com.moft.xfapply.app.Constant;
import com.moft.xfapply.model.entity.DataDownloadDBInfo;
import com.moft.xfapply.utils.DbUtil;

import net.tsz.afinal.FinalDb;  
public class DownloadInfoDao {
    
    public DownloadInfoDao() {  
    }  
  
    /**  
     * 查看数据库中是否有数据  
     */  
    public boolean isHasInfors(String urlstr) {  
        FinalDb db = DbUtil.getInstance().getDB(Constant.DB_NAME_COMMON);

        List<DataDownloadDBInfo> dbModels = new ArrayList<DataDownloadDBInfo>();
        dbModels = db.findAllByWhere(DataDownloadDBInfo.class, "url = '" + urlstr + "'");
        if (dbModels.size() > 0) {
            return true;
        } else {
            return false;
        }
    }  
  
    /**  
     * 保存 下载的具体信息   
     */  
    public void saveInfos(List<DataDownloadDBInfo> infos) {
        FinalDb db = DbUtil.getInstance().getDB(Constant.DB_NAME_COMMON);
        
        for (DataDownloadDBInfo info : infos) {
            db.save(info);
        }  
    }  
  
    /**  
     * 得到下载具体信息  
     */  
    public List<DataDownloadDBInfo> getInfos(String urlstr) {
        FinalDb db = DbUtil.getInstance().getDB(Constant.DB_NAME_COMMON);

        List<DataDownloadDBInfo> dbModels = new ArrayList<DataDownloadDBInfo>();
        dbModels = db.findAllByWhere(DataDownloadDBInfo.class, "url = '" + urlstr + "'");
        
        return dbModels;  
    }  
  
    /**  
     * 更新数据库中的下载信息  
     */  
    public void updataInfos(int threadId, long compeleteSize, String urlstr) {
        FinalDb db = DbUtil.getInstance().getDB(Constant.DB_NAME_COMMON);

        List<DataDownloadDBInfo> dbModels = new ArrayList<DataDownloadDBInfo>();
        dbModels = db.findAllByWhere(DataDownloadDBInfo.class, "url = '" + urlstr + "' and thread_id = " + threadId);
        if (dbModels.size() <= 0) {
            return;
        }
        
        DataDownloadDBInfo di = dbModels.get(0);
        di.setCompeleteSize(compeleteSize);
        
        db.update(di);
    }  
    
    /**  
     * 下载完成后删除数据库中的数据  
     */  
    public void delete(String url) {  
        FinalDb db = DbUtil.getInstance().getDB(Constant.DB_NAME_COMMON);
        db.deleteByWhere(DataDownloadDBInfo.class, "url = '" + url + "'");
    }  
}
