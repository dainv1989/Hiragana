package com.dainv.hiragana.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import com.dainv.hiragana.R;

/**
 * Created by dainv on 17/07/21.
 */

public class Settings {

    private Context context = null;
    private Resources resources = null;
    private static SharedPreferences.Editor editor = null;
    private static SharedPreferences preferences = null;

    public Settings(Context context) {
        if (this.context != context) {
            this.context = context;
            resources = context.getResources();
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
    }

    /* [START] ninja icon settings */
    public boolean getNinjaSoundConfig() {
        return getBooleanKey(resources.getString(
                R.string.pref_ninja_touch_sound_key));
    }

    public boolean getNinjaUpdateMode() {
        return getBooleanKey(resources.getString(
                R.string.pref_ninja_random_mode_key));
    }

    public int getNinjaInterval() {
        return getIntKey(resources.getString(
                R.string.pref_ninja_timer_key));
    }
    /* [END] ninja icon settings */

    /* [START] exercise settings */
    public int getQuestionCount() {
        return getIntKey(resources.getString(
                R.string.pref_exercise_question_key));
    }

    public void setQuestionCount(int numbers) {
        setIntKey(resources.getString(
                R.string.pref_exercise_question_key),
                numbers);
    }

    public boolean getExerciseSoundConfig() {
        return getBooleanKey(resources.getString(
                R.string.pref_exercise_sound_key));
    }

    public void setExerciseSoundConfig(boolean is_enable) {
        setBooleanKey(resources.getString(
                R.string.pref_exercise_sound_key),
                is_enable);
    }
    /* [END] exercise settings */

    private void setBooleanKey(String booleanKey, boolean value) {
        editor = preferences.edit();
        editor.putBoolean(booleanKey, value);
        editor.apply();
    }

    private boolean getBooleanKey(String booleanKey) {
        return preferences.getBoolean(booleanKey, true);
    }

    private void setIntKey(String strKey, int value) {
        editor = preferences.edit();
        editor.putInt(strKey, value);
        editor.apply();
    }

    private int getIntKey(String strKey) {
        Integer value = preferences.getInt(strKey, 10);
        return value.intValue();
    }
}
