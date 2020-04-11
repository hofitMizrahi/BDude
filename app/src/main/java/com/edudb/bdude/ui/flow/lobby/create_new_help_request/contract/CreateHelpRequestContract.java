package com.edudb.bdude.ui.flow.lobby.create_new_help_request.contract;

import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.db.modules.Product;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.base.BaseView;
import com.google.firebase.firestore.GeoPoint;

import java.util.List;

public interface CreateHelpRequestContract {

    interface View extends BaseView {
        void initViews();
        String getLocationName();
        String getNumber();
        String getName();
        String getBody();
        void showNotAskMoreRequestView();
        List<Product> getProducts();
        void setLocationAddress(String locationName);
        void navigateToSendRequestActivity(HelpRequest post);
    }

    interface Presenter extends BasePresenter {
        void sendRequest();
        void selectLocationClicked();
        void updateLocation(GeoPoint location);
    }
}
