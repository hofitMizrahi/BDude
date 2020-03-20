package com.edudb.bdude.di.modules;

import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.ui.flow.lobby.my_requests.contract.MyRequestsContract;
import com.edudb.bdude.ui.flow.lobby.my_requests.presenter.MyRequestsPresenter;
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

    @Provides
    @PerActivity
    MyRequestsContract.Presenter providePresenter(MyRequestsPresenter presenter ){
        return presenter;
    }
}
