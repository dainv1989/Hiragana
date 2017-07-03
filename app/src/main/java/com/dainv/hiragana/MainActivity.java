package com.dainv.hiragana;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dainv.hiragana.model.JPChar;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private final static String TAG="MainActivity";

    private static final int HIRAGANA_CHART = 1;
    private static final int KATAKANA_CHART = 2;

    private TextView btnHira;
    private TextView btnKata;
    private TextView btnExer;

    private TextView tvMemoRoma;
    private TextView tvMemoHira;
    private TextView tvMemoKata;

    private ImageView imgLight;
    private ImageView imgVote;
    private ImageView imgShare;
    private ImageView imgInfo;

    private static ArrayList<String> lstRoma = null;
    private static ArrayList<String> lstHira = null;
    private static ArrayList<String> lstKata = null;
    private static boolean is_init = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHira = (TextView)findViewById(R.id.btnHira);
        btnKata = (TextView)findViewById(R.id.btnKata);
        btnExer = (TextView)findViewById(R.id.btnExer);

        tvMemoHira = (TextView)findViewById(R.id.txtMemoHira);
        tvMemoKata = (TextView)findViewById(R.id.txtMemoKata);
        tvMemoRoma = (TextView)findViewById(R.id.txtMemoRoma);

        imgLight = (ImageView)findViewById(R.id.iconLight);
        imgInfo = (ImageView)findViewById(R.id.mainInfo);
        imgShare = (ImageView)findViewById(R.id.mainShare);
        imgVote = (ImageView)findViewById(R.id.mainVote);

        if (is_init == false) {
            lstRoma = new ArrayList<>();
            lstHira = new ArrayList<>();
            lstKata = new ArrayList<>();
            JPChar.getFullChars(lstRoma, lstHira, lstKata);
            is_init = true;
        }
        generateMemo();     /* show a random char for memo panel */

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

        /* gerenate new memo when user clicks on image */
        imgLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateMemo();
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
                shareIntent.setType("text/html");
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText + appUrl);

                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        generateMemo();
    }

    private void generateMemo() {
        if (is_init == false)
            return;

        Random random = new Random();
        int index = random.nextInt(lstRoma.size()); /* min is 0, max is 103 */

        tvMemoRoma.setText(lstRoma.get(index));
        tvMemoHira.setText(lstHira.get(index));
        tvMemoKata.setText(lstKata.get(index));
    }
}
