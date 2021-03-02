package com.example.mvi_annotations


@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class MVIState(
    val target: StateTarget = StateTarget.FRAGMENT
)