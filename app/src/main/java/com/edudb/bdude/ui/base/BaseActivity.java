package com.edudb.bdude.ui.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.edudb.bdude.R;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements BaseView{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        ButterKnife.bind(this);
        initDependencies();
        if(getPresenter() != null){
            getPresenter().onStart();
        }
    }

    public void setContentView() {

        View root = getLayoutInflater().inflate(R.layout.activity_base, null);
        setContentView(root);

        if(getLayoutResource() != 0) {
            View view = getLayoutInflater().inflate(getLayoutResource(), null);
            ViewGroup mContentContainer = findViewById(R.id.content_container);
            mContentContainer.addView(view);
        }
    }

    public BasePresenter getPresenter(){
        return null;
    }

    public abstract int getLayoutResource();

    public abstract void initDependencies();
}
