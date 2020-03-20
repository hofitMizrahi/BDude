package com.edudb.bdude.di.modules;

import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.ui.flow.lobby.requests_list_screen.contract.HelpRequestsListContract;
import com.edudb.bdude.ui.flow.lobby.requests_list_screen.presenter.HelpRequestsListPresenter;
import com.edudb.bdude.ui.flow.lobby.requests_list_screen.view.adapter.HelpRequestsRecyclerAdapter;
import dagger.Module;
import dagger.Provides;

@Module
public class HelpRequestsModule {

    private HelpRequestsListContract.View mView;

    public HelpRequestsModule(HelpRequestsListContract.View view) {
        mView = view;
    }

    @PerActivity
    @Provides
    HelpRequestsListContract.View provideView(){
        return  mView;
    }

    @PerActivity
    @Provides
    HelpRequestsRecyclerAdapter provideAdapter(){
        return  new HelpRequestsRecyclerAdapter();
    }

    @Provides
    @PerActivity
    HelpRequestsListContract.Presenter providePresenter(HelpRequestsListPresenter presenter ){
        return presenter;
    }


}
