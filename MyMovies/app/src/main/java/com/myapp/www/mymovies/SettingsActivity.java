package com.myapp.www.mymovies;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
public class SettingsActivity extends PreferenceActivity
        implements Preference.OnPreferenceChangeListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref_general);
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_sort_key)));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

        String stringValue = newValue.toString();
        if(preference instanceof ListPreference)
        {
            ListPreference listPreference=(ListPreference) preference;
            int prefndex=listPreference.findIndexOfValue(stringValue);

            if(prefndex >= 0)
            {
                preference.setSummary(listPreference.getEntries()[prefndex]);

            }
            else{
                preference.setSummary(stringValue);
            }
        }


        return true;
    }
    private void bindPreferenceSummaryToValue(Preference preference)
    {
        preference.setOnPreferenceChangeListener(this);
        onPreferenceChange(preference, getPreferenceManager().getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(),""));
    }

}
