package com.moft.xfapply.base;

import android.app.Activity;
import android.view.View;

public class ViewLogic {
    private View parent = null;
    private Activity ba = null;

    public ViewLogic(View v, Activity a) {
        setView(v);
        setActivity(a);
    }

    public View getView() {
        return parent;
    }

    public void setView(View parent) {
        this.parent = parent;
    }

    public Activity getActivity() {
        return ba;
    }

    public void setActivity(Activity ba) {
        this.ba = ba;
    }
}
