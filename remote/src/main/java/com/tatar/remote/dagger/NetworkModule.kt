package com.tatar.remote.dagger

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tatar.core.dagger.scope.ApplicationScope
import com.tatar.remote.api.ScoreGuesserAPI
import com.tatar.remote.util.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@Module(includes = [RemoteModule::class])
object NetworkModule {

    @ApplicationScope
    @Provides
    fun scoreGuesserAPI(retrofit: Retrofit): ScoreGuesserAPI {
        return retrofit.create(ScoreGuesserAPI::class.java)
    }

    @ApplicationScope
    @Provides
    fun retrofit(
        okHttpClient: OkHttpClient,
        rxJava3CallAdapterFactory: RxJava3CallAdapterFactory,
        moshiConverterFactory: MoshiConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(rxJava3CallAdapterFactory)
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .build()
    }

    @ApplicationScope
    @Provides
    fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(Constants.API_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(Constants.API_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @ApplicationScope
    @Provides
    fun rxJava3CallAdapterFactory(): RxJava3CallAdapterFactory {
        return RxJava3CallAdapterFactory.create()
    }

    @ApplicationScope
    @Provides
    fun moshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @ApplicationScope
    @Provides
    fun moshiConverterFactory(moshi: Moshi): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }
}
