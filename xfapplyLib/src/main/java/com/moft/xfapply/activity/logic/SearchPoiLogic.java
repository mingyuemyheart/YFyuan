package com.moft.xfapply.activity.logic;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.adapter.CityPoiAdapter;
import com.moft.xfapply.activity.adapter.SearchMapAdapter;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.base.ViewLogic;
import com.moft.xfapply.logic.setting.PrefUserInfo;
import com.moft.xfapply.model.common.CityPoi;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.entity.MapSearchInfo;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.widget.XListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchPoiLogic extends ViewLogic implements
        OnGetPoiSearchResultListener, XListView.IXListViewListener {
    public SearchPoiLogic(View v, Activity a) {
        super(v, a);
    }
    private SearchSuggestLogic.OnSearchMapLogicListener mListener = null;

    private View mViewSelf = null;
    private View re_select_con = null;
    private TextView tv_result = null;
    private XListView listView;

    private SearchMapAdapter adapter = null;
    private CityPoiAdapter adapterCity = null;

    private OnSearchPoiLogicListener mListenerSelf = null;

    private PoiSearch mPoiSearch = null;

    private List<MapSearchInfo> vrList = new ArrayList<>();
    private List<CityPoi> cityPoiList = new ArrayList<>();
    private Map<String, MapSearchInfo> vrMap = new HashMap<>();
    private int loadIndex = 0;
    private int totalPageNum = 0;

    private boolean isSearchLevl = false;
    private int mutiCityCount = 0;

    private String mCurCityName = "";
    private String mKey = "";

    private SearchMapAdapter.OnItemSelectedListener mOnItemSelectedListener = null;
    private CityPoiAdapter.OnItemSelectedListener mOnItemSelectedListenerCity = null;

    public void init() {
        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);

        mViewSelf = getActivity().getLayoutInflater().inflate(R.layout.map_search_poi, null);

        re_select_con = (View) mViewSelf.findViewById(R.id.re_select_con);
        re_select_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListenerSelf != null) {
                    mListenerSelf.onSearchChanged(0);
                }
            }
        });

        LinearLayout ll_content = (LinearLayout)getView();
        ll_content.addView(mViewSelf);

        mOnItemSelectedListener = new SearchMapAdapter.OnItemSelectedListener() {
            @Override
            public void onItemClick(MapSearchInfo dto) {
                if (mListener != null) {
                    mListener.onStartMapPos(dto);
                }
            }

            @Override
            public void onItemLongClick(MapSearchInfo dto) {
            }
        };

        mOnItemSelectedListenerCity = new CityPoiAdapter.OnItemSelectedListener() {
            @Override
            public void onItemClick(CityPoi dto) {
                if (dto.getTotalCnt() == 0) {
                    Toast.makeText(getActivity(), "未找到相关结果",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                isSearchLevl = true;
                doSearchCity(dto.getCityInfo());
            }

            @Override
            public void onItemLongClick(CityPoi dto) {
            }
        };

        tv_result = (TextView) mViewSelf.findViewById(R.id.tv_result);

        listView = (XListView) mViewSelf.findViewById(R.id.list);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mListener != null) {
                    mListener.onKeyBoardClose();
                }
                return false;
            }
        });
        listView.setXListViewListener(this);
        listView.setPullRefreshEnable(false);
        listView.setPullLoadEnable(true);
    }

    public void setSearchKey(String key) {
        initData();
        tv_result.setText("正在搜索\"" + key + "\"");
        mKey = key;
    }

    private void clearCityData() {
        vrList.clear();
        vrMap.clear();
        loadIndex = 0;
        totalPageNum = 0;
        mCurCityName = "";
    }

    private void initData() {
        isSearchLevl = false;
        clearCityData();
        cityPoiList.clear();
        mutiCityCount = 0;
    }

    private void doSearchCity(String cityName) {
        clearCityData();
        mCurCityName = cityName;

        adapter = new SearchMapAdapter(getActivity(), vrList, mOnItemSelectedListener);
        listView.setAdapter(adapter);
        adapter.setKey(mKey);
        reSearchCity();
    }

    private void reSearchCity() {
        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city(mCurCityName)
                .keyword(StringUtil.handleTransferChar(mKey))
                .pageNum(loadIndex)
                .cityLimit(true));
    }

    public void searchByKey() {
        initData();
        tv_result.setText("正在搜索\"" + mKey + "\"");

        List<String> cityList = LvApplication.getInstance().getCityList();
        if (cityList.size() == 0) {
            tv_result.setText("未找到相关结果");
            return;
        }

        if (cityList.size() == 1) {
            doSearchCity(cityList.get(0));
        } else {
            for (String cityName : cityList) {
                CityPoi cityPoi = new CityPoi();
                cityPoi.setCityInfo(cityName);
                cityPoi.setTotalCnt(-1);
                cityPoi.setPoiList(null);
                cityPoiList.add(cityPoi);
            }

            adapterCity = new CityPoiAdapter(getActivity(), cityPoiList, mOnItemSelectedListenerCity);
            listView.setAdapter(adapterCity);
            listView.finishLoadMore();

            for (CityPoi city : cityPoiList) {
                SearchInCityAsyncTask task = new SearchInCityAsyncTask(new OnSearchResultListener() {

                    @Override
                    public void onPreExecute() {

                    }

                    @Override
                    public void onProgress(Integer... progress) {

                    }

                    @Override
                    public void onPostExecute(CityPoi cityPoi) {
                        mutiCityCount += cityPoi.getTotalCnt();
                        if (StringUtil.isEmpty(mCurCityName)) {
                            tv_result.setText("共找到" + mutiCityCount + "相关个结果");
                            adapterCity.notifyDataSetChanged();
                        }
                    }
                });
                task.execute(city, mKey);
            }
        }
    }

    public boolean canBack() {
        if (!isSearchLevl) {
            return true;
        }
        return false;
    }

    public void doBack() {
        isSearchLevl = false;

        clearCityData();
        tv_result.setText("共找到" + mutiCityCount + "相关个结果");
        listView.setAdapter(adapterCity);
        listView.finishLoadMore();
    }

    public void hide() {
        if (mViewSelf != null) {
            mViewSelf.setVisibility(View.GONE);
            initData();
        }
    }

    public boolean isShowing() {
        if (mViewSelf != null) {
            if (View.VISIBLE == mViewSelf.getVisibility()) {
                return true;
            }
        }
        return false;
    }

    public void unhide() {
        if (mViewSelf != null) {
            mViewSelf.setVisibility(View.VISIBLE);
        }
        initData();
    }

    public void destroy() {
        if (mPoiSearch != null) {
            mPoiSearch.destroy();
        }
    }

    public SearchSuggestLogic.OnSearchMapLogicListener getListener() {
        return mListener;
    }

    public void setListener(SearchSuggestLogic.OnSearchMapLogicListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onGetPoiResult(PoiResult result) {
        if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            tv_result.setText("未找到相关结果");
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            totalPageNum = result.getTotalPageNum();
            listView.stopLoadMore();
            if (loadIndex >= (totalPageNum - 1)) {
                listView.finishLoadMore();
            } else {
                listView.setLoadMoreNormal();
            }

            int totalCnt = result.getTotalPoiNum();
            tv_result.setText("共找到" + totalCnt + "相关个结果");

            List<PoiInfo> poiList = result.getAllPoi();

            for (PoiInfo poi : poiList) {
                if (poi.location == null) {
                    continue;
                }
                MapSearchInfo msi = new MapSearchInfo();
                msi.setName(poi.name);
                msi.setAddress(StringUtil.getSimpleAddress(poi.address));
                msi.setType(Constant.MAP_SEARCH_POI);
                msi.setLat((int)poi.location.latitudeE6);
                msi.setLng((int)poi.location.longitudeE6);
                msi.setUid(poi.uid);
                vrList.add(msi);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult result) {
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult res) {

    }

    @Override
    public void onRefresh() {

    }

    private Handler mHandler = new Handler();
    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                loadIndex++;
                reSearchCity();
            }
        }, 500);
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

    }


    public void setListenerSelf(OnSearchPoiLogicListener mListener) {
        this.mListenerSelf = mListener;
    }

    public interface OnSearchPoiLogicListener{
        void onSearchChanged(int type);
    }

    public class SearchInCityAsyncTask extends AsyncTask<Object, Integer, Object>
            implements OnGetPoiSearchResultListener {
        private PoiSearch mPoiSearch = null;
        private OnSearchResultListener listener = null;
        private CityPoi cityPoi = null;
        private String key = "";
        private boolean isFinish = false;

        public SearchInCityAsyncTask(OnSearchResultListener listener) {
            super();
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // 初始化搜索模块，注册搜索事件监听
            mPoiSearch = PoiSearch.newInstance();
            mPoiSearch.setOnGetPoiSearchResultListener(this);
        }

        @Override
        protected String doInBackground(Object... params) {
            cityPoi = (CityPoi)params[0];
            key = (String)params[1];
            isFinish = false;

            mPoiSearch.searchInCity((new PoiCitySearchOption())
                    .city(cityPoi.getCityInfo())
                    .keyword(StringUtil.handleTransferChar(key))
                    .pageNum(loadIndex)
                    .cityLimit(true));

            int nSleepNum = 0;
            while (!isFinish && nSleepNum < 200) {
                try {
                    Thread.sleep(100);
                    nSleepNum++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            if(listener != null) {
                listener.onProgress(progress);
            }
        }

        @Override
        protected void onPostExecute(Object result) {
            if(listener != null) {
                listener.onPostExecute(cityPoi);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        public void onGetPoiResult(PoiResult result) {
            int totalCnt = result.getTotalPoiNum();
            List<PoiInfo> poiList = result.getAllPoi();
            cityPoi.setTotalCnt(totalCnt);
            cityPoi.setPoiList(poiList);
            isFinish = true;
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }

        @Override
        public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    }

    public interface OnSearchResultListener {
        public void onPreExecute();
        public void onProgress(Integer... progress);
        public void onPostExecute(CityPoi cityPoi);
    }
}
