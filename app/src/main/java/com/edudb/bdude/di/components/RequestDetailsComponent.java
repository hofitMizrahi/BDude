package com.edudb.bdude.di.components;

import com.edudb.bdude.di.modules.RequestDetailsModule;
import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.ui.flow.lobby.request_details.view.RequestDetailsActivity;
import dagger.Component;

@PerActivity
@Component(modules = RequestDetailsModule.class, dependencies = ApplicationComponent.class)
public interface RequestDetailsComponent {

    void inject (RequestDetailsActivity activity);
}
