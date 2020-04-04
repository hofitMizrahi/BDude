package com.edudb.bdude.di.components;

import com.edudb.bdude.di.modules.IntroTermsModule;
import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.ui.flow.intro.container.view.IntroTermsActivity;
import dagger.Component;

@PerActivity
@Component(modules = IntroTermsModule.class, dependencies = ApplicationComponent.class)
public interface IntroTermsComponent {

    void inject(IntroTermsActivity activity);
}
