package com.example.hackernews.ui.newslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernews.R
import com.example.hackernews.databinding.ItemNewslistBinding
import com.example.hackernews.presentation.model.NewsItem

class NewsListAdapter(private val newsList: MutableList<NewsItem>) : RecyclerView.Adapter<NewsListAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ItemViewHolder = ItemViewHolder(
        DataBindingUtil
            .inflate(
                LayoutInflater.from(parent.context), R.layout.item_newslist,
                parent, false
            )
    )

    override fun getItemCount(): Int {
        return if (newsList.isNullOrEmpty()) 0 else newsList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItems(newsList[position])
    }

    inner class ItemViewHolder(private val binding: ItemNewslistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItems(newsItem: NewsItem) {
            binding.newsItem = newsItem
            binding.newsItemCv.tag = newsItem
            binding.newsItemCv.setOnClickListener {v ->
                val bundle = bundleOf("details" to v.tag as NewsItem)
                Navigation.findNavController(v).navigate(R.id.newsDetails, bundle)
            }

        }
    }
}