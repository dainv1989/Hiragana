package com.dainv.hiragana;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by dainv on 17/07/20.
 */

public class SettingFragment extends PreferenceFragment {
    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
