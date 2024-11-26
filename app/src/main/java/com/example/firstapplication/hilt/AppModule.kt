package com.example.firstapplication.hilt

import android.content.Context
import androidx.room.Room
import com.example.firstapplication.AppDataBase
import com.example.firstapplication.Converters
import com.example.firstapplication.FakeTmdbAPI
import com.example.firstapplication.Repository
import com.example.firstapplication.TmdbAPI
import com.example.firstapplication.daos.ActorDao
import com.example.firstapplication.daos.FilmDao
import com.example.firstapplication.daos.SerieDao
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class FakeApi
@Qualifier
annotation class RealApi

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @RealApi
  @Singleton
  @Provides
  fun provideTmdbApi() : TmdbAPI =
    Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
    .addConverterFactory(MoshiConverterFactory.create())
    .build()
    .create(TmdbAPI::class.java)

  @FakeApi
  @Singleton
  @Provides
  fun provideFakeTmdbApi() : TmdbAPI { return FakeTmdbAPI() }

  @Singleton
  @Provides
  fun provideRepository(@RealApi api: TmdbAPI, filmDao: FilmDao, serieDao: SerieDao, actorDao: ActorDao) = Repository(api, filmDao, serieDao, actorDao)

  @Singleton
  @Provides
  fun provideConverters(moshi: Moshi): Converters {
    return Converters(moshi)
  }

  @Singleton
  @Provides
  fun provideDatabase(
    @ApplicationContext context: Context,
    converters: Converters
  ): AppDataBase {
    return Room.databaseBuilder(
      context,
      AppDataBase::class.java, "database-name"
    )
      .addTypeConverter(converters)
      .fallbackToDestructiveMigration()
      .build()
  }

  @Singleton
  @Provides
  fun provideFilmDao(database: AppDataBase): FilmDao {
    return database.filmDao()
  }

  @Singleton
  @Provides
  fun provideActorDao(database: AppDataBase): ActorDao {
    return database.actorDao()
  }

  @Singleton
  @Provides
  fun provideSerieDao(database: AppDataBase): SerieDao {
    return database.serieDao()
  }
}