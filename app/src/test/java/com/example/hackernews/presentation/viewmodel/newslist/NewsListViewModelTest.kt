package com.example.hackernews.presentation.viewmodel.newslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.hackernews.di.MockRepositoryModule
import com.example.hackernews.domain.ApiInterface
import com.example.hackernews.domain.ApiResponseHandler
import com.example.hackernews.presentation.model.NewsItem
import com.example.hackernews.ui.main.MainActivity
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import mockData.MockDataObserver
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.greaterThan
import org.hamcrest.core.Is
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import utils.*
import java.lang.NullPointerException

@RunWith(RobolectricTestRunner::class)
class NewsListViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val mockAppModule = MockRepositoryModule()

    private val newsRepositoryImp = mockAppModule.getNewsListRepository()

    private val viewModel by lazy { NewsListViewModel(newsRepositoryImp) }

    private var activity = Robolectric.buildActivity(MainActivity::class.java)
        .create()
        .resume()
        .get()

    @Before
    fun init() {
        viewModel.newsList.clear()
        viewModel.isLoading.value = true
        viewModel.destroy()

        newsRepositoryImp.newsList.clear()
        newsRepositoryImp.newsIdsList.clear()
        newsRepositoryImp.disposable = null
    }

    @Test
    fun testMockNewsList() {

        val apiInterface = Mockito.mock(ApiInterface::class.java)

        val topStories = topStories
        Mockito.`when`(apiInterface.topStories()).thenReturn(Single.just(topStories))
        assertThat(topStories.size, greaterThan(0))

        whenever(apiInterface.storyData(topStories[0])).thenReturn(Single.just(newsList[0]))
        assertEquals(20316803, newsList[0].id)
        newsList[0].kids?.size?.let {
            assertThat(it, greaterThan(0))
        }

        assertEquals(20317002, newsList[1].id)
        newsList[1].kids?.size?.let {
            assertThat(it, greaterThan(0))
        }
    }

    @Test
    fun testMockSuccess() {
        newsRepositoryImp.newsList.clear()

        newsRepositoryImp.disposable = CompositeDisposable()
        MockDataObserver.getTopStoriesMockDataObserver(activity, "topstories.json")?.let {
            newsRepositoryImp.disposable?.add(it.subscribeWith(object : DisposableObserver<NewsItem>() {

                override fun onStart() {
                    viewModel.onStart()
                }
                override fun onComplete() {
                    viewModel.onCompleted()
                    viewModel.destroy()
                    viewModel.isLoading.value = false
                }

                override fun onNext(t: NewsItem) {
                    viewModel.onNext(t)
                    newsRepositoryImp.newsList.add(t)
                    viewModel.newsList.add(t)
                }

                override fun onError(e: Throwable) {
                    viewModel.onError(e.localizedMessage)
                    viewModel.isLoading.value = false
                }

            }))
        }
    }

    @Test
    fun testMockError() {
        newsRepositoryImp.newsList.clear()

        newsRepositoryImp.disposable = CompositeDisposable()
        MockDataObserver.getTopStoriesMockDataObserver(activity, "")?.let {
            newsRepositoryImp.disposable?.add(it.subscribeWith(object : DisposableObserver<NewsItem>() {

                override fun onStart() {
                    viewModel.onStart()
                    assertTrue(viewModel.isLoading.value == true)
                }
                override fun onComplete() {
                    viewModel.onCompleted()
                    viewModel.destroy()
                    viewModel.isLoading.value = false

                    assertTrue(viewModel.isLoading.value == false)

                    assertThat(viewModel.newsList.size, greaterThan(0))
                    assertThat(newsRepositoryImp.newsList.size, greaterThan(0))
                }

                override fun onNext(t: NewsItem) {
                    viewModel.onNext(t)
                    viewModel.newsList.add(t)
                    newsRepositoryImp.newsList.add(t)
                }

                override fun onError(e: Throwable) {
                    viewModel.onError(e.localizedMessage)
                    viewModel.isLoading.value = false
                }

            }))
        } ?: run {
            viewModel.onError("")
            viewModel.isLoading.value = false
        }
    }
}