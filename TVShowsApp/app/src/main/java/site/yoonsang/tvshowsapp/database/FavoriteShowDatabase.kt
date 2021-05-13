package site.yoonsang.tvshowsapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteShow::class], version = 1, exportSchema = false)
abstract class FavoriteShowDatabase: RoomDatabase() {
    abstract fun favoriteShowDao(): FavoriteShowDao
}