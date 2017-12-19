package com.smart.interview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smart.interview.view.PartView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpandRecyclerViewActivity extends AppCompatActivity {

    private static final int TYPE_TITLE = 0;
    private static final int TYPE_CONTENT = 1;
    @BindView(R.id.rv)
    RecyclerView mRv;
    String[] status;
    MyAdapter myAdapter;
    private static final int SIZE = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_rv);
        ButterKnife.bind(this);
        initData();
        myAdapter = new MyAdapter(this);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(myAdapter);
    }

    private void initData() {
        status = new String[SIZE];
        for (int i = 0; i < SIZE; i++) {
            status[i] = "0";
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private LayoutInflater mInflater;
        private Context mContext;

        public MyAdapter(Context context) {
            this.mContext = context;
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getItemViewType(int position) {
            return (position % 2 == 0) ? TYPE_TITLE : TYPE_CONTENT;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case TYPE_TITLE:
                    return new MyViewHolder(mInflater.inflate(R.layout.expand_rv_item_title, parent, false));
                case TYPE_CONTENT:
                    return new MyViewHolder(mInflater.inflate(R.layout.expand_rv_item_content, parent, false));
                default:
                    return null;
            }
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {

            if (position % 2 == 0) {
                holder.mItemTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.equals(status[position + 1], "0")) {
                            status[position + 1] = "1";
                        } else {
                            status[position + 1] = "0";
                        }
                        notifyDataSetChanged();
                    }
                });
            } else {

                ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(180, 150);
                int CCount = holder.mPartView.getChildCount();

                if (CCount == 0) {
                    for (int i = 0; i < 9; i++) {
                        TextView tv = new TextView(mContext);
                        tv.setLayoutParams(params);
                        tv.setText(position + "--" + i);
                        tv.setGravity(Gravity.CENTER);
                        tv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        tv.setTag(i);
                        holder.mPartView.addView(tv);
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(mContext, "点击了 第 " + position + " 行 第 " + v.getTag() + " 列", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } else {
                    for (int i = 0; i < CCount; i++) {
                        TextView tv = (TextView) holder.mPartView.getChildAt(i);
                        if (tv != null) {
                            tv.setText(position + "--" + i);
                        }
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(mContext, "点击了 第 " + position + " 行 第 " + v.getTag() + " 列", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
                RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) holder.mPartView.getLayoutParams();
                if (TextUtils.equals(status[position], "0")) {
                    holder.mPartView.setVisibility(View.GONE);
                    param.height = 0;
                    param.width = 0;
                } else {
                    holder.mPartView.setVisibility(View.VISIBLE);
                    param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    param.width = LinearLayout.LayoutParams.MATCH_PARENT;
                }
                holder.mPartView.setLayoutParams(param);
            }
        }

        @Override
        public int getItemCount() {
            return status.length;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.item_title)
            @Nullable
            TextView mItemTitle;
            @BindView(R.id.content_top)
            @Nullable
            PartView mPartView;

            public MyViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }
}
