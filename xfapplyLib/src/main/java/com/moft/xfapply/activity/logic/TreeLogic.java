package com.moft.xfapply.activity.logic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.moft.xfapply.R;
import com.moft.xfapply.base.ViewLogic;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.widget.tree.TreeAdapter;
import com.moft.xfapply.widget.tree.TreePoint;
import com.moft.xfapply.widget.tree.TreeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sxf on 2019-04-16.
 */

public class TreeLogic extends ViewLogic {
    public TreeLogic(View v, Activity a) {
        super(v, a);
    }

    private AlertDialog dlg = null;
    private View popInfoView = null;
    private OnSelectedListener listener = null;

    private TreeAdapter adapter;
    private ListView listView;
    private EditText et_filter;
    private List<TreePoint> pointList = new ArrayList<>();
    private HashMap<String, TreePoint> pointMap = new HashMap<>();
    private boolean isDefaultExpand = false;

    private void init(View parent) {
        adapter = new TreeAdapter(getActivity(), pointList, pointMap);
        adapter.setListener(listener);
        listView = (ListView) parent.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        et_filter = (EditText) parent.findViewById(R.id.et_filter);
        addListener();
    }

    private void putPointList(List<Dictionary> dicList, String parentCode) {
        for (int i = 0; i < dicList.size(); i++) {
            String isLeaf = "0";
            if(dicList.get(i).getSubDictionary() == null
                    || dicList.get(i).getSubDictionary().size() == 0) {
                isLeaf = "1";
            }
            pointList.add(new TreePoint(dicList.get(i).getCode(), dicList.get(i).getValue(),
                    parentCode, isLeaf, i+1));

            if("0".equals(isLeaf)) {
                putPointList(dicList.get(i).getSubDictionary(), dicList.get(i).getCode());
            }
        }
    }

    public void setDefaultExpand(boolean expand) {
        isDefaultExpand = expand;
    }

    public void initData(List<Dictionary> contentList) {
        pointList.clear();

        putPointList(contentList, "0");
        if(isDefaultExpand) {
            expandRootNode();
        }
        //打乱集合中的数据
        Collections.shuffle(pointList);
        //对集合中的数据重新排序
        updateData();
    }

    private void expandRootNode() {
        if(pointList != null && pointList.size() > 0) {
            for(TreePoint item : pointList) {
                if("0".equals(item.getPARENTID())) {
                    item.setExpand(true);
                }
            }
        }
    }
    private void addListener() {
        et_filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchAdapter(s);
            }
        });
    }

    private void searchAdapter(Editable s) {
        adapter.setKeyword(s.toString());
    }

    //对数据排序 深度优先
    private void updateData() {
        for (TreePoint treePoint : pointList) {
            pointMap.put(treePoint.getID(), treePoint);
        }
        Collections.sort(pointList, new Comparator<TreePoint>() {
            @Override
            public int compare(TreePoint lhs, TreePoint rhs) {
                int llevel = TreeUtils.getLevel(lhs, pointMap);
                int rlevel = TreeUtils.getLevel(rhs, pointMap);
                if (llevel == rlevel) {
                    if (lhs.getPARENTID().equals(rhs.getPARENTID())) {  //左边小
                        return lhs.getDISPLAY_ORDER() > rhs.getDISPLAY_ORDER() ? 1 : -1;
                    } else {  //如果父辈id不相等
                        //同一级别，不同父辈
                        TreePoint ltreePoint = TreeUtils.getTreePoint(lhs.getPARENTID(), pointMap);
                        TreePoint rtreePoint = TreeUtils.getTreePoint(rhs.getPARENTID(), pointMap);
                        return compare(ltreePoint, rtreePoint);  //父辈
                    }
                } else {  //不同级别
                    if (llevel > rlevel) {   //左边级别大       左边小
                        if (lhs.getPARENTID().equals(rhs.getID())) {
                            return 1;
                        } else {
                            TreePoint lreasonTreePoint = TreeUtils.getTreePoint(lhs.getPARENTID(), pointMap);
                            return compare(lreasonTreePoint, rhs);
                        }
                    } else {   //右边级别大   右边小
                        if (rhs.getPARENTID().equals(lhs.getID())) {
                            return -1;
                        }
                        TreePoint rreasonTreePoint = TreeUtils.getTreePoint(rhs.getPARENTID(), pointMap);
                        return compare(lhs, rreasonTreePoint);
                    }
                }
            }
        });
    }

    public void show() {
        popInfoView = getActivity().getLayoutInflater().inflate(R.layout.popup_tree, null);

        init(popInfoView);

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display d = wm.getDefaultDisplay();

        LinearLayout ll_content = (LinearLayout) popInfoView.findViewById(R.id.ll_content);
        ViewGroup.LayoutParams params = ll_content.getLayoutParams();
        params.height = (int) (d.getHeight() * 0.8);
        ll_content.setLayoutParams(params);

        dlg = new AlertDialog.Builder(getActivity()).create();
        dlg.setView(popInfoView);
        dlg.show();
    }

    public void hide() {
        if (dlg != null && dlg.isShowing()) {
            dlg.dismiss();
            dlg = null;
        }
    }

    public void setListener(OnSelectedListener listener) {
        this.listener = listener;
    }

    public interface OnSelectedListener{
        void onSelected(String code);
        void onSelectedParentNode(String code);
        void onDismiss();
    }
}
