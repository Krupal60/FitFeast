package com.fit.feast.di

import com.fit.feast.data.workouts.repository.FitRepositoryImpl
import com.fit.feast.data.workouts.usecases.GetBodyPartsUseCaseImpl
import com.fit.feast.data.workouts.usecases.GetWorkoutByBodyPartUseCaseImpl
import com.fit.feast.data.workouts.usecases.GetWorkoutUseCaseImpl
import com.fit.feast.network.workouts.FitnessApiService
import com.fit.feast.util.FitApiConstants.Companion.BASE_URL
import com.fit.feast.util.FitApiConstants.Companion.X_RapidAPI_Host
import com.fit.feast.util.FitApiConstants.Companion.X_RapidAPI_Key
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
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
            .baseUrl(BASE_URL)
            .client(OkHttpClient
                .Builder()
                .callTimeout(90, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .connectTimeout(90, TimeUnit.SECONDS)
                .addInterceptor {
                    val request: Request.Builder = it.request().newBuilder()
                    request.addHeader("X-RapidAPI-Key", X_RapidAPI_Key)
                    request.addHeader("X-RapidAPI-Host", X_RapidAPI_Host)
                    return@addInterceptor it.proceed(request.build())
                }
                .build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideFitApi(retrofit: Retrofit): FitnessApiService =
        retrofit.create(FitnessApiService::class.java)


    @Singleton
    @Provides
    fun provideRepository(api: FitnessApiService): FitRepositoryImpl = FitRepositoryImpl(api)


    @Singleton
    @Provides
    fun provideGetWorkoutUseCase(workoutRepository: FitRepositoryImpl): GetWorkoutUseCaseImpl =
        GetWorkoutUseCaseImpl(workoutRepository)

    @Singleton
    @Provides
    fun provideGetWorkoutByBodyPartUseCase(workoutRepository: FitRepositoryImpl): GetWorkoutByBodyPartUseCaseImpl =
        GetWorkoutByBodyPartUseCaseImpl(workoutRepository)

    @Singleton
    @Provides
    fun provideGetBodyPartsUseCase(workoutRepository: FitRepositoryImpl): GetBodyPartsUseCaseImpl =
        GetBodyPartsUseCaseImpl(workoutRepository)


}

