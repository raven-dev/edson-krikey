package com.ravn.edsonkrikey.di.modules

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ravn.edsonkrikey.BuildConfig
import com.ravn.edsonkrikey.core.App
import com.ravn.edsonkrikey.repository.ItunesRepository
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Edson Arratea Ope on 5/6/19.
 * Ravn Development
 **/

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setDateFormat(DATE_FMT)
            .create()
    }

    @Provides
    @Singleton
    fun providesOkHttp(context: Context): OkHttpClient {
        val httpBuilder = OkHttpClient.Builder()
        httpBuilder.addNetworkInterceptor {
            var request = it.request()
            val appVersion = App.context.version
            val builder = request.newBuilder().addHeader("User-Agent", "Spotify/$appVersion/android")
            builder.addHeader("Content-Type", "application/json")
            request = builder.build()
            it.proceed(request)
        }
        httpBuilder.cache(Cache(File(context.cacheDir, "http"), 1024 * 1024 * 100))
        // output response logs in debug builds
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpBuilder.interceptors().add(logging)
        }
        httpBuilder.readTimeout(60, TimeUnit.SECONDS)
        httpBuilder.connectTimeout(60, TimeUnit.SECONDS)
        return httpBuilder.build()
    }

    @Provides
    @Singleton
    fun providesRetrofitClient(okHttp: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp)
            .build()
    }

    @Provides
    @Singleton
    fun providesItunesRepository(api: Retrofit): ItunesRepository {
        return ItunesRepository(api)
    }

    companion object {
        const val DATE_FMT = "EEE, dd MMM yyyy HH:mm:ss Z"
        const val SEARCH = "/search?"
        const val FIND_VALUE = "term"
    }
}