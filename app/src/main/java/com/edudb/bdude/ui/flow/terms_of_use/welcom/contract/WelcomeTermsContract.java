package com.edudb.bdude.ui.flow.terms_of_use.welcom.contract;

import com.edudb.bdude.enums.EnumStepNumber;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.base.BaseView;

public interface WelcomeTermsContract {

    interface View extends BaseView {
        void initView(String string);
        EnumStepNumber getStepNumber();
    }

    interface Presenter extends BasePresenter {
        void nextStep();
    }
}
