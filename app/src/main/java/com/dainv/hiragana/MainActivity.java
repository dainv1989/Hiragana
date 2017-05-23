package com.dainv.hiragana;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dainv.hiragana.view.GifImageView;

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
    }
}
