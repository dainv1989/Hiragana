package com.dainv.hiragana;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dainv.hiragana.model.JPChar;
import com.dainv.hiragana.view.GifImageView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class CharActivity extends AppCompatActivity {

    private static final int HIRAGANA_CHART = 1;
    private static final int KATAKANA_CHART = 2;

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

    public static final String basic_hira[] = {
            "あ", "い", "う", "え", "お",
            "か", "き", "く", "け", "こ",
            "さ", "し", "す", "せ", "そ",
            "た", "ち", "つ", "て", "と",
            "な", "に", "ぬ", "ね", "の",
            "は", "ひ", "ふ", "へ", "ほ",
            "ま", "み", "む", "め", "も",
            "や", "ゆ", "よ",
            "ら", "り", "る", "れ", "ろ",
            "わ", "を",
            "ん"
    };

    public static final String basic_kata[] = {
            "ア", "イ", "ウ", "エ", "オ",
            "カ", "キ", "ク", "ケ", "コ",
            "サ", "シ", "ス", "セ", "ソ",
            "タ", "チ", "ツ", "テ", "ト",
            "ナ", "ニ", "ヌ", "ネ", "ノ",
            "ハ", "ヒ", "フ", "ヘ", "ホ",
            "マ", "ミ", "ム", "メ", "モ",
            "ヤ", "ユ", "ヨ",
            "ラ", "リ", "ル", "レ", "ロ",
            "ワ", "ヲ",
            "ン"
    };

    private static int display_char_type = 0;
    private static int selected_position = 0;

    private RecyclerView bottomCharScroll;
    private GifImageView gifCharDisplay;

    private ImageView imgPlaySound;
    private ImageView imgSwitchChar;

    private TextView txtCharStatic;

    private AdView adView;

    private String currentCharAsset = "hira/a.gif";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char);

        Intent intent = getIntent();
        display_char_type = intent.getIntExtra("CHAR_TYPE", 0);

        imgSwitchChar = (ImageView)findViewById(R.id.imgSwitchKana);
        imgPlaySound = (ImageView)findViewById(R.id.imgCharSound);

        txtCharStatic = (TextView)findViewById(R.id.txtCharStatic);

        gifCharDisplay = (GifImageView)findViewById(R.id.gifCharDisplay);
        bottomCharScroll = (RecyclerView)findViewById(R.id.bottomCharScroll);

        gifCharDisplay.setGifImageAsset(currentCharAsset);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false); /* horizontal display list */
        bottomCharScroll.setLayoutManager(layoutManager);

        CharAdapter adapter = new CharAdapter(basic_chars);
        bottomCharScroll.setAdapter(adapter);
        bottomCharScroll.setHasFixedSize(true);

        imgPlaySound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JPChar.playSound(basic_chars[selected_position], getApplicationContext());
            }
        });

        imgSwitchChar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(display_char_type == HIRAGANA_CHART) {
                    txtCharStatic.setText(basic_kata[selected_position]);
                    currentCharAsset = "kata/" + basic_chars[selected_position] + ".gif";
                    gifCharDisplay.setGifImageAsset(currentCharAsset);

                    display_char_type = KATAKANA_CHART;
                } else {
                    txtCharStatic.setText(basic_hira[selected_position]);
                    currentCharAsset = "hira/" + basic_chars[selected_position] + ".gif";
                    gifCharDisplay.setGifImageAsset(currentCharAsset);

                    display_char_type = HIRAGANA_CHART;
                }
            }
        });

        adView = (AdView)findViewById(R.id.adsCharBanner);
        adView.setVisibility(View.GONE);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("1F17B575D2A0B81A953E526D33694A52")
                .build();
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                adView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                adView.setVisibility(View.GONE);
            }
        });
        adView.loadAd(adRequest);
    }

    @Override
    public void onPause() {
        if (adView != null)
            adView.pause();
        super.onPause();
    }

    @Override
    public void onResume() {
        if (adView != null)
            adView.resume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (adView != null)
            adView.destroy();
        super.onDestroy();
    }

    private class CharAdapter extends RecyclerView.Adapter<CharAdapter.ViewHolder> {
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
                    /* update old as well as new selected position view */
                    notifyItemChanged(selected_position);
                    selected_position = position;
                    notifyItemChanged(selected_position);

                    if(display_char_type == HIRAGANA_CHART) {
                        txtCharStatic.setText(basic_hira[selected_position]);
                        currentCharAsset = "hira/" + basic_chars[selected_position] + ".gif";
                        gifCharDisplay.setGifImageAsset(currentCharAsset);
                    } else {
                        txtCharStatic.setText(basic_kata[selected_position]);
                        currentCharAsset = "kata/" + basic_chars[selected_position] + ".gif";
                        gifCharDisplay.setGifImageAsset(currentCharAsset);
                    }
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

            if (position == selected_position) {
                holder.itemView.setSelected(true);
            } else {
                holder.itemView.setSelected(false);
            }
        }

        @Override
        public int getItemCount() {
            return dataset.length;
        }
    }
}
