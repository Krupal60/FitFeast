package com.fit.feast.di.recipes

import com.fit.feast.data.recipes.repository.RecipeRepositoryImpl
import com.fit.feast.network.recipes.RecipeApiService
import com.fit.feast.util.RecipeApiConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(RecipeApiConstants.BASE_URL)
            .client(
                OkHttpClient
                    .Builder()
                    .callTimeout(90, TimeUnit.SECONDS)
                    .readTimeout(90, TimeUnit.SECONDS)
                    .writeTimeout(90, TimeUnit.SECONDS)
                    .connectTimeout(90, TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideRecipeApi(retrofit: Retrofit): RecipeApiService =
        retrofit.create(RecipeApiService::class.java)


    @Singleton
    @Provides
    fun provideRecipeRepository(api: RecipeApiService): RecipeRepositoryImpl =
        RecipeRepositoryImpl(api)


}

