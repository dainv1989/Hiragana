package com.dainv.hiragana;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dainv.hiragana.model.JPChar;
import com.dainv.hiragana.view.GifImageView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private final static String TAG="MainActivity";

    private static final int HIRAGANA_CHART = 1;
    private static final int KATAKANA_CHART = 2;

    private Button btnHira;
    private Button btnKata;
    private Button btnExer;

    private TextView tvMemoRoma;
    private TextView tvMemoHira;
    private TextView tvMemoKata;

    private ImageView imgLight;

    private static ArrayList<String> lstRoma = null;
    private static ArrayList<String> lstHira = null;
    private static ArrayList<String> lstKata = null;
    private static boolean is_init = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHira = (Button)findViewById(R.id.btnHira);
        btnKata = (Button)findViewById(R.id.btnKata);
        btnExer = (Button)findViewById(R.id.btnExer);

        tvMemoHira = (TextView)findViewById(R.id.txtMemoHira);
        tvMemoKata = (TextView)findViewById(R.id.txtMemoKata);
        tvMemoRoma = (TextView)findViewById(R.id.txtMemoRoma);

        imgLight = (ImageView)findViewById(R.id.iconLight);

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
                Intent intent = new Intent(context, ExcerciseActivity.class);
                intent.putExtra("EXERCISE_TYPE", 0);
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
