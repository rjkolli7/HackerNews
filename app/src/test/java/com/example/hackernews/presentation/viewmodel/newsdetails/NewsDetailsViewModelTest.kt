package com.example.hackernews.presentation.viewmodel.newsdetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.hackernews.di.MockRepositoryModule
import com.example.hackernews.domain.ApiInterface
import com.example.hackernews.presentation.model.CommentItem
import com.example.hackernews.presentation.model.NewsItem
import com.example.hackernews.presentation.viewmodel.newslist.NewsListViewModel
import com.example.hackernews.ui.main.MainActivity
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import mockData.MockDataObserver
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import utils.*

@RunWith(RobolectricTestRunner::class)
class NewsDetailsViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val mockAppModule = MockRepositoryModule()

    private val newsDetailsRepositoryImp = mockAppModule.getNewsDetailsRepository()

    private val viewModel by lazy { NewsDetailsViewModel(newsDetailsRepositoryImp) }

    private var activity = Robolectric.buildActivity(MainActivity::class.java)
        .create()
        .resume()
        .get()

    private val kids = listOf(
        19379692,
        19381514,
        19382165
    )

    @Before
    fun init() {
        viewModel.commentsList.clear()
        viewModel.isLoading.value = true
        viewModel.destroy()

        newsDetailsRepositoryImp.commentsList.clear()
        newsDetailsRepositoryImp.disposable = null
    }

    @Test
    fun testNewsDetailsList() {

        val apiInterface = Mockito.mock(ApiInterface::class.java)

        whenever(apiInterface.comments(kids[0])).thenReturn(Single.just(commentsList[0]))

        whenever(apiInterface.comments(kids[0])).thenReturn(Single.just(commentsList[0]))
        Assert.assertThat(commentsList[0].kids.size, Matchers.equalTo(0))
        Assert.assertThat(commentsList[0].replies.size, Matchers.equalTo(0))

        whenever(apiInterface.comments(kids[1])).thenReturn(Single.just(commentsList[1]))
        Assert.assertThat(commentsList[1].kids.size, Matchers.greaterThan(0))
        Assert.assertThat(commentsList[1].replies.size, Matchers.greaterThan(0))
    }

    @Test
    fun testMockSuccess() {
        newsDetailsRepositoryImp.commentsList.clear()
        newsDetailsRepositoryImp.disposable = CompositeDisposable()
        MockDataObserver.getCommentsMockDataObserver(activity, kids)?.let {
            newsDetailsRepositoryImp.disposable?.add(it.subscribeWith(object : DisposableObserver<CommentItem>() {

                override fun onStart() {
                    viewModel.onStart()
                    Assert.assertTrue(viewModel.isLoading.value == true)
                }

                override fun onComplete() {
                    viewModel.onCompleted()
                    viewModel.destroy()
                    viewModel.isLoading.value = false

                    Assert.assertTrue(viewModel.isLoading.value == false)

                    Assert.assertThat(viewModel.commentsList.size, Matchers.greaterThan(0))
                    Assert.assertThat(newsDetailsRepositoryImp.commentsList.size, Matchers.greaterThan(0))
                }

                override fun onNext(t: CommentItem) {
                    viewModel.onNext(t)
                    newsDetailsRepositoryImp.commentsList.add(t)
                    viewModel.commentsList.add(t)
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
        newsDetailsRepositoryImp.commentsList.clear()

        newsDetailsRepositoryImp.disposable = CompositeDisposable()
        MockDataObserver.getMockCommentItem(activity, "")?.let {
            newsDetailsRepositoryImp.disposable?.add(it.subscribeWith(object : DisposableObserver<CommentItem>() {

                override fun onStart() {
                    viewModel.onStart()
                    Assert.assertTrue(viewModel.isLoading.value == true)
                }

                override fun onComplete() {
                    viewModel.onCompleted()
                    viewModel.destroy()
                    viewModel.isLoading.value = false

                    Assert.assertTrue(viewModel.isLoading.value == false)

                    Assert.assertThat(viewModel.commentsList.size, Matchers.equalTo(0))
                    Assert.assertThat(newsDetailsRepositoryImp.commentsList.size, Matchers.equalTo(0))
                }

                override fun onNext(t: CommentItem) {
                    viewModel.onNext(t)
                    viewModel.commentsList.add(t)
                    newsDetailsRepositoryImp.commentsList.add(t)
                }

                override fun onError(e: Throwable) {
                    viewModel.onError(e.localizedMessage)
                    viewModel.isLoading.value = false
                }

            }))
        } ?: run {
            viewModel.onError("")
            viewModel.isLoading.value = false
            Assert.assertTrue(viewModel.isLoading.value == false)

            Assert.assertThat(viewModel.commentsList.size, Matchers.equalTo(0))
            Assert.assertThat(newsDetailsRepositoryImp.commentsList.size, Matchers.equalTo(0))
        }
    }
}