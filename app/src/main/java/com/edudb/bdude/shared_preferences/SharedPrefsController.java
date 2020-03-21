package com.edudb.bdude.shared_preferences;

public interface SharedPrefsController {

    void putString(String key, String serialized);

    String getString(String key);

    boolean getBoolean(String key);

    void putBoolean(String key, boolean value);

    void putLong(String key, long value);

    Long getLong(String key);

    void removeByKey(String key);
}
