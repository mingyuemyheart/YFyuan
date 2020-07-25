package com.moft.xfapply.activity.logic;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.adapter.CheckBoxAdapter;
import com.moft.xfapply.activity.bussiness.CommonBussiness;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.base.ViewLogic;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.external.AppDefs;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sxf on 2019-04-09.
 */

public class DzMutilSelLogic extends ViewLogic {
    public static int TYPE_LEVEL1 = 1;
    public static int TYPE_LEVEL2 = 2;
    public static int TYPE_LEVEL3 = 3;

    private PopupWindow popInfo = null;
    private View popInfoView = null;
    private LinearLayout popup_content = null;

    private View re_allselect = null;
    private View tv_confirm = null;
    private ImageView iv_allselect = null;
    private View re_level2 = null;
    private View re_level3 = null;
    private ListView list1 = null;
    private ListView list2 = null;
    private ListView list3 = null;

    private CheckBoxAdapter adapter1 = null;
    private CheckBoxAdapter adapter2 = null;
    private CheckBoxAdapter adapter3 = null;

    private boolean isAllChecked = true;
    private Boolean defaultCheckValue = true;
    private int type = TYPE_LEVEL1;
    private List<Dictionary> dataList1 = new ArrayList<>();
    private List<Dictionary> dataList2 = new ArrayList<>();
    private List<Dictionary> dataList3 = new ArrayList<>();
    private List<Boolean> dataListCheck1 = new ArrayList<>();
    private List<Boolean> dataListCheck2 = new ArrayList<>();
    private List<Boolean> dataListCheck3 = new ArrayList<>();
    private int curIndex1 = -1;
    private int curIndex2 = -1;
    private int curIndex3 = -1;

    private Map<String, List<Dictionary>> dataMap = new LinkedHashMap<>();
    private Map<String, List<Boolean>> dataMapCheck = new LinkedHashMap<>();
    private OnDzMutilSelLogicListener listener;

    private boolean isConfirm = false;

    public DzMutilSelLogic(View v, Activity a) {
        super(v, a);
    }

    private void init() {
        isAllChecked = defaultCheckValue;

        re_allselect = getView().findViewById(R.id.re_allselect);
        tv_confirm = getView().findViewById(R.id.tv_confirm);
        iv_allselect = (ImageView) getView().findViewById(R.id.iv_allselect);
        if (isAllChecked) {
            iv_allselect.setImageResource(R.drawable.checkbox_outline);
        } else {
            iv_allselect.setImageResource(R.drawable.checkbox_outline_bl);
        }
        re_allselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllChecked = !isAllChecked;
                //修改首次打开选择非全选，再次打开状态不对问题。
                defaultCheckValue = isAllChecked;
                if (isAllChecked) {
                    iv_allselect.setImageResource(R.drawable.checkbox_outline);
                } else {
                    iv_allselect.setImageResource(R.drawable.checkbox_outline_bl);
                }
                setCheck1(isAllChecked);

                adapter1.notifyDataSetChanged();
                if (type >= TYPE_LEVEL2) {
                    adapter2.notifyDataSetChanged();

                    if (type >= TYPE_LEVEL3) {
                        adapter3.notifyDataSetChanged();
                    }
                }
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isConfirm = true;
                hide();
                if(listener != null) {
                    Map<String, Dictionary> items = new LinkedHashMap<>();
                    if(TYPE_LEVEL1 == type) {
                        items = getLevel1SelectItems();
                    } else if(TYPE_LEVEL2 == type) {
                        items = getLevel2SelectItems();
                    } else if(TYPE_LEVEL3 == type) {
                        items = getLevel3SelectItems();
                    }
                    listener.onConfirmClick(items);
                }
            }
        });

        re_level2 = getView().findViewById(R.id.re_level2);
        re_level3 = getView().findViewById(R.id.re_level3);

        if (type == TYPE_LEVEL1) {
            re_level2.setVisibility(View.GONE);
            re_level3.setVisibility(View.GONE);
        } else if (type == TYPE_LEVEL2) {
            re_level3.setVisibility(View.GONE);
        } else if (type == TYPE_LEVEL3) {
        }

        list1 = (ListView) getView().findViewById(R.id.list1);
        list2 = (ListView) getView().findViewById(R.id.list2);
        list3 = (ListView) getView().findViewById(R.id.list3);

        loadList1();
        adapter1 = new CheckBoxAdapter(getActivity(), dataList1, dataListCheck1, curIndex1, listener1);
        adapter1.setBackground(R.drawable.btn_list_item_bg_level1);
        adapter1.setCurBackground(R.drawable.btn_list_item_bg_level2);

        if(dataList1 != null && dataList1.size() > 0) {
            if (type >= TYPE_LEVEL2) {
                curIndex1 = 0;
                adapter1.setCurIndex(curIndex1);
                adapter1.setLeveling(true);

                refreshList2(dataList1.get(curIndex1));
                if (type >= TYPE_LEVEL3) {
                    curIndex2 = 0;
                    adapter2.setCurIndex(curIndex2);
                    adapter2.setLeveling(true);
                    adapter2.notifyDataSetChanged();
                    if (dataList2.size() > 0) {
                        refreshList3(dataList1.get(curIndex1), dataList2.get(curIndex2));
                    } else {
                        refreshList3();
                    }
                }
            }
        }
        list1.setAdapter(adapter1);
    }

    public void show(View parent, final int type) {
        isConfirm = false;
        this.type = type;

        if (popInfo != null && popInfo.isShowing()) {
            popInfo.dismiss();
            popInfo = null;
        }

        popInfoView = getActivity().getLayoutInflater().inflate(R.layout.popup_info, null);
        popup_content = (LinearLayout) popInfoView.findViewById(R.id.popup_content);

        popInfo = new PopupWindow(popInfoView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popInfo.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popInfo.setOutsideTouchable(false);
        popInfo.setAnimationStyle(R.style.AnimTop2);
        popInfo.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (!isConfirm) {
                    if (listener != null) {
                        listener.onUnChanged();
                    }
                }
            }
        });

        View popCon = getActivity().getLayoutInflater().inflate(R.layout.duizhan_select, null);
        setView(popCon);
        init();

        popup_content.addView(popCon);
        if (popInfo != null && !popInfo.isShowing()) {
            popInfo.showAsDropDown(parent);
        }
    }

    public void hide() {
        if (popInfo != null && popInfo.isShowing()) {
            popInfo.dismiss();
            popInfo = null;
        }
    }

    public void setOnDzMutilSelLogicListener(OnDzMutilSelLogicListener listener) {
        this.listener = listener;
    }
    private void setCheck1(boolean isChecked) {
        if (!dataMapCheck.containsKey("root")) {
            List<Dictionary> dlist1 = CommonBussiness.getInstance().getDetachmentOrgMap(LvApplication.getInstance().getCityName());
            dataMap.put("root", dlist1);

            List<Boolean> dlc1 = new ArrayList<>();
            resetDataListCheck(dlc1, dlist1.size(), defaultCheckValue);
            dataMapCheck.put("root", dlc1);
        }
        List<Boolean> dlc1 = dataMapCheck.get("root");
        resetDataListCheck(dlc1, dlc1.size(), isChecked);

        if (type >= TYPE_LEVEL2) {
            List<Dictionary> dl1 = dataMap.get("root");
            for (Dictionary d : dl1) {
                setCheck2(d, isChecked);
            }
        }
    }

    private void setCurrentCheck1(boolean isChecked) {
        if(!dataListCheck1.get(curIndex1)) {
            dataListCheck1.set(curIndex1, isChecked);
            adapter1.notifyDataSetChanged();
        }
    }

    private void setCheck2(Dictionary dic, boolean isChecked) {
        if (!dataMapCheck.containsKey(dic.getValue())) {
            List<Dictionary> dlist2 = CommonBussiness.getInstance().getChildOrgList(dic.getCode());
            dataMap.put(dic.getValue(), dlist2);

            List<Boolean> dlc2 = new ArrayList<>();
            resetDataListCheck(dlc2, dlist2.size(), defaultCheckValue);
            dataMapCheck.put(dic.getValue(), dlc2);
        }
        List<Boolean> dlc2 = dataMapCheck.get(dic.getValue());
        resetDataListCheck(dlc2, dlc2.size(), isChecked);

        if (type >= TYPE_LEVEL3) {
            List<Dictionary> dl2 = dataMap.get(dic.getValue());
            for (Dictionary d : dl2) {
                setCheck3(dic, d, isChecked);
            }
        }
    }

    private void setCurrentCheck2(boolean isChecked) {
        if(!dataListCheck2.get(curIndex2)) {
            dataListCheck2.set(curIndex2, isChecked);
            adapter2.notifyDataSetChanged();
            setCurrentCheck1(isChecked);
        }
    }

    private void setCheck3(Dictionary dic, Dictionary d, boolean isChecked) {
        String key = dic.getValue() + d.getValue();
        if (!dataMapCheck.containsKey(key)) {
            List<Dictionary> dlist3 = CommonBussiness.getInstance().getChildOrgList(d.getCode());
            dataMap.put(key, dlist3);

            List<Boolean> dlc3 = new ArrayList<>();
            resetDataListCheck(dlc3, dlist3.size(), defaultCheckValue);
            dataMapCheck.put(key, dlc3);
        }
        List<Boolean> dlc3 = dataMapCheck.get(key);
        resetDataListCheck(dlc3, dlc3.size(), isChecked);
    }

    private CheckBoxAdapter.OnItemSelectedListener listener1 = new CheckBoxAdapter.OnItemSelectedListener() {
        @Override
        public void onItemClick(Dictionary dic, int pos) {
            curIndex1 = pos;
            if (type >= TYPE_LEVEL2) {
                curIndex2 = -1;
                refreshList2(dataList1.get(curIndex1));
                if (type >= TYPE_LEVEL3) {
                    curIndex2 = 0;
                    adapter2.setCurIndex(curIndex2);
                    adapter2.setLeveling(true);
                    adapter2.notifyDataSetChanged();
                    curIndex3 = -1;
                    if (dataList2.size() > 0) {
                        refreshList3(dataList1.get(curIndex1), dataList2.get(curIndex2));
                    } else {
                        refreshList3();
                    }
                }
            }
        }

        @Override
        public void onItemCheck(Dictionary dic, boolean isChecked, int pos) {
            if (type >= TYPE_LEVEL2) {
                setCheck2(dic, isChecked);
            }
            if (curIndex1 == pos) {
                if (type >= TYPE_LEVEL2) {
                    adapter2.notifyDataSetChanged();

                    if (type >= TYPE_LEVEL3) {
                        adapter3.notifyDataSetChanged();
                    }
                }
            }
        }

        @Override
        public void onSelectedRefresh(int index) {
            adapter1.setCurIndex(index);
            adapter1.notifyDataSetChanged();
        }
    };

    private CheckBoxAdapter.OnItemSelectedListener listener2 = new CheckBoxAdapter.OnItemSelectedListener(){
        @Override
        public void onItemClick(Dictionary dic, int pos) {
            curIndex2 = pos;
            //追加选中大队，支队也选中状态
            if(type == TYPE_LEVEL2 && curIndex2 < dataListCheck2.size()) {
                boolean isChecked = dataListCheck2.get(curIndex2);
                if(isChecked) {
                    setCurrentCheck1(isChecked);
                }
            }
            if (type >= TYPE_LEVEL3) {
                curIndex3 = -1;
                if (dataList2.size() > 0) {
                    refreshList3(dataList1.get(curIndex1), dataList2.get(curIndex2));
                } else {
                    refreshList3();
                }
            }
        }

        @Override
        public void onItemCheck(Dictionary dic, boolean isChecked, int pos) {
            if (type >= TYPE_LEVEL3) {
                setCheck3(dataList1.get(curIndex1), dic, isChecked);
            }
            if (curIndex2 == pos) {
                if (type >= TYPE_LEVEL3) {
                    adapter3.notifyDataSetChanged();
                }
            }
            //追加选中大队，支队也选中状态
            if(isChecked) {
                setCurrentCheck1(isChecked);
            }
        }

        public void onSelectedRefresh(int index) {
            adapter2.setCurIndex(index);
            adapter2.notifyDataSetChanged();
        }
    };

    private CheckBoxAdapter.OnItemSelectedListener listener3 = new CheckBoxAdapter.OnItemSelectedListener(){
        @Override
        public void onItemClick(Dictionary dic, int pos) {
            curIndex3 = pos;
            if(curIndex3 < dataListCheck3.size()) {
                boolean isChecked = dataListCheck3.get(curIndex3);
                if(isChecked) {
                    setCurrentCheck2(isChecked);
                }
            }
        }

        @Override
        public void onItemCheck(Dictionary dic, boolean isChecked, int pos) {

        }

        public void onSelectedRefresh(int index) {
            adapter3.setCurIndex(index);
            adapter3.notifyDataSetChanged();
        }
    };

    private void loadList1() {
        if (dataMap.containsKey("root")) {
            dataList1 = dataMap.get("root");
        } else {
            // 无支队列表获取接口，暂用城市列表接口
            dataList1 = CommonBussiness.getInstance().getDetachmentOrgMap(LvApplication.getInstance().getCityName());
            dataMap.put("root", dataList1);
        }
        if (dataMapCheck.containsKey("root")) {
            dataListCheck1 = dataMapCheck.get("root");
        } else {
            resetDataListCheck(dataListCheck1, dataList1.size(), defaultCheckValue);
            dataMapCheck.put("root", dataListCheck1);
        }
    }

    private void loadList2(Dictionary dicLevel1) {
        if (dataMap.containsKey(dicLevel1.getValue())) {
            dataList2 = dataMap.get(dicLevel1.getValue());
        } else {
            dataList2 = CommonBussiness.getInstance().getChildOrgList(dicLevel1.getCode());
            dataMap.put(dicLevel1.getValue(), dataList2);
        }

        if (dataMapCheck.containsKey(dicLevel1.getValue())) {
            dataListCheck2 = dataMapCheck.get(dicLevel1.getValue());
        } else {
            dataListCheck2 = new ArrayList<>();
            resetDataListCheck(dataListCheck2, dataList2.size(), defaultCheckValue);
            dataMapCheck.put(dicLevel1.getValue(), dataListCheck2);
        }
    }

    private void loadList3(Dictionary dicLevel1, Dictionary dicLevel2) {
        String key = dicLevel1.getValue() + dicLevel2.getValue();
        if (dataMap.containsKey(key)) {
            dataList3 = dataMap.get(key);
        } else {
            dataList3 = CommonBussiness.getInstance().getChildOrgList(dicLevel2.getCode());
            dataMap.put(key, dataList3);
        }

        if (dataMapCheck.containsKey(key)) {
            dataListCheck3 = dataMapCheck.get(key);
        } else {
            dataListCheck3 = new ArrayList<>();
            resetDataListCheck(dataListCheck3, dataList3.size(), defaultCheckValue);
            dataMapCheck.put(key, dataListCheck3);
        }
    }

    private void refreshList2(Dictionary dicLevel1) {
        loadList2(dicLevel1);

        adapter2 = new CheckBoxAdapter(getActivity(), dataList2, dataListCheck2, curIndex2, listener2);
        adapter2.setBackground(R.drawable.btn_list_item_bg_level2);
        adapter2.setCurBackground(R.drawable.btn_list_item_bg);
        list2.setAdapter(adapter2);
    }

    private void refreshList3(Dictionary dicLevel1, Dictionary dicLevel2) {
        loadList3(dicLevel1, dicLevel2);

        adapter3 = new CheckBoxAdapter(getActivity(), dataList3, dataListCheck3, curIndex3, listener3);
        list3.setAdapter(adapter3);
    }

    private void refreshList3() {
        dataList3 = new ArrayList<Dictionary>();
        dataListCheck3 = new ArrayList<Boolean>();
        curIndex3 = -1;

        adapter3 = new CheckBoxAdapter(getActivity(), dataList3, dataListCheck3, curIndex3, null);
        list3.setAdapter(adapter3);
    }

    private void resetDataListCheck(List<Boolean> dlc, int size, Boolean value) {
        dlc.clear();
        for (int i = 0; i < size; i++) {
            dlc.add(value);
        }
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return  this.type;
    }

    public void setDefaultCheckValue(Boolean defaultCheckValue) {
        this.defaultCheckValue = defaultCheckValue;
    }

    private Map<String, Dictionary> getLevel1SelectItems() {
        Map<String, Dictionary> items = new LinkedHashMap<>();
        String key = "root";
        List<Dictionary> list = dataMap.get(key);
        List<Boolean> checkList = dataMapCheck.get(key);
        for(int index = 0; index < list.size(); ++index) {
            if(checkList.get(index)) {
                items.put(list.get(index).getId(), list.get(index));
            }
        }
        return items;
    }

    private Map<String, Dictionary> getLevel2SelectItems() {
        Map<String, Dictionary> items = new LinkedHashMap<>();
        String key = "root";
        List<Dictionary> list = dataMap.get(key);
        List<Boolean> checkList = dataMapCheck.get(key);
        for(int index = 0; index < list.size(); ++index) {
            if(checkList.get(index)) {
                List<Dictionary> ddList = dataMap.get(list.get(index).getValue());
                List<Boolean> checkDdList = dataMapCheck.get(list.get(index).getValue());
                if(ddList != null && ddList.size() > 0) {
                    for(int ddIndex = 0; ddIndex < ddList.size(); ++ddIndex) {
                        if(checkDdList.get(ddIndex)) {
                            items.put(ddList.get(ddIndex).getId(), ddList.get(ddIndex));
                        }
                    }
                }
                items.put(list.get(index).getId(), list.get(index));
            }
        }
        return items;
    }

    private Map<String, Dictionary> getLevel3SelectItems() {
        Map<String, Dictionary> items = new LinkedHashMap<>();
        String key = "root";
        List<Dictionary> list = dataMap.get(key);
        List<Boolean> checkList = dataMapCheck.get(key);
        for(int index = 0; index < list.size(); ++index) {
            if(checkList.get(index)) {
                List<Dictionary> ddList = dataMap.get(list.get(index).getValue());
                List<Boolean> checkDdList = dataMapCheck.get(list.get(index).getValue());
                if(ddList != null && ddList.size() > 0) {
                    for(int ddIndex = 0; ddIndex < ddList.size(); ++ddIndex) {
                        if(checkDdList.get(ddIndex)) {
                            String ddKey = list.get(index).getValue() + ddList.get(ddIndex).getValue();
                            List<Dictionary> zdList = dataMap.get(ddKey);
                            List<Boolean> checkZdList = dataMapCheck.get(ddKey);
                            if(zdList != null && zdList.size() > 0) {
                                for (int zdIndex = 0; zdIndex < zdList.size(); ++zdIndex) {
                                    if(checkZdList.get(zdIndex)) {
                                        items.put(zdList.get(zdIndex).getId(), zdList.get(zdIndex));
                                    }
                                }
                            }
                            items.put(ddList.get(ddIndex).getId(), ddList.get(ddIndex));
                        }
                    }
                }
                items.put(list.get(index).getId(), list.get(index));
            }
        }
        return items;
    }

    public interface OnDzMutilSelLogicListener {
        public void onConfirmClick(Map<String, Dictionary> selectItems);
        public void onUnChanged();
    }
}
