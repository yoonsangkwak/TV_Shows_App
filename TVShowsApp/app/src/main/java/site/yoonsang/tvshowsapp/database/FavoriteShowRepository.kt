package site.yoonsang.tvshowsapp.database

import androidx.lifecycle.LiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteShowRepository @Inject constructor(private val favoriteShowDao: FavoriteShowDao) {

    suspend fun insert(favoriteShow: FavoriteShow) {
        favoriteShowDao.insert(favoriteShow)
    }

    suspend fun delete(favoriteShow: FavoriteShow) {
        favoriteShowDao.delete(favoriteShow)
    }

    suspend fun deleteAll() {
        favoriteShowDao.deleteAll()
    }

    fun getFavoriteShows(): LiveData<List<FavoriteShow>> = favoriteShowDao.getFavoriteShows()

    suspend fun checkShow(id: String) = favoriteShowDao.checkShow(id)

    suspend fun removeFromFavorite(id: String) {
        favoriteShowDao.removeFromFavorite(id)
    }
}