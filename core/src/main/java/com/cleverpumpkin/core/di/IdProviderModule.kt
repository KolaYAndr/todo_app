package com.cleverpumpkin.core.di

import com.cleverpumpkin.core.data.id_handlers.DeviceIdProviderImpl
import com.cleverpumpkin.core.data.id_handlers.IdGeneratorImpl
import com.cleverpumpkin.core.id_handlers.DeviceIdProvider
import com.cleverpumpkin.core.id_handlers.IdGenerator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface IdProviderModule {

    @Binds
    @Singleton
    fun bindIdGenerator(generatorImpl: IdGeneratorImpl): IdGenerator

    @Binds
    @Singleton
    fun bindDeviceIdProvider(idProviderImpl: DeviceIdProviderImpl): DeviceIdProvider
}
