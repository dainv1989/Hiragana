package com.dainv.hiragana.model;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by dainv on 5/22/2017.
 */

public final class JPChar {

    private final static String TAG = "JPChar";

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

    public static final int HIRAGANA_CHART = 1;
    public static final int KATAKANA_CHART = 2;
    public static final int TABLE_BASIC = 0;
    public static final int TABLE_DAKUTEN = 1;
    public static final int TABLE_COMBO = 2;

    public static List<AlphabetItem> lstHiraBasic;
    public static List<AlphabetItem> lstHiraDakuten;
    public static List<AlphabetItem> lstHiraCombo;

    public static List<AlphabetItem> lstKataBasic;
    public static List<AlphabetItem> lstKataDakuten;
    public static List<AlphabetItem> lstKataCombo;

    private static boolean is_init = false;

    public static String[] getBasicChars() {
        return basic_chars;
    }

    public static String[] getComboChars() {
        return combo_chars;
    }

    public static String[] getDakutenChars() {
        return dakuten_chars;
    }

    public static void initAlphabetChart() {
        if (is_init == true)
            return;

        int i = 0;
        lstHiraBasic = new ArrayList<>();
        lstHiraCombo = new ArrayList<>();
        lstHiraDakuten = new ArrayList<>();
        lstKataCombo = new ArrayList<>();
        lstKataDakuten = new ArrayList<>();
        lstKataBasic = new ArrayList<>();

        for (i = 0; i < JPChar.basic_chars.length; i++) {
            lstHiraBasic.add(new AlphabetItem(JPChar.basic_hira[i], JPChar.basic_chars[i]));
            lstKataBasic.add(new AlphabetItem(JPChar.basic_kata[i], JPChar.basic_chars[i]));
        }

        for (i = 0; i < JPChar.combo_chars.length; i++) {
            lstHiraCombo.add(new AlphabetItem(JPChar.combo_hira[i], JPChar.combo_chars[i]));
            lstKataCombo.add(new AlphabetItem(JPChar.combo_kata[i], JPChar.combo_chars[i]));
        }

        for (i = 0; i < JPChar.dakuten_chars.length; i++) {
            lstHiraDakuten.add(new AlphabetItem(JPChar.dakuten_hira[i], JPChar.dakuten_chars[i]));
            lstKataDakuten.add(new AlphabetItem(JPChar.dakuten_kata[i], JPChar.dakuten_chars[i]));
        }
        is_init = true;
    }

    private static final int TOTAL_CHARS = 104;
    public static void getFullChars(List<String> lstRoma,
                                    List<String> lstHira,
                                    List<String> lstKata) {
        int i = 0;
        for (i = 0; i < basic_chars.length; i++) {
            if (basic_chars[i] != "") {
                lstRoma.add(basic_chars[i]);
                lstHira.add(basic_hira[i]);
                lstKata.add(basic_kata[i]);
            }
        }

        for (i = 0; i < dakuten_chars.length; i++) {
            lstRoma.add(dakuten_chars[i]);
            lstHira.add(dakuten_hira[i]);
            lstKata.add(dakuten_kata[i]);
        }

        for (i = 0; i < combo_chars.length; i++) {
            lstRoma.add(combo_chars[i]);
            lstHira.add(combo_hira[i]);
            lstKata.add(combo_kata[i]);
        }
    }

    public static void playSound(String fileName, Context context) {
        AssetManager assetManager = context.getAssets();
        final MediaPlayer player = new MediaPlayer();
        try {
            AssetFileDescriptor fd = assetManager.openFd("sound/" + fileName + ".mp3");
            player.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            player.prepare();
            player.start();

            /* release resource after playing audio */
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    player.release();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            Log.v(TAG, "open sound file failed");
        }
    }

    public static final int QTYPE_READ_HIRA = 0;
    public static final int QTYPE_READ_KATA = 1;
    public static final int QTYPE_SOUND_HIRA = 2;
    public static final int QTYPE_SOUND_KATA = 3;
    public static final int QTYPE_READ_HIRA_INV = 4;
    public static final int QTYPE_READ_KATA_INV = 5;

    private static final int TOTAL_ANSWERS = 3;

    public static List<QuestionItem> generateQuestions(int count, int type) {
        int question_count = 0;
        Random random = new Random();
        ArrayList<QuestionItem> questions  = new ArrayList<>();
        ArrayList<AlphabetItem> dictionary = new ArrayList<>();

        if (is_init == false)
            initAlphabetChart();

        switch (type) {
            case QTYPE_READ_HIRA:
            case QTYPE_SOUND_HIRA:
            case QTYPE_READ_HIRA_INV:
                dictionary.addAll(lstHiraBasic);
                dictionary.addAll(lstHiraDakuten);
                dictionary.addAll(lstHiraCombo);
                break;
            case QTYPE_READ_KATA:
            case QTYPE_SOUND_KATA:
            case QTYPE_READ_KATA_INV:
                dictionary.addAll(lstKataBasic);
                dictionary.addAll(lstKataDakuten);
                dictionary.addAll(lstKataCombo);
                break;
            default:
                break;
        }

        /* randomize dictionary */
        Collections.shuffle(dictionary);

        /* build questions and answers */
        int i = 0;
        question_count = 0;
        while (question_count < count) {
            if (dictionary.get(i).getRomaji() != "") {
                String romaji;
                AlphabetItem alphabet = dictionary.get(i);
                QuestionItem question = new QuestionItem();
                ArrayList<String> answers = new ArrayList<>();

                question.setQuestion(alphabet.getJpchar());
                question.setCorrectAnswer(alphabet.getRomaji());
                romaji = alphabet.getRomaji();

                if ((type == QTYPE_SOUND_HIRA) ||
                    (type == QTYPE_SOUND_KATA) ||
                    (type == QTYPE_READ_HIRA_INV) ||
                    (type == QTYPE_READ_KATA_INV)) {
                    question.setQuestion(alphabet.getRomaji());
                    question.setCorrectAnswer(alphabet.getJpchar());
                    romaji = alphabet.getJpchar();
                }
                answers.add(romaji);

                int answer_count = 1;
                int index;
                while (answer_count < TOTAL_ANSWERS) {
                    index = random.nextInt(TOTAL_CHARS);
                    romaji = dictionary.get(index).getRomaji();

                    if ((type == QTYPE_SOUND_HIRA) ||
                        (type == QTYPE_SOUND_KATA) ||
                        (type == QTYPE_READ_HIRA_INV) ||
                        (type == QTYPE_READ_KATA_INV)) {
                        romaji = dictionary.get(index).getJpchar();
                    }

                    if ((index != i) && (romaji != "")) {
                        answers.add(romaji);
                        answer_count++;
                    }
                }

                /* randomize answer list */
                Collections.shuffle(answers);
                question.setAnswers(answers);
                questions.add(question);

                question_count++;
            }
            i++;
        }

        return questions;
    }
}
