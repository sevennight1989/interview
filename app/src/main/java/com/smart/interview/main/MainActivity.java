package com.smart.interview.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ConvertUtils;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.smart.interview.R;
import com.smart.interview.aop.Permission;
import com.smart.interview.main.camera.CameraActivity;
import com.smart.interview.main.explorer.FileExplorerActivity;
import com.smart.interview.view.RotateTextView;


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
    String[] mData;
    @BindView(R.id.slider)
    SliderLayout mSlider;
    private ItemListAdapter mAdapter;

    @Override
    @Permission(value = "checck", type = 0)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        ButterKnife.bind(this);
        setTitle(mTitle);
        mAdapter = new ItemListAdapter();
        mRV.setLayoutManager(new GridLayoutManager(this, 3));
        mRV.setAdapter(mAdapter);
        setSliderShow();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void setSliderShow() {
        int[] imgs = new int[]{R.drawable.img_001, R.drawable.img_002, R.drawable.img_003, R.drawable.img_004};
        String[] sliderText = getResources().getStringArray(R.array.slider_text);
        for (int i = 0; i < imgs.length; i++) {
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView.description(sliderText[i]).image(imgs[i]).setScaleType(BaseSliderView.ScaleType.Fit);
            mSlider.addSlider(textSliderView);
        }
        mSlider.setPresetTransformer(SliderLayout.Transformer.RotateDown);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(4000);
    }


    class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {


        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_list, null);
            return new ItemViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, final int position) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) holder.itemTv.getLayoutParams();
            lp.height = ConvertUtils.dp2px(200);
            holder.itemTv.setLayoutParams(lp);
            holder.itemTv.setText(mData[position]);
            holder.itemTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = null;
                    switch (position) {
                        case 0:
                            intent = new Intent(MainActivity.this, CustViewGroupActivity.class);

                            break;

                        case 1:
                            break;

                        case 2:
                            intent = new Intent(MainActivity.this, ExpandRecyclerViewActivity.class);
                            break;

                        case 3:
                            intent = new Intent(MainActivity.this, CameraActivity.class);
                            break;

                        case 4:
                            intent = new Intent(MainActivity.this, RxJavaTestActivity.class);
                            break;

                        case 5:
                            intent = new Intent(MainActivity.this, StorageTestActivity.class);
                            break;

                        case 6:
                            intent = new Intent(MainActivity.this, QueueAndThreadActivity.class);
                            break;

                        case 7:
                            intent = makeLaunchIntent(FileExplorerActivity.class);
                            break;
                    }
                    if (intent != null) {
                        startActivity(intent);
                    }


                    //启动外部应用,等同于应用安装完成后点击打开按钮启动应用
//                    String packageName = "";
//                    if(position == 0) {
//                        packageName = "com.etop.vincode";
//                    }
//                    Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
//                    if (intent != null) {
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                    }

                }
            });
        }

        private Intent makeLaunchIntent(Class clazz) {
            return new Intent(MainActivity.this, clazz);
        }

        @Override
        public int getItemCount() {
            return mData.length;
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.item_text)
            RotateTextView itemTv;

            public ItemViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }


}
