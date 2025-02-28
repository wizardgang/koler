package com.chooloo.www.chooloolib.ui.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chooloo.www.chooloolib.di.activitycomponent.ActivityComponentImpl

abstract class BaseActivity : AppCompatActivity(), BaseContract.View {
    override val component by lazy { ActivityComponentImpl(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(component.preferences.accentTheme.theme)
        super.onCreate(savedInstanceState)
        contentView?.let { setContentView(it) }
    }

    override fun onStart() {
        super.onStart()
        onSetup()
    }

    override fun onStop() {
        super.onStop()
        component.disposables.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        component.disposables.dispose()
    }

    override fun finish() {
        super<AppCompatActivity>.finish()
    }

    override fun showError(stringResId: Int) {
        Toast.makeText(
            applicationContext,
            component.strings.getString(stringResId),
            Toast.LENGTH_LONG
        ).show()
    }

    override fun showMessage(stringResId: Int) {
        Toast.makeText(
            this,
            component.strings.getString(stringResId),
            Toast.LENGTH_SHORT
        ).show()
    }

    abstract val contentView: View?
}