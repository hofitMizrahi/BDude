package com.edudb.bdude.ui.base;

import android.app.Activity;

import com.edudb.bdude.db.modules.Post;
import com.edudb.bdude.enums.EnumNavigation;

public interface BaseView {

    Activity getActivity();
    void displayProgressBar();
    void hideProgressBar();
    void checkLocation();
    void checkLoginAndNavigate(EnumNavigation tag);
    void navigateToRequestDetailsScreen(Post request);
}
