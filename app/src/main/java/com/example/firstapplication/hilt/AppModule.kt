package com.example.firstapplication.hilt

import com.example.firstapplication.FakeTmdbAPI
import com.example.firstapplication.Repository
import com.example.firstapplication.TmdbAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
  fun provideRepository(@RealApi api: TmdbAPI) = Repository(api)
}