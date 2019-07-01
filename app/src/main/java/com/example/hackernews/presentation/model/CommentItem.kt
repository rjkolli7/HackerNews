package com.example.hackernews.presentation.model

import android.os.Build
import android.text.Html
import android.text.Spanned
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CommentItem(
    @SerializedName("by")
    @Expose
    var by: String? = "",

    @SerializedName("text")
    @Expose
    internal var text: String? = "",

    @SerializedName("type")
    @Expose
    var type: String? = null,

    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("score")
    @Expose
    var score: Int? = null,

    @SerializedName("parent")
    @Expose
    var parent: Int? = null,

    @SerializedName("time")
    @Expose
    var time: Long? = null,

    @SerializedName("kids")
    @Expose
    var kids: List<Int> = ArrayList(),

    var replies: List<CommentItem?> = ArrayList()

) : Serializable {

    fun getHtmlText(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY).toString().trim()
        } else {
            @Suppress("DEPRECATION")
            Html.fromHtml(text).toString().trim()
        }
    }
}
