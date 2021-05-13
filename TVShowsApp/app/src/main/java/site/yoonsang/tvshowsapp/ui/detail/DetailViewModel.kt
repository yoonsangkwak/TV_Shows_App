package site.yoonsang.tvshowsapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import site.yoonsang.tvshowsapp.database.FavoriteShow
import site.yoonsang.tvshowsapp.database.FavoriteShowRepository
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: FavoriteShowRepository) :
    ViewModel() {

    fun addToFavorite(favoriteShow: FavoriteShow) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(favoriteShow)
        }
    }

    suspend fun checkShow(id: String) = repository.checkShow(id)

    fun removeFromFavorite(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeFromFavorite(id)
        }
    }
}