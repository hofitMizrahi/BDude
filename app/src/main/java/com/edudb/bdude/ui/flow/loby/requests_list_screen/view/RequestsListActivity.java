package com.edudb.bdude.ui.flow.loby.requests_list_screen.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.edudb.bdude.R;
import com.edudb.bdude.ui.base.BaseActivity;

public class RequestsListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_reuests_list;
    }

    @Override
    public int initDependencies() {
        return 0;
    }
}
