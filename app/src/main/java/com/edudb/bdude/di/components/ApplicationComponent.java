package com.edudb.bdude.di.components;

import android.content.Context;
import com.edudb.bdude.di.modules.ApplicationModule;
import com.edudb.bdude.di.qualifier.ApplicationContext;
import com.edudb.bdude.di.scope.PerApplication;
import com.edudb.bdude.ui.base.BaseActivity;
import dagger.Component;

@PerApplication
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    @ApplicationContext
    Context provideAppContext();

    void inject(BaseActivity baseActivity);
}
