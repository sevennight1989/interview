package com.smart.interview.main;


import android.os.Bundle;

import com.smart.interview.BaseActivity;
import com.smart.interview.R;

import butterknife.BindString;

public class CustViewGroupActivity extends BaseActivity {

    @BindString(R.string.cust_view_group)
    String mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean showActionBar() {
        return true;
    }

    @Override
    public int layoutID() {
        return R.layout.activity_cust_view_group;
    }

    @Override
    public String title() {
        return mTitle;
    }

    @Override
    public boolean showBackArrow() {
        return true;
    }
}
