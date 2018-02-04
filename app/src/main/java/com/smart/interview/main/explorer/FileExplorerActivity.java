package com.smart.interview.main.explorer;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smart.interview.BaseActivity;
import com.smart.interview.R;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FileExplorerActivity extends BaseActivity {

    //存放当前页面展示的文件列表
    private List<ItemBean> itemBeanList;

    private Stack<List<ItemBean>> stack;

    @BindString(R.string.file_explorer)
    String mTitle;
    @BindView(R.id.rv_fileList)
    RecyclerView mRv;
    private FileRecyclerViewAdapter mAdapter;

    //是否最高层级
    boolean isTop = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBase();
        initData();
        initView();
    }

    //初始化数据源载体
    private void initBase() {
        itemBeanList = new ArrayList<>();
        //test
        for (int i = 0; i < 20; i++) {
            ItemBean itemBean = new ItemBean();
            itemBeanList.add(itemBean);
        }


        stack = new Stack<>();
    }

    private void initView() {
        mAdapter = new FileRecyclerViewAdapter(this);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(mAdapter);
    }

    private void initData() {

    }

    @Override
    public boolean showActionBar() {
        return true;
    }

    @Override
    public int layoutID() {
        return R.layout.activity_file_explorer;
    }

    @Override
    public String title() {
        return mTitle;
    }

    @Override
    public boolean showBackArrow() {
        return true;
    }


    class FileRecyclerViewAdapter extends RecyclerView.Adapter<FileRecyclerViewAdapter.FileViewHolder> {
        private Context mContext;

        FileRecyclerViewAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.file_list_item_layout, null);
            return new FileViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(FileViewHolder holder, int position) {
            if (position % 2 == 1) {
                holder.fileHeadView.setBackgroundResource(R.drawable.file);
                holder.typeTv.setText(getText(R.string.type_file));
            }
        }

        @Override
        public int getItemCount() {
            return itemBeanList.size();
        }

        class FileViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.file_head)
            ImageView fileHeadView;
            @BindView(R.id.name)
            TextView nameTv;
            @BindView(R.id.type)
            TextView typeTv;

            public FileViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (isTop) {
                    finish();
                } else {
                    backToPre();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (isTop) {
                finish();
            } else {
                backToPre();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //返回上一个层级
    private void backToPre() {
    }

    //进入下一层级
    private void toNext() {

    }
}
