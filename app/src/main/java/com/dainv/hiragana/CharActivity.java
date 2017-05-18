package com.dainv.hiragana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CharActivity extends AppCompatActivity {

    private static final int HIRAGANA_CHART = 1;
    private static final int KATAKANA_CHART = 2;

    private final String basic_chars[] = {
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

    private static int display_char_type = 0;

    private RecyclerView bottomCharScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char);

        Intent intent = getIntent();
        display_char_type = intent.getIntExtra("CHAR_TYPE", 0);

        bottomCharScroll = (RecyclerView)findViewById(R.id.bottomCharScroll);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false); /* horizontal display list */
        bottomCharScroll.setLayoutManager(layoutManager);

        CharAdapter adapter = new CharAdapter(basic_chars);
        bottomCharScroll.setAdapter(adapter);
    }

    public class CharAdapter extends RecyclerView.Adapter<CharAdapter.ViewHolder> {
        private String[] dataset;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView view;
            public ViewHolder(TextView view) {
                super(view);
                this.view = view;
            }
        }

        public CharAdapter(String[] dataset) {
            this.dataset = dataset;
        }

        @Override
        public CharAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView view = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.bottom_text_bar, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.view.setText(dataset[position]);
        }

        @Override
        public int getItemCount() {
            return dataset.length;
        }
    }
}
