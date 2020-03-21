package com.edudb.bdude.shared_preferences;

import android.content.SharedPreferences;
import javax.inject.Inject;

public class SharedPrefsControllerImpl implements SharedPrefsController {

    @Inject
    SharedPreferences mSharedPreferences;

    @Inject
    public SharedPrefsControllerImpl() {
    }


    @Override
    public void putString(String key, String value) {
        mSharedPreferences.edit()
                .putString(key, value)
                .apply();
    }

    @Override
    public void putBoolean(String key, boolean b) {
        mSharedPreferences.edit()
                .putBoolean(key, b)
                .apply();
    }

    @Override
    public String getString(String key) {
        return mSharedPreferences.getString(key, "");
    }

    @Override
    public void putLong(String key, long value) {
        mSharedPreferences.edit()
                .putLong(key, value)
                .apply();
    }

    @Override
    public Long getLong(String key) {
        return mSharedPreferences.getLong(key, 0);
    }

    @Override
    public boolean getBoolean(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    @Override
    public void removeByKey(String key) {
        mSharedPreferences.edit().remove(key).apply();
    }
}
