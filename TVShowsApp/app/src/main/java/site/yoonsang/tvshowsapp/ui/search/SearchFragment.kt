package site.yoonsang.tvshowsapp.ui.search

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
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
import site.yoonsang.tvshowsapp.databinding.FragmentSearchBinding
import site.yoonsang.tvshowsapp.ui.TVShowViewModel

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search), ShowPagingAdapter.OnItemClickListener {

    private val viewModel by viewModels<TVShowViewModel>()

    var binding: FragmentSearchBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchBinding.bind(view)

        val adapter = ShowPagingAdapter(this)

        binding.apply {
            this!!.searchList.adapter = adapter.withLoadStateHeaderAndFooter(
                header = ShowLoadStateAdapter { adapter.retry() },
                footer = ShowLoadStateAdapter { adapter.retry() }
            )
            buttonRetry.setOnClickListener {
                adapter.retry()
            }
        }

        viewModel.searchShows.observe(viewLifecycleOwner) { data ->
            adapter.submitData(viewLifecycleOwner.lifecycle, data)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                this!!.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                searchList.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error
            }

            // empty view
            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                binding!!.searchList.isVisible = false
                binding!!.textViewEmpty.isVisible = true
            } else {
                binding!!.textViewEmpty.isVisible = false
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Enter title"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding!!.searchList.scrollToPosition(0)
                    viewModel.getSearchShows(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(show: Show) {
        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailFragment(show))
    }
}