package com.dainv.hiragana;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.dainv.hiragana.view.AlphabetAdapter;
import com.dainv.hiragana.view.AlphabetItem;
import com.dainv.hiragana.view.StaticGridView;

import java.util.ArrayList;
import java.util.List;

public class HiraganaActivity extends AppCompatActivity {

    private static String basic_chars[] = {
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

    private static String dakuten_chars[] = {
            "ga", "gi", "gu", "ge", "go",
            "za", "ji", "zu", "ze", "zo",
            "da", "di", "du", "de", "do",
            "ba", "bi", "bu", "be", "bo",
            "pa", "pi", "pu", "pe", "po"
    };

    private static String combo_chars[] = {
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

    private static String basic_hira[] = {
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

    private static String dakuten_hira[] = {
            "が", "ぎ", "ぐ", "げ", "ご",
            "ざ", "じ", "ず", "ぜ", "ぞ",
            "だ", "ぢ", "づ", "で", "ど",
            "ば", "び", "ぶ", "べ", "ぼ",
            "ぱ", "ぴ", "ぷ", "ぺ", "ぽ"
    };

    private static String combo_hira[] = {
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

    private GridView gvBasicChart;
    private GridView gvDakutenChart;
    private GridView gvComboChart;

    private static List<AlphabetItem> lstHiraBasic;
    private static List<AlphabetItem> lstHiraDakuten;
    private static List<AlphabetItem> lstHiraCombo;

    private static List<AlphabetItem> lstKataBasic;
    private static List<AlphabetItem> lstKataDakuten;
    private static List<AlphabetItem> lstKataCombo;
    private static boolean is_init = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiragana);

        gvBasicChart = (StaticGridView)findViewById(R.id.gridBasic);
        gvDakutenChart = (StaticGridView)findViewById(R.id.gridDakuten);
        gvComboChart = (StaticGridView)findViewById(R.id.gridCombo);

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

        AlphabetAdapter adapterBasic = new AlphabetAdapter(this, gvBasicChart.getId(), lstHiraBasic);
        AlphabetAdapter adapterDakuten = new AlphabetAdapter(this, gvBasicChart.getId(), lstHiraDakuten);
        AlphabetAdapter adapterCombo = new AlphabetAdapter(this, gvBasicChart.getId(), lstHiraCombo);

        gvBasicChart.setAdapter(adapterBasic);
        gvDakutenChart.setAdapter(adapterDakuten);
        gvComboChart.setAdapter(adapterCombo);
    }

    private void initAlphabetChart() {
        int i = 0;
        for (i = 0; i < basic_chars.length; i++) {
            lstHiraBasic.add(new AlphabetItem(basic_hira[i], basic_chars[i]));
        }

        for (i = 0; i < combo_chars.length; i++) {
            lstHiraCombo.add(new AlphabetItem(combo_hira[i], combo_chars[i]));
        }

        for (i = 0; i < dakuten_chars.length; i++) {
            lstHiraDakuten.add(new AlphabetItem(dakuten_hira[i], dakuten_chars[i]));
        }
    }
}
