package site.yoonsang.tvshowsapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import site.yoonsang.tvshowsapp.database.FavoriteShowDatabase
import site.yoonsang.tvshowsapp.network.TVApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFavoriteShowDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        FavoriteShowDatabase::class.java,
        "favorite_db"
    ).build()

    @Singleton
    @Provides
    fun provideFavoriteShowDao(db: FavoriteShowDatabase) = db.favoriteShowDao()

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(TVApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideTVApi(retrofit: Retrofit): TVApi =
        retrofit.create(TVApi::class.java)
}