package com.example.hackernews.ui.newslist

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.hackernews.R
import com.example.hackernews.presentation.viewmodel.newslist.NewsListViewModel
import com.example.hackernews.ui.main.MainActivity
import com.example.hackernews.utils.DataBindingIdlingResourceRule
import com.example.hackernews.utils.RecyclerHelpers
import com.example.hackernews.utils.ViewModelUtil
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class NewsListFragmentTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java, true, true)

    @Rule
    @JvmField
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule(activityRule)

    lateinit var viewModel: NewsListViewModel

    private val fragment = NewsListFragment()
    @Before
    fun init() {
        viewModel = Mockito.mock(NewsListViewModel::class.java)
        fragment.viewModelFactory = ViewModelUtil.createFor(viewModel)
    }

    @Test
    fun loadNewsList() {
        onView(allOf(withId(R.id.news_rv), not(isDisplayed())))
        onView(allOf(withId(R.id.news_shimmer_layout), isDisplayed()))
        onView(withId(R.id.news_rv)).perform(
            RecyclerHelpers.waitUntil(
                RecyclerHelpers.hasItemCount(greaterThan(0))
            )
        )
        onView(withId(R.id.news_rv)).check(matches(RecyclerHelpers.hasItemCount(greaterThan(0))))
        onView(allOf(withId(R.id.news_rv), isDisplayed()))
        onView(allOf(withId(R.id.news_shimmer_layout), not(isDisplayed())))
    }
}