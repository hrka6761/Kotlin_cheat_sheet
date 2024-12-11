package ir.hrka.kotlin.domain.entities.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "snippet_code")
data class KotlinTopicSnippetCodeModel(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "point_id") val pointId: Long,
    @ColumnInfo(name = "snippet_code_text") val snippetCodeText: String,
)