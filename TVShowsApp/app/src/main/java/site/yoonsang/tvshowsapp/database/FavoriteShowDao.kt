package site.yoonsang.tvshowsapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteShow: FavoriteShow)

    @Query("select * from favorite_table")
    fun getFavoriteShows(): LiveData<List<FavoriteShow>>

    @Query("select count(*) from favorite_table where favorite_table.id = :id")
    suspend fun checkShow(id: String): Int

    @Query("delete from favorite_table where favorite_table.id = :id")
    suspend fun removeFromFavorite(id: String): Int

    @Delete
    suspend fun delete(favoriteShow: FavoriteShow)

    @Query("delete from favorite_table")
    suspend fun deleteAll()
}