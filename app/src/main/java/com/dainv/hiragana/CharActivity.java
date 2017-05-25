package com.dainv.hiragana;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dainv.hiragana.view.GifImageView;

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
    private GifImageView gifCharDisplay;

    private ImageView imgReplayChar;
    private ImageView imgPlaySound;

    private String currentCharAsset = "hira/a.gif";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char);

        Intent intent = getIntent();
        display_char_type = intent.getIntExtra("CHAR_TYPE", 0);

        imgReplayChar = (ImageView)findViewById(R.id.imgCharAnimate);
        imgPlaySound = (ImageView)findViewById(R.id.imgCharSound);

        gifCharDisplay = (GifImageView)findViewById(R.id.gifCharDisplay);
        bottomCharScroll = (RecyclerView)findViewById(R.id.bottomCharScroll);

        gifCharDisplay.setGifImageAsset(currentCharAsset);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false); /* horizontal display list */
        bottomCharScroll.setLayoutManager(layoutManager);

        CharAdapter adapter = new CharAdapter(basic_chars);
        bottomCharScroll.setAdapter(adapter);
        bottomCharScroll.setHasFixedSize(true);

        imgReplayChar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gifCharDisplay.replay();
            }
        });

        imgPlaySound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: play sound of a char
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        gifCharDisplay.replay();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    public class CharAdapter extends RecyclerView.Adapter<CharAdapter.ViewHolder> {
        private String[] dataset;

        public class ViewHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener {
            public TextView view;
            private Context context;

            public ViewHolder(Context context, TextView view) {
                super(view);
                this.view = view;
                this.context = context;
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    currentCharAsset = "hira/" + this.view.getText() + ".gif";
                    Toast.makeText(context, currentCharAsset, Toast.LENGTH_SHORT).show();

                    gifCharDisplay.setGifImageAsset(currentCharAsset);
                    gifCharDisplay.destroyDrawingCache();
                    gifCharDisplay.replay();
                }
            }
        }

        public CharAdapter(String[] dataset) {
            this.dataset = dataset;
        }

        @Override
        public CharAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            TextView view = (TextView) LayoutInflater.from(context)
                    .inflate(R.layout.bottom_text_bar, parent, false);
            ViewHolder viewHolder = new ViewHolder(context, view);
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
