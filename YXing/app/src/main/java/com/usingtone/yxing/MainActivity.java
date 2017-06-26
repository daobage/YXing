package com.usingtone.yxing;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import com.usingtone.yxing.views.CircleImageView;

public class MainActivity extends Activity implements View.OnClickListener {
    CircleImageView civ_photo;
    CircleImageView civ_pic;
    DrawerLayout drawerLayout;

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
}
