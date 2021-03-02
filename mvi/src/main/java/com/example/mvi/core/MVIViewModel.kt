package com.example.mvi.core

import androidx.lifecycle.*
import com.example.mvi.UiStateMachine
import br.com.xds.mvi.event.Event

abstract class MVIViewModel<A, R, S>(private val dispatcher: MVIDispatcher<A, R>)
    : ViewModel(), UiStateMachine<S> {

    private val _action = MutableLiveData<A>()

    private val result : LiveData<R> = _action.switchMap {
        dispatcher.fromAction(it)
    }

    override val state: LiveData<Event<S>> = result.map {
        Event(reduce(it))
    }

    fun mutate(action: A) {
        _action.postValue(action)
    }

    abstract fun reduce(result: R): S

}

inline val <reified S> MVIViewModel<*, *, S>.currentState : S
    get() = (state.value?.content ?: S::class.java.newInstance())