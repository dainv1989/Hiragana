package com.dainv.hiragana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CharActivity extends AppCompatActivity {

    private static final int HIRAGANA_CHART = 1;
    private static final int KATAKANA_CHART = 2;

    private static int display_char_type = 0;

    private ListView lvCharScroll;

    private static final String basic_chars[] = {
            "a", "i", "u", "e", "o",
            "ka", "ki", "ku", "ke", "ko",
            "sa", "shi", "su", "se", "so",
            "ta", "chi", "tsu", "te", "to",
            "na", "ni", "nu", "ne", "no",
            "ha", "hi", "fu", "he", "ho",
            "ma", "mi", "mu", "me", "mo",
            "ya", "yu", "yo",
            "ra", "ri", "ru", "re", "ro",
            "wa", "wo",
            "n"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char);

        Intent intent = getIntent();
        display_char_type = intent.getIntExtra("CHAR_TYPE", 0);

        lvCharScroll = (ListView)findViewById(R.id.listCharsNav);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, basic_chars);
        lvCharScroll.setAdapter(adapter);
    }
}
