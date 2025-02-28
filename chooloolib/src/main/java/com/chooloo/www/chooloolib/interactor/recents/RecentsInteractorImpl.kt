package com.chooloo.www.chooloolib.interactor.recents

import android.Manifest.permission.WRITE_CALL_LOG
import android.content.Context
import android.provider.CallLog
import androidx.annotation.RequiresPermission
import com.chooloo.www.chooloolib.R
import com.chooloo.www.chooloolib.contentresolver.RecentsContentResolver
import com.chooloo.www.chooloolib.data.account.RecentAccount
import com.chooloo.www.chooloolib.interactor.base.BaseInteractorImpl

class RecentsInteractorImpl(private val context: Context) :
    BaseInteractorImpl<RecentsInteractor.Listener>(), RecentsInteractor {

    @RequiresPermission(WRITE_CALL_LOG)
    override fun deleteRecent(recentId: Long) {
        context.contentResolver.delete(
            CallLog.Calls.CONTENT_URI,
            String.format("%s = %s", CallLog.Calls._ID, recentId),
            null
        )
    }

    override fun queryRecent(recentId: Long) =
        RecentsContentResolver(context, recentId).queryItems().getOrNull(0)

    override fun queryRecent(recentId: Long, callback: (RecentAccount?) -> Unit) {
        RecentsContentResolver(context, recentId).queryItems {
            callback.invoke(it.getOrNull(0))
        }
    }

    override fun getCallTypeImage(@RecentAccount.CallType callType: Int) = when (callType) {
        RecentAccount.TYPE_INCOMING -> R.drawable.ic_call_received_black_24dp
        RecentAccount.TYPE_OUTGOING -> R.drawable.ic_call_made_black_24dp
        RecentAccount.TYPE_MISSED -> R.drawable.ic_call_missed_black_24dp
        RecentAccount.TYPE_REJECTED -> R.drawable.ic_call_missed_outgoing_black_24dp
        RecentAccount.TYPE_VOICEMAIL -> R.drawable.ic_voicemail_black_24dp
        RecentAccount.TYPE_BLOCKED -> R.drawable.round_block_24
        else -> R.drawable.ic_call_made_black_24dp
    }

    override fun getLastOutgoingCall() = CallLog.Calls.getLastOutgoingCall(context)
}