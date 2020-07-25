package com.moft.xfapply.activity.logic;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moft.xfapply.R;
import com.moft.xfapply.base.ViewLogic;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.common.PropertyConditon;
import com.moft.xfapply.utils.DateUtil;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.widget.dialog.ListDialog;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyConditionLogic extends ViewLogic {
    public PropertyConditionLogic(View v, Activity a) {
        super(v, a);
    }
    private OnPropertyConditionListener mListener = null;

    private List<PropertyConditon> pcListDetail = null;

    private static HashMap<PropertyConditon, View> valueTvList = new HashMap<>();

    public String getSQL(boolean isOr) {
        String strSQL = "";

        String linkMark = "and";
        if (isOr) {
            linkMark = "or";
        }
        return getSQL(linkMark, false);
    }

    public String getSQLDesc(boolean isOr) {
        String strSQL = "";

        String linkMark = "且";
        if (isOr) {
            linkMark = "或";
        }
        return getSQL(linkMark, true);
    }

    private String getSQL(String linkMark, boolean isDesc) {
        String strSQL = "";

        for (PropertyConditon pc : pcListDetail) {
            String value = "";
            String valueSub = "";

            View resView = valueTvList.get(pc);
            TextView tv_value = (TextView) resView.findViewById(R.id.tv_value);
            value = tv_value.getText().toString();
            TextView tv_value2 = (TextView) resView.findViewById(R.id.tv_value2);
            if (tv_value2 != null) {
                valueSub = tv_value2.getText().toString();
            }
            String columnSQL = "";
            if (isDesc) {
                columnSQL = pc.getSQLDesc(value, valueSub);
            } else {
                columnSQL = pc.getSQL(value, valueSub);
            }
            if (!StringUtil.isEmpty(columnSQL)) {
                columnSQL = "(" + columnSQL + ")";
                if (StringUtil.isEmpty(strSQL)) {
                    strSQL = columnSQL;
                } else {
                    strSQL += " " + linkMark + " " + columnSQL;
                }
            }
        }

        return strSQL;
    }

    public Map<String, List<String>> getColumnValue() {
        Map<String, List<String>> ret = new HashMap<String, List<String>>();

        for (PropertyConditon pc : pcListDetail) {
            View resView = valueTvList.get(pc);

            TextView tv_value = (TextView) resView.findViewById(R.id.tv_value);

            String value = tv_value.getText().toString();
            if (!StringUtil.isEmpty(value)) {

                ret.put(pc.getColumnName(), pc.getColumnValue(value));
            }
        }

        return ret;
    }

    public void init() {
        LinearLayout parent = (LinearLayout)getView();

        for (PropertyConditon pd : pcListDetail) {
            View resView = null;
            switch (pd.getType()) {
                case PropertyConditon.TYPE_EDIT:
                    resView = getViewTypeEdit(pd);
                    break;
                case PropertyConditon.TYPE_EDIT_NUMBER:
                    resView = getViewTypeEditNumber(pd);
                    break;
                case PropertyConditon.TYPE_LIST:
                    resView = getViewTypeList(pd);
                    break;
                case PropertyConditon.TYPE_DATE:
                    resView = getViewTypeDate(pd);
                    break;
                default:
                    break;
            }

            if (resView != null) {
                parent.addView(resView);
            }
        }
    }

    private View getViewTypeEdit(final PropertyConditon pd) {
        int itemResourceId = R.layout.property_edit;

        final View resView = getActivity().getLayoutInflater().inflate(itemResourceId, null);

        TextView tv_name = (TextView) resView.findViewById(R.id.tv_name);
        tv_name.setText(pd.getTitle());

        final TextView tv_value = (TextView) resView.findViewById(R.id.tv_value);
        tv_value.setText("");

        final ImageView iv_delete = (ImageView) resView.findViewById(R.id.iv_delete);
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_value.setText("");
            }
        });

        EditText et_value = (EditText) tv_value;
        et_value.setSelection(et_value.getText().length());

        if (pd.getColumnType() == Integer.class) {
            et_value.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (pd.getColumnType() == Double.class) {
            et_value.setInputType(InputType.TYPE_CLASS_NUMBER
                    | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }

        et_value.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    iv_delete.setVisibility(View.VISIBLE);
                } else {
                    iv_delete.setVisibility(View.GONE);
                }
            }
        });

        resView.setTag(pd);
        valueTvList.put(pd, resView);
        return resView;
    }

    private View getViewTypeEditNumber(final PropertyConditon pd) {
        int itemResourceId = R.layout.property_edit_number;

        final View resView = getActivity().getLayoutInflater().inflate(itemResourceId, null);

        TextView tv_name = (TextView) resView.findViewById(R.id.tv_name);
        tv_name.setText(pd.getTitle());

        EditText et_value1 = (EditText) resView.findViewById(R.id.tv_value);
        if (pd.getColumnType() == Integer.class) {
            et_value1.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (pd.getColumnType() == Double.class) {
            et_value1.setInputType(InputType.TYPE_CLASS_NUMBER
                    | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }

        EditText et_value2 = (EditText) resView.findViewById(R.id.tv_value2);
        if (pd.getColumnType() == Integer.class) {
            et_value2.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (pd.getColumnType() == Double.class) {
            et_value2.setInputType(InputType.TYPE_CLASS_NUMBER
                    | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }

        resView.setTag(pd);
        valueTvList.put(pd, resView);
        return resView;
    }

    private View getViewTypeDate(PropertyConditon pd) {
        View resView = null;

        int itemResourceId = R.layout.property_view;
        resView = getActivity().getLayoutInflater().inflate(itemResourceId, null);

        TextView tv_name = (TextView) resView.findViewById(R.id.tv_name);
        tv_name.setText(pd.getTitle());

        final TextView tv_value = (TextView) resView.findViewById(R.id.tv_value);
        tv_value.setText("");

        resView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (v.getTag() == null) {
                    return;
                }

                final PropertyConditon pd = (PropertyConditon)v.getTag();
                showDateDialog(tv_value);
            }
        });

        resView.setTag(pd);
        valueTvList.put(pd, resView);
        return resView;
    }

    private View getViewTypeList(PropertyConditon pd) {
        int itemResourceId = R.layout.property_list;
        final View resView = getActivity().getLayoutInflater().inflate(itemResourceId, null);

        TextView tv_name = (TextView) resView.findViewById(R.id.tv_name);
        tv_name.setText(pd.getTitle());

        final TextView tv_value_edit = (TextView) resView.findViewById(R.id.tv_value_edit);
        final TextView tv_value = (TextView) resView.findViewById(R.id.tv_value);

        tv_value.setText("");
        tv_value_edit.setText(tv_value.getText());
        tv_value_edit.setVisibility(View.GONE);

        resView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (v.getTag() == null) {
                    return;
                }

                final PropertyConditon pd = (PropertyConditon)v.getTag();

                final List<Dictionary> contentList = pd.getDicList();

                final TreeLogic treeLogic = new TreeLogic(getView(), getActivity());
                treeLogic.setListener(new TreeLogic.OnSelectedListener() {
                    @Override
                    public void onSelected(String code) {
                        Dictionary dic = getDictionaryByCode(contentList, code);
                        if (dic != null) {
                            pd.setCurDic(dic);

                            tv_value.setText(dic.getValue());
                            tv_value_edit.setText(tv_value.getText());

                        }
                        treeLogic.hide();
                    }

                    @Override
                    public void onSelectedParentNode(String code) {
                        Dictionary dic = getDictionaryByCode(contentList, code);
                        if (dic != null) {
                            pd.setCurDic(dic);

                            tv_value.setText(dic.getValue());
                            tv_value_edit.setText(tv_value.getText());

                        }
                        treeLogic.hide();
                    }

                    @Override
                    public void onDismiss() {

                    }
                });
                treeLogic.initData(contentList);
                treeLogic.show();
            }
        });

        resView.setTag(pd);
        valueTvList.put(pd, resView);

        return resView;
    }

    private void showMultiListDialog(final PropertyConditon pd, final List<Dictionary> subDicList, final TextView tv_value, final TextView tv_value_edit) {
        ListDialog.show(getActivity(), subDicList, -1, new ListDialog.OnSelectedListener() {
            @Override
            public void onSelected(int pos) {
                Dictionary dic = subDicList.get(pos);
                if (dic.getSubDictionary() == null || dic.getSubDictionary().size() == 0) {
                    pd.setCurDic(dic);
                    tv_value.setText(dic.getValue());
                    tv_value_edit.setText(tv_value.getText());
                } else {
                    showMultiListDialog(pd, dic.getSubDictionary(), tv_value, tv_value_edit);
                }
            }
        });
    }

    private void showDateDialog(final TextView view) {
        final Calendar selectedDate = DateUtil.parseDateStr(view.getText().toString(), "yyyy-MM-dd");
        final DatePickerDialog dateDlg = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {

                    }
                },
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH));
        //手动设置按钮
        dateDlg.setButton(DialogInterface.BUTTON_POSITIVE, "完成", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //通过mDialog.getDatePicker()获得dialog上的DatePicker组件，然后可以获取日期信息
                DatePicker datePicker = dateDlg.getDatePicker();
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();
                selectedDate.set(year, month, day);
                view.setText(DateUtil.formatDate(selectedDate, "yyyy-MM-dd"));
            }
        });
        //取消按钮，如果不需要直接不设置即可
        dateDlg.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", (DialogInterface.OnClickListener)null);
        DatePicker datePicker = dateDlg.getDatePicker();
        datePicker.setCalendarViewShown(false);
        dateDlg.show();
    }

    private Dictionary getDictionaryByCode(List<Dictionary> contentList, String code) {
        if (contentList == null || contentList.size() == 0) {
            return null;
        }
        Dictionary dicObject = null;
        for (Dictionary dic : contentList) {
            if (dic.getCode().equals(code)) {
                dicObject = dic;
                break;
            }
            dicObject = getDictionaryByCode(dic.getSubDictionary(), code);
            if (dicObject != null) {
                break;
            }
        }
        return dicObject;
    }

    public void setPcListDetail(List<PropertyConditon> pcListDetail) {
        this.pcListDetail = pcListDetail;
    }

    public void setListener(OnPropertyConditionListener mListener) {
        this.mListener = mListener;
    }

    public interface OnPropertyConditionListener{
    }
}
