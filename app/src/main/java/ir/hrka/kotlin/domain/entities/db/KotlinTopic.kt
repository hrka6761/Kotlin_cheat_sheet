package ir.hrka.kotlin.domain.entities.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kotlin_topic")
data class KotlinTopic(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "title") val name: String,
    @ColumnInfo(name = "version_name") val versionName: String,
    @ColumnInfo(name = "has_updated") var hasUpdated: Boolean = true,
)
