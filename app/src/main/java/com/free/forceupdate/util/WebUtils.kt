package com.free.forceupdate.util

import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.free.forceupdate.R

object WebUtils {

    @JvmStatic
    fun openWebPage(context: Context, link:String = "https://www.google.com/") {
        val chromeTabLauncher = CustomTabsIntent.Builder()
            .apply { setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary)) }
            .build()
        chromeTabLauncher.launchUrl(context, link.toUri())
    }
}