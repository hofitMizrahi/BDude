package com.edudb.bdude.di.modules;

import com.edudb.bdude.db.modules.Post;
import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.ui.flow.lobby.request_details.contract.RequestDetailsContract;
import com.edudb.bdude.ui.flow.lobby.request_details.view.RequestDetailsActivity;
import com.edudb.bdude.ui.flow.lobby.requests_list_screen.view.adapter.items_adapter.ProductsItemsAdapter;

import dagger.Module;
import dagger.Provides;

import static com.edudb.bdude.ui.base.BaseActivity.REQUEST_DETAILS;

@Module
public class RequestDetailsModule {

    private RequestDetailsContract.View mView;
    private RequestDetailsActivity mActivity;

    public RequestDetailsModule(RequestDetailsContract.View mView) {
        this.mView = mView;
        mActivity = (RequestDetailsActivity) mView.getActivity();
    }

    @PerActivity
    @Provides
    RequestDetailsContract.View provideView() {
        return mView;
    }

    @PerActivity
    @Provides
    Post provideRequestDetails() {
        return (Post) mActivity.getIntent().getExtras().getParcelable(REQUEST_DETAILS);
    }
}
