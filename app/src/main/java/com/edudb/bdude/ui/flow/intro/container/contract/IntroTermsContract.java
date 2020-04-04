package com.edudb.bdude.ui.flow.intro.container.contract;

import com.edudb.bdude.enums.EnumStepNumber;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.base.BaseView;

public interface IntroTermsContract {

    interface View extends BaseView{
        void initViews();
        void navigateToLocationFragment();
        void navigateHealthWarningsFragment();
    }

    interface Presenter extends BasePresenter {
        void navigateToNextStep(EnumStepNumber stepNumber);
    }
}
