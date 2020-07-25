package com.moft.xfapply.activity.logic;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.MapPosActivity;
import com.moft.xfapply.activity.PickBigImagesActivity;
import com.moft.xfapply.activity.adapter.AttachPhotoAdapter;
import com.moft.xfapply.activity.adapter.PropertyPartAdapter;
import com.moft.xfapply.activity.bussiness.GeomEleBussiness;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.base.ViewLogic;
import com.moft.xfapply.model.KeyDiagram.Geometry;
import com.moft.xfapply.model.KeyDiagram.GeometryBD;
import com.moft.xfapply.model.KeyDiagram.GeometryPlot;
import com.moft.xfapply.model.KeyDiagram.PlaneDiagram;
import com.moft.xfapply.model.KeyDiagram.PmtInfo;
import com.moft.xfapply.model.KeyUnitJson.CompositionJsonInfo;
import com.moft.xfapply.model.KeyUnitJson.HazardMediaJsonInfo;
import com.moft.xfapply.model.KeyUnitJson.MaterialJsonInfo;
import com.moft.xfapply.model.KeyUnitJson.ProductJsonInfo;
import com.moft.xfapply.model.KeyUnitJson.StorageMediaJsonInfo;
import com.moft.xfapply.model.base.IMediaInfo;
import com.moft.xfapply.model.base.IPropertyPartInfo;
import com.moft.xfapply.model.common.ComLocation;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.common.Point;
import com.moft.xfapply.model.common.PropertyDes;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.utils.DateUtil;
import com.moft.xfapply.utils.DictionaryUtil;
import com.moft.xfapply.utils.GsonUtil;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.utils.Utils;
import com.moft.xfapply.widget.NoScrollGridView;
import com.moft.xfapply.widget.dialog.CustomAlertDialog;
import com.moft.xfapply.widget.dialog.DialogComposition;
import com.moft.xfapply.widget.dialog.DialogHazardMedia;
import com.moft.xfapply.widget.dialog.DialogMaterial;
import com.moft.xfapply.widget.dialog.DialogProduct;
import com.moft.xfapply.widget.dialog.DialogStorageMedia;
import com.moft.xfapply.widget.dialog.ListDialog;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.phoneNumber;

public class PropertyLogic extends ViewLogic {
    public PropertyLogic(View v, Activity a) {
        super(v, a);
    }

    private OnPropertyLogicListener mListener = null;

    private boolean isEditable = false;
    private List<PropertyDes> pdListDetail = null;
    private HashMap<PropertyDes, View> childViewMap = new HashMap<>();

    //property容器下所有元素的view
    private HashMap<PropertyDes, TextView> valueTvList = new HashMap<>();
    //property容器下所有元素的propertyList
    private HashMap<PropertyDes, List<IPropertyPartInfo>> propertyPartList = new HashMap<>();
    private boolean isOtherChanged = false;

    public static final int MAP_POS = 0;

    public void init() {
        isOtherChanged = false;

        refresh(pdListDetail);
    }

    public void clear() {
        valueTvList.clear();
    }

    public void refresh(List<PropertyDes> pdList) {
        LinearLayout parent = (LinearLayout)getView();

        View resView = null;
        for (PropertyDes pd : pdList) {
            if (childViewMap.containsKey(pd)) {
                resView = childViewMap.get(pd);
                try {
                    parent.addView(resView);
                } catch (Exception e) {
                    Log.d("PropertyLogic.refresh", "PropertyDes repeat");
                }
                continue;
            }

            switch (pd.getType()) {
                case PropertyDes.TYPE_NONE:
                    resView = getViewTypeNone(pd);
                    break;
                case PropertyDes.TYPE_TEXT:
                    resView = getViewTypeText(pd);
                    break;
                case PropertyDes.TYPE_EDIT:
                    resView = getViewTypeEdit(pd);
                    break;
                //ID:S869489 19-02-12 车辆信息以分类形式展示（分为基本类信息，作战用信息，管理用信息），同理包括器材、灭火剂（基本信息、详细信息） 王泉 开始
                case PropertyDes.TYPE_DETAIL:
                    resView = getViewTypeDetail(pd);
                    break;
                //ID:S869489 19-02-12 车辆信息以分类形式展示（分为基本类信息，作战用信息，管理用信息），同理包括器材、灭火剂（基本信息、详细信息） 王泉 结束
                case PropertyDes.TYPE_LIST:
                    resView = getViewTypeList(pd);
                    break;
                case PropertyDes.TYPE_PART_LIST:
                    resView = getViewTypePartList(pd);
                    break;
                case PropertyDes.TYPE_PHOTO:
                    resView = getViewTypePhoto(pd);
                    break;
                case PropertyDes.TYPE_MAP_POS:
                    resView = getViewTypeMapPos(pd);
                    break;
                case PropertyDes.TYPE_PIC_POS:
                    resView = getViewTypePicPos(pd);
                    break;
                case PropertyDes.TYPE_DATE:
                    resView = getViewTypeDate(pd);
                    break;
                case PropertyDes.TYPE_PHOTO_STATIC:
                    resView = getViewTypePhotoStatic(pd);
                    break;
                case PropertyDes.TYPE_ZTPMT_LIST:
                    resView = getViewTypePartPmt(pd);
                    break;
                default:
                    break;
            }

            if (resView != null) {
                //ID:S869489 19-02-12 车辆信息以分类形式展示（分为基本类信息，作战用信息，管理用信息），同理包括器材、灭火剂（基本信息、详细信息） 王泉 开始
                if(pd.getDetailType() != PropertyDes.PropertyDetail.NONE && pd.getType() != PropertyDes.TYPE_DETAIL) {
                    PropertyDes dp = getTypeDetailPd(pd.getEntityUuid(), pd.getDetailType());
                    if(dp != null) {
                        if ((boolean)dp.getValue()) {
                            resView.setVisibility(View.VISIBLE);
                        } else {
                            resView.setVisibility(View.GONE);
                        }
                    }
                }
                //ID:S869489 19-02-12 车辆信息以分类形式展示（分为基本类信息，作战用信息，管理用信息），同理包括器材、灭火剂（基本信息、详细信息） 王泉 结束
                parent.addView(resView);
                childViewMap.put(pd, resView);
            }
        }
    }

    //ID:S869489 19-02-12 车辆信息以分类形式展示（分为基本类信息，作战用信息，管理用信息），同理包括器材、灭火剂（基本信息、详细信息） 王泉
    private PropertyDes getTypeDetailPd(String uuid, PropertyDes.PropertyDetail detailType) {
        PropertyDes retPd = null;
        for(PropertyDes pd : pdListDetail) {
            if(!StringUtil.isEmpty(uuid) && uuid.equals(pd.getEntityUuid()) && detailType == pd.getDetailType() && pd.getType() == PropertyDes.TYPE_DETAIL) {
                retPd = pd;
                break;
            }
        }
        return retPd;
    }

    private void preSetTitle(TextView tv, String name) {
        int markIndex = name.indexOf("*");
        if (markIndex != -1) {
            String preFix = "<font color='#FF0000'>";
            String tailFix = "</font>";
            String content = name.substring(0, markIndex) + preFix
                    + name.substring(markIndex, markIndex + 1) + tailFix
                    + name.substring(markIndex+1);
            tv.setText((Html.fromHtml(content)));
        } else {
            tv.setText(name);
        }
    }

    public void updateTextValue(PropertyDes pd) {
        TextView tv_value = valueTvList.get(pd);
        if (tv_value != null) {
            tv_value.setText((String)pd.getValue());
        }
    }

    private View getViewTypePartPmt(PropertyDes pd) {
        View resView = null;

        int itemResourceId = R.layout.property_part_pmt;
        resView = getActivity().getLayoutInflater().inflate(itemResourceId, null);

        TextView tv_name = (TextView) resView.findViewById(R.id.tv_name);
        preSetTitle(tv_name, pd.getName());

        final List<Dictionary> contentList = getAttachDicList(pd.getAttachList());
        pd.setDicList(contentList);

        String value = "";
        final PmtInfo pmtInfo = (PmtInfo)pd.getValue();

        PlaneDiagram planeDiagram = getPlaneDiagram(pmtInfo.getPlaneDiagram());
        if (planeDiagram != null) {
            value = planeDiagram.getAttachUuid();
        }
        value = DictionaryUtil.getValueByCode(value, pd.getDicList());

        final TextView tv_value = (TextView) resView.findViewById(R.id.tv_value);
        tv_value.setText(value);
        ImageView iv_operation = (ImageView) resView.findViewById(R.id.iv_operation);
        iv_operation.setVisibility(View.VISIBLE);

        resView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() == null) {
                    return;
                }

                final PropertyDes pd = (PropertyDes) v.getTag();

                if (isEditable) {
                    ListDialog.show(getActivity(), contentList, pd.getCurIndex(), new ListDialog.OnSelectedListener() {
                        @Override
                        public void onSelected(int position) {
                            Dictionary dic = contentList.get(position);

                            if(pd.getCurIndex() != position) {
                                isOtherChanged = true;
                            }
                            if (!"-1".equals(dic.getCode())) {
                                pd.setCurDic(dic);
                                tv_value.setText(dic.getValue());
                            } else {
                                tv_value.setText("");
                            }
                            pd.setCurIndex(position);
                        }
                    });
                } else {
                    String attachName = tv_value.getText().toString();
                    if(!StringUtil.isEmpty(attachName)) {
                        ArrayList<IMediaInfo> mediaList =  getAttachListByName(attachName, pd.getAttachList());
                        GeometryPlot geometryPlot = mediaList.size() > 0 ? getGeometryPlot(mediaList.get(0), pmtInfo.getGeometryText()) : null;
                        doTakePhoto(mediaList, 0, geometryPlot);
                    }
                }
            }
        });

        resView.setTag(pd);
        valueTvList.put(pd, tv_value);

        return resView;
    }

    private GeometryPlot getGeometryPlot(IMediaInfo mediaInfo, String geometryText) {
        GeometryPlot info = null;
        List<Geometry> geometryList = jsonToGeometrys(geometryText);
        if (geometryList != null && geometryList.size() > 0) {
            for (Geometry item : geometryList) {
                if(item instanceof GeometryPlot) {
                    GeometryPlot geometryPlot = (GeometryPlot)item;
                    if (mediaInfo.getUuid().equals(geometryPlot.getBaseUuid())) {
                        info = geometryPlot;
                        break;
                    }
                }
            }
        }
        return info;
    }

    private List<Geometry> jsonToGeometrys(String geometryText) {
        List<Geometry> geometryList = new ArrayList<>();
        Gson gson = GsonUtil.create();
        JSONArray jsonArray = new JSONArray();
        if (!StringUtil.isEmpty(geometryText)) {
            try {
                jsonArray = new JSONArray(geometryText);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for (int index = 0; index < jsonArray.length(); ++index) {
            try {
                String geometry = jsonArray.getString(index);
                Geometry info = gson.fromJson(geometry, Geometry.class);
                if (AppDefs.CoordinateType.ONPLOT.toString().equals(info.getGeometryType())) {
                    GeometryPlot plot = gson.fromJson(geometry, GeometryPlot.class);
                    if (plot != null) {
                        geometryList.add(plot);
                    }
                } else if (AppDefs.CoordinateType.BD_09.toString().equals(info.getGeometryType())) {
                    GeometryBD bd = gson.fromJson(geometry, GeometryBD.class);
                    if (bd != null) {
                        geometryList.add(bd);
                    }
                } else {
                    geometryList.add(info);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return geometryList;
    }

    private PlaneDiagram getPlaneDiagram(String value) {
        PlaneDiagram planeDiagram = null;
        //解析FloorPlanPosition的json数据
        try {
            Gson gson = GsonUtil.create();
            planeDiagram = gson.fromJson(value, PlaneDiagram.class);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return planeDiagram;
    }

    private List<Dictionary> getAttachDicList(List<IMediaInfo> attachList) {
        List<Dictionary> dicList = new ArrayList<>();
        if(attachList != null && attachList.size() > 0) {
            for(IMediaInfo item : attachList) {
                dicList.add(new Dictionary(item.getMediaDescription(), item.getUuid()));
            }
        }
        dicList.add(new Dictionary("无", "-1"));
        return dicList;
    }

    private ArrayList<IMediaInfo> getAttachListByName(String name, List<IMediaInfo> attachList) {
        ArrayList<IMediaInfo> retList = new ArrayList<>();
        if(attachList != null && attachList.size() > 0) {
            for(IMediaInfo item : attachList) {
                if(name.equals(item.getMediaDescription())) {
                    retList.add(item);
                    break;
                }
            }
        }
        return retList;
    }

    private static IMediaInfo getAttachByName(String name, List<IMediaInfo> attachList) {
        IMediaInfo attachInfo = null;
        if(attachList != null && attachList.size() > 0) {
            for(IMediaInfo item : attachList) {
                if(name.equals(item.getMediaDescription())) {
                    attachInfo = item;
                    break;
                }
            }
        }
        return attachInfo;
    }

    private void doTakePhoto(ArrayList<IMediaInfo> afList, int position, GeometryPlot geometryPlot) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), PickBigImagesActivity.class);
        //ID:886059 19-02-18 【数据】查看详细数据，有大量附件图片，会导致异常崩溃OOM。 王泉
        if(mListener != null) {
            intent.putExtra(PickBigImagesActivity.EXTRA_HISTORY, mListener.onGetIsHistory());
        }
        intent.putExtra(PickBigImagesActivity.EXTRA_DATA, afList);
        intent.putExtra(PickBigImagesActivity.EXTRA_CURRENT_PIC, position);
        intent.putExtra(PickBigImagesActivity.EXTRA_GEOMETRY_PLOT, geometryPlot);
        getActivity().startActivity(intent);
    }

    private View getViewTypeText(PropertyDes pd) {
        int itemResourceId = R.layout.property_view;
        final View resView = getActivity().getLayoutInflater().inflate(itemResourceId, null);

        TextView tv_name = (TextView) resView.findViewById(R.id.tv_name);
        preSetTitle(tv_name, pd.getName());

        final TextView tv_value = (TextView) resView.findViewById(R.id.tv_value);
        tv_value.setText(objConvertToStr(pd.getValue()));
        resView.setTag(pd);

        valueTvList.put(pd, tv_value);
        return resView;
    }

    private View getViewTypeEdit(final PropertyDes pd) {
        int itemResourceId = R.layout.property_view;
        if (isEditable) {
            itemResourceId = R.layout.property_edit;
        }

        final View resView = getActivity().getLayoutInflater().inflate(itemResourceId, null);

        TextView tv_name = (TextView) resView.findViewById(R.id.tv_name);
        preSetTitle(tv_name, pd.getName());

        final TextView tv_value = (TextView) resView.findViewById(R.id.tv_value);
        tv_value.setText(objConvertToStr(pd.getValue()));

        if (!isEditable) {
            if (pd.getName().indexOf("值班电话") != -1 ||
                    pd.getName().indexOf("联系方式") != -1) {
                if (!StringUtil.isEmpty(tv_value.getText().toString())) {
                    tv_value.setTextColor(Color.rgb(102, 153, 204));
                    tv_value.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_DIAL,
                                    Uri.parse("tel:" + tv_value.getText().toString()));
                            getActivity().startActivity(intent);
                        }
                    });
                }
            }
        }

        if (isEditable) {
            final ImageView iv_delete = (ImageView) resView.findViewById(R.id.iv_delete);
            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_value.setText("");
                }
            });

            EditText et_value = (EditText) tv_value;
            et_value.setSelection(et_value.getText().length());

            if (pd.getParamType() == Integer.class) {
                et_value.setInputType(InputType.TYPE_CLASS_NUMBER);
            } else if (pd.getParamType() == Double.class) {
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
        }

        resView.setTag(pd);
        valueTvList.put(pd, tv_value);
        return resView;
    }

    private View getViewTypeDate(PropertyDes pd) {
        View resView = null;

        int itemResourceId = R.layout.property_view;
        resView = getActivity().getLayoutInflater().inflate(itemResourceId, null);

        TextView tv_name = (TextView) resView.findViewById(R.id.tv_name);
        preSetTitle(tv_name, pd.getName());

        final TextView tv_value = (TextView) resView.findViewById(R.id.tv_value);

        tv_value.setText(DateUtil.format((Date)pd.getValue(), "yyyy-MM-dd"));
        resView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (!isEditable || v.getTag() == null) {
                    return;
                }

                final PropertyDes pd = (PropertyDes)v.getTag();
                showDateDialog(tv_value);
            }
        });

        resView.setTag(pd);
        valueTvList.put(pd, tv_value);
        return resView;
    }

    private View getViewTypeList(PropertyDes pd) {
        int itemResourceId = R.layout.property_list;
        final View resView = getActivity().getLayoutInflater().inflate(itemResourceId, null);

        TextView tv_name = (TextView) resView.findViewById(R.id.tv_name);
        preSetTitle(tv_name, pd.getName());

        final TextView tv_value = (TextView) resView.findViewById(R.id.tv_value);

        String value = objConvertToStr(pd.getValue());
        String tmpValue = DictionaryUtil.getValueByCode(value, pd.getDicList());
        if (!StringUtil.isEmpty(value) && StringUtil.isEmpty(tmpValue)) {
        } else {
            value = tmpValue;
        }

        if (StringUtil.isEmpty(value)) {
            value = objConvertToStr(pd.getValue());
        }
        tv_value.setText(value);

        resView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (!isEditable || v.getTag() == null) {
                    return;
                }

                final PropertyDes pd = (PropertyDes)v.getTag();

                final List<Dictionary> contentList = pd.getDicList();
                final TreeLogic treeLogic = new TreeLogic(getView(), getActivity());
                if(contentList != null && contentList.size() == 1) {
                    treeLogic.setDefaultExpand(true);
                }
                treeLogic.setListener(new TreeLogic.OnSelectedListener() {
                    @Override
                    public void onSelected(String code) {
                        final Dictionary dic = getDictionaryByCode(contentList, code);
                        if (dic != null) {
                            pd.setCurDic(dic);

                            tv_value.setText(dic.getValue());
                            //解决数据字典同名，code不一致问题，最终保存code错误。
                            tv_value.setTag(dic.getCode());
                            treeLogic.hide();
                        }
                    }

                    @Override
                    public void onSelectedParentNode(String code) {
                        final Dictionary dic = getDictionaryByCode(contentList, code);
                        if (dic != null) {
                            pd.setCurDic(dic);

                            tv_value.setText(dic.getValue());
                            //解决数据字典同名，code不一致问题，最终保存code错误。
                            tv_value.setTag(dic.getCode());
                            treeLogic.hide();
                        }
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
        valueTvList.put(pd, tv_value);

        return resView;
    }

    private View getViewTypePartList(PropertyDes pd) {
        View resView = null;

        int itemResourceId = R.layout.property_part_list;
        resView = getActivity().getLayoutInflater().inflate(itemResourceId, null);

        RelativeLayout rl_title = (RelativeLayout) resView.findViewById(R.id.rl_title);
        TextView tv_name = (TextView) resView.findViewById(R.id.tv_name);
        ImageView iv_add = (ImageView) resView.findViewById(R.id.iv_add);
        final ListView list = (ListView)resView.findViewById(R.id.list);
        final List<IPropertyPartInfo> partList = jsonConvertToList(pd.getName(), objConvertToStr(pd.getValue()));
        final PropertyPartAdapter adapter = new PropertyPartAdapter(getActivity(), partList);
        final String title = pd.getName();

        preSetTitle(tv_name, title);
        list.setAdapter(adapter);
        setListViewHeightBasedOnChildren(list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                IPropertyPartInfo info = partList.get(position);

                if(title.equals(Constant.KEY_UNIT_JSON_TITLE_ZZZC)) {
                    showDialogComposition(title, partList, adapter, list, (CompositionJsonInfo)info, false);
                } else if(title.equals(Constant.KEY_UNIT_JSON_TITLE_ZZYL)) {
                    showDialogMaterial(title, partList, adapter, list, (MaterialJsonInfo)info, false);
                } else if(title.equals(Constant.KEY_UNIT_JSON_TITLE_ZZCW)) {
                    showDialogProduct(title, partList, adapter, list, (ProductJsonInfo)info, false);
                } else if(title.equals(Constant.KEY_UNIT_JSON_TITLE_CCJZ)) {
                    showDialogStorageMedia(title, partList, adapter, list, (StorageMediaJsonInfo)info, false);
                } else if(title.equals(Constant.KEY_UNIT_JSON_TITLE_WXJZ)) {
                    showDialogHazardMedia(title, partList, adapter, list, (HazardMediaJsonInfo)info, false);
                }
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (isEditable) {
                    String content = "请确认是否删除本记录？";
                    final int pos = position;
                    CustomAlertDialog.show(getActivity(), content, new CustomAlertDialog.OnDoingListener() {

                        @Override
                        public void onOK() {
                            isOtherChanged = true;
                            partList.remove(pos);
                            adapter.notifyDataSetChanged();
                            setListViewHeightBasedOnChildren(list);
                        }

                        @Override
                        public void onNG() {
                        }
                    });
                }
                return true;
            }
        });

        if (isEditable) {
            iv_add.setVisibility(View.VISIBLE);

            rl_title.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(title.equals(Constant.KEY_UNIT_JSON_TITLE_ZZZC)) {
                        showDialogComposition(title, partList, adapter, list, new CompositionJsonInfo(), true);
                    } else if(title.equals(Constant.KEY_UNIT_JSON_TITLE_ZZYL)) {
                        showDialogMaterial(title, partList, adapter, list, new MaterialJsonInfo(), true);
                    } else if(title.equals(Constant.KEY_UNIT_JSON_TITLE_ZZCW)) {
                        showDialogProduct(title, partList, adapter, list, new ProductJsonInfo(), true);
                    } else if(title.equals(Constant.KEY_UNIT_JSON_TITLE_CCJZ)) {
                        showDialogStorageMedia(title, partList, adapter, list, new StorageMediaJsonInfo(), true);
                    } else if(title.equals(Constant.KEY_UNIT_JSON_TITLE_WXJZ)) {
                        showDialogHazardMedia(title, partList, adapter, list, new HazardMediaJsonInfo(), true);
                    }
                }
            });
        } else {
            iv_add.setVisibility(View.GONE);
        }

        resView.setTag(pd);
        propertyPartList.put(pd, partList);
        return resView;
    }

    private void showDialogComposition(String title, final List<IPropertyPartInfo> partList,
                                       final PropertyPartAdapter adapter, final ListView list, final CompositionJsonInfo info, final boolean isAdd) {
        DialogComposition.show(getActivity(), isEditable, title, info, new DialogComposition.OnEditDoingListener() {
            @Override
            public void onOK() {
                if(isAdd) {
                    partList.add(info);
                    setListViewHeightBasedOnChildren(list);
                }
                adapter.notifyDataSetChanged();
                isOtherChanged = true;
            }

            @Override
            public void onNG() {

            }
        });
    }

    private void showDialogMaterial(String title, final List<IPropertyPartInfo> partList,
                                    final PropertyPartAdapter adapter, final ListView list, final MaterialJsonInfo info, final boolean isAdd) {
        DialogMaterial.show(getActivity(), isEditable, title, info, new DialogComposition.OnEditDoingListener() {
            @Override
            public void onOK() {
                if(isAdd) {
                    partList.add(info);
                    setListViewHeightBasedOnChildren(list);
                }
                adapter.notifyDataSetChanged();
                isOtherChanged = true;
            }

            @Override
            public void onNG() {

            }
        });
    }

    private void showDialogProduct(String title, final List<IPropertyPartInfo> partList,
                                   final PropertyPartAdapter adapter, final ListView list, final ProductJsonInfo info, final boolean isAdd) {
        DialogProduct.show(getActivity(), isEditable, title, info, new DialogComposition.OnEditDoingListener() {
            @Override
            public void onOK() {
                if(isAdd) {
                    partList.add(info);
                    setListViewHeightBasedOnChildren(list);
                }
                adapter.notifyDataSetChanged();
                isOtherChanged = true;
            }

            @Override
            public void onNG() {

            }
        });
    }

    private void showDialogStorageMedia(String title, final List<IPropertyPartInfo> partList,
                                        final PropertyPartAdapter adapter, final ListView list, final StorageMediaJsonInfo info, final boolean isAdd) {
        DialogStorageMedia.show(getActivity(), isEditable, title, info, new DialogComposition.OnEditDoingListener() {
            @Override
            public void onOK() {
                if(isAdd) {
                    partList.add(info);
                    setListViewHeightBasedOnChildren(list);
                }
                adapter.notifyDataSetChanged();
                isOtherChanged = true;
            }

            @Override
            public void onNG() {

            }
        });
    }

    private void showDialogHazardMedia(String title, final List<IPropertyPartInfo> partList,
                                       final PropertyPartAdapter adapter, final ListView list, final HazardMediaJsonInfo info, final boolean isAdd) {
        DialogHazardMedia.show(getActivity(), isEditable, title, info, new DialogComposition.OnEditDoingListener() {
            @Override
            public void onOK() {
                if(isAdd) {
                    partList.add(info);
                    setListViewHeightBasedOnChildren(list);
                }
                adapter.notifyDataSetChanged();
                isOtherChanged = true;
            }

            @Override
            public void onNG() {

            }
        });
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private List<IPropertyPartInfo> jsonConvertToList(String name, String value) {
        List<IPropertyPartInfo> list = new ArrayList<>();
        try {
            if (!StringUtil.isEmpty(value)) {
                Gson gson = GsonUtil.create();
                if (Constant.KEY_UNIT_JSON_TITLE_ZZZC.toString().equals(name)) {
                    Type listType = new TypeToken<List<CompositionJsonInfo>>() {
                    }.getType();
                    List<CompositionJsonInfo> jsonList = gson.fromJson(value, listType);
                    list = convertToPropertyPartList(jsonList);
                } else if (Constant.KEY_UNIT_JSON_TITLE_ZZYL.toString().equals(name)) {
                    Type listType = new TypeToken<List<MaterialJsonInfo>>() {
                    }.getType();
                    List<MaterialJsonInfo> jsonList = gson.fromJson(value, listType);
                    list = convertToPropertyPartList(jsonList);
                } else if (Constant.KEY_UNIT_JSON_TITLE_ZZCW.toString().equals(name)) {
                    Type listType = new TypeToken<List<ProductJsonInfo>>() {
                    }.getType();
                    List<ProductJsonInfo> jsonList = gson.fromJson(value, listType);
                    list = convertToPropertyPartList(jsonList);
                } else if (Constant.KEY_UNIT_JSON_TITLE_CCJZ.toString().equals(name)) {
                    Type listType = new TypeToken<List<StorageMediaJsonInfo>>() {
                    }.getType();
                    List<StorageMediaJsonInfo> jsonList = gson.fromJson(value, listType);
                    list = convertToPropertyPartList(jsonList);
                } else if (Constant.KEY_UNIT_JSON_TITLE_WXJZ.toString().equals(name)) {
                    Type listType = new TypeToken<List<HazardMediaJsonInfo>>() {
                    }.getType();
                    List<HazardMediaJsonInfo> jsonList = gson.fromJson(value, listType);
                    list = convertToPropertyPartList(jsonList);
                }
            }
        } catch (Exception e) {

        }
        return list;
    }

    private <T> List<IPropertyPartInfo> convertToPropertyPartList(List<T> mediaInfos) {
        List<IPropertyPartInfo> retInfos = new ArrayList<>();

        if(mediaInfos != null && mediaInfos.size() > 0) {
            for(T item : mediaInfos) {
                if(item != null) {
                    retInfos.add((IPropertyPartInfo) item);
                }
            }
        }
        return retInfos;
    }

    private void showMultiListDialog(final PropertyDes pd, final List<Dictionary> subDicList, final TextView tv_value, final TextView tv_value_edit) {
        ListDialog.show(getActivity(), subDicList, -1, new ListDialog.OnSelectedListener() {
            @Override
            public void onSelected(int pos) {
                Dictionary dic = subDicList.get(pos);
                if (dic.getSubDictionary() == null || dic.getSubDictionary().size() == 0) {
                    pd.setCurDic(dic);
                    tv_value.setText(dic.getValue());
                    //解决数据字典同名，code不一致问题，最终保存code错误。
                    tv_value.setTag(dic.getCode());
                    tv_value_edit.setText(tv_value.getText());
                    //解决数据字典同名，code不一致问题，最终保存code错误。
                    tv_value_edit.setTag(dic.getCode());
                } else {
                    showMultiListDialog(pd, dic.getSubDictionary(), tv_value, tv_value_edit);
                }
            }
        });
    }

    private View getViewTypeNone(PropertyDes pd) {
        View resView = null;

        int itemResourceId = R.layout.property_none;
        resView = getActivity().getLayoutInflater().inflate(itemResourceId, null);

        TextView tv_name = (TextView) resView.findViewById(R.id.tv_name);
        preSetTitle(tv_name, pd.getName());

        ImageView iv_delete = (ImageView) resView.findViewById(R.id.iv_delete);
        iv_delete.setTag(pd);
        if (isEditable) {
            iv_delete.setVisibility(View.VISIBLE);
        } else {
            iv_delete.setVisibility(View.GONE);
        }
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String content = "确认要删除本条数据吗？";
                CustomAlertDialog.show(getActivity(), content,
                        new CustomAlertDialog.OnDoingListener() {
                    @Override
                    public void onOK() {
                        PropertyDes pd = (PropertyDes) v.getTag();
                        if (pd == null) {
                            return;
                        }
                        childViewMap.remove(pd);

                        if (mListener != null) {
                            mListener.onDelete(pd);
                        }
                    }

                    @Override
                    public void onNG() {
                    }
                });
            }
        });

        return resView;
    }

    //ID:S869489 19-02-12 车辆信息以分类形式展示（分为基本类信息，作战用信息，管理用信息），同理包括器材、灭火剂（基本信息、详细信息） 王泉
    private View getViewTypeDetail(PropertyDes pd) {
        View resView = null;

        int itemResourceId = R.layout.property_detail;
        resView = getActivity().getLayoutInflater().inflate(itemResourceId, null);

        TextView tv_name = (TextView) resView.findViewById(R.id.tv_name);
        preSetTitle(tv_name, pd.getName());

        final ImageView iv_detail_expanding = (ImageView) resView.findViewById(R.id.iv_detail_expanding);
        final ImageView iv_detail_collapsing = (ImageView) resView.findViewById(R.id.iv_detail_collapsing);
        resView.setTag(pd);
        if((boolean)pd.getValue()) {
            iv_detail_collapsing.setVisibility(View.VISIBLE);
            iv_detail_expanding.setVisibility(View.GONE);
        } else {
            iv_detail_collapsing.setVisibility(View.GONE);
            iv_detail_expanding.setVisibility(View.VISIBLE);
        }

        resView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PropertyDes pd = (PropertyDes) v.getTag();
                pd.setValue(!(boolean)pd.getValue());
                if((boolean)pd.getValue()) {
                    iv_detail_collapsing.setVisibility(View.VISIBLE);
                    iv_detail_expanding.setVisibility(View.GONE);
                } else {
                    iv_detail_collapsing.setVisibility(View.GONE);
                    iv_detail_expanding.setVisibility(View.VISIBLE);
                }
                if(pd.getDetailType() != PropertyDes.PropertyDetail.NONE) {
                    updateDetailView(pd.getEntityUuid(), pd.getDetailType(), (boolean)pd.getValue());
                }
            }
        });

        return resView;
    }

    //ID:S869489 19-02-12 车辆信息以分类形式展示（分为基本类信息，作战用信息，管理用信息），同理包括器材、灭火剂（基本信息、详细信息） 王泉
    private void updateDetailView(String uuid, PropertyDes.PropertyDetail detailType, boolean isVisible) {
        for( PropertyDes pd : pdListDetail) {
            if(!StringUtil.isEmpty(uuid) && uuid.equals(pd.getEntityUuid()) && detailType.equals(pd.getDetailType()) && pd.getType() != PropertyDes.TYPE_DETAIL) {
                View view = childViewMap.get(pd);
                if(view != null) {
                    if (isVisible) {
                        view.setVisibility(View.VISIBLE);
                    } else {
                        view.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    private View getViewTypePhoto(final PropertyDes pd) {
        View resView = null;

        final List<IMediaInfo> attachFileList = pd.getAttachList();
        if (attachFileList == null) {
            return resView;
        }

        if (!isEditable && attachFileList.size() == 0) {
            return resView;
        }

        int itemResourceId = R.layout.property_photo;
        resView = getActivity().getLayoutInflater().inflate(itemResourceId, null);

        TextView tv_name = (TextView) resView.findViewById(R.id.tv_name);
        if (!StringUtil.isEmpty(pd.getName())) {
            preSetTitle(tv_name, pd.getName());
        }

        final NoScrollGridView gv_photos = (NoScrollGridView)resView.findViewById(R.id.gv_photos);
        final AttachPhotoAdapter photoAdapter = new AttachPhotoAdapter(getActivity(),
                getActivity().getWindow().getDecorView());

        AttachPhotoAdapter.AttachFileAdapterListener listener =
                new AttachPhotoAdapter.AttachFileAdapterListener() {
            @Override
            public void onTakeAttach() {
                CameraLogic cameraLogic = new CameraLogic(getView(), (BaseActivity) getActivity());
                cameraLogic.setListener(new CameraLogic.OnCameraLogicListener() {
                    @Override
                    public void onPhoto(String path) {
                        isOtherChanged = true;

                        if (mListener != null) {
                            mListener.onAttach(pd, path);
                        }

                        photoAdapter.setAfList(attachFileList);
                        gv_photos.setAdapter(photoAdapter);
                    }
                });
                cameraLogic.init();

                if (mListener != null) {
                    mListener.onSetCameraLogic(cameraLogic);
                }
                cameraLogic.doPhoto();
            }

            @Override
            public void onRemoveAttach() {
                List<IMediaInfo> cgListDeleted = new ArrayList<IMediaInfo>();

                for (int i = 0; i < photoAdapter.getSelectCount(); i++) {
                    int sel = photoAdapter.getSelectItem(i);
                    cgListDeleted.add(attachFileList.get(sel));
                }

                for (IMediaInfo af : cgListDeleted) {
                    attachFileList.remove(af);
                }
                isOtherChanged = true;

                photoAdapter.setAfList(attachFileList);
                gv_photos.setAdapter(photoAdapter);
            }

            @Override
            public void onUpdateAttach(IMediaInfo info) {
                if (mListener != null) {
                    GeomEleBussiness.getInstance().updateAttachFile(mListener.onGetCityCode(), info, mListener.onGetIsHistory());
                }
            }

            //ID:886059 19-02-18 【数据】查看详细数据，有大量附件图片，会导致异常崩溃OOM。 王泉
            public void onTakePhoto(ArrayList<IMediaInfo> mediaList, int position) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), PickBigImagesActivity.class);
                if(mListener != null) {
                    intent.putExtra(PickBigImagesActivity.EXTRA_HISTORY, mListener.onGetIsHistory());
                }
                intent.putExtra(PickBigImagesActivity.EXTRA_DATA, mediaList);
                intent.putExtra(PickBigImagesActivity.EXTRA_CURRENT_PIC, position);
                getActivity().startActivity(intent);
            }
        };
        photoAdapter.setEditalbe(isEditable);
        photoAdapter.setListener(listener);
        photoAdapter.setAfList(attachFileList);

        gv_photos.setAdapter(photoAdapter);

        return resView;
    }

    private View getViewTypePhotoStatic(final PropertyDes pd) {
        View resView = null;

        final List<IMediaInfo> attachFileList = pd.getAttachList();
        if (attachFileList == null || attachFileList.size() == 0) {
            return resView;
        }

        int itemResourceId = R.layout.property_photo_static;
        resView = getActivity().getLayoutInflater().inflate(itemResourceId, null);

        final NoScrollGridView gv_photos = (NoScrollGridView)resView.findViewById(R.id.gv_photos);
        final AttachPhotoAdapter photoAdapter = new AttachPhotoAdapter(getActivity(),
                getActivity().getWindow().getDecorView());

        AttachPhotoAdapter.AttachFileAdapterListener listener =
                new AttachPhotoAdapter.AttachFileAdapterListener() {
                    @Override
                    public void onTakeAttach() {
                        CameraLogic cameraLogic = new CameraLogic(getView(), (BaseActivity) getActivity());
                        cameraLogic.setListener(new CameraLogic.OnCameraLogicListener() {
                            @Override
                            public void onPhoto(String path) {
                                isOtherChanged = true;

                                if (mListener != null) {
                                    mListener.onAttach(pd, path);
                                }

                                photoAdapter.setAfList(attachFileList);
                                gv_photos.setAdapter(photoAdapter);
                            }
                        });
                        cameraLogic.init();

                        if (mListener != null) {
                            mListener.onSetCameraLogic(cameraLogic);
                        }
                        cameraLogic.doPhoto();
                    }

                    @Override
                    public void onRemoveAttach() {
                        List<IMediaInfo> cgListDeleted = new ArrayList<IMediaInfo>();

                        for (int i = 0; i < photoAdapter.getSelectCount(); i++) {
                            int sel = photoAdapter.getSelectItem(i);
                            cgListDeleted.add(attachFileList.get(sel));
                        }

                        for (IMediaInfo af : cgListDeleted) {
                            attachFileList.remove(af);
                        }
                        isOtherChanged = true;

                        photoAdapter.setAfList(attachFileList);
                        gv_photos.setAdapter(photoAdapter);
                    }

                    @Override
                    public void onUpdateAttach(IMediaInfo info) {
                        if (mListener != null) {
                            GeomEleBussiness.getInstance().updateAttachFile(mListener.onGetCityCode(), info, mListener.onGetIsHistory());
                        }
                    }

                    //ID:886059 19-02-18 【数据】查看详细数据，有大量附件图片，会导致异常崩溃OOM。 王泉
                    public void onTakePhoto(ArrayList<IMediaInfo> mediaList, int position) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), PickBigImagesActivity.class);
                        if(mListener != null) {
                            intent.putExtra(PickBigImagesActivity.EXTRA_HISTORY, mListener.onGetIsHistory());
                        }
                        intent.putExtra(PickBigImagesActivity.EXTRA_DATA, mediaList);
                        intent.putExtra(PickBigImagesActivity.EXTRA_CURRENT_PIC, position);
                        getActivity().startActivity(intent);
                    }
                };
        photoAdapter.setEditalbe(isEditable);
        photoAdapter.setListener(listener);
        photoAdapter.setAfList(attachFileList);

        gv_photos.setAdapter(photoAdapter);

        return resView;
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
                isOtherChanged = true;
            }
        });
        //取消按钮，如果不需要直接不设置即可
        dateDlg.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", (DialogInterface.OnClickListener)null);
        DatePicker datePicker = dateDlg.getDatePicker();
        datePicker.setCalendarViewShown(false);
        dateDlg.show();
    }

    private View getViewTypeMapPos(PropertyDes pd) {
        View resView = null;

        int itemResourceId = R.layout.property_map_pos;
        resView = getActivity().getLayoutInflater().inflate(itemResourceId, null);

        TextView tv_name = (TextView) resView.findViewById(R.id.tv_name);
        preSetTitle(tv_name, pd.getName());

        TextView tv_value = (TextView) resView.findViewById(R.id.tv_value);
        tv_value.setText((String)pd.getValue());

        ImageView iv_operation = (ImageView) resView.findViewById(R.id.iv_operation);
        iv_operation.setVisibility(View.VISIBLE);

        resView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (v.getTag() == null) {
                    return;
                }

                PropertyDes pd = (PropertyDes)v.getTag();
                ComLocation cl = null;
                if (mListener != null) {
                    cl = mListener.onGetComLocation(pd);
                }

                Intent intent = new Intent(getActivity(), MapPosActivity.class);
                intent.putExtra("PropertyDes", pd);
                intent.putExtra("ComLocation", cl);
                intent.putExtra("isEditable", isEditable);
                getActivity().startActivityForResult(intent, MAP_POS);
            }
        });

        resView.setTag(pd);
        valueTvList.put(pd, tv_value);

        return resView;
    }

    private View getViewTypePicPos(PropertyDes pd) {
        View resView = null;
        return resView;
    }

    private String objConvertToStr(Object value) {
        String ret = "";
        if(value != null) {
            ret = value.toString();
        }
        return ret;
    }

    private static Object strConvertToObj(String value, Class<?> cls) {
        Object ret = null;
        if (cls == Integer.class) {
            if (!StringUtil.isEmpty(value)) {
                ret = Integer.valueOf(value);
            }
        } else if (cls == Double.class) {
            if (!StringUtil.isEmpty(value)) {
                ret = Double.valueOf(value);
            }
        } else if (cls == Boolean.class) {
            if (!StringUtil.isEmpty(value)) {
                ret = Boolean.valueOf(value);
            }
        } else if (cls == Date.class) {
            if (!StringUtil.isEmpty(value)) {
                ret = DateUtil.toDate(value, "yyyy-MM-dd");
            }
        } else {
            ret = value;
        }
        return ret;
    }

    public void setIsChanged(boolean changed) {
        isOtherChanged = changed;
    }

    public boolean isChanged() {
        if (isOtherChanged) {
            return true;
        }

        boolean isC = false;

        TextView tv = null;
        String value = "";
        for (PropertyDes pd : pdListDetail) {
            if (pd.getType() == PropertyDes.TYPE_EDIT ||
                    pd.getType() == PropertyDes.TYPE_LIST) {
                tv = valueTvList.get(pd);
                if (tv == null) {
                    continue;
                }
                value = tv.getText().toString().trim();
                String oldValue = "";
                if (pd.getType() == PropertyDes.TYPE_LIST) {
                    oldValue = DictionaryUtil.getValueByCode(
                            objConvertToStr(pd.getValue()), pd.getDicList());
                    if (StringUtil.isEmpty(oldValue)) {
                        oldValue = objConvertToStr(pd.getValue());
                    }
                } else {
                    oldValue = objConvertToStr(pd.getValue());
                }

                if (!checkEquals(oldValue, value, pd.getParamType())) {
                    isC = true;
                    break;
                }
            } else if(pd.getType() == PropertyDes.TYPE_PART_LIST) {
                List<IPropertyPartInfo> list = propertyPartList.get(pd);
                String oldValue = objConvertToStr(pd.getValue());
                String valueJson = null;
                if(list == null || list.size() == 0) {
                    valueJson = "";
                } else {
                    Gson gson = GsonUtil.create();
                    valueJson = gson.toJson(list);
                }
                if(!(StringUtil.isEmpty(valueJson) && StringUtil.isEmpty(value))) {
                    if (!valueJson.equals(objConvertToStr(oldValue))) {
                        isC = true;
                        break;
                    }
                }
            }
        }

        return isC;
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
    }

    public void setPdListDetail(List<PropertyDes> pdListDetail) {
        this.pdListDetail = pdListDetail;
    }

    public List<PropertyDes> getPdListDetail() {
        return this.pdListDetail;
    }

    public void setListener(OnPropertyLogicListener mListener) {
        this.mListener = mListener;
    }

    private boolean checkEquals(String oldVaule, String value, Class<?> cls) {
        boolean ret = false;
        if(cls == Double.class) {
            Double dOldValue = Utils.convertToDouble(oldVaule);
            Double dValue = Utils.convertToDouble(value);

            if(dValue != null) {
                ret = dValue.equals(dOldValue);
            }else if(dOldValue != null) {
                ret = dOldValue.equals(dValue);
            } else {
                ret = true;
            }
        } else {
            ret = oldVaule.equals(value);
        }
        return ret;
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

    public interface OnPropertyLogicListener{
        public void onSetCameraLogic(CameraLogic cl);
        public boolean onDelete(PropertyDes pd);
        public void onAttach(PropertyDes pd, String path);
        public ComLocation onGetComLocation(PropertyDes pd);
        public String onGetCityCode();
        public boolean onGetIsHistory();
    }
}
