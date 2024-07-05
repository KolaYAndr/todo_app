package com.cleverpumpkin.todoapp.data.id_handlers

import com.cleverpumpkin.todoapp.domain.id_handlers.IdGenerator
import java.util.UUID
import javax.inject.Inject

class IdGeneratorImpl @Inject constructor() : IdGenerator {
    override fun generateId(): String = UUID.randomUUID().toString()
}