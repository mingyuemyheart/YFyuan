package com.moft.xfapply.activity.logic;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.adapter.NavAdapter;
import com.moft.xfapply.activity.bussiness.GeomEleBussiness;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.base.ViewLogic;
import com.moft.xfapply.model.base.IGeomElementInfo;
import com.moft.xfapply.model.common.ComLocation;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.common.EleCondition;
import com.moft.xfapply.model.common.PropertyDes;
import com.moft.xfapply.model.entity.DataVerifyDBInfo;
import com.moft.xfapply.model.entity.EnterpriseFourceDBInfo;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.utils.DictionaryUtil;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.utils.Utils;
import com.moft.xfapply.widget.dialog.CheckAlertDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static com.moft.xfapply.activity.logic.PropertyLogic.MAP_POS;

public abstract class DetailLogic extends ViewLogic {
    public DetailLogic(View v, Activity a) {
        super(v, a);
    }

    protected OnDetailLogicListener mListener = null;
    private Map<String, Class<?>> classMap = null;

    protected Map<String, Class<?>> mediaClassMap = null;
    private PopupWindow popOptionNav = null;
    protected View popup = null;

    protected String mKey = ""; //如果是查看数据，mkey为uuid; 如果是查看历史数据，mkey为snapshotUuid
    protected String mType = "";
    protected String mCityCode = "";
    protected boolean isHistory = false;

    protected boolean isEditable = false;
    protected String operType = "";

    protected PropertyLogic mCurPropertyLogic = null;

    protected HashMap<String, ComLocation> mComLocationMap = new HashMap<>();

    protected IGeomElementInfo mGeomEleDBInfo = null;
    private DataVerifyDBInfo verifyInfo = null;

    protected CameraLogic mCurCameraLogic = null;

    protected LinearLayout ll_tab = null;
    protected LinearLayout ll_content = null;

    protected abstract void onInited(IGeomElementInfo info);
    protected abstract void onInitData(IGeomElementInfo obj);
    protected abstract void onInitDataNew(IGeomElementInfo obj);
    protected abstract void onPreInitView();
    protected abstract void onInitView();
    protected abstract void onPropertyInit();
    protected abstract void onInitPopNav();
    protected abstract void onSelectNavOperation(String code);

    public IGeomElementInfo getGeomEleDBInfo() {
        return mGeomEleDBInfo;
    }

    public boolean init() {
        boolean ret = true;
        classMap = GeomEleBussiness.getInstance().getElementClsMap();
        mediaClassMap = GeomEleBussiness.getInstance().getMediaClsMap();
        ll_tab = (LinearLayout) getView().findViewById(R.id.ll_tab);
        ll_content = (LinearLayout) getView().findViewById(R.id.ll_content);

        if (StringUtil.isEmpty(mKey)) {
            mKey = UUID.randomUUID().toString();
            initDataNew();
        } else {
            ret = initData();
        }
        if(ret) {
            onInited(mGeomEleDBInfo);
        }
        return ret;
    }

    protected void createComLocation(IGeomElementInfo info) {
        ComLocation cl = new ComLocation();
        cl.setLat(Utils.convertToDouble(info.getLatitude()));
        cl.setLng(Utils.convertToDouble(info.getLongitude()));
        cl.setName(info.getName());
        cl.setEleUuid(info.getUuid());
        mComLocationMap.put(info.getUuid(), cl);
    }

    private boolean initData() {
        return initData(mCityCode);
    }

    private boolean initData(String cityCode) {
        //修改数据更新后，当前View不刷新问题
        mCurPropertyLogic = null;
        boolean ret = false;
        EleCondition ec = new EleCondition();
        ec.setUuid(mKey);
        ec.setType(mType);
        ec.setCityCode(cityCode);
        ec.setHistory(isHistory);

        mGeomEleDBInfo = GeomEleBussiness.getInstance().getSpecGeomEleInfoByUuid(ec);

        if (mGeomEleDBInfo != null) {
            onInitData(mGeomEleDBInfo);
            ret = true;
        }
        return ret;
    }

    private void initDataNew() {
        isEditable = true;
        operType = AppDefs.ActionType.NEW.toString();
        mCityCode = LvApplication.getInstance().getCityCode();

        try {
            if (!classMap.containsKey(mType)) {
                return;
            }

            mGeomEleDBInfo = (IGeomElementInfo) classMap.get(mType).newInstance();

            mGeomEleDBInfo.setUuid(mKey);
            mGeomEleDBInfo.setEleType(mType);
            mGeomEleDBInfo.setBelongtoGroup(mCityCode);

            onInitDataNew(mGeomEleDBInfo);
        } catch (Exception e) {

        }
    }

    public void initView() {
        onPreInitView();

        initProperty();

        onInitView();

        onInitPopNav();
    }

    protected void initProperty() {
        ll_content.removeAllViews();

        onPropertyInit();
    }

    protected void setOptionNav(final List<Dictionary> contList) {
        popup = getActivity().getLayoutInflater().inflate(R.layout.popup_option_nav, null);

        final ListView list = (ListView) popup.findViewById(R.id.list);

        NavAdapter adapter = new NavAdapter(getActivity(), contList, -1);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Dictionary sel = contList.get(position);
                onSelectNavOperation(sel.getCode());

                hidePopNav();
            }
        });
    }

    public void doMore() {
        showPopNav();
    }

    public void doSave() {

    }

    public void doSubmit() {

    }

    public void getEntityData() {
        GeomEleBussiness.getInstance().setListener(new GeomEleBussiness.OnGeomEleListener() {
            @Override
            public void onSuccess(String cid) {
                if (!"DETAIL_LOGIC".equals(cid)) {
                    return;
                }
                initData(mCityCode);

                if (mListener != null) {
                    mListener.onRefreshSuccess();
                }
            }
            @Override
            public void onFailure(String msg, String cid) {
                if (!"DETAIL_LOGIC".equals(cid)) {
                    return;
                }

                Toast.makeText(getActivity(), "无效的数据", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });
        GeomEleBussiness.getInstance().updateGeomElement(mType, mKey, "DETAIL_LOGIC");
    }

    public void doRefresh() {
        GeomEleBussiness.getInstance().setListener(new GeomEleBussiness.OnGeomEleListener() {
            @Override
            public void onSuccess(String cid) {
                initData(mCityCode);

                if (mListener != null) {
                    mListener.onRefreshSuccess();
                }
            }
            @Override
            public void onFailure(String msg, String cid) {
                if (mListener != null) {
                    mListener.onRefreshFailure();
                }
            }
        });
        GeomEleBussiness.getInstance().updateGeomElement(mGeomEleDBInfo.getEleType(), mGeomEleDBInfo.getUuid(), null);
    }

    public void doEdit() {

    }

    public void doDelete() {

    }

    public void doFinish(Boolean neededMapChange) {
        if (neededMapChange) {
            ComLocation cl = mComLocationMap.get(mGeomEleDBInfo.getUuid());
            if (cl == null || cl.isInvalid()) {
                Toast.makeText(getActivity(), "无效地址！", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent();
            intent.putExtra("neededMapChange", neededMapChange);
            intent.putExtra("CompElement", mGeomEleDBInfo);
            intent.putExtra("city", mCityCode);
            getActivity().setResult(RESULT_OK, intent);
        }

        getActivity().finish();
    }

    protected void addPropertyDesMapPos(List<PropertyDes> list, String uuid) {
        PropertyDes pro = getPropertyDesMapPos(uuid);
        if(pro != null) {
            list.add(pro);
        }
    }

    private PropertyDes getPropertyDesMapPos(String uuid) {
        PropertyDes pd = null;
        if(!AppDefs.CompEleType.FIRE_VEHICLE.toString().equals(mType) &&
                !AppDefs.CompEleType.EQUIPMENT.toString().equals(mType) &&
                !AppDefs.CompEleType.EXTINGUISHING_AGENT.toString().equals(mType)) {
            ComLocation cl = mComLocationMap.get(uuid);

            pd = new PropertyDes(
                    "地图定位", cl.getDesc(), PropertyDes.TYPE_MAP_POS);
            pd.setEntityUuid(uuid);
        }
        return pd;
    }

    protected void doGetMapPos(ComLocation cl) {
        if (mCurPropertyLogic == null || mCurPropertyLogic.getPdListDetail() == null) {
            return;
        }

        PropertyDes curPd = null;
        for (PropertyDes pd : mCurPropertyLogic.getPdListDetail()) {
            if (pd.getType() == PropertyDes.TYPE_MAP_POS &&
                    cl.getEleUuid().equals(pd.getEntityUuid())) {
                curPd = pd;
                break;
            }
        }
        if (curPd == null) {
            return;
        }

        ComLocation curCl = mComLocationMap.get(curPd.getEntityUuid());
        if (curCl == null) {
            curCl = new ComLocation();
            curCl.setEleUuid(curPd.getEntityUuid());
            mComLocationMap.put(curPd.getEntityUuid(), curCl);
        }
        curCl.setLat(cl.getLat());
        curCl.setLng(cl.getLng());
        curCl.setAddress(cl.getAddress());

        curPd.setValue(curCl.getDesc());
        mCurPropertyLogic.updateTextValue(curPd);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MAP_POS) {
            if (!(data.hasExtra("ComLocation"))) {
                return;
            }
            doGetMapPos((ComLocation)data.getSerializableExtra("ComLocation"));
        } else if (requestCode == CameraLogic.PHOTO_REQUEST_TAKEPHOTO) {
            if (mCurCameraLogic != null) {
                mCurCameraLogic.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    public boolean isChanged() {
        return false;
    }

    public String getTypeName() {
        String typeName = DictionaryUtil.getValueByCode(mType,
                LvApplication.getInstance().getEleTypeDicList());
        if (StringUtil.isEmpty(typeName)) {
            typeName = "详情";
            if (mGeomEleDBInfo != null && mGeomEleDBInfo instanceof EnterpriseFourceDBInfo) {
                EnterpriseFourceDBInfo ef = (EnterpriseFourceDBInfo)mGeomEleDBInfo;
                typeName = DictionaryUtil.getValueByCode(mType + ef.getType(),
                        LvApplication.getInstance().getEleTypeDicList());
            }
        }
        return typeName;
    }

    public boolean isSaveDisable() {
        return !AppDefs.ActionType.NEW.toString().equals(operType);
    }

    public boolean isRefreshDisable() {
        return isHistory() || isEditable;
    }

    public boolean isEditDisable() {
        return AppDefs.ActionType.NEW.toString().equals(operType) ||
                AppDefs.ActionType.DELETE.toString().equals(operType);
    }

    protected void showPopNav() {
        if (popOptionNav == null || !popOptionNav.isShowing()) {
            RelativeLayout rl_nav = (RelativeLayout) getActivity().getWindow().
                    getDecorView().findViewById(R.id.rl_nav);

            popOptionNav = new PopupWindow(popup, 320,
                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
            popOptionNav.setOutsideTouchable(true);
            // 实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0000000000);
            popOptionNav.setBackgroundDrawable(dw);
            popOptionNav.showAsDropDown(rl_nav);
            popOptionNav.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    popOptionNav = null;
                }
            });
        } else {
            hidePopNav();
        }
    }

    private void hidePopNav() {
        if (popOptionNav != null && popOptionNav.isShowing()) {
            popOptionNav.dismiss();
            popOptionNav = null;
        }
    }

    protected void showUpdateConfirmDialog() {
        if (LvApplication.getInstance().getIsUpdateDataConfirm()) {
            String content = "本条数据不是最新数据，请确认是否更新最新数据？";

            CheckAlertDialog.show(getActivity(), content, new CheckAlertDialog.OnDoingListener() {
                @Override
                public void onOK(boolean isChecked) {
                    doRefresh();
                    LvApplication.getInstance().setIsUpdateDataConfirm(!isChecked);
                    if (isChecked) {
                        LvApplication.getInstance().setIsDoUpdateData(true);
                    }
                }

                @Override
                public void onNG(boolean isChecked) {
                    LvApplication.getInstance().setIsUpdateDataConfirm(!isChecked);
                    if (isChecked) {
                        LvApplication.getInstance().setIsDoUpdateData(false);
                    }
                }
            });
        }
    }

    public void destroy() {
        GeomEleBussiness.getInstance().setListener(null);
    }

    public void setKey(String mKey) {
        this.mKey = mKey;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public String getCityCode() {
        return mCityCode;
    }

    public void setCityCode(String mCityCode) {
        this.mCityCode = mCityCode;
    }

    private boolean isHistory() {
        return isHistory;
    }

    public void setHistory(boolean history) {
        isHistory = history;
    }

    public void setListener(OnDetailLogicListener mListener) {
        this.mListener = mListener;
    }

    public interface OnDetailLogicListener{
        void onSaveSuccess();
        void onSubmitSuccess();
        void onSubmitFailure(String msg);
        void onRefreshSuccess();
        void onRefreshFailure();
        void onScrollButtom();
        void onRefresh();  // ID:886048 数据详细界面，不提示是否更新最新版本数据，自动更新最新数据。 2019-02-22
    }
}
