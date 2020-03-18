package com.edudb.bdude.ui.base;

import android.graphics.Point;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.edudb.bdude.R;

public abstract class BaseActivity extends AppCompatActivity implements BaseView{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView();
    }

    public void setContentView() {

        View root = getLayoutInflater().inflate(R.layout.activity_base, null);
        setContentView(root);

        View view = getLayoutInflater().inflate(getLayoutResource(), null);
        ViewGroup mContentContainer = findViewById(R.id.content_container);
        mContentContainer.addView(view);
    }

    abstract int getLayoutResource();
}
