package com.moft.xfapply.activity.logic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.WsDetailActivity;
import com.moft.xfapply.activity.adapter.SearchMapAdapter;
import com.moft.xfapply.activity.bussiness.CommonBussiness;
import com.moft.xfapply.activity.bussiness.GeomEleBussiness;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.base.ViewLogic;
import com.moft.xfapply.model.entity.GeomElementDBInfo;
import com.moft.xfapply.model.entity.MapSearchInfo;
import com.moft.xfapply.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class SearchSuggestLogic extends ViewLogic implements
        OnGetSuggestionResultListener {
    public SearchSuggestLogic(View v, Activity a) {
        super(v, a);
    }

    private OnSearchMapLogicListener mListener = null;

    private View mViewSelf = null;
    private ListView listView;
    private SearchMapAdapter adapter = null;
    private List<MapSearchInfo> vrList = new ArrayList<>();
    private Map<String, MapSearchInfo> vrMap = new HashMap<>();

    private PoiSearch mPoiSearch = null;
    private SuggestionSearch mSuggestionSearch = null;
    private GeoCoder mGeoCoder = null;

    private SearchMapAdapter.OnItemSelectedListener mOnItemSelectedListener = null;
    private ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    private boolean isContinueCmd;
    private static final int REQUEST_OPERATION_RESULT = 0;

    public void init() {
        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();

        // 初始化建议搜索模块，注册建议搜索事件监听
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);

        mGeoCoder = GeoCoder.newInstance();

        mViewSelf = getActivity().getLayoutInflater().inflate(R.layout.map_search_suggest, null);

        LinearLayout ll_content = (LinearLayout)getView();
        ll_content.addView(mViewSelf);

        mOnItemSelectedListener = new SearchMapAdapter.OnItemSelectedListener() {
            @Override
            public void onItemClick(MapSearchInfo dto) {
                if (!dto.isGeoPosValid()) {
                    boolean isDeal = false;
                    if (Constant.MAP_SEARCH_FIRE.equals(dto.getType())) {
                        String extra = dto.getExtra();
                        if (!StringUtil.isEmpty(extra)) {
                            String[] exs = extra.split(Constant.OUTLINE_DIVIDER);
                            if (exs.length >= 3) {
                                isDeal = true;
                                Intent intent = new Intent(getActivity(), WsDetailActivity.class);
                                intent.putExtra("type", exs[0]);
                                intent.putExtra("key", exs[1]);
                                intent.putExtra("city", exs[2]);
                                getActivity().startActivityForResult(intent,
                                        REQUEST_OPERATION_RESULT);
                            }
                        }
                    }

                    if (!isDeal) {
                        if (mListener != null) {
                            mListener.onStartSearch(dto);
                        }
                    }
                    return;
                }

                if (mListener != null) {
                    mListener.onStartMapPos(dto);
                }
            }

            @Override
            public void onItemLongClick(MapSearchInfo dto) {
            }
        };

        listView = (ListView) mViewSelf.findViewById(R.id.list);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mListener != null) {
                    mListener.onKeyBoardClose();
                }
                return false;
            }
        });
        adapter = new SearchMapAdapter(getActivity(), vrList, mOnItemSelectedListener);
        listView.setAdapter(adapter);
    }

    public void searchByKey(String key) {
        vrMap.clear();
        vrList.clear();

        List<MapSearchInfo> msiEleList = GeomEleBussiness.getInstance().getMapSearchInfoList(key);
        if (msiEleList != null) {
            vrList.addAll(msiEleList);
            isContinueCmd = true;
        }

        /**
         * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
         */
        mSuggestionSearch.requestSuggestion((
                new SuggestionSearchOption()).keyword(key).city(LvApplication.getInstance().getCityName()));
        adapter.setKey(key);
    }

    public void hide() {
        if (mViewSelf != null) {
            mViewSelf.setVisibility(View.GONE);
        }
    }

    public void unhide() {
        if (mViewSelf != null) {
            mViewSelf.setVisibility(View.VISIBLE);
        }
    }

    public void destroy() {
        if (mPoiSearch != null) {
            mPoiSearch.destroy();
        }
        if (mSuggestionSearch != null) {
            mSuggestionSearch.destroy();
        }
        if (mGeoCoder != null) {
            mGeoCoder.destroy();
        }
    }

    private class MyOnGetGeoCoderResultListener implements OnGetGeoCoderResultListener {
        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUid() {
            return uid;
        }

        private String uid;

        @Override
        public void onGetGeoCodeResult(GeoCodeResult result) {

        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                return;
            }
            MapSearchInfo msi = vrMap.get(uid);
            if (msi == null) {
                return;
            }
            msi.setAddress(StringUtil.getSimpleAddress(result.getAddress()));
            adapter.notifyDataSetChanged();
        }
    }

    private class MyOnGetPoiSearchResultListener implements OnGetPoiSearchResultListener {
        private String uid;

        @Override
        public void onGetPoiResult(PoiResult poiResult) {

        }

        @Override
        public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult result) {
            MapSearchInfo msi = vrMap.get(uid);
            if (msi == null) {
                return;
            }

            if (result.error != SearchResult.ERRORNO.NO_ERROR) {
                if (!msi.isGeoPosValid()) {
                    return;
                }

                LatLng mLatLng = new LatLng(msi.getLat() / 1e6, msi.getLng() / 1e6);

                ReverseGeoCodeOption mReverseGeoCodeOption = new ReverseGeoCodeOption();
                mReverseGeoCodeOption.location(mLatLng);

                MyOnGetGeoCoderResultListener listener = new MyOnGetGeoCoderResultListener();
                listener.setUid(uid);

                mGeoCoder.reverseGeoCode(mReverseGeoCodeOption);
                mGeoCoder.setOnGetGeoCodeResultListener(listener);
            } else {
                String address = "";
                if (!StringUtil.isEmpty(result.getAddress())) {
                    address += result.getAddress() + " ";
                }
                if (!StringUtil.isEmpty(result.getTelephone())) {
                    address += result.getTelephone() + " ";
                }
                msi.setAddress(StringUtil.getSimpleAddress(address));

                if (result.getLocation() != null) {
                    msi.setLat((int) result.getLocation().latitudeE6);
                    msi.setLng((int) result.getLocation().longitudeE6);
                }

                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }

    public OnSearchMapLogicListener getListener() {
        return mListener;
    }

    public void setListener(OnSearchMapLogicListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onGetSuggestionResult(SuggestionResult res) {
        if (res == null || res.getAllSuggestions() == null) {
            adapter.notifyDataSetChanged();
            return;
        }

        for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
            if (info.key != null) {
                boolean isExist = false;
                for (MapSearchInfo mm : vrList) {
                    if (info.key.equals(mm.getName())) {
                        isExist = true;
                        break;
                    }
                }
                if (isExist) {
                    continue;
                }

                MapSearchInfo msi = new MapSearchInfo();
                msi.setName(info.key);
                msi.setType(Constant.MAP_SEARCH_POI);
                msi.setUid(info.uid);
                if (info.pt != null) {
                    msi.setLat((int) info.pt.latitudeE6);
                    msi.setLng((int) info.pt.longitudeE6);
                }
                vrList.add(msi);
                vrMap.put(info.uid, msi);
            }
        }
        adapter.notifyDataSetChanged();

        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                excutePoiSearchByMapInfo();
            }
        });
    }

    private void excutePoiSearchByMapInfo() {
        while (isContinueCmd) {
            isContinueCmd = false;
            try {
                List<MapSearchInfo> searchList = cloneList(vrList);
                for (MapSearchInfo msi : searchList) {
                    if (Constant.MAP_SEARCH_HISTORY.equals(msi.getType())) {
                        continue;
                    }
                    MyOnGetPoiSearchResultListener listener = new MyOnGetPoiSearchResultListener();
                    listener.setUid(msi.getUid());
                    mPoiSearch.setOnGetPoiSearchResultListener(listener);
                    mPoiSearch.searchPoiDetail((new PoiDetailSearchOption()).poiUid(msi.getUid()));
                    sleep(100);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized List<MapSearchInfo> cloneList(List<MapSearchInfo> list) {
        List<MapSearchInfo> searchList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for(MapSearchInfo item : list) {
                MapSearchInfo info = new MapSearchInfo();
                info.setType(item.getType());
                info.setUid(item.getUid());
                info.setAddress(item.getAddress());
                info.setExtra(item.getExtra());
                info.setId(item.getId());
                info.setLat(item.getLat());
                info.setLng(item.getLng());
                info.setName(item.getName());
                info.setTag(item.getTag());
                searchList.add(info);
            }
        }
        return searchList;
    }

    public interface OnSearchMapLogicListener{
        boolean onPreStartDetailActivity(GeomElementDBInfo dto);
        void onStartMapPos(MapSearchInfo dto);
        void onStartSearch(MapSearchInfo dto);
        void onKeyBoardClose();
    }
}
