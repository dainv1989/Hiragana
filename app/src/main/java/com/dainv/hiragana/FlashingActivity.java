package com.dainv.hiragana;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.Timer;

public class FlashingActivity extends AppCompatActivity {

    private static final int TIME_OUT = 1500;
    private static final int TIMER_STICK = 500;

    private ImageView imgLogoFlasher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashing);

        imgLogoFlasher = (ImageView)findViewById(R.id.imgLogoFlash);

        CountDownTimer timer = new CountDownTimer(TIME_OUT, TIMER_STICK) {
            @Override
            public void onTick(long millisUntilFinished) {
                /* do nothing */
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        };

        timer.start();
        ((AnimationDrawable)imgLogoFlasher.getDrawable()).start();
    }
}
