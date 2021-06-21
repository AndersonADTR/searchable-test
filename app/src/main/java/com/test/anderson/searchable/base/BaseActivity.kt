package com.test.anderson.searchable.base

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.fxn.cue.Cue
import com.fxn.cue.enums.Duration
import com.fxn.cue.enums.Type

open class BaseActivity : AppCompatActivity() {

    open fun showMessage(message: String) {
        onShowMessage(this, message)
    }

    open fun showLoader(show: Boolean) { }

    open fun showError(error: String){
        showLoader(false)
        onShowError(this, error)
    }

    private fun onShowError(context: Context, error: String?) {
        Cue.init()
            .with(context)
            .setMessage(error)
            .setGravity(Gravity.BOTTOM)
            .setType(Type.CUSTOM)
            .setDuration(Duration.LONG)
            .setBorderWidth(0)
            .setCornerRadius(100)
            .setCustomFontColor(
                Color.parseColor("#FF0000"),
                Color.parseColor("#ffffff"),
                Color.parseColor("#FF0000")
            )
            .setPadding(25)
            .setTextSize(12)
            .show()
    }

    private fun onShowMessage(context: Context, message: String?) {
        Cue.init()
            .with(context)
            .setMessage(message)
            .setGravity(Gravity.BOTTOM)
            .setType(Type.CUSTOM)
            .setDuration(Duration.LONG)
            .setBorderWidth(0)
            .setCornerRadius(100)
            .setCustomFontColor(
                Color.parseColor("#00FF00"),
                Color.parseColor("#ffffff"),
                Color.parseColor("#00FF00")
            )
            .setPadding(25)
            .setTextSize(12)
            .show()
    }
}