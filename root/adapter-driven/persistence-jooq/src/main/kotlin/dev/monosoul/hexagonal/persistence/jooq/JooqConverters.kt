package dev.monosoul.hexagonal.persistence.jooq

import dev.monosoul.hexagonal.domain.model.MessageBody
import dev.monosoul.hexagonal.domain.model.MessageId
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jooq.Converter
import org.jooq.JSONB

object JooqConverters {
    @JvmStatic
    private val jsonMapper = Json

    @JvmStatic
    private val classToConverter: MutableMap<String, Converter<*, *>> = mutableMapOf()

    @Suppress("UNCHECKED_CAST")
    @JvmStatic
    fun <From, To> get(type: String) = classToConverter[type] as Converter<From, To>

    init {
        converterOf(::MessageId, MessageId::uuid)
        converterOf<JSONB, MessageBody>(
            {
                jsonMapper.decodeFromString(it.data())
            },
            {
                JSONB.valueOf(jsonMapper.encodeToString(it))
            }
        )
    }

    @JvmStatic
    private inline fun <reified From, reified To> converterOf(
        noinline fromConverter: (From) -> To,
        noinline toConverter: (To) -> From
    ) {
        classToConverter[To::class.java.name] = Converter.of(
            From::class.java, To::class.java, fromConverter, toConverter
        )
    }
}
