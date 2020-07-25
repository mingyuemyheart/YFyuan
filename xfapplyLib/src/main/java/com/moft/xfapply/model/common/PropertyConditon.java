package com.moft.xfapply.model.common;

import com.moft.xfapply.utils.DictionaryUtil;
import com.moft.xfapply.utils.StringUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PropertyConditon implements Serializable {
    private String title;
    private String tableName;
    private String columnName;
    private Class columnType;
    private String values;
    private int type;
    private boolean isConvertDic = false;

    private List<Dictionary> dicList;
    private Dictionary curDic;
    private int curIndex;

    public static final int TYPE_NONE = 0;
    public static final int TYPE_LIST = 1;
    public static final int TYPE_EDIT = 2;
    public static final int TYPE_EDIT_NUMBER = 3;
    public static final int TYPE_DATE = 4;

    public PropertyConditon() {
        this.curIndex = -1;
        this.curDic = null;
    }

    public PropertyConditon(String title, String tableName, String columnName, Class columnType, int type) {
        this();
        this.title = title;
        this.tableName = tableName;
        this.columnName = columnName;
        this.columnType = columnType;
        this.type = type;
        this.isConvertDic = false;
    }

    public PropertyConditon(String title, String tableName, String columnName, Class columnType, int type, List<Dictionary> dicList, boolean isConvertDic) {
        this(title, tableName, columnName, columnType, type);
        this.dicList = dicList;
        this.isConvertDic = isConvertDic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Class getColumnType() {
        return columnType;
    }

    public void setColumnType(Class columnType) {
        this.columnType = columnType;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Dictionary> getDicList() {
        return dicList;
    }

    public void setDicList(List<Dictionary> dicList) {
        this.dicList = dicList;
    }

    public Dictionary getCurDic() {
        return curDic;
    }

    public void setCurDic(Dictionary curDic) {
        this.curDic = curDic;
    }

    public int getCurIndex() {
        return curIndex;
    }

    public void setCurIndex(int curIndex) {
        this.curIndex = curIndex;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    private String getSQL(String title, String value, String valueSub, boolean isListValue) {
        String strSQL = "";

        switch (type) {
            case TYPE_EDIT_NUMBER:
                if (!StringUtil.isEmpty(value)) {
                    strSQL = title;
                    if (columnType == Double.class || columnType == Integer.class) {
                        strSQL += " >= " + value;
                    } else {
                        strSQL += " like '%" + value + "%'";
                    }
                }
                if (!StringUtil.isEmpty(valueSub)) {
                    if (!StringUtil.isEmpty(value)) {
                        strSQL += " and " + title;
                    } else {
                        strSQL = title;
                    }
                    if (columnType == Double.class || columnType == Integer.class) {
                        strSQL += " <= " + valueSub;
                    } else {
                        strSQL += " like '%" + valueSub + "%'";
                    }
                }
                break;
            case TYPE_EDIT:
                if (!StringUtil.isEmpty(value)) {
                    strSQL = title;
                    if (columnType == String.class) {
                        strSQL += " like '%" + value + "%'";
                    } else {
                        strSQL += " = " + value;
                    }
                }
                break;
            case TYPE_LIST:
                if (!StringUtil.isEmpty(value)) {
                    if (isListValue) {
                        strSQL = title;
                        strSQL += " = '" + value + "'";
                    } else {
//                        String code = DictionaryUtil.getCodeByValue(value, dicList);
                        List<String> codes = DictionaryUtil.getChildDictionaryByValue(value, dicList);
                        if (codes != null && !codes.isEmpty()) {
                            strSQL = title;
                            strSQL += " in (" + StringUtil.list2String(codes) + ") ";
                        }
                    }
                }
                break;
            case TYPE_DATE:
                break;
            default:
                break;
        }

        return strSQL;
    }

public List<String> getColumnValue(String value) {
    List<String> values = new ArrayList<String>();
    switch (type) {
        case TYPE_LIST:
            List<String> codes= DictionaryUtil.getChildDictionaryByValue(value, dicList);
            if (codes != null && !codes.isEmpty()) {
                for (String code : codes) {
                    values.add(DictionaryUtil.getValueByCode(code, dicList));
                }
            }

            break;
        default:
            values.add(value);
            break;
    }

    return values;
}

    public String getSQL(String value, String valueSub) {
        String strSQL = "";

        if (columnName.contains(";;")) {
            String columns[] = columnName.split(";;");
            for (int i = 0; i < columns.length; i++) {
                String strTemp = getSQL(columns[i], value, valueSub, !isConvertDic);
                if (StringUtil.isEmpty(strTemp)) {
                    continue;
                }
                if (StringUtil.isEmpty(strSQL)) {
                    strSQL = strTemp;
                } else {
                    strSQL += " or " + strTemp;
                }
            }
        } else {
            strSQL = getSQL(columnName, value, valueSub, !isConvertDic);
        }

        return strSQL;
    }

    public String getSQLDesc(String value, String valueSub) {
        return getSQL(title, value, valueSub, true);
    }
}
