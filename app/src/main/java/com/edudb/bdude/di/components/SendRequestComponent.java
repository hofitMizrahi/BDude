package com.edudb.bdude.di.components;

import com.edudb.bdude.di.modules.SendRequestModule;
import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.ui.flow.lobby.send_request.view.SendRequestActivity;
import dagger.Component;

@PerActivity
@Component(modules = SendRequestModule.class, dependencies = ApplicationComponent.class)
public interface SendRequestComponent {

    void inject(SendRequestActivity activity);
}
