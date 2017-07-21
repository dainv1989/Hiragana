package com.dainv.hiragana;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import com.dainv.hiragana.model.Settings;

/**
 * Created by dainv on 17/07/20.
 */

public class SettingFragment extends PreferenceFragment implements
        SharedPreferences.OnSharedPreferenceChangeListener {
    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        PreferenceScreen prefScreen = getPreferenceScreen();
        SharedPreferences settings = prefScreen.getSharedPreferences();
        Resources resources = getResources();

        setNinjaTimerSummary(settings, resources);
        setQACountSummary(settings, resources);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public  void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Resources resources = getResources();

        if(key.equals(resources.getString(R.string.pref_exercise_question_key)))
            setQACountSummary(sharedPreferences, resources);

        if(key.equals(resources.getString(R.string.pref_ninja_timer_key)))
            setNinjaTimerSummary(sharedPreferences, resources);
    }

    private void setNinjaTimerSummary(SharedPreferences settings, Resources resources) {
        String strTimerKey = resources.getString(R.string.pref_ninja_timer_key);
        String strTimer = settings.getString(strTimerKey, "5");

        Preference prefTimer = findPreference(strTimerKey);

        if(strTimer.equals("0")) {
            prefTimer.setSummary(resources.getString(R.string.pref_timer_off));
        } else {
            prefTimer.setSummary(resources.getString(R.string.pref_ninja_timer_summary) + " " +
                    strTimer + " " +
                    resources.getString(R.string.pref_timer_unit));
        }
    }

    private void setQACountSummary(SharedPreferences settings, Resources resources) {
        String strQACountKey = resources.getString(R.string.pref_exercise_question_key);
        String strQACount = settings.getString(strQACountKey, "10");

        Preference prefQACount = findPreference(strQACountKey);
        prefQACount.setSummary(strQACount + " " +
                resources.getString(R.string.pref_question_unit));
    }
}
