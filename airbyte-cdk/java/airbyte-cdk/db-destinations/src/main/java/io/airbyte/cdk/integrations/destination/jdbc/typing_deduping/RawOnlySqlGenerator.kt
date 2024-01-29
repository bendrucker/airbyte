package io.airbyte.cdk.integrations.destination.jdbc.typing_deduping

import io.airbyte.cdk.integrations.destination.NamingConventionTransformer
import io.airbyte.cdk.integrations.destination.jdbc.TableDefinition
import io.airbyte.integrations.base.destination.typing_deduping.AirbyteType
import io.airbyte.integrations.base.destination.typing_deduping.ColumnId
import io.airbyte.integrations.base.destination.typing_deduping.StreamConfig
import org.jooq.Condition
import org.jooq.DataType
import org.jooq.Field
import org.jooq.SQLDialect
import java.util.*

class RawOnlySqlGenerator(private val namingTransformer: NamingConventionTransformer) :
    JdbcSqlGenerator(namingTransformer) {
    override fun getStructType(): DataType<*>? {
        throw NotImplementedError("This Destination Does not support typing")
    }

    override fun getArrayType(): DataType<*>? {
        throw NotImplementedError("This Destination Does not support typing")
    }

    override fun getWidestType(): DataType<*>? {
        throw NotImplementedError("This Destination Does not support typing")
    }

    override fun getDialect(): SQLDialect? {
        throw NotImplementedError("This Destination Does not support typing")
    }

    override fun extractRawDataFields(
        columns: LinkedHashMap<ColumnId, AirbyteType>,
        useExpensiveSaferCasting: Boolean
    ): List<Field<*>>? {
        return null
    }

    override fun buildAirbyteMetaColumn(columns: LinkedHashMap<ColumnId, AirbyteType>): Field<*>? {
        return null
    }

    override fun cdcDeletedAtNotNullCondition(): Condition? {
        return null
    }

    override fun getRowNumber(
        primaryKey: List<ColumnId>,
        cursorField: Optional<ColumnId>
    ): Field<Int>? {
        return null
    }

    override fun existingSchemaMatchesStreamConfig(
        stream: StreamConfig,
        existingTable: TableDefinition
    ): Boolean {
        return false
    }
}