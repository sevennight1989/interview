package com.zp.commonlibrary;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutID());
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();{
        }
        if(actionBar != null){
            if (!showActionBar()) {
                actionBar.hide();
            } else {
                actionBar.setTitle(title());
                if (showBackArrow()) {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    actionBar.setDisplayShowHomeEnabled(true);
                }
            }

        }
    }

    public abstract boolean showActionBar();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public abstract int layoutID();

    public abstract String title();

    public abstract boolean showBackArrow();

}
