package com.edudb.bdude.di.modules;

import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.ui.flow.lobby.create_new_help_request.contract.CreateHelpRequestContract;
import com.edudb.bdude.ui.flow.lobby.create_new_help_request.presenter.CreateHelpRequestPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class CreateHelpRequestModule {

    CreateHelpRequestContract.View mView;

    public CreateHelpRequestModule(CreateHelpRequestContract.View mView) {
        this.mView = mView;
    }

    @PerActivity
    @Provides
    CreateHelpRequestContract.View provideView(){
        return  mView;
    }

    @Provides
    @PerActivity
    CreateHelpRequestContract.Presenter providePresenter(CreateHelpRequestPresenter presenter ){
        return presenter;
    }
}
