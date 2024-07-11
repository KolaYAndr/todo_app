package com.cleverpumpkin.todoapp.data.id_handlers

import com.cleverpumpkin.todoapp.domain.id_handlers.IdGenerator
import java.util.UUID
import javax.inject.Inject

/**
 * Implementation of the IdGenerator interface that generates unique IDs using UUIDs.
 */

class IdGeneratorImpl @Inject constructor() : IdGenerator {
    override fun generateId(): String = UUID.randomUUID().toString()
}
