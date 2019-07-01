package com.example.hackernews.presentation.viewmodel.newsdetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hackernews.domain.ApiResponseHandler
import com.example.hackernews.domain.newsdetails.NewsDetailsRepositoryImp
import com.example.hackernews.presentation.model.CommentItem
import com.example.hackernews.testing.OpenForTesting
import com.example.hackernews.ui.newsdetails.NewsDetailsListAdapter
import javax.inject.Inject

@OpenForTesting
open class NewsDetailsViewModel @Inject constructor(private val newsDetailsRepositoryImp: NewsDetailsRepositoryImp): ViewModel(), ApiResponseHandler<CommentItem> {

    var isLoading: MutableLiveData<Boolean> = MutableLiveData()

    var commentsList = mutableListOf<CommentItem>()

    var adapter = NewsDetailsListAdapter()

    init {
        isLoading.value = true
    }

    fun loadComments(kids: List<Int>?) {
        kids?.let {
            newsDetailsRepositoryImp.getComments(this, kids)
        }
    }

    override fun onStart() {
        isLoading.value = true
    }

    override fun onNext(it: CommentItem?) {
        it?.let {
            isLoading.value = false
            commentsList.add(it)
        }
    }

    override fun onCompleted() {
        isLoading.value = false
        adapter.notifyAdapter(commentsList)
    }

    override fun onError(t: String) {
        isLoading.value = false
    }

    fun destroy() {
        newsDetailsRepositoryImp.disposable?.dispose()
    }
}