package site.yoonsang.tvshowsapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import site.yoonsang.tvshowsapp.R
import site.yoonsang.tvshowsapp.ShowLoadStateAdapter
import site.yoonsang.tvshowsapp.ShowPagingAdapter
import site.yoonsang.tvshowsapp.data.model.Show
import site.yoonsang.tvshowsapp.databinding.FragmentHomeBinding
import site.yoonsang.tvshowsapp.ui.TVShowViewModel

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), ShowPagingAdapter.OnItemClickListener {

    private val viewModel by viewModels<TVShowViewModel>()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)

        val adapter = ShowPagingAdapter(this)

        binding.apply {
            this!!.recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = null
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = ShowLoadStateAdapter { adapter.retry() },
                footer = ShowLoadStateAdapter { adapter.retry() }
            )
            buttonRetry.setOnClickListener {
                adapter.retry()
            }
        }
        viewModel.mostPopularShows.observe(viewLifecycleOwner) { data ->
            adapter.submitData(viewLifecycleOwner.lifecycle, data)
        }
        adapter.addLoadStateListener { loadState ->
            binding.apply {
                this!!.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error
            }

            // empty view
            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                binding!!.recyclerView.isVisible = false
                binding!!.textViewEmpty.isVisible = true
            } else {
                binding!!.textViewEmpty.isVisible = false
            }
        }
    }

    override fun onItemClick(show: Show) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(show))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}