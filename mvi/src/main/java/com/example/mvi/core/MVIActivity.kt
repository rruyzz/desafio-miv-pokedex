package com.example.mvi.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.map
import com.example.mvi.UiStateMachine
import br.com.xds.mvi.event.EventObserver

abstract class MVIActivity<S>: AppCompatActivity() {

    abstract val uiStateMachine: UiStateMachine<S>

    val currentState get() = uiStateMachine.state.map {
        it.content
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiStateMachine.state.observe(this, EventObserver { state ->
            render(state)
        })
    }

    abstract fun render(state: S)
}