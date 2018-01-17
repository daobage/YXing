package com.usingtone.arcview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ProgressActivity extends AppCompatActivity {
 MyProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        progressBar = (MyProgressBar) findViewById(R.id.myprogressbar);
        progressBar.setListener(new MyProgressBar.OnProgressCompleteListener() {
            @Override
            public void onFinish() {
                Toast.makeText(ProgressActivity.this, "执行完毕", Toast.LENGTH_SHORT).show();
            }
        });
        progressBar.startToProgress();
    }
}
