package com.edudb.bdude.ui.flow.terms_of_use.contract;

import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.base.BaseView;

public interface TermsOfUseContract {

    interface View extends BaseView{
        void initText(String string);
    }

    interface Presenter extends BasePresenter {
    }
}
