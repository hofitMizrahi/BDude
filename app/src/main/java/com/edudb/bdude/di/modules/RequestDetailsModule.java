package com.edudb.bdude.di.modules;

import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.ui.flow.lobby.request_details.contract.RequestDetailsContract;
import dagger.Module;
import dagger.Provides;

@Module
public class RequestDetailsModule {

    private RequestDetailsContract.View mView;

    public RequestDetailsModule(RequestDetailsContract.View mView) {
        this.mView = mView;
    }

    @PerActivity
    @Provides
    RequestDetailsContract.View provideView(){
        return  mView;
    }

}
