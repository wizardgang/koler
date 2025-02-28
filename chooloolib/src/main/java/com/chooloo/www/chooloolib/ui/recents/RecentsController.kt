package com.chooloo.www.chooloolib.ui.recents

import com.chooloo.www.chooloolib.R
import com.chooloo.www.chooloolib.adapter.RecentsAdapter
import com.chooloo.www.chooloolib.data.account.RecentAccount
import com.chooloo.www.chooloolib.ui.list.ListController
import com.chooloo.www.chooloolib.ui.recent.RecentFragment

class RecentsController<V : RecentsContract.View>(view: V) :
    ListController<RecentAccount, V>(view),
    RecentsContract.Controller<V> {

    override val adapter by lazy { RecentsAdapter(component) }

    override val noResultsIconRes = R.drawable.round_history_24
    override val noResultsTextRes = R.string.error_no_results_recents
    override val noPermissionsTextRes = R.string.error_no_permissions_recents


    private val recentsLiveData by lazy {
        component.liveDataFactory.allocRecentsProviderLiveData()
    }

    override fun applyFilter(filter: String) {
        super.applyFilter(filter)
        try {
            recentsLiveData.filter = filter
        } catch (e: Exception) {
        }
    }

    override fun fetchData(callback: (items: List<RecentAccount>, hasPermissions: Boolean) -> Unit) {
        component.permissions.runWithReadCallLogPermissions {
            if (it) {
                recentsLiveData.observe(component.lifecycleOwner) { data ->
                    callback.invoke(data, true)
                }
            } else {
                callback.invoke(emptyList(), false)
            }
        }
    }
}