package com.example.hackernews.domain.news

import com.example.hackernews.domain.ApiInterface
import com.example.hackernews.domain.ApiResponseHandler
import com.example.hackernews.presentation.model.NewsItem
import com.example.hackernews.testing.OpenForTesting
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

@OpenForTesting
open class NewsRepositoryImp(private val apiInterface: ApiInterface) {

    var disposable: CompositeDisposable? = null

    var newsIdsList = mutableListOf<Int>()
    var newsList = mutableListOf<NewsItem>()

    private fun newsList(): Observable<List<Int>> =
        apiInterface.topStories()
            .toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    private fun newsItem(id: Any): Observable<NewsItem> =
        apiInterface.storyData(id)
            .toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getNewsList(responseHandler: ApiResponseHandler<NewsItem>?) {
        newsIdsList.clear()
        disposable = CompositeDisposable()
        disposable?.add(newsList().subscribeWith(object : DisposableObserver<List<Int>>() {
            override fun onStart() {
                super.onStart()
                responseHandler?.onStart()
            }

            override fun onNext(t: List<Int>) {
                newsIdsList.addAll(t)
            }

            override fun onComplete() {
                disposable?.dispose()
                getNews(responseHandler)
            }

            override fun onError(e: Throwable) {
                disposable?.dispose()
            }
        }))
    }

    private fun newsObservable(): Observable<NewsItem> =
        Observable.just(newsIdsList)
            .flatMap { ids ->
                Observable.fromIterable(ids)
            }
            .flatMap { id -> newsItem(id) }

    private fun getNews(responseHandler: ApiResponseHandler<NewsItem>?) {
        newsList.clear()
        disposable = CompositeDisposable()
        disposable?.add(newsObservable().subscribeWith(object : DisposableObserver<NewsItem>() {
            override fun onStart() {
                super.onStart()
                responseHandler?.onStart()
            }

            override fun onNext(t: NewsItem) {
                newsList.add(t)
                responseHandler?.onNext(t)
            }

            override fun onComplete() {
                disposable?.dispose()
                responseHandler?.onCompleted()
            }

            override fun onError(e: Throwable) {
                disposable?.dispose()
                responseHandler?.onError(e.localizedMessage)
            }
        }))
    }
}