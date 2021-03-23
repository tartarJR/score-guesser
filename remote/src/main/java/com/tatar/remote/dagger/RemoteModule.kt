package com.tatar.remote.dagger

import com.tatar.data.source.RemoteDataSource
import com.tatar.remote.source.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
interface RemoteModule {
    @Binds
    fun bindRemoteDataSource(remoteDataSource: RemoteDataSourceImpl): RemoteDataSource
}
