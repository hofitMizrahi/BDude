package com.edudb.bdude.di.modules;

import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.ui.flow.lobby.requests_list_screen.view.adapter.items_adapter.ProductsItemsAdapter;
import com.edudb.bdude.ui.flow.lobby.send_request.contract.SendRequestContract;
import com.edudb.bdude.ui.flow.lobby.send_request.presenter.SendRequestPresenter;
import com.edudb.bdude.ui.flow.lobby.send_request.view.SendRequestActivity;
import dagger.Module;
import dagger.Provides;

import static com.edudb.bdude.ui.flow.lobby.create_new_help_request.view.CreateHelpRequestActivity.POST_OBJ;

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

    @PerActivity
    @Provides
    HelpRequest provideRequestDetails() {
        return (HelpRequest) mActivity.getIntent().getExtras().getParcelable(POST_OBJ);
    }

}
