package com.example.csyvi.medpack;

import android.app.FragmentManager;
import android.content.Intent;
import android.icu.util.Measure;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

/**
 * The type Scan vital signs.
 */
public class ScanVitalSigns extends AppCompatActivity  {

    /**
     * The Text view.
     */
    TextView textView;
    Button scanButton;
    Button submitButton;
    /**
     * The Ring progress bar 1.
     */
    RingProgressBar ringProgressBar1;

    /**
     * The Progress.
     */
    int progress=0;
    /**
     * The My handler.
     */
    Handler myHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if(msg.what == 0)
            {
                if(progress < 100)
                {
                    progress++;
                    ringProgressBar1.setProgress(progress);
                }

                if(progress == 100)
                {
                    progress = 0;
                    ringProgressBar1.setProgress(0);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.measurevitalsigns_scanningvitalsigns);

        textView = findViewById(R.id.progressText);
        scanButton = findViewById(R.id.scanButton);
        submitButton = findViewById(R.id.submitButton);
        scanButton.setVisibility(View.VISIBLE);
        submitButton.setVisibility(View.INVISIBLE);
        scanButton.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                textView.setText("Scanning ...");
                submitButton.setVisibility(View.INVISIBLE);
                scanButton.setVisibility(View.INVISIBLE);
                ringProgressBar1 = findViewById(R.id.progress_bar_1);
                ringProgressBar1.setOnProgressListener(new RingProgressBar.OnProgressListener( ) {
                    @Override
                    public void progressToComplete() {
                        textView.setText("Scan Completed!!");
                        submitButton.setVisibility(View.VISIBLE);
                        scanButton.setText("Rescan Vital Signs");
                        scanButton.setVisibility(View.VISIBLE);
                    }
                });

                new Thread(new Runnable() {
                    @Override
                    public void run()
                    {
                        for(int i=0;i<100;i++)
                        {
                            try
                            {
                                Thread.sleep(100);
                                myHandler.sendEmptyMessage(0);
                            }

                            catch(InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScanVitalSigns.this, MeasureVitalSignsManager.class);
                intent.putExtra("sessionID","submitVitals");
                startActivity(intent);
            }
        });
    }
}