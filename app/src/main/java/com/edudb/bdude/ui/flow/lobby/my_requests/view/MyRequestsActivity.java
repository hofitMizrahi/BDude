package com.edudb.bdude.ui.flow.lobby.my_requests.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.OnClick
import com.edudb.bdude.R
import com.edudb.bdude.application.BdudeApplication
import com.edudb.bdude.db.modules.HelpRequest
import com.edudb.bdude.db.modules.User
import com.edudb.bdude.di.components.DaggerMyRequestsComponent
import com.edudb.bdude.di.modules.MyRequestsModule
import com.edudb.bdude.general.BaseActionBar.ShareMessageEvent
import com.edudb.bdude.general.utils.Utils
import com.edudb.bdude.session.SessionManager
import com.edudb.bdude.ui.base.BaseActivity
import com.edudb.bdude.ui.base.BasePresenter
import com.edudb.bdude.ui.flow.dialogs.UpdateNameDialogFragment
import com.edudb.bdude.ui.flow.dialogs.UpdatePhoneDialogFragment
import com.edudb.bdude.ui.flow.lobby.my_requests.contract.MyRequestsContract
import com.edudb.bdude.ui.flow.lobby.my_requests.presenter.MyRequestsPresenter
import com.edudb.bdude.ui.flow.lobby.my_requests.view.adapter.MyRequestsRecyclerAdapter
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class MyRequestsActivity : BaseActivity(), MyRequestsContract.View {

    @JvmField
    @Inject
    var mPresenter: MyRequestsPresenter? = null

    @JvmField
    @Inject
    var mCurrentUser: User? = null

    @JvmField
    @Inject
    var mAdapter: MyRequestsRecyclerAdapter? = null

    @JvmField
    @BindView(R.id.my_requests_recycler_view)
    var mRecycler: RecyclerView? = null

    @JvmField
    @BindView(R.id.name)
    var mName: TextView? = null

    @JvmField
    @BindView(R.id.address)
    var mAddress: TextView? = null

    @JvmField
    @BindView(R.id.phoneNumber)
    var mPhoneNumber: TextView? = null

    @JvmField
    @BindView(R.id.emailEditText)
    var mEmailContentET: EditText? = null

    @OnClick(R.id.btnShare)
    fun onShareBtnClicked() {
        EventBus.getDefault().post(ShareMessageEvent())
    }

    @OnClick(R.id.backBtn)
    fun onBackBtnClicked() {
        onBackPressed()
    }

    @OnClick(R.id.editAddress)
    fun onUpdateLocationClicked() {
        mPresenter!!.selectLocationClicked()
    }

    @OnClick(R.id.editName)
    fun onUpdateNameClicked() {
        UpdateNameDialogFragment.showDialog(supportFragmentManager, "") { name: String? -> mPresenter!!.saveUserName(name) }
    }

    @OnClick(R.id.editPhone)
    fun onUpdatePhoneClicked() {
        UpdatePhoneDialogFragment.showDialog(supportFragmentManager, "") { s: String? -> mPresenter!!.setUserPhoneClicked(s) }
    }

    @OnClick(R.id.sendToEmail)
    fun onSendEmailClicked() {
        if (!Utils.isNullOrWhiteSpace(mEmailContentET!!.text.toString())) {

            //TODO change this to do in background
            val mailto = "mailto:covid19communityhelp@gmail.com" +
                    "?cc=" + "covid19communityhelp@gmail.com" +
                    "&subject=" + Uri.encode("חוות דעת") +
                    "&body=" + Uri.encode(mEmailContentET!!.text.toString())
            val i = Intent(Intent.ACTION_SENDTO)
            i.data = Uri.parse(mailto)
            try {
                startActivity(Intent.createChooser(i, "Send mail..."))
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getPresenter(): BasePresenter {
        return mPresenter!!
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_my_requests
    }

    override fun initDependencies() {
        DaggerMyRequestsComponent.builder().applicationComponent(BdudeApplication.getInstance().applicationComponent)
                .myRequestsModule(MyRequestsModule(this))
                .build()
                .inject(this)
    }

    override fun displayList(posts: List<HelpRequest>) {
        mRecycler!!.visibility = View.VISIBLE
        mRecycler!!.layoutManager = LinearLayoutManager(this)
        mRecycler!!.isNestedScrollingEnabled = false
        mAdapter!!.setDate(posts, mPresenter)
        mRecycler!!.adapter = mAdapter
    }

    override fun onResume() {
        super.onResume()
        mPresenter!!.onStartReloadData()
    }

    override fun initView() {
        if (Utils.isNullOrWhiteSpace(mCurrentUser!!.name)) {
            mName!!.text = "הכנס שם"
            mName!!.setTextColor(ContextCompat.getColor(this, R.color.gray_light))
        } else {
            mName!!.text = mCurrentUser!!.name
            mAddress!!.setTextColor(ContextCompat.getColor(this, R.color.yellow))
        }
        if (Utils.isNullOrWhiteSpace(mCurrentUser!!.address_text)) {
            mAddress!!.text = "הכנס כתובת"
            mAddress!!.setTextColor(ContextCompat.getColor(this, R.color.yellow))
        } else {
            mAddress!!.text = mCurrentUser!!.address_text
            mAddress!!.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
        }
        if (Utils.isNullOrWhiteSpace(mCurrentUser!!.phone_number)) {
            mPhoneNumber!!.text = "הכנס מספר טלפון"
            mPhoneNumber!!.setTextColor(ContextCompat.getColor(this, R.color.gray_light))
        } else {
            mPhoneNumber!!.text = mCurrentUser!!.phone_number
            mAddress!!.setTextColor(ContextCompat.getColor(this, R.color.yellow))
        }
    }

    override fun refreshList(list: List<HelpRequest>) {
        mAdapter!!.refreshData(list)
        mAdapter!!.notifyDataSetChanged()
    }

    override fun setCurrentUser() {
        mCurrentUser = SessionManager.getInstance().user
    }
}