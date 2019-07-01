package com.example.hackernews.ui.bindings

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.BindingAdapter
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hackernews.R
import com.example.hackernews.presentation.model.NewsItem
import com.example.hackernews.ui.newsdetails.NewsDetailsListAdapter

@BindingAdapter("visible")
fun bindVisibility(view: View, value: Boolean) {
    view.visibility = if (value) View.VISIBLE else View.GONE
}

@BindingAdapter("setAdapter")
fun bindRecyclerViewAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    recyclerView.setHasFixedSize(true)
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    recyclerView.adapter = adapter
}

@BindingAdapter("setCommentsAdapter")
fun bindCommentsRecyclerViewAdapter(recyclerView: RecyclerView, adapter: NewsDetailsListAdapter) {
    recyclerView.setHasFixedSize(true)
    recyclerView.layoutManager = GridLayoutManager(recyclerView.context, adapter.spanCount).apply {
        spanSizeLookup = adapter.spanSizeLookup
    }
    recyclerView.adapter = adapter
}

@BindingAdapter("setScore")
fun bindScore(v: AppCompatTextView, score: Int) {
    val points = "$score ${v.context.getString(R.string.points)} "
    v.text = points
}

@BindingAdapter("setBy")
fun bindBy(v: AppCompatTextView, by: String) {
    val postedBy = "${v.context.getString(R.string.by)} $by"
    v.text = postedBy
}

@BindingAdapter("setComments")
fun bindComment(v: AppCompatTextView, comment: Int) {
    val comments = "$comment ${v.context.getString(R.string.comments)}"
    v.text = comments
}

@BindingAdapter("repliesCount")
fun bindRepliesCount(view: AppCompatTextView, kids: List<Int>?) {
    if(kids.isNullOrEmpty())
        view.text = view.context.getString(R.string.no_replies)
    else {
        val count = "${kids.size} ${view.context.getString(R.string.replies)}"
        view.text = count
    }
}

//@BindingAdapter("repliesCount")
//@JvmStatic
//fun bindRepliesCount(view: TextView, kids: List<Int>?) {
//
//    if(kids?.size!! > 0) {
//        val count = kids.size
//        view.text = "$count Replies"
//    } else {
//        view.text = view.context.getString(R.string.no_replies)
//    }
//}