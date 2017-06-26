package com.usingtone.yxing;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import com.usingtone.yxing.views.CircleImageView;
import com.usingtone.yxing.views.NoScrollViewPager;

import java.util.List;

import github.chenupt.springindicator.SpringIndicator;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    CircleImageView civ_photo;
    CircleImageView civ_pic;
    DrawerLayout drawerLayout;
    NoScrollViewPager nsvpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
    }
    public void initView(){
        civ_pic  = (CircleImageView) findViewById(R.id.civ_pic);
        civ_photo = (CircleImageView) findViewById(R.id.civ_photo);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        nsvpager = (NoScrollViewPager) findViewById(R.id.nsvp_content);
        civ_photo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.civ_photo:
                drawerLayout.openDrawer(Gravity.START);
                break;

        }
    }
    class FragmentAdapter extends FragmentStatePagerAdapter{

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }
    }
}
