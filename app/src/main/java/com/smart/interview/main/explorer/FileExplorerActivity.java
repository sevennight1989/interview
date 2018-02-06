package com.smart.interview.main.explorer;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.smart.interview.BaseActivity;
import com.smart.interview.R;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.smart.interview.main.explorer.FileType.FOLDER;

public class FileExplorerActivity extends BaseActivity {

    //存放当前页面展示的文件列表
    private List<ItemBean> itemBeanList;
    //存放每个页面的任务栈，包含路径，滑动的位置
    private Stack<PageInfoBean> stack;

    @BindString(R.string.file_explorer)
    String mTitle;
    @BindView(R.id.rv_fileList)
    RecyclerView mRv;
    private FileRecyclerViewAdapter mAdapter;
    private SDCardFileObserver mFileObserver;
    //是否最高层级
    boolean isTop = true;
    private String mCurrentPath;
    String rootPath = Environment.getExternalStorageDirectory().getPath();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBase();
        initData();
        initView();
        mFileObserver = new SDCardFileObserver(Environment.getExternalStorageDirectory().getPath());
        mFileObserver.startWatching();
    }

    //初始化数据源载体
    private void initBase() {
        itemBeanList = new ArrayList<>();
        mCurrentPath = rootPath;
        stack = new Stack<>();
    }

    private void initView() {
        mAdapter = new FileRecyclerViewAdapter(this);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(mAdapter);
    }

    private void initData() {
        listFiles(mCurrentPath);
    }

    private void listFiles(String path) {

        if (itemBeanList != null && itemBeanList.size() > 0) {
            itemBeanList.clear();
        }
        Observable.just(path).map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                File file = new File(s);
                File[] files = file.listFiles();
                for (File f : files) {
                    ItemBean itemBean = new ItemBean();
                    itemBean.setName(f.getName());
                    if (f.isDirectory()) {
                        itemBean.setFileType(FOLDER);
                        itemBean.setSize(0);
                        itemBean.setFileTypeName(getResources().getString(R.string.type_folder));
                    } else if (f.isFile()) {
                        itemBean.setFileType(FileType.FILE);
                        itemBean.setSize(f.length());
                        itemBean.setFileTypeName(getResources().getString(R.string.type_file));
                    }
                    itemBean.setPath(f.getPath());
                    itemBeanList.add(itemBean);
                }
                return "";
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted() {
                mAdapter.notifyDataSetChanged();
                LogUtils.v("加载文件完成");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        });


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
            final ItemBean itemBean = itemBeanList.get(position);
            holder.nameTv.setText(itemBean.getName());
            holder.typeTv.setText(itemBean.getFileTypeName());
            switch (itemBean.getFileType()) {
                case FOLDER:
                    holder.fileHeadView.setBackgroundResource(R.drawable.folder);
                    break;

                case FILE:
                    holder.fileHeadView.setBackgroundResource(R.drawable.file);
                    break;
            }
            holder.mFileTimeLy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemBean.getFileType() == FOLDER) {
                        mCurrentPath = mCurrentPath + File.separator + itemBean.getName();
                        toNext();
                    }

                }
            });
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
            @BindView(R.id.file_item)
            RelativeLayout mFileTimeLy;

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
//        mAdapter.notifyDataSetChanged();
//        mRv.scrollToPosition(stack.pop().getPosition());
    }

    //进入下一层级
    private void toNext() {
        listFiles(mCurrentPath);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFileObserver.stopWatching();
    }

    public class SDCardFileObserver extends FileObserver {
        public SDCardFileObserver(String path) {
       /*
       * 这种构造方法是默认监听所有事件的,如果使用 super(String,int)这种构造方法，
       * 则int参数是要监听的事件类型.
       */
            super(path);
        }

        @Override
        public void onEvent(int event, String path) {
            //触发SD卡事件，刷新文件列表
        }
    }

}
