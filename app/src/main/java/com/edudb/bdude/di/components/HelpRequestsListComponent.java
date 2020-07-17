package com.edudb.bdude.di.components;

import com.edudb.bdude.di.modules.HelpRequestsModule;
import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.ui.flow.lobby.main_screen.view.HelpRequestsListActivity;

import dagger.Component;

@PerActivity
@Component(modules = HelpRequestsModule.class, dependencies = ApplicationComponent.class)
public interface HelpRequestsListComponent {

    void inject (HelpRequestsListActivity activity);
}
