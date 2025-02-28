package com.chooloo.www.chooloolib.ui.base

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceGroup


abstract class BasePreferenceFragment : PreferenceFragmentCompat(), BaseContract.View {
    override val component get() = baseActivity.component

    protected val argsSafely get() = arguments ?: Bundle()
    protected val baseActivity by lazy { context as BaseActivity }

    abstract val preferenceResource: Int


    override fun onAttach(context: Context) {
        super.onAttach(context)
        assert(context is BaseActivity)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(preferenceResource, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDivider(ColorDrawable(Color.TRANSPARENT))
        setDividerHeight(0)
        initAllPreferences()
        onSetup()
    }

    override fun onSetup() {}

    override fun showMessage(@StringRes stringResId: Int) {
        baseActivity.showMessage(stringResId)
    }

    override fun showError(@StringRes stringResId: Int) {
        baseActivity.showError(stringResId)
    }


    private fun initAllPreferences() {
        (0 until preferenceScreen.preferenceCount).forEach { x ->
            val preference = preferenceScreen.getPreference(x)
            if (preference is PreferenceGroup) {
                (0 until preference.preferenceCount).forEach { y ->
                    val nestedPreference = preference.getPreference(y)
                    if (nestedPreference is PreferenceGroup) {
                        (0 until nestedPreference.preferenceCount).forEach { k ->
                            initPreference(nestedPreference.getPreference(k))
                        }
                    } else {
                        initPreference(nestedPreference)
                    }
                }
            } else {
                initPreference(preference)
            }
        }
    }

    private fun initPreference(preference: Preference) {
        preference.apply {
            isIconSpaceReserved = false
            setOnPreferenceClickListener {
                onPreferenceClickListener(preference)
                true
            }
            setOnPreferenceChangeListener { preference, newValue ->
                onPreferenceChangeListener(preference, newValue)
                true
            }
        }
    }

    protected fun <T : Preference> getPreference(@StringRes keyString: Int): T? {
        return findPreference<T>(getString(keyString))
    }


    open fun onPreferenceClickListener(preference: Preference) {}
    open fun onPreferenceChangeListener(preference: Preference, newValue: Any) {}
}