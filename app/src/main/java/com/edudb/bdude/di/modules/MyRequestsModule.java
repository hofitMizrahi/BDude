package com.edudb.bdude.di.modules;

import com.edudb.bdude.db.modules.User;
import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.session.SessionManager;
import com.edudb.bdude.ui.flow.lobby.my_requests.contract.MyRequestsContract;
import com.edudb.bdude.ui.flow.lobby.my_requests.presenter.MyRequestsPresenter;
import com.edudb.bdude.ui.flow.lobby.my_requests.view.adapter.MyRequestsRecyclerAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class MyRequestsModule {

    MyRequestsContract.View mView;

    public MyRequestsModule(MyRequestsContract.View mView) {
        this.mView = mView;
    }

    @PerActivity
    @Provides
    MyRequestsContract.View provideView(){
        return  mView;
    }

    @PerActivity
    @Provides
    User provideUser(){
        return SessionManager.getInstance().getUser();
    }

    @PerActivity
    @Provides
    MyRequestsRecyclerAdapter provideAdapter(){
        return new MyRequestsRecyclerAdapter();
    }

    @Provides
    @PerActivity
    MyRequestsContract.Presenter providePresenter(MyRequestsPresenter presenter ){
        return presenter;
    }
}
