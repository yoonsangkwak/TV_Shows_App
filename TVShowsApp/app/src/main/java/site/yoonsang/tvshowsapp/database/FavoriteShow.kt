package site.yoonsang.tvshowsapp.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite_table")
@Parcelize
class FavoriteShow(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var start_date: String,
    var country: String,
    var status: String,
    var image_thumbnail_path: String
): Parcelable