package com.notix.notixsdk

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.notix.notixsdk.api.ApiClient
import java.util.*


@Suppress("unused")
class NotixSDK: Service() {
    private val binder = LocalBinder()
    private var context: Context? = null
    private var notixAppId: String = ""
    private var notixToken: String = ""
    private var notixFirebaseInitProvider: NotixFirebaseInitProvider? = null
    private val storage = StorageProvider()

    fun init(context: Context, notixAppId: String, notixToken: String) {
        this.context = context.applicationContext
        this.notixAppId = notixAppId

        notixFirebaseInitProvider = NotixFirebaseInitProvider()
        notixFirebaseInitProvider!!.init(context)

        ApiClient().getConfig(context, notixAppId, notixToken)
        storage.getUUID(context)
        storage.setAppId(context, notixAppId)
    }

    fun getContext(): Context? {
        return context
    }

    // Remove after
    inner class LocalBinder: Binder() {
        fun getService() : NotixSDK = this@NotixSDK
    }

    override fun onBind(p0: Intent?): IBinder {
        return Binder()
    }
}