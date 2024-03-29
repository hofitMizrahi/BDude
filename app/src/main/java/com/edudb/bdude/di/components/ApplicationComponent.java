package com.edudb.bdude.di.components;

import android.content.Context;

import com.edudb.bdude.db.DatabaseController;
import com.edudb.bdude.di.modules.ApplicationModule;
import com.edudb.bdude.di.modules.DbModule;
import com.edudb.bdude.di.modules.StorageModule;
import com.edudb.bdude.di.qualifier.ApplicationContext;
import com.edudb.bdude.di.scope.PerApplication;
import com.edudb.bdude.shared_preferences.SharedPrefsController;
import com.edudb.bdude.ui.base.BaseActivity;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import dagger.Component;

@PerApplication
@Component(modules = {ApplicationModule.class, DbModule.class, StorageModule.class})
public interface ApplicationComponent {

    @ApplicationContext
    Context provideAppContext();

    SharedPrefsController sharedPrefsController();

    FirebaseRemoteConfig firebaseConfig();

    void inject(BaseActivity baseActivity);

    void inject(DatabaseController db);
}
