package com.edudb.bdude.di.modules;

import com.edudb.bdude.db.FirebaseDbHelper;
import com.edudb.bdude.di.scope.PerApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

    @Provides
    @Singleton
    FirebaseDbHelper provideDb(){
        return FirebaseDbHelper.getInstance();
    }
}
