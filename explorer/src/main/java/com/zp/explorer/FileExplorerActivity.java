package com.zp.explorer;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.google.common.collect.Lists;
import com.zp.commonlibrary.BaseActivity;

import java.io.File;
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

import static com.zp.explorer.FileType.FOLDER;


public class FileExplorerActivity extends BaseActivity {

    //存放当前页面展示的文件列表
    private List<ItemBean> itemBeanList;
    //存放每个页面的任务栈，包含路径，滑动的位置
    private Stack<int[]> stack;
    //module中需要使用R2
    @BindString(R2.string.file_explorer)
    String mTitle;
    @BindView(R2.id.rv_fileList)
    RecyclerView mRv;
    private FileRecyclerViewAdapter mAdapter;
    private SDCardFileObserver mFileObserver;
    //是否最高层级
    boolean isTop = true;
    private String mCurrentPath;
    String rootPath = Environment.getExternalStorageDirectory().getPath();
    //当前位置包括位置和偏移
    private int[] curPos = new int[2];
    private LinearLayoutManager layoutManager;

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
        itemBeanList = Lists.newArrayList();
        mCurrentPath = rootPath;
        stack = new Stack<>();
        curPos[0] = 0;
        curPos[1] = 0;
    }


    private void initView() {
        mAdapter = new FileRecyclerViewAdapter(this);
        layoutManager = new LinearLayoutManager(this);
        mRv.setLayoutManager(layoutManager);
        mRv.setAdapter(mAdapter);
    }

    private void initData() {
        listFiles(mCurrentPath, curPos);
    }

    private void listFiles(String path) {
        int[] pos = new int[2];
        pos[0] = 0;
        pos[1] = 0;
        listFiles(path, pos, false);
    }

    private void listFiles(String path, final int[] pos) {
        listFiles(path, pos, true);

    }

    private void listFiles(final String path, final int[] pos, final boolean scroll) {
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
                if (scroll) {
                    layoutManager.scrollToPositionWithOffset(pos[0], pos[1]);
                }
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
                        toNext(itemBean.getName());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return itemBeanList.size();
        }

        class FileViewHolder extends RecyclerView.ViewHolder {
            @BindView(R2.id.file_head)
            ImageView fileHeadView;
            @BindView(R2.id.name)
            TextView nameTv;
            @BindView(R2.id.type)
            TextView typeTv;
            @BindView(R2.id.file_item)
            RelativeLayout mFileTimeLy;

            FileViewHolder(View itemView) {
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
        mCurrentPath = new File(mCurrentPath).getParent();
        if (TextUtils.equals(mCurrentPath, rootPath)) {
            isTop = true;
        }
        curPos = stack.pop();
        listFiles(mCurrentPath, curPos);
    }

    //进入下一层级
    private void toNext(String folderName) {
        mCurrentPath = mCurrentPath + File.separator + folderName;
        isTop = false;
        stack.push(getRecyclerViewLastPosition(layoutManager));
        listFiles(mCurrentPath);
    }

    //获得RecyclerView最后的位置
    private int[] getRecyclerViewLastPosition(LinearLayoutManager layoutManager) {
        int[] pos = new int[2];
        pos[0] = layoutManager.findFirstCompletelyVisibleItemPosition();
        OrientationHelper orientationHelper = OrientationHelper.createOrientationHelper(layoutManager, OrientationHelper.VERTICAL);
        int fromIndex = 0;
        int toIndex = itemBeanList.size();
        final int start = orientationHelper.getStartAfterPadding();
        final int end = orientationHelper.getEndAfterPadding();
        final int next = toIndex > fromIndex ? 1 : -1;
        for (int i = fromIndex; i != toIndex; i += next) {
            final View child = mRv.getChildAt(i);
            final int childStart = orientationHelper.getDecoratedStart(child);
            final int childEnd = orientationHelper.getDecoratedEnd(child);
            if (childStart < end && childEnd > start) {
                if (childStart >= start && childEnd <= end) {
                    pos[1] = childStart;
                    LogUtils.v("position = " + pos[0] + " off = " + pos[1]);
                    return pos;
                }
            }
        }
        return pos;
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
