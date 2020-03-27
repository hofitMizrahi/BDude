package com.edudb.bdude.application;

import android.app.Application;
import com.edudb.bdude.di.components.ApplicationComponent;
import com.edudb.bdude.di.components.DaggerApplicationComponent;
import com.edudb.bdude.di.modules.ApplicationModule;
import com.edudb.bdude.location.LocationHelper;

public class BDudeApplication extends Application {

    private static BDudeApplication mAppInstance;
    private ApplicationComponent mApplicationComponent;

    public static BDudeApplication getInstance() {
        return mAppInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initCommonAppComponents();
        initSingletonDependencies();

        LocationHelper.checkFirstLocation(getApplicationContext());
    }

    public ApplicationComponent getApplicationComponent(){
        if (mApplicationComponent == null)
            initSingletonDependencies();
        return mApplicationComponent;
    }

    private void initSingletonDependencies() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
    }

    private void initCommonAppComponents() {
        mAppInstance = this;
    }
}
