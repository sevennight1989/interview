package ll.news;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zp.commonlibrary.BaseActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import ll.news.widget.PullToRefreshListView;

public class NewListActivity extends BaseActivity implements PullToRefreshListView.OnRefreshListener {

    @BindString(R2.string.title)
    String mTitle;
    @BindView(R2.id.news_list)
    PullToRefreshListView mPullToRefreshListView;

    private List<String> mDatas;
    private NewListAdapter mAdapter;
    int index = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mDatas.add("我是新闻序号 " + i);
        }
        mAdapter = new NewListAdapter(this);
        mPullToRefreshListView.setAdapter(mAdapter);
        mPullToRefreshListView.setonRefreshListener(this);
    }

    @Override
    public boolean showActionBar() {
        return true;
    }

    @Override
    public int layoutID() {
        return R.layout.activity_new_list;
    }

    @Override
    public String title() {
        return mTitle;
    }

    @Override
    public boolean showBackArrow() {
        return true;
    }

    @Override
    public void onRefresh() {
        new AsyncTask<Void, Void, Void>() {
            //刷新过程中需要做的操作在这里
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mDatas.add("new item " + index++);
                return null;
            }

            //刷新完成后要通知listview进行界面调整
            @Override
            protected void onPostExecute(Void result) {
                mAdapter.notifyDataSetChanged();
                mPullToRefreshListView.onRefreshComplete();
            }

        }.execute(new Void[]{});
    }

    class ViewHolder {
        TextView newsText;
    }

    private class NewListAdapter extends BaseAdapter {

        private Context context;

        public NewListAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.news_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.newsText = convertView.findViewById(R.id.news_item_text);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.newsText.setText(mDatas.get(position));
            return convertView;
        }
    }
}
