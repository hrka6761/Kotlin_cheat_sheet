package ir.hrka.kotlin.domain.entities.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kotlin_topic")
data class KotlinTopicModel(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "title") val name: String,
    @ColumnInfo(name = "version_name") val versionName: String,
    @ColumnInfo(name = "has_content_updated") var hasContentUpdated: Boolean = true,
)
