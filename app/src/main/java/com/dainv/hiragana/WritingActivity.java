package com.dainv.hiragana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WritingActivity extends AppCompatActivity {

    private static final int HIRAGANA_CHART = 1;
    private static final int KATAKANA_CHART = 2;

    private static int display_char_type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

        Intent intent = getIntent();
        display_char_type = intent.getIntExtra("CHAR_TYPE", 0);
    }
}
