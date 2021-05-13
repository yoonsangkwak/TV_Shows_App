package site.yoonsang.tvshowsapp.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import site.yoonsang.tvshowsapp.database.FavoriteShow
import site.yoonsang.tvshowsapp.database.FavoriteShowRepository
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: FavoriteShowRepository) :
    ViewModel() {

        val shows = repository.getFavoriteShows()

    fun insert(favoriteShow: FavoriteShow) {
        viewModelScope.launch {
            repository.insert(favoriteShow)
        }
    }

    fun delete(favoriteShow: FavoriteShow) {
        viewModelScope.launch {
            repository.delete(favoriteShow)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
}