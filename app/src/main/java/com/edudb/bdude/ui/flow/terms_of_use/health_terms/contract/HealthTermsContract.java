package com.edudb.bdude.ui.flow.terms_of_use.health_terms.contract;

import com.edudb.bdude.enums.EnumStepNumber;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.base.BaseView;

public interface HealthTermsContract {

    interface View extends BaseView {
        void initViews();

        EnumStepNumber getStepNumber();
    }

    interface Presenter extends BasePresenter {
        void nextStep();
    }
}
