package com.cleverpumpkin.core.id_handlers

interface DeviceIdProvider {
    fun provideDeviceId(): String
}
