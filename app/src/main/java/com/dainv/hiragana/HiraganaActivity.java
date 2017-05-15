package com.dainv.hiragana;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.dainv.hiragana.view.AlphabetAdapter;
import com.dainv.hiragana.view.AlphabetItem;
import com.dainv.hiragana.view.StaticGridView;

import java.util.ArrayList;
import java.util.List;

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
            "ビャ", "", "ビョ",
            "ピャ", "ピュ", "ピョ"
    };

    private static final int HIRAGANA_CHART = 1;
    private static final int KATAKANA_CHART = 2;

    private GridView gvBasicChart;
    private GridView gvDakutenChart;
    private GridView gvComboChart;

    private ImageView btnSwitchChart;

    private static List<AlphabetItem> lstHiraBasic;
    private static List<AlphabetItem> lstHiraDakuten;
    private static List<AlphabetItem> lstHiraCombo;

    private static List<AlphabetItem> lstKataBasic;
    private static List<AlphabetItem> lstKataDakuten;
    private static List<AlphabetItem> lstKataCombo;

    private static boolean is_init = false;
    private static int current_chart = HIRAGANA_CHART;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiragana);

        gvBasicChart = (StaticGridView)findViewById(R.id.gridBasic);
        gvDakutenChart = (StaticGridView)findViewById(R.id.gridDakuten);
        gvComboChart = (StaticGridView)findViewById(R.id.gridCombo);

        btnSwitchChart = (ImageView)findViewById(R.id.btnSwitch);

        if (is_init == false) {
            lstHiraBasic = new ArrayList<>();
            lstHiraCombo = new ArrayList<>();
            lstHiraDakuten = new ArrayList<>();
            lstKataCombo = new ArrayList<>();
            lstKataDakuten = new ArrayList<>();
            lstKataBasic = new ArrayList<>();

            initAlphabetChart();
            is_init = true;
        }

        Intent intent = getIntent();
        int chart_type = intent.getIntExtra("CHART_TYPE", HIRAGANA_CHART);
        if ((chart_type == HIRAGANA_CHART) || (chart_type == KATAKANA_CHART))
            showChart(chart_type);

        btnSwitchChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current_chart == HIRAGANA_CHART) {
                    showChart(KATAKANA_CHART);
                    current_chart = KATAKANA_CHART;
                } else {
                    showChart(HIRAGANA_CHART);
                    current_chart = HIRAGANA_CHART;
                }
            }
        });
    }

    private void showChart(int chartType) {
        AlphabetAdapter adapterBasic = null;
        AlphabetAdapter adapterDakuten = null;
        AlphabetAdapter adapterCombo = null;

        if(chartType == KATAKANA_CHART) {
            adapterBasic = new AlphabetAdapter(this, gvBasicChart.getId(), lstKataBasic);
            adapterDakuten = new AlphabetAdapter(this, gvBasicChart.getId(), lstKataDakuten);
            adapterCombo = new AlphabetAdapter(this, gvBasicChart.getId(), lstKataCombo);
            current_chart = KATAKANA_CHART;
        }
        if(chartType == HIRAGANA_CHART) {
            adapterBasic = new AlphabetAdapter(this, gvBasicChart.getId(), lstHiraBasic);
            adapterDakuten = new AlphabetAdapter(this, gvBasicChart.getId(), lstHiraDakuten);
            adapterCombo = new AlphabetAdapter(this, gvBasicChart.getId(), lstHiraCombo);
            current_chart = KATAKANA_CHART;
        }
        gvBasicChart.setAdapter(adapterBasic);
        gvDakutenChart.setAdapter(adapterDakuten);
        gvComboChart.setAdapter(adapterCombo);
    }

    private void initAlphabetChart() {
        int i = 0;
        for (i = 0; i < basic_chars.length; i++) {
            lstHiraBasic.add(new AlphabetItem(basic_hira[i], basic_chars[i]));
            lstKataBasic.add(new AlphabetItem(basic_kata[i], basic_chars[i]));
        }

        for (i = 0; i < combo_chars.length; i++) {
            lstHiraCombo.add(new AlphabetItem(combo_hira[i], combo_chars[i]));
            lstKataCombo.add(new AlphabetItem(combo_kata[i], combo_chars[i]));
        }

        for (i = 0; i < dakuten_chars.length; i++) {
            lstHiraDakuten.add(new AlphabetItem(dakuten_hira[i], dakuten_chars[i]));
            lstKataDakuten.add(new AlphabetItem(dakuten_kata[i], dakuten_chars[i]));
        }
    }
}
