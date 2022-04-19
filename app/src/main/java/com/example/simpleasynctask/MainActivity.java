package com.example.simpleasynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv_status, tv_percent;
    Button btn_start;
    ProgressBar progressBar;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_status = findViewById(R.id.tv_status);
        tv_percent = findViewById(R.id.tv_percent);
        btn_start = findViewById(R.id.btn_start);
        progressBar = findViewById(R.id.pgbTask);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doStartTask();
            }
        });

    }

    public void doStartTask(){
        final int mMaxPercent = 100;
        progressBar.setMax(mMaxPercent);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        btn_start.setEnabled(false);
                        tv_status.setText("Napping ...");
                    }
                });
                for(int i =0;i<mMaxPercent;i++){
                    final int progress = i+1;
                    SystemClock.sleep(600);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progress);
                            int percent = (progress*100)/mMaxPercent;
                            tv_percent.setText(" "+percent+"%");
                            if(progress==mMaxPercent){
                                tv_status.setText("Working ...");
                                btn_start.setEnabled(true);
                            }
                        }
                    });
                }
            }
        });
        thread.start();
    }
}