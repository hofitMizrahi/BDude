package com.edudb.bdude.di.modules;

import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import com.edudb.bdude.db.AlgoliaSearchController;
import com.edudb.bdude.di.scope.PerActivity;
import com.edudb.bdude.ui.flow.lobby.main_screen.contract.HelpRequestsListContract;
import com.edudb.bdude.ui.flow.lobby.main_screen.presenter.HelpRequestsListPresenter;
import com.edudb.bdude.ui.flow.lobby.main_screen.view.adapter.HelpRequestsRecyclerAdapter;

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
        return AlgoliaSearchController.getInstance().getIndex();
    }

    @Provides
    @PerActivity
    Query provideQuery() {
        return new Query().setPage(0);
    }
}
