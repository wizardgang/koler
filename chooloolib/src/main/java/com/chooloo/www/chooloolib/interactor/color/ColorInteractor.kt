package com.chooloo.www.chooloolib.interactor.color

import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import com.chooloo.www.chooloolib.interactor.base.BaseInteractor

interface ColorInteractor : BaseInteractor<ColorInteractor.Listener> {
    interface Listener

    fun getColor(@ColorRes colorRes: Int): Int
    fun getAttrColor(@AttrRes colorRes: Int): Int
}