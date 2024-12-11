package ir.hrka.kotlin.data.datasource.db.dbinteractions

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ir.hrka.kotlin.domain.entities.db.KotlinTopicSubPointModel

@Dao
interface KotlinTopicSubPointsDao {

    @Insert
    suspend fun insertPointSubPoints(vararg subPoint: KotlinTopicSubPointModel)

    @Query("SELECT * FROM sub_point where point_id = :pointId")
    suspend fun getPointSubPoints(pointId: Long): List<KotlinTopicSubPointModel>

    @Query("DELETE FROM sub_point where point_id = :pointId")
    suspend fun deletePointSubPoints(pointId: Long)
}