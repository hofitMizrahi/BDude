package com.edudb.bdude.ui.flow.lobby.requests_list_screen.contract;

import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.db.modules.Post;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.base.BaseView;
import com.edudb.bdude.ui.flow.lobby.requests_list_screen.view.adapter.HelpRequestsRecyclerAdapter;

import java.util.Collection;
import java.util.List;

public interface HelpRequestsListContract {

    interface View extends BaseView {
        void displayDataList(List<Post> posts);
        void displayEmptyView();
        void navigateTermsOfUseScreen();
        void displayMoreResults();
    }

    interface Presenter extends BasePresenter, HelpRequestsRecyclerAdapter.InteractionListener {
        boolean canLoadMore();
        void createHelpRequestClicked();
        void onResultsScroll(int lastVisibleItem);
        List<Post> getSearchResultItems();
    }
}
