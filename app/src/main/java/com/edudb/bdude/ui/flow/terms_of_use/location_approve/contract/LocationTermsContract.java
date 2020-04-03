package com.edudb.bdude.ui.flow.terms_of_use.location_approve.contract;

import com.edudb.bdude.enums.EnumStepNumber;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.base.BaseView;

public interface LocationTermsContract {

    interface View extends BaseView {
        void initView(String string);

        EnumStepNumber getStepNumber();
    }

    interface Presenter extends BasePresenter {
        void nextStep();
        void askLocation();
    }
}
