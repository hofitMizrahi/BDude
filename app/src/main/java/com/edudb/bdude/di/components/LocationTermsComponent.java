package com.edudb.bdude.di.components;

import com.edudb.bdude.di.modules.LocationTermsModule;
import com.edudb.bdude.di.scope.PerFragment;
import com.edudb.bdude.ui.flow.terms_of_use.location_approve.view.LocationTermsFragment;

import dagger.Component;

@PerFragment
@Component(modules = LocationTermsModule.class, dependencies = ApplicationComponent.class)
public interface LocationTermsComponent {

    void inject(LocationTermsFragment fragment);
}
