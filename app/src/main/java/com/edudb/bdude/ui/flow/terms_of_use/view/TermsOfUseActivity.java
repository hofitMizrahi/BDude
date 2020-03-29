package com.edudb.bdude.ui.flow.terms_of_use.view;

import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

import com.edudb.bdude.R;
import com.edudb.bdude.application.BDudeApplication;
import com.edudb.bdude.di.components.DaggerTermsOfUseComponent;
import com.edudb.bdude.di.modules.TermsOfUseModule;
import com.edudb.bdude.ui.base.BaseActivity;
import com.edudb.bdude.ui.base.BasePresenter;
import com.edudb.bdude.ui.flow.terms_of_use.contract.TermsOfUseContract;
import com.edudb.bdude.ui.flow.terms_of_use.presenter.TermsOfUsePresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class TermsOfUseActivity extends BaseActivity implements TermsOfUseContract.View {

    @Inject
    TermsOfUsePresenter mPresenter;

    @OnClick(R.id.btnApprove)
    void onApproveBtnClicked(){
        finish();
    }

    @BindView(R.id.termsTV)
    TextView mHealthWarningTerms;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_terms_of_use;
    }

    @Override
    public void initDependencies() {
        DaggerTermsOfUseComponent.builder().applicationComponent(BDudeApplication.getInstance().getApplicationComponent())
                .termsOfUseModule(new TermsOfUseModule(this))
                .build()
                .inject(this);
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void initText(String str) {
        mHealthWarningTerms.setText(Html.fromHtml(str));
        setTextViewHTML(mHealthWarningTerms, str);
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
