package com.moft.xfapply.activity.logic;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.moft.xfapply.R;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.base.ViewLogic;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sxf on 2019-04-02.
 */

public class DuizhanListLogic extends ViewLogic {
    private OnDuizhanListLogicListener mListener = null;
    private List<Dictionary> duizhanList = new ArrayList<Dictionary>();
    private Map<String, Dictionary> duizhanMap = new HashMap<String, Dictionary>();

    private View re_select = null;
    private String code = "00";
    private String name = "";

    public DuizhanListLogic(View v, Activity a) {
        super(v, a);
    }

    public void init() {
        re_select = (View) getView().findViewById(R.id.re_select);
        re_select.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                show();
            }
        });
        reloadData();
    }

    public void initData(String c, String n) {
        if (StringUtil.isEmpty(c)) {
            c = "00";
        }
        if (StringUtil.isEmpty(n)) {
            n = LvApplication.getInstance().getCityName();
        }
        code = c;
        name = n;
    }

    public void reloadData() {
        convertTreeDicList();
        code = "00";
        name = LvApplication.getInstance().getCityName();
        refreshView();
    }

    public void refreshView() {
    }

    private void show() {
        final TreeLogic treeLogic = new TreeLogic(getView(), getActivity());
        treeLogic.setListener(new TreeLogic.OnSelectedListener() {
            @Override
            public void onSelected(String s) {
                doSelect(s);
                treeLogic.hide();
            }

            @Override
            public void onSelectedParentNode(String s) {
                doSelect(s);
                treeLogic.hide();
            }

            @Override
            public void onDismiss() {

            }
        });
        treeLogic.setDefaultExpand(true);
        treeLogic.initData(duizhanList);
        treeLogic.show();

    }

    private void doSelect(String s) {
        Dictionary dic = duizhanMap.get(s);
        if (dic != null) {
            code = dic.getCode();
            name = dic.getValue();
            refreshView();
            if (mListener != null) {
                mListener.onSelectedDuizhan(code, name);
            }
        } else {
            Toast.makeText(getActivity(), "队站类型code错误，请联系管理员", Toast.LENGTH_SHORT).show();
        }
    }

    private void convertTreeDicList() {
        List<Dictionary> currentOrgList = LvApplication.getInstance().getCurrentOrgList();
        if(currentOrgList != null) {
            duizhanList.clear();
            duizhanMap.clear();
            for (Dictionary item : currentOrgList) {
                if (convertOrgCode(LvApplication.getInstance().getCityCode()).equals(item.getId())) {
                    duizhanList.add(item);
                    item.setSubDictionary(getChildOrgList(item.getCode(), currentOrgList));
                }
                duizhanMap.put(item.getCode(), item);
            }
        }
    }

    private String convertOrgCode(String cityCode) {
        StringBuffer orgCode = new StringBuffer();
        if(!StringUtil.isEmpty(cityCode)) {
            orgCode.append(cityCode);
            for(int index = cityCode.length(); index < 8; ++index) {
                orgCode.append("0");
            }
        }
        return orgCode.toString();
    }

    private List<Dictionary> getChildOrgList(String parentUuid, List<Dictionary> orgList) {
        List<Dictionary> list = new ArrayList<>();
        if(!StringUtil.isEmpty(parentUuid)) {
            for (Dictionary item : orgList) {
                if (parentUuid.equals(item.getParentCode())) {
                    list.add(item);
                    item.setSubDictionary(getChildOrgList(item.getCode(), orgList));
                }
            }
        }
        return list;
    }

    public void addListener(OnDuizhanListLogicListener mListener) {
        this.mListener = mListener;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public interface OnDuizhanListLogicListener{
        void onSelectedDuizhan(String code, String name);
    }
}
