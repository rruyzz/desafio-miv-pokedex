package com.example.mvi.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers

abstract class MVIDispatcher<A, R> {

    fun fromAction(action: A): LiveData<R> = liveData(Dispatchers.IO) {
        handleAction(action)
    }

    abstract suspend fun LiveDataScope<R>.handleAction(action: A)

}