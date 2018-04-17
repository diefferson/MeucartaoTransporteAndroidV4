package br.com.disapps.meucartaotransporte.services

import android.app.Service
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.MutableLiveData
import android.content.Intent
import android.os.IBinder

abstract class BaseService : Service(), LifecycleOwner {

    protected val isComplete = MutableLiveData<Boolean>()
    protected var isManual = false

    private val mLifecycleRegistry: LifecycleRegistry by lazy {
        LifecycleRegistry(this)
    }

    override fun onCreate() {
        super.onCreate()
        mLifecycleRegistry.markState(Lifecycle.State.CREATED)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val onStart = super.onStartCommand(intent, flags, startId)
        mLifecycleRegistry.markState(Lifecycle.State.STARTED)
        return onStart
    }

    override fun onDestroy() {
        super.onDestroy()
        mLifecycleRegistry.markState(Lifecycle.State.DESTROYED)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun getLifecycle(): Lifecycle {
        return  mLifecycleRegistry
    }
}