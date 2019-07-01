package com.example.hackernews.domain.newsdetails

import com.example.hackernews.domain.ApiInterface
import com.example.hackernews.domain.ApiResponseHandler
import com.example.hackernews.presentation.model.CommentItem
import com.example.hackernews.presentation.model.NewsItem
import com.example.hackernews.testing.OpenForTesting
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

@OpenForTesting
class NewsDetailsRepositoryImp(private val apiInterface: ApiInterface) {

    var disposable: CompositeDisposable? = null

    private var commentIdsList = mutableListOf<Int>()
    var commentsList = mutableListOf<CommentItem>()

    private fun commentItem(id: Any) =
        apiInterface.comments(id)
            .toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    private fun getCommentsWithRepliesObserver(kids: List<Int>): Observable<CommentItem> {
        return Observable.just(kids)
            .flatMap { Observable.fromIterable(it) }
            .flatMap { commentItem(it) }
            .flatMap { getReplies(it) }
    }

    private fun getReplies(commentItem: CommentItem): Observable<CommentItem> {
        return Observable.just(commentItem)
            .flatMap { Observable.fromIterable(it.kids) }
            .flatMap { commentItem(it) }
            .map { it }
            .toList()
            .toObservable()
            .map {
                commentItem.replies = it
                commentItem
            }
    }

    fun getComments(responseHandler: ApiResponseHandler<CommentItem>?, kids: List<Int>?): Boolean {
        if (kids.isNullOrEmpty())
            return false
        commentIdsList.addAll(kids)
        commentsList.clear()
        disposable = CompositeDisposable()
        disposable?.add(getCommentsWithRepliesObserver(kids).subscribeWith(object : DisposableObserver<CommentItem>() {
            override fun onStart() {
                super.onStart()
                responseHandler?.onStart()
            }

            override fun onNext(t: CommentItem) {
                commentsList.add(t)
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
        return true
    }
}