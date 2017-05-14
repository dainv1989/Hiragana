package com.dainv.hiragana;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private final static String TAG="MainActivity";

    private Button btnHira;
    private Button btnKata;
    private Button btnExer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHira = (Button)findViewById(R.id.btnHira);
        btnKata = (Button)findViewById(R.id.btnKata);
        btnExer = (Button)findViewById(R.id.btnExer);

        final Context context = this;
        btnHira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HiraganaActivity.class);
                context.startActivity(intent);
            }
        });

        btnKata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: open katakana chart
            }
        });

        btnExer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: oopen exercise activity
            }
        });
    }
}
