package com.edudb.bdude.di.components;

import com.edudb.bdude.di.modules.MyRequestsModule;
import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.ui.flow.lobby.my_requests.view.MyRequestsActivity;
import dagger.Component;

@PerActivity
@Component(modules = MyRequestsModule.class, dependencies = ApplicationComponent.class)
public interface MyRequestsComponent {

    void inject (MyRequestsActivity activity);
}
