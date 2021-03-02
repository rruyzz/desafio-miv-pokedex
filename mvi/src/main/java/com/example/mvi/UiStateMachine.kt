package com.example.mvi

import androidx.lifecycle.LiveData
import br.com.xds.mvi.event.Event

interface UiStateMachine<S> {
    val state: LiveData<Event<S>>
}