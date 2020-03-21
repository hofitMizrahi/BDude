package com.edudb.bdude.di.components;

import com.edudb.bdude.di.modules.TermsOfUseModule;
import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.ui.flow.terms_of_use.view.TermsOfUseActivity;
import dagger.Component;

@PerActivity
@Component(modules = TermsOfUseModule.class, dependencies = ApplicationComponent.class)
public interface TermsOfUseComponent {

    void inject(TermsOfUseActivity activity);
}
