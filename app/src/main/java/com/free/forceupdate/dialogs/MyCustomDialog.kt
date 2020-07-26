package com.free.forceupdate.dialogs

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import com.free.forceupdate.R
import kotlinx.android.synthetic.main.fragment_base_dialog.view.*

class MyCustomDialog(
    var title: String = "",
    val content: () -> View,
    private var showSecondButton: Boolean = false,
    private var cancelableWithButton: Boolean = true,
    private var dialogIsDismissable: Boolean = true,
    private var secondButtonText: String = "",
    private var secondButton: () -> Unit = {},
    private var cancelButtonText: String = "Cancel",
    private var cancelButton: () -> Unit = {}
) : DialogFragment() {

    override fun onResume() {
        super.onResume()
        if(!dialogIsDismissable){
            dialog!!.setOnKeyListener { dialog, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return@setOnKeyListener true
                }
                 false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog?.window?.setLayout(width, height)
        }
    }




    override fun setCancelable(cancelable: Boolean) {
        super.setCancelable(dialogIsDismissable)
    }

    override fun isCancelable(): Boolean {
        return dialogIsDismissable
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_base_dialog, container, false)
        view.tv_saving_header.text = title
        (view.content as FrameLayout).addView(content())

        view.secondary_button.visibility = if (showSecondButton) View.VISIBLE else View.GONE
        view.secondary_button.text = secondButtonText
        view.secondary_button.setOnClickListener {
            secondButton()
            dialog?.dismiss()
        }
        view.btn_cancel.visibility = if(cancelableWithButton) View.VISIBLE else View.GONE
        view.btn_cancel.text = cancelButtonText
        view.btn_cancel.setOnClickListener {
            cancelButton()
            dialog?.dismiss()
        }

        return view
    }

    companion object {
        const val FRAGMENT_TAG = "MyCustomDialog"
    }
}