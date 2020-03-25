package com.edudb.bdude.di.modules;

import com.algolia.search.saas.AbstractQuery;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import com.edudb.bdude.db.AlgoliaSearch;
import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.location.LocationHelper;
import com.edudb.bdude.session.SessionManager;
import com.edudb.bdude.ui.base.BaseActivity;
import com.edudb.bdude.ui.flow.lobby.requests_list_screen.contract.HelpRequestsListContract;
import com.edudb.bdude.ui.flow.lobby.requests_list_screen.presenter.HelpRequestsListPresenter;
import com.edudb.bdude.ui.flow.lobby.requests_list_screen.view.adapter.HelpRequestsRecyclerAdapter;
import com.google.firebase.firestore.GeoPoint;

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
    HelpRequestsListContract.View provideView() {
        return mView;
    }

    @PerActivity
    @Provides
    HelpRequestsRecyclerAdapter provideAdapter() {
        return new HelpRequestsRecyclerAdapter();
    }

    @Provides
    @PerActivity
    HelpRequestsListContract.Presenter providePresenter(HelpRequestsListPresenter presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    Index provideIndex() {
        return AlgoliaSearch.getInstance().getIndex();
    }

    @Provides
    @PerActivity
    Query provideQuery() {
        return new Query().setPage(0);
    }
}
