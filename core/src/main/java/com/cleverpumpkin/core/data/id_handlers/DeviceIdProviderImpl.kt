package com.cleverpumpkin.core.data.id_handlers

import android.content.SharedPreferences
import com.cleverpumpkin.core.data.preference.PreferenceKeys
import com.cleverpumpkin.core.id_handlers.DeviceIdProvider
import com.cleverpumpkin.core.id_handlers.IdGenerator
import javax.inject.Inject

/**
 * Implementation of the DeviceIdProvider interface that provides and stores a device ID using SharedPreferences.
 */

class DeviceIdProviderImpl @Inject constructor(
    private val prefs: SharedPreferences,
    private val idGenerator: IdGenerator
) : DeviceIdProvider {

    private var id
        get() = prefs.getString(PreferenceKeys.DEVICE_ID, null)
        set(value) {
            prefs.edit().putString(PreferenceKeys.DEVICE_ID, value).apply()
        }

    override fun provideDeviceId(): String {
        return id ?: idGenerator.generateId().also { id = it }
    }
}
