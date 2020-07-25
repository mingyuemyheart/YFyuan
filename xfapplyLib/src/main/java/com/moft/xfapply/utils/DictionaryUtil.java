package com.moft.xfapply.utils;


import com.moft.xfapply.model.common.Dictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangquan on 2017/4/20.
 */

public class DictionaryUtil {
    public static String getValueByCode(String type, String code, Map<String, List<Dictionary>> map) {
        List<Dictionary> list = map.get(type);
        String retValue = "";

        if(list != null) {
            retValue = getValueByCode(code, list);
        }
        return retValue;
    }

    public static String getCodeByValue(String type, String value, Map<String, List<Dictionary>> map) {
        List<Dictionary> list = map.get(type);
        String retValue = "";

        if(list != null) {
            retValue = getCodeByValue(value, list);
        }
        return retValue;
    }

    public static Dictionary getDictionaryByValue(String value, List<Dictionary> list) {
        Dictionary retDic = null;

        if (!StringUtil.isEmpty(value) && list != null) {
            for (Dictionary dic : list) {
                if (value.equals(dic.getValue())) {
                    retDic = dic;
                    break;
                } else {
                    List<Dictionary> subList = dic.getSubDictionary();
                    if(subList != null && subList.size() > 0) {
                        retDic = getDictionaryByValue(value, subList);
                        if(retDic != null) {
                            break;
                        }
                    }
                }
            }
        }

        return retDic;
    }

    public static Dictionary getDictionaryByCode(String code, List<Dictionary> list) {
        Dictionary retDic = null;

        if (!StringUtil.isEmpty(code) && list != null) {
            for (Dictionary dic : list) {
                if (code.equals(dic.getCode())) {
                    retDic = dic;
                    break;
                } else {
                    List<Dictionary> subList = dic.getSubDictionary();
                    if(subList != null && subList.size() > 0) {
                        retDic = getDictionaryByCode(code, subList);
                        if(retDic != null) {
                            break;
                        }
                    }
                }
            }
        }

        return retDic;
    }

    public static Dictionary getDictionaryById(String id, List<Dictionary> list) {
        Dictionary retDic = null;

        if (!StringUtil.isEmpty(id) && list != null) {
            for (Dictionary dic : list) {
                if (id.equals(dic.getId())) {
                    retDic = dic;
                    break;
                } else {
                    List<Dictionary> subList = dic.getSubDictionary();
                    if(subList != null && subList.size() > 0) {
                        retDic = getDictionaryById(id, subList);
                        if(retDic != null) {
                            break;
                        }
                    }
                }
            }
        }

        return retDic;
    }

    public static String getValueByCode(String code, List<Dictionary> list) {
        String value = "";

        if (!StringUtil.isEmpty(code) && list != null) {
            for (Dictionary dic : list) {
                if (code.equals(dic.getCode())) {
                    value = dic.getValue();
                    break;
                } else {
                    List<Dictionary> subList = dic.getSubDictionary();
                    if(subList != null && subList.size() > 0) {
                        value = getValueByCode(code, subList);
                        if(!StringUtil.isEmpty(value)) {
                            break;
                        }
                    }
                }
            }
        }

        return value;
    }

    public static String getCodeByValue(String value, List<Dictionary> list) {
        String code = "";

        if (!StringUtil.isEmpty(value) && list != null) {
            for (Dictionary dic : list) {
                if (value.equals(dic.getValue())) {
                    code = dic.getCode();
                    break;
                } else {
                    List<Dictionary> subList = dic.getSubDictionary();
                    if(subList != null && subList.size() > 0) {
                        code = getCodeByValue(value, subList);
                        if(!StringUtil.isEmpty(code)) {
                            break;
                        }
                    }
                }
            }
        }

        return code;
    }

    public static List<String> getChildDictionaryByValue(String value, List<Dictionary> list) {
        List<String> childCodes = new ArrayList<String>();
        Dictionary dic = getDictionaryByValue(value, list);

        if (dic != null) {
            childCodes.add(dic.getCode());
            List<Dictionary> tmp = dic.getSubDictionary();
            if (tmp != null && !tmp.isEmpty()) {
                addChildCode(childCodes, tmp);
            }
        }
        return childCodes;
    }

    private static void addChildCode(List<String> childCodes, List<Dictionary> dics) {
        if (dics != null) {
            for (Dictionary dic : dics) {
                childCodes.add(dic.getCode());
                if (dic.getSubDictionary() != null && !dic.getSubDictionary().isEmpty()) {
                    addChildCode(childCodes, dic.getSubDictionary());
                }
            }
        }
    }

}
