package com.example.hackernews.presentation.viewmodel.newslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hackernews.domain.ApiResponseHandler
import com.example.hackernews.domain.news.NewsRepositoryImp
import com.example.hackernews.presentation.model.NewsItem
import com.example.hackernews.testing.OpenForTesting
import com.example.hackernews.ui.newslist.NewsListAdapter
import javax.inject.Inject

@OpenForTesting
open class NewsListViewModel @Inject constructor(private val newsRepositoryImp: NewsRepositoryImp) : ViewModel(),
    ApiResponseHandler<NewsItem> {

    var isLoading: MutableLiveData<Boolean> = MutableLiveData()

    var newsList = mutableListOf<NewsItem>()

    var adapter = NewsListAdapter(newsList)

    init {
        isLoading.value = true
    }

    fun loadNewsList() {
        newsRepositoryImp.getNewsList(this)
    }

    override fun onStart() {
        isLoading.value = true
    }

    override fun onNext(it: NewsItem?) {
        it?.let {
            newsList.add(it)
            adapter.notifyItemInserted(newsList.size - 1)
            isLoading.value = false
        }
    }

    override fun onCompleted() {
        isLoading.value = false
    }

    override fun onError(t: String) {
        isLoading.value = false
    }

    fun destroy() {
        newsRepositoryImp.disposable?.dispose()
    }
}