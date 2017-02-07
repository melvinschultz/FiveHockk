package net.schultzoss_montpellier.melvin.fivehock.Tools;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;
import net.schultzoss_montpellier.melvin.fivehock.R;

public class ProgressBarExample extends AppCompatActivity {

    ProgressBar androidProgressBar;
    int progressStatusCounter = 0;
    TextView textView;
    Handler progressHandler = new Handler();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        androidProgressBar = (ProgressBar) findViewById(R.id.horizontal_progress_bar);
        textView = (TextView) findViewById(R.id.TextViewExperience);
        //Start progressing
        new Thread(new Runnable() {
            public void run() {
                while (progressStatusCounter < 100) {
                    progressStatusCounter += 2;
                    progressHandler.post(new Runnable() {
                        public void run() {
                            androidProgressBar.setProgress(progressStatusCounter);
                            //Status update in textview
                            textView.setText("Status: " + progressStatusCounter + "/" + androidProgressBar.getMax());
                        }
                    });
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}