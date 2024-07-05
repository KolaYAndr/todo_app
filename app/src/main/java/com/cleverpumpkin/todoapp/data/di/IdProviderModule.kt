package com.cleverpumpkin.todoapp.data.di

import com.cleverpumpkin.todoapp.data.id_handlers.DeviceIdProviderImpl
import com.cleverpumpkin.todoapp.data.id_handlers.IdGeneratorImpl
import com.cleverpumpkin.todoapp.domain.id_handlers.DeviceIdProvider
import com.cleverpumpkin.todoapp.domain.id_handlers.IdGenerator
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