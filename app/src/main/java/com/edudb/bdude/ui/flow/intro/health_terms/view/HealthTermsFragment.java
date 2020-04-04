package com.edudb.bdude.ui.flow.intro.health_terms.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edudb.bdude.R;
import com.edudb.bdude.application.BdudeApplication;
import com.edudb.bdude.di.components.DaggerHealthTermsComponent;
import com.edudb.bdude.di.modules.HealthTermsModule;
import com.edudb.bdude.enums.EnumStepNumber;
import com.edudb.bdude.ui.base.BaseFragment;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.flow.intro.health_terms.contract.HealthTermsContract;
import com.edudb.bdude.ui.flow.intro.health_terms.presenter.HealthTermsPresenter;

import javax.inject.Inject;

import butterknife.OnClick;

public class HealthTermsFragment extends BaseFragment implements HealthTermsContract.View {

    @Inject
    HealthTermsPresenter mPresenter;

    @OnClick(R.id.nextBtn)
    void onNextBtnClicked() {
        mPresenter.nextStep();
    }

    public static HealthTermsFragment getInstance() {
        return new HealthTermsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_health_terms, container, false);
    }

    @Override
    public void initViews() {
    }

    @Override
    protected void initDependencies() {
        DaggerHealthTermsComponent.builder().applicationComponent(BdudeApplication.getInstance().getApplicationComponent())
                .healthTermsModule(new HealthTermsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public EnumStepNumber getStepNumber() {
        return EnumStepNumber.THREE;
    }

    @Override
    public void initText(String string) {
     //   mHealthWarningTerms.setText(Html.fromHtml(str));
       // setTextViewHTML(mHealthWarningTerms, str);
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }


    protected void makeLinkClickable(SpannableStringBuilder strBuilder, final URLSpan span)
    {
        int start = strBuilder.getSpanStart(span);
        int end = strBuilder.getSpanEnd(span);
        int flags = strBuilder.getSpanFlags(span);
        ClickableSpan clickable = new ClickableSpan() {
            public void onClick(View view) {
                String url = "https://www.health.gov.il/Subjects/disease/corona/Pages/default.aspx";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        };
        strBuilder.setSpan(clickable, start, end, flags);
        strBuilder.removeSpan(span);
    }

    protected void setTextViewHTML(TextView text, String html)
    {
        CharSequence sequence = Html.fromHtml(html);
        SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
        URLSpan[] urls = strBuilder.getSpans(0, sequence.length(), URLSpan.class);
        for(URLSpan span : urls) {
            makeLinkClickable(strBuilder, span);
        }
        text.setText(strBuilder);
        text.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
