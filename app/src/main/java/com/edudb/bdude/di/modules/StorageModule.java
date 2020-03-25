package com.edudb.bdude.di.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.edudb.bdude.di.qualifier.ApplicationContext;
import com.edudb.bdude.di.scope.PerApplication;
import com.edudb.bdude.shared_preferences.SharedPrefsController;
import com.edudb.bdude.shared_preferences.SharedPrefsControllerImpl;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import dagger.Module;
import dagger.Provides;

@Module
public class StorageModule {

    @Provides
    @PerApplication
    SharedPreferences sharedPreferences(@ApplicationContext Context appContext) {
        return PreferenceManager.getDefaultSharedPreferences(appContext);
    }

    @Provides
    @PerApplication
    SharedPrefsController sharedPreferencesController(SharedPrefsControllerImpl sharedPrefsController) {
        return sharedPrefsController;
    }

    @Provides
    @PerApplication
    FirebaseRemoteConfig provideFirebaseRemoteConfig(SharedPrefsControllerImpl sharedPrefsController) {
        FirebaseRemoteConfig config = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build();
        config.setConfigSettingsAsync(configSettings);
        return config;
    }
}
