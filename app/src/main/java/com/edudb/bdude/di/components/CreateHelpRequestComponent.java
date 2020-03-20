package com.edudb.bdude.di.components;

import com.edudb.bdude.di.modules.CreateHelpRequestModule;
import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.ui.flow.lobby.create_new_help_request.view.CreateHelpRequestActivity;
import dagger.Component;

@PerActivity
@Component(modules = CreateHelpRequestModule.class, dependencies = ApplicationComponent.class)
public interface CreateHelpRequestComponent {

    void inject (CreateHelpRequestActivity activity);
}
