package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.menues;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.R;

public class MySettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}