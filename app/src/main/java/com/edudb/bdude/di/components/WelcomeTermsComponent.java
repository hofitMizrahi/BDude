package com.edudb.bdude.di.components;

import com.edudb.bdude.di.modules.WelcomeTermsModule;
import com.edudb.bdude.di.scope.PerFragment;
import com.edudb.bdude.ui.flow.terms_of_use.welcom.view.WelcomeTermsFragment;

import dagger.Component;

@PerFragment
@Component(modules = WelcomeTermsModule.class, dependencies = ApplicationComponent.class)
public interface WelcomeTermsComponent {

    void inject(WelcomeTermsFragment fragment);
}
