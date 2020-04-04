package com.edudb.bdude.di.components;

import com.edudb.bdude.di.modules.HealthTermsModule;
import com.edudb.bdude.di.scope.PerFragment;
import com.edudb.bdude.ui.flow.intro.health_terms.view.HealthTermsFragment;
import dagger.Component;

@PerFragment
@Component(modules = HealthTermsModule.class, dependencies = ApplicationComponent.class)
public interface HealthTermsComponent {

    void inject (HealthTermsFragment fragment);
}
