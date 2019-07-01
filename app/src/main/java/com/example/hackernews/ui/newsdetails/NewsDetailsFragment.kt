package com.example.hackernews.ui.newsdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.hackernews.R
import com.example.hackernews.databinding.FragmentNewsDetailsBinding
import com.example.hackernews.di.injector.Injectable
import com.example.hackernews.presentation.model.NewsItem
import com.example.hackernews.presentation.viewmodel.newsdetails.NewsDetailsViewModel
import com.example.hackernews.testing.OpenForTesting
import com.example.hackernews.ui.main.MainActivity
import javax.inject.Inject

@OpenForTesting
class NewsDetailsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: NewsDetailsViewModel

    lateinit var binding: FragmentNewsDetailsBinding

    var newsItem: NewsItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsItem = arguments?.getSerializable("details") as NewsItem?
        activity?.title = newsItem?.title
        val activity = activity as? MainActivity
        activity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_details, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewsDetailsViewModel::class.java)
        binding.model = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        viewModel.loadComments(newsItem?.kids)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val activity = activity as? MainActivity
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        viewModel.destroy()
        super.onDestroy()
    }
}