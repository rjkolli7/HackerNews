package com.example.hackernews.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.hackernews.R
import com.example.hackernews.presentation.viewmodel.newsdetails.NewsDetailsViewModel
import com.example.hackernews.presentation.viewmodel.newslist.NewsListViewModel
import com.example.hackernews.ui.newsdetails.NewsDetailsFragment
import com.example.hackernews.ui.newslist.NewsListFragment
import com.example.hackernews.utils.RecyclerHelpers
import com.example.hackernews.utils.ViewModelUtil
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java, true, true)

    lateinit var newsDetailsViewModel: NewsDetailsViewModel

    lateinit var newsListViewModel: NewsListViewModel

    private val newsDetailsFragment = NewsDetailsFragment()

    private val newsListFragment = NewsListFragment()

    @Before
    fun init() {
        newsListViewModel = Mockito.mock(NewsListViewModel::class.java)
        newsListFragment.viewModelFactory = ViewModelUtil.createFor(newsListViewModel)

        newsDetailsViewModel = Mockito.mock(NewsDetailsViewModel::class.java)
        newsDetailsFragment.viewModelFactory = ViewModelUtil.createFor(newsDetailsViewModel)
    }

    @Test
    fun testNavigationFlow() {

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

        onView(withId(R.id.news_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click())
        )

        onView(allOf(withId(R.id.news_details_rv), not(isDisplayed())))
        onView(allOf(withId(R.id.news_details_shimmer_layout), isDisplayed()))

        onView(withId(R.id.news_details_rv)).perform(
            RecyclerHelpers.waitUntil(
                RecyclerHelpers.hasItemCount(greaterThan(0))
            )
        )

        onView(withId(R.id.news_details_rv)).check(matches(RecyclerHelpers.hasItemCount(greaterThan(0))))
        onView(allOf(withId(R.id.news_details_rv), isDisplayed()))
        onView(allOf(withId(R.id.news_details_shimmer_layout), not(isDisplayed())))

        onView(withId(R.id.news_details_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click())
        )
    }
}