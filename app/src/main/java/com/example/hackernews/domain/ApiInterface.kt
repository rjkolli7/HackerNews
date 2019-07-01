package com.example.hackernews.domain

import com.example.hackernews.presentation.model.CommentItem
import com.example.hackernews.presentation.model.NewsItem
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("topstories.json?print=pretty")
    fun topStories(): Single<List<Int>>

    @GET("item/{id}.json?print=pretty")
    fun storyData(@Path("id") id: Any): Single<NewsItem>

    @GET("item/{id}.json?print=pretty")
    fun comments(@Path("id") id: Any): Single<CommentItem>
}