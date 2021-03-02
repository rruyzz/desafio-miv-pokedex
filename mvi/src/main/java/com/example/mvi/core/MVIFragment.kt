package com.example.mvi.core

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.map
import com.example.mvi.UiStateMachine
import br.com.xds.mvi.event.EventObserver

abstract class MVIFragment<S>: Fragment() {

    abstract val uiStateMachine: UiStateMachine<S>

    val currentState get() = uiStateMachine.state.map {
        it.content
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        uiStateMachine.state.observe(viewLifecycleOwner, EventObserver { state ->
            render(state)
        })
        configureBindings()
        fetch()
    }

    abstract fun render(state: S)

    open fun fetch(){}
    open fun configureBindings(){}

}