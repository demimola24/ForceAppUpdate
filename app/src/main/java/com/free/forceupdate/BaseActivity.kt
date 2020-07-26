package com.free.forceupdate

import android.app.Activity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.free.forceupdate.dialogs.MyCustomDialog

abstract class BaseActivity : AppCompatActivity() {

    fun showCustomDialog(
        title: String, view: (() -> View),
        cancelButtonText: String = "Cancel",
        cancelAction: (() -> Unit) = {},
        secondButtonText : String = "",
        dialogIsDismissable: Boolean = true,
        secondButtonAction: (() -> Unit) = {},
        showSecondButton: Boolean = false
    ) {
        val dialog = MyCustomDialog(title, view, cancelButton = { cancelAction() },secondButtonText = secondButtonText,
            secondButton = secondButtonAction, showSecondButton = showSecondButton, cancelButtonText = cancelButtonText, dialogIsDismissable =dialogIsDismissable )
        dialog.show(supportFragmentManager, MyCustomDialog.FRAGMENT_TAG)
    }
}