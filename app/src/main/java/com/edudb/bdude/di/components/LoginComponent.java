package com.edudb.bdude.di.components;

import com.edudb.bdude.di.modules.LoginModule;
import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.ui.flow.login.view.LoginActivity;
import dagger.Component;
import dagger.Provides;

@PerActivity
@Component(modules = LoginModule.class, dependencies = ApplicationComponent.class)
public interface LoginComponent {

    void inject (LoginActivity activity);
}
