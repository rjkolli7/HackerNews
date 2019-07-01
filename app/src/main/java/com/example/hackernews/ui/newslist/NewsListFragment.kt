package com.example.hackernews.ui.newslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.hackernews.R
import com.example.hackernews.databinding.FragmentNewslistBinding
import com.example.hackernews.di.injector.Injectable
import com.example.hackernews.presentation.viewmodel.newslist.NewsListViewModel
import com.example.hackernews.testing.OpenForTesting
import com.example.hackernews.ui.main.MainActivity
import javax.inject.Inject

@OpenForTesting
class NewsListFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: FragmentNewslistBinding

    lateinit var viewModel: NewsListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_newslist, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewsListViewModel::class.java)
        viewModel.loadNewsList()
        binding.model = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    override fun onResume() {
        super.onResume()
        activity?.title = getString(R.string.app_name)
        val activity = activity as? MainActivity
        activity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(true)
    }
    override fun onDestroy() {
        viewModel.destroy()
        super.onDestroy()
    }
}