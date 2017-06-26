package com.usingtone.yxing;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import github.chenupt.springindicator.SpringIndicator;

/**
 * Created by luze on 2017-06-26.
 */

public class HomePageFragment extends Fragment {
    RecyclerView recyclerView;
    SpringIndicator spingIndicator;
    ViewPager viewPager;
    List<ImageView> imageViews;
    int currentItem = 0;
    int[] Resouce = {
            R.drawable.image_cloud_card,
            R.drawable.bracelet,
            R.drawable.pass_white_bar,
            R.drawable.backg_account,
            R.drawable.activition_tourism,
            R.drawable.primary_secondary_card,
            R.drawable.old_card,
    };
    String[] Strings = {
            "云卡",
            "手环充值",
            "信用白条",
            "武汉通钱包",
            "旅游年卡",
            "中小学生卡",
            "老年卡",
    };
    static  final int CURRENTITEM = 19;
    int[] imagesId= {
            R.drawable.bg_wode,
            R.drawable.bg_wode,
            R.drawable.bg_wode
    };
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
           switch (msg.what){
               case CURRENTITEM:
                   break;
           }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_home_page,null);
        initData();
        initView(view);
        return view;
    }
    public void initData(){
        imageViews = new ArrayList<>();
        for (int i =0;i<imagesId.length;i++){
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(imagesId[i]);
            imageViews.add(imageView);
        }

    }
    public void initView(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        spingIndicator = (SpringIndicator) view.findViewById(R.id.indicator);
        viewPager = (ViewPager) view.findViewById(R.id.vp_banner);
        viewPager.setAdapter(new ViewPagerAdapter());
        spingIndicator.setViewPager(viewPager);
        new ViewPagerThread().start();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),4);
        recyclerView.setLayoutManager(layoutManager);
    }
    /*
  * 广告位的ViewPager适配器
  *
  * */
    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            view.removeView(imageViews.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, final int position) {
            view.addView(imageViews.get(position));
            return imageViews.get(position);
        }
    }

    class  ViewPagerThread extends Thread{
        public boolean stop = false;

        @Override
        public void run() {

            while (!stop){
                Message message = Message.obtain();
                currentItem = (currentItem + 1) % imagesId.length;
                message.what = CURRENTITEM;
                message.arg1 = currentItem;
                mHandler.sendMessage(message);
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
