package com.edudb.bdude.di.modules;

import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.ui.flow.lobby.send_request.contract.SendRequestContract;
import com.edudb.bdude.ui.flow.lobby.send_request.presenter.SendRequestPresenter;
import com.edudb.bdude.ui.flow.lobby.send_request.view.SendRequestActivity;
import dagger.Module;
import dagger.Provides;

@Module
public class SendRequestModule {

    private SendRequestContract.View mView;
    private SendRequestActivity mActivity;

    public SendRequestModule(SendRequestContract.View mView) {
        this.mView = mView;
        mActivity = (SendRequestActivity) mView.getActivity();
    }

    @PerActivity
    @Provides
    SendRequestContract.View provideView() {
        return mView;
    }

    @Provides
    @PerActivity
    SendRequestContract.Presenter providePresenter(SendRequestPresenter presenter ){
        return presenter;
    }

//    @PerActivity
//    @Provides
//    Post provideRequestDetails() {
//        return (Post) mActivity.getIntent().getExtras().getSerializable(REQUEST_DETAILS);
//    }

}
