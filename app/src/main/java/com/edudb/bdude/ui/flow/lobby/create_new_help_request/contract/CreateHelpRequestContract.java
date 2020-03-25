package com.edudb.bdude.ui.flow.lobby.create_new_help_request.contract;

import com.edudb.bdude.ui.base.BasePresenter;
import com.google.firebase.firestore.GeoPoint;

public interface CreateHelpRequestContract {

    interface View{
        void initViews();
        GeoPoint getLocation();
        String getNumber();
        String getGender();
        String getBody();
        String getFullTitle();
    }

    interface Presenter extends BasePresenter {
        void sendRequest();
    }
}
