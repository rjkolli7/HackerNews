package com.example.hackernews.domain

interface ApiResponseHandler<T> {
    fun onStart()
    fun onNext(it: T?)
    fun onCompleted()
    fun onError(t: String)
}