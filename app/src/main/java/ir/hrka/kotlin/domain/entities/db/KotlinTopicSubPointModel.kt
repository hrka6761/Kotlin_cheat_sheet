package ir.hrka.kotlin.domain.entities.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sub_point")
data class KotlinTopicSubPointModel(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "point_id") val pointId: Long,
    @ColumnInfo(name = "sub_point_text") val subPointText: String,
)