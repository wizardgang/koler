package com.chooloo.www.chooloolib.ui.recent

import com.chooloo.www.chooloolib.R
import com.chooloo.www.chooloolib.ui.base.BaseController
import com.chooloo.www.chooloolib.ui.briefcontact.BriefContactFragment
import com.chooloo.www.chooloolib.ui.recents.RecentsFragment
import com.chooloo.www.chooloolib.util.getElapsedTimeString
import java.util.*

class RecentController<V : RecentContract.View>(view: V) :
    BaseController<V>(view),
    RecentContract.Controller<V> {

    private val _recent by lazy { component.recents.queryRecent(view.recentId) }

    override fun onStart() {
        super.onStart()
        if (_recent == null) return
        val recentCaptions = arrayListOf(_recent!!.relativeTime)
        if (_recent!!.duration > 0) {
            recentCaptions.add(getElapsedTimeString(_recent!!.duration))
        }
        component.permissions.runWithDefaultDialer {
            if (component.blocked.isNumberBlocked(_recent!!.number)) {
                recentCaptions.add(
                    component.strings.getString(R.string.error_blocked).uppercase(Locale.ROOT)
                )
            }
        }
        view.recentCaption = recentCaptions.joinToString(", ")
        view.recentImage =
            component.drawables.getDrawable(component.recents.getCallTypeImage(_recent!!.type))
        view.recentName =
            if (_recent!!.cachedName?.isNotEmpty() == true) _recent!!.cachedName else _recent!!.number

        component.phones.lookupAccount(_recent!!.number) {
            view.isContactVisible = it?.name != null
            view.isAddContactVisible = it?.name == null
        }
    }

    override fun onActionSms() {
        _recent?.let { component.navigations.sendSMS(it.number) }
    }

    override fun onActionCall() {
        _recent?.let { component.navigations.call(it.number) }
    }

    override fun onActionDelete() {
        _recent?.let { recent ->
            component.permissions.runWithWriteCallLogPermissions {
                if (it) {
                    component.dialogs.askForValidation(R.string.explain_delete_recent) { result ->
                        if (result) {
                            component.recents.deleteRecent(recent.id)
                            view.finish()
                        }
                    }
                }
            }
        }
    }

    override fun onActionAddContact() {
        _recent?.let { component.navigations.addContact(it.number) }
    }

    override fun onActionOpenContact() {
        _recent?.let {
            component.phones.lookupAccount(it.number) { account ->
                account?.contactId?.let { id ->
                    component.prompts.showFragment(BriefContactFragment.newInstance(id))
                }
            }
        }
    }

    override fun onActionShowHistory() {
        _recent?.let {
            component.prompts.showFragment(RecentsFragment.newInstance(it.number).apply {
                controller.setOnItemClickListener { recent ->
                    component.prompts.showFragment(RecentFragment.newInstance(recent.id))
                }
            })
        }
    }
}