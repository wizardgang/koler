package com.chooloo.www.chooloolib.interactor.phoneaccounts

import com.chooloo.www.chooloolib.data.account.PhoneAccount
import com.chooloo.www.chooloolib.data.account.PhoneLookupAccount
import com.chooloo.www.chooloolib.interactor.base.BaseInteractor

interface PhonesInteractor : BaseInteractor<PhonesInteractor.Listener> {
    interface Listener

    fun lookupAccount(number: String?, callback: (PhoneLookupAccount?) -> Unit)
    fun getContactAccounts(contactId: Long, callback: (Array<PhoneAccount>?) -> Unit)
}