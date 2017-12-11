package com.smart.interview.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smart.interview.R;


import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindString(R.string.item_list)
    String mTitle;
    @BindView(R.id.rv)
    RecyclerView mRV;
    @BindArray(R.array.main_ui_item)
    String mData[];
    private ItemListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        ButterKnife.bind(this);
        setTitle(mTitle);
        mAdapter = new ItemListAdapter();
        mRV.setLayoutManager(new LinearLayoutManager(this));
        mRV.setAdapter(mAdapter);
    }



     class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>{


        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_list,null);
            return new ItemViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position) {
            holder.itemTv.setText(mData[position]);
        }

        @Override
        public int getItemCount() {
            return mData.length;
        }

        class ItemViewHolder extends RecyclerView.ViewHolder{
            @BindView(R.id.item_text)
            TextView itemTv;
            public ItemViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
            }
        }
    }
}
