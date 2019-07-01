package com.example.hackernews.presentation.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NewsItem(

    @SerializedName("by")
    @Expose
    var by: String = "",

    @SerializedName("title")
    @Expose
    var title: String = "",

    @SerializedName("type")
    @Expose
    var type: String? = null,

    @SerializedName("url")
    @Expose
    var url: String? = null,

    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("descendants")
    @Expose
    var descendants: Int? = null,

    @SerializedName("score")
    @Expose
    var score: Int = 0,

    @SerializedName("time")
    @Expose
    var time: Long? = null,

    @SerializedName("kids")
    @Expose
    var kids: List<Int>? = null
) : Serializable