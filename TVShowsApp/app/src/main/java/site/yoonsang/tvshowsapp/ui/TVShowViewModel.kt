package site.yoonsang.tvshowsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import site.yoonsang.tvshowsapp.data.TVShowRepository
import javax.inject.Inject

@HiltViewModel
class TVShowViewModel @Inject constructor(
    private val repository: TVShowRepository
) : ViewModel() {

    val mostPopularShows = repository.getPopularShows().cachedIn(viewModelScope)
}