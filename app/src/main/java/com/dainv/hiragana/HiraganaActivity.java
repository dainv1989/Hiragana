package com.dainv.hiragana;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.dainv.hiragana.model.JPChar;
import com.dainv.hiragana.view.ChartFragment;

public class HiraganaActivity extends AppCompatActivity {

    private static final String basic_chars[] = {
            "a", "i", "u", "e", "o",
            "ka", "ki", "ku", "ke", "ko",
            "sa", "shi", "su", "se", "so",
            "ta", "chi", "tsu", "te", "to",
            "na", "ni", "nu", "ne", "no",
            "ha", "hi", "fu", "he", "ho",
            "ma", "mi", "mu", "me", "mo",
            "ya", "", "yu", "", "yo",
            "ra", "ri", "ru", "re", "ro",
            "wa", "", "", "", "wo",
            "n"
    };

    private static final String dakuten_chars[] = {
            "ga", "gi", "gu", "ge", "go",
            "za", "ji", "zu", "ze", "zo",
            "da", "di", "du", "de", "do",
            "ba", "bi", "bu", "be", "bo",
            "pa", "pi", "pu", "pe", "po"
    };

    private static final String combo_chars[] = {
            "kya", "kyu", "kyo",
            "sha", "shu", "sho",
            "cha", "chu", "cho",
            "nya", "nyu", "nyo",
            "hya", "hyu", "hyo",
            "mya", "myu", "myo",
            "rya", "ryu", "ryo",
            "gya", "gyu", "gyo",
            "ja",  "ju",  "jo",
            "bya", "byu", "byo",
            "pya", "pyu", "pyo"
    };

    private static final String basic_hira[] = {
            "あ", "い", "う", "え", "お",
            "か", "き", "く", "け", "こ",
            "さ", "し", "す", "せ", "そ",
            "た", "ち", "つ", "て", "と",
            "な", "に", "ぬ", "ね", "の",
            "は", "ひ", "ふ", "へ", "ほ",
            "ま", "み", "む", "め", "も",
            "や", "", "ゆ", "", "よ",
            "ら", "り", "る", "れ", "ろ",
            "わ", "", "", "", "を",
            "ん"
    };

    private static final String dakuten_hira[] = {
            "が", "ぎ", "ぐ", "げ", "ご",
            "ざ", "じ", "ず", "ぜ", "ぞ",
            "だ", "ぢ", "づ", "で", "ど",
            "ば", "び", "ぶ", "べ", "ぼ",
            "ぱ", "ぴ", "ぷ", "ぺ", "ぽ"
    };

    private static final String combo_hira[] = {
            "きゃ", "きゅ", "きょ",
            "しゃ", "しゅ", "しょ",
            "ちゃ", "ちゅ", "ちょ",
            "にゃ", "にゅ", "にょ",
            "ひゃ", "ひゅ", "ひょ",
            "みゃ", "みゅ", "みょ",
            "りゃ", "りゅ", "りょ",
            "ぎゃ", "ぎゅ", "ぎょ",
            "じゃ",  "じゅ", "じょ",
            "びゃ", "びゅ", "びょ",
            "びゃ", "ぴゅ", "ぴょ"
    };

    private static final String basic_kata[] = {
            "ア", "イ", "ウ", "エ", "オ",
            "カ", "キ", "ク", "ケ", "コ",
            "サ", "シ", "ス", "セ", "ソ",
            "タ", "チ", "ツ", "テ", "ト",
            "ナ", "ニ", "ヌ", "ネ", "ノ",
            "ハ", "ヒ", "フ", "ヘ", "ホ",
            "マ", "ミ", "ム", "メ", "モ",
            "ヤ", "", "ユ", "", "ヨ",
            "ラ", "リ", "ル", "レ", "ロ",
            "ワ", "", "", "", "ヲ",
            "ン"
    };

    private static final String dakuten_kata[] = {
            "ガ", "ギ", "グ", "ゲ", "ゴ",
            "ザ", "ジ", "ズ", "ゼ", "ゾ",
            "ダ", "ヂ", "ヅ", "デ", "ド",
            "バ", "ビ", "ブ", "ベ", "ボ",
            "パ", "ピ", "プ", "ペ", "ポ"
    };

    private static final String combo_kata[] = {
            "キャ", "キュ", "キョ",
            "シャ", "シュ", "ショ",
            "チャ", "チュ", "チョ",
            "ニャ", "ニュ", "ニョ",
            "ヒャ", "ヒュ", "ヒョ",
            "ミャ", "ミュ", "ミョ",
            "リャ", "リュ", "リョ",
            "ギャ", "ギュ", "ギョ",
            "ジャ",  "ジュ", "ジョ",
            "ビャ", "ビュ", "ビョ",
            "ピャ", "ピュ", "ピョ"
    };

    private ImageView btnSwitchChart;
    private ImageView btnWriting;
    private ImageView btnExercise;
    private ImageView btnPlaySound;

    private TabLayout tabChartType;
    private ViewPager pagerChart;
    private PagerAdapter pagerAdapter;

    private static boolean is_playing = false;
    private static int current_chart = JPChar.HIRAGANA_CHART;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiragana);

        btnSwitchChart = (ImageView)findViewById(R.id.btnSwitch);
        btnPlaySound = (ImageView)findViewById(R.id.btnPlayAll);
        btnExercise = (ImageView)findViewById(R.id.btnHiraExer);
        btnWriting = (ImageView)findViewById(R.id.btnPencil);

        final Context context = this;
        Intent intent = getIntent();
        int chart_type = intent.getIntExtra("CHART_TYPE", JPChar.HIRAGANA_CHART);
        if ((chart_type == JPChar.HIRAGANA_CHART) || (chart_type == JPChar.KATAKANA_CHART))
            current_chart = chart_type;

        /* tablayout implement */
        tabChartType = (TabLayout)findViewById(R.id.tabChartType);
        tabChartType.addTab(tabChartType.newTab().setText(R.string.basic_header));
        tabChartType.addTab(tabChartType.newTab().setText(R.string.dakuten_header));
        tabChartType.addTab(tabChartType.newTab().setText(R.string.combo_header));

        pagerChart = (ViewPager)findViewById(R.id.pagerChart);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerChart.setAdapter(pagerAdapter);
        pagerChart.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tabChartType));

        tabChartType.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateChartView();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                /* Do nothing */
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                /* Do nothing */
            }
        });
        /* end tablayout */

        btnSwitchChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current_chart == JPChar.HIRAGANA_CHART)
                    current_chart = JPChar.KATAKANA_CHART;
                else
                    current_chart = JPChar.HIRAGANA_CHART;
                updateChartView();
                pagerAdapter.notifyDataSetChanged();
            }
        });

        btnPlaySound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_playing == false) {
                    // TODO: play all sounds
                    btnPlaySound.setImageResource(R.drawable.stop);
                    is_playing = true;
                } else {
                    stopPlaySound();
                    is_playing = false;
                }
            }
        });

        btnExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaySound();
                Intent intent = new Intent(context, ExcerciseActivity.class);
                // TODO: send type of exercise
                context.startActivity(intent);
            }
        });

        btnWriting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaySound();
                Intent intent = new Intent(context, CharActivity.class);
                intent.putExtra("CHAR_TYPE", current_chart);
                context.startActivity(intent);
            }
        });
    }

    private void playAllSounds() {
        // TODO:
    }

    private void stopPlaySound() {
        // TODO:
        btnPlaySound.setImageResource(R.drawable.play);
    }

    private void updateChartView() {
        int tab_index = tabChartType.getSelectedTabPosition();
        pagerChart.setCurrentItem(tab_index);
    }

    private final static int NUMBER_OF_TABS = 3;
    private class PagerAdapter extends FragmentStatePagerAdapter {
        private int numTabs = NUMBER_OF_TABS;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ChartFragment fragment = ChartFragment.newInstance(current_chart, position);
            return fragment;
        }

        @Override
        public int getCount() {
            return numTabs;
        }

        /**
         * This function helps force to switch chart table from hiragana to katakana
         * and vice versa
         */
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
