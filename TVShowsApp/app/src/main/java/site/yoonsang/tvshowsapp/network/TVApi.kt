package site.yoonsang.tvshowsapp.network

import retrofit2.http.GET
import retrofit2.http.Query

interface TVApi {

    companion object {
        const val BASE_URL = "https://www.episodate.com/api/"
    }

    @GET("most-popular")
    suspend fun getPopularShows(
        @Query("page")
        page: Int
    ): TVShowResponse

    @GET("search")
    suspend fun getSearchShows(
        @Query("q")
        query: String,
        @Query("page")
        page: Int
    ): TVShowResponse
}