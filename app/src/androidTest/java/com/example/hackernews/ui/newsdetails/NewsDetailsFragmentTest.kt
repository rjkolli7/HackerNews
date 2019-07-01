package com.example.hackernews.ui.newsdetails

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.hackernews.R
import com.example.hackernews.presentation.viewmodel.newsdetails.NewsDetailsViewModel
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
import utils.kids

@RunWith(AndroidJUnit4::class)
class NewsDetailsFragmentTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java, true, true)

    @Rule
    @JvmField
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule(activityRule)

    lateinit var viewModel: NewsDetailsViewModel

    private val fragment = NewsDetailsFragment()
    @Before
    fun init() {
        viewModel = Mockito.mock(NewsDetailsViewModel::class.java)
        fragment.viewModelFactory = ViewModelUtil.createFor(viewModel)
    }

    @Test
    fun loadCommentsList() {
        onView(allOf(withId(R.id.news_details_rv), not(isDisplayed())))
        onView(allOf(withId(R.id.news_details_shimmer_layout), isDisplayed()))
        viewModel.loadComments(kids)
        onView(withId(R.id.news_details_rv)).perform(
            RecyclerHelpers.waitUntil(
                RecyclerHelpers.hasItemCount(greaterThan(0))
            )
        )
        onView(withId(R.id.news_details_rv)).check(matches(RecyclerHelpers.hasItemCount(greaterThan(0))))
        onView(allOf(withId(R.id.news_details_rv), isDisplayed()))
        onView(allOf(withId(R.id.news_details_shimmer_layout), not(isDisplayed())))
    }
}