package com.chooloo.www.chooloolib.data.account

import android.provider.ContactsContract.CommonDataKinds.Phone

data class PhoneLookupAccount(
    val name: String?,
    val number: String? = null,
    val contactId: Long? = null,
    val photoUri: String? = null,
    val starred: Boolean? = false,
    val type: Int = Phone.TYPE_OTHER
) {
    val displayString: String
        get() = name ?: (number ?: "Unknown")

    companion object {
        val PRIVATE = PhoneLookupAccount("Private Number")
    }
}
