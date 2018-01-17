package com.usingtone.arcview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    Intent intent;
    @BindView(R.id.arcview)
    ArcView arcview;
    @BindView(R.id.btn_progress)
    Button btnProgress;
    @BindView(R.id.btn_temp)
    Button btnTemp;
    @BindView(R.id.btn_temperature)
    Button btnTemperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    void init() {
        if (arcview != null) {
            arcview.setImage("http://img01.sogoucdn.com/app/a/100520024/a99762f244f3e093f8f839912eefe94c");
        }
    }

    @OnClick({R.id.btn_progress, R.id.btn_temp, R.id.btn_temperature})
   public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_progress:
                intent = new Intent(MainActivity.this, ProgressActivity.class);
                break;
            case R.id.btn_temp:
                intent = new Intent(MainActivity.this, TempControlActivity.class);
                break;
            case R.id.btn_temperature:
                intent = new Intent(MainActivity.this, TemperatureActivity.class);
                break;
        }
        startActivity(intent);
    }

}
