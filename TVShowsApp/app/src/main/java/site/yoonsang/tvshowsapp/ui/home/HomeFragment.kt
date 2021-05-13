package site.yoonsang.tvshowsapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import site.yoonsang.tvshowsapp.R
import site.yoonsang.tvshowsapp.databinding.FragmentHomeBinding
import site.yoonsang.tvshowsapp.ui.TVShowViewModel

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel by viewModels<TVShowViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentHomeBinding.bind(view)

        viewModel.mostPopularShows.observe(viewLifecycleOwner) { data ->
            Log.d("tag", "onViewCreated: " + data)
        }
    }
}