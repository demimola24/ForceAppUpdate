package com.free.forceupdate

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import com.bumptech.glide.Glide
import com.free.forceupdate.model.ForceUpdateModel
import com.free.forceupdate.util.WebUtils
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseActivity() {
    private val mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
    private val versionAlias = "force_update"
    private val backgroundAlias = "background_url"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

    }

    override fun onResume() {
        super.onResume()
        checkAppVersion()
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun checkAppVersion(){
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(60)
            .build()
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings)

        mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener {task ->
            if (task.isSuccessful) {
                val latestAppVersion = mFirebaseRemoteConfig.getString(versionAlias)
                val backgroundImageUrl = mFirebaseRemoteConfig.getString(backgroundAlias)
                if(backgroundImageUrl.isNotEmpty()){
                    Glide.with(this).load(backgroundImageUrl).centerCrop().into(app_background)
                }
                val gson = Gson()
                val value = gson.fromJson(latestAppVersion, ForceUpdateModel::class.java)
                if(value.currentVersion>BuildConfig.VERSION_CODE){
                    if(value.enforce){
                        updateDialog(value.storeUrl)
                    }else{
                        updateDialog(value.storeUrl)
                    }
                }

            }
        }
    }

    private fun updateDialog(url: String, enforce: Boolean=false) {
        showCustomDialog("App Update", {
            val view = View.inflate(this, R.layout.dialog_app_update, null)
            view
        }, cancelButtonText = "Update Now", cancelAction = {
            WebUtils.openWebPage(this,url)
        }, dialogIsDismissable = enforce)
    }
}
