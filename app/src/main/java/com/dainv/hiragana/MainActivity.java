package com.dainv.hiragana;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dainv.hiragana.model.JPChar;
import com.dainv.hiragana.model.Settings;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private final static String TAG="MainActivity";

    private static final int HIRAGANA_CHART = 1;
    private static final int KATAKANA_CHART = 2;

    private TextView btnHira;
    private TextView btnKata;
    private TextView btnExer;
    private ImageView btnMore;

    private TextView tvMemoRoma;
    private TextView tvMemoHira;
    private TextView tvMemoKata;

    private ImageView imgNinja;
    private ImageView imgVote;
    private ImageView imgShare;
    private ImageView imgInfo;
    private ImageView imgSetting;

    private static Timer timer = null;
    private static TimerTask task = null;
    private static Runnable runAnimation;

    private static ArrayList<String> lstRoma = null;
    private static ArrayList<String> lstHira = null;
    private static ArrayList<String> lstKata = null;
    private static boolean is_init = false;

    /* determine whether play memo character sound or not */
    private static boolean is_play_sound = false;

    private static int memo_index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHira = (TextView)findViewById(R.id.btnHira);
        btnKata = (TextView)findViewById(R.id.btnKata);
        btnExer = (TextView)findViewById(R.id.btnExer);
        btnMore = (ImageView)findViewById(R.id.btnMore);

        tvMemoHira = (TextView)findViewById(R.id.txtMemoHira);
        tvMemoKata = (TextView)findViewById(R.id.txtMemoKata);
        tvMemoRoma = (TextView)findViewById(R.id.txtMemoRoma);

        imgNinja = (ImageView)findViewById(R.id.imgNinja);
        imgInfo = (ImageView)findViewById(R.id.mainInfo);
        imgShare = (ImageView)findViewById(R.id.mainShare);
        imgVote = (ImageView)findViewById(R.id.mainVote);
        imgSetting = (ImageView)findViewById(R.id.mainSetting);

        if (is_init == false) {
            lstRoma = new ArrayList<>();
            lstHira = new ArrayList<>();
            lstKata = new ArrayList<>();
            JPChar.getFullChars(lstRoma, lstHira, lstKata);
            is_init = true;
        }

        runAnimation = new Runnable() {
            @Override
            public void run() {
                animateNinja();
                generateMemo();
            }
        };
        setNinjaTimer(5);

        final Context context = this;
        btnHira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HiraganaActivity.class);
                intent.putExtra("CHART_TYPE", HIRAGANA_CHART);
                context.startActivity(intent);
            }
        });

        btnKata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HiraganaActivity.class);
                intent.putExtra("CHART_TYPE", KATAKANA_CHART);
                context.startActivity(intent);
            }
        });

        btnExer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SelectExerciseActivity.class);
                context.startActivity(intent);
            }
        });

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MoreActivity.class);
                context.startActivity(intent);
            }
        });

        /* gerenate new memo when user clicks on image */
        imgNinja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings settings = new Settings(getApplicationContext());
                int interval = settings.getNinjaInterval();

                /* enable play sound if it's selected in settings */
                if(settings.getNinjaSoundConfig())
                    is_play_sound = true;

                if(interval > 0) {
                    setNinjaTimer(5);
                } else {
                    animateNinja();
                    generateMemo();
                }
            }
        });

        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SettingActivity.class);
                startActivity(intent);
            }
        });

        /* open app information window */
        imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AboutActivity.class);
                startActivity(intent);
            }
        });

        /* open Google play market for voting app */
        imgVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String packageName = getApplicationContext().getPackageName();
                Uri uri = Uri.parse("market://details?id=" + packageName);
                Intent market = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(market);
                }
                catch (ActivityNotFoundException e) {
                    uri = Uri.parse("http://play.google.com/store/apps/details?id=" + packageName);
                    market = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(market);
                    e.printStackTrace();
                }
            }
        });

        /* sharing app on social */
        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareText = context.getResources().getString(R.string.share_content);
                String appUrl = " http://play.google.com/store/apps/details?id=" +
                        context.getPackageName();

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText + appUrl);

                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        Settings settings = new Settings(getApplicationContext());
        int interval = settings.getNinjaInterval();
        if(interval > 0)
            setNinjaTimer(interval);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(task != null)
            task.cancel();
    }

    private void generateMemo() {
        if (is_init == false)
            return;

        Settings settings = new Settings(this);
        if(settings.getNinjaUpdateMode()) {
            Random random = new Random();
            memo_index = random.nextInt(lstRoma.size()); /* min is 0, max is 103 */
        } else {
            /* sequential character display */
            memo_index++;
            memo_index = (memo_index % lstRoma.size());
        }

        tvMemoRoma.setText(lstRoma.get(memo_index));
        tvMemoHira.setText(lstHira.get(memo_index));
        tvMemoKata.setText(lstKata.get(memo_index));

        if(is_play_sound) {
            JPChar.playSound(lstRoma.get(memo_index), this);
            is_play_sound = false;
        }
    }

    private void animateNinja() {
        AnimationDrawable ninjaAnim = (AnimationDrawable)imgNinja.getDrawable();
        ninjaAnim.stop();
        ninjaAnim.start();
    }

    /**
     * Set timer to auto generate memo
     * @param seconds
     */
    private void setNinjaTimer(int seconds) {
        if (timer == null)
            timer = new Timer();

        if (task != null)
            task.cancel();

        task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(runAnimation);
            }
        };
        timer.schedule(task, 0, seconds * 1000);
    }
}
