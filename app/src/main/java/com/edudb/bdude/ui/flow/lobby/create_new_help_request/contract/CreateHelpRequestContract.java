package com.edudb.bdude.ui.flow.lobby.create_new_help_request.contract;

import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.base.BaseView;
import com.google.firebase.firestore.GeoPoint;

public interface CreateHelpRequestContract {

    interface View extends BaseView {
        void initViews();
        String getLocationName();
        String getNumber();
        String getName();
        String getBody();
        String getFullTitle();
        void changeLocationText(String locationName);
    }

    interface Presenter extends BasePresenter {
        void sendRequest();
        void selectLocationClicked();
        void changeLocation(GeoPoint location);
    }
}
