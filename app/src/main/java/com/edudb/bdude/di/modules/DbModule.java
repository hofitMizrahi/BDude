package com.edudb.bdude.di.modules;

import com.edudb.bdude.db.DatabaseController;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

    @Provides
    @Singleton
    DatabaseController provideDb(){
        return DatabaseController.getInstance();
    }
}
