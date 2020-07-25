package com.moft.xfapply.activity.logic;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.FragmentMap;
import com.moft.xfapply.activity.MapSearchActivity;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.base.ViewLogic;

public class SearchLogic extends ViewLogic {

    public SearchLogic(View v, Activity a) {
        super(v, a);
    }
    
    private ImageView ib_plus = null;

    public void init() {
        ib_plus = (ImageView) getView().findViewById(R.id.ib_plus);
        
        refresh();
        
        ib_plus.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                doPlus();
            }
        });
    }
    
    public void refresh() {
        ib_plus.setImageResource(R.drawable.btn_search_more_select_item);
    }
    
    private void doPlus() {
        Intent intent = new Intent(getActivity(), MapSearchActivity.class);

        getActivity().startActivityForResult(intent, Constant.SEARCH_INFO_MAP);
    }

}
