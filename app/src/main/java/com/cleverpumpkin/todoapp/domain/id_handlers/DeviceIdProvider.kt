package com.cleverpumpkin.todoapp.domain.id_handlers

interface DeviceIdProvider {
    fun provideDeviceId(): String
}