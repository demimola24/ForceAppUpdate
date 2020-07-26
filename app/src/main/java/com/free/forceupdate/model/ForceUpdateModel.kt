package com.free.forceupdate.model

data class ForceUpdateModel(
    val currentVersion: Int,
    val enforce: Boolean,
    val storeUrl: String
)