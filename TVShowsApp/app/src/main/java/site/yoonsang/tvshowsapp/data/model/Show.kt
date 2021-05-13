package site.yoonsang.tvshowsapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Show(
    var id: Int,
    var name: String,
    var start_date: String,
    var country: String,
    var status: String,
    var image_thumbnail_path: String
): Parcelable