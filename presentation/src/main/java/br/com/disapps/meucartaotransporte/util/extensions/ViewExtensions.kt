package br.com.disapps.meucartaotransporte.util.extensions

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.widget.TextView

fun TextView.setMutableText(owner: LifecycleOwner, mutableText: MutableLiveData<String>){
    mutableText.observe(owner, Observer { this.text = it.toString() })
}
