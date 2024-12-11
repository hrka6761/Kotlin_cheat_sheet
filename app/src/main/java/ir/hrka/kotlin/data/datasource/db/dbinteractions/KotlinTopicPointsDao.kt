package ir.hrka.kotlin.data.datasource.db.dbinteractions

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ir.hrka.kotlin.domain.entities.db.KotlinTopicPointModel

@Dao
interface KotlinTopicPointsDao {

    @Insert
    suspend fun insertKotlinTopicPoints(point: KotlinTopicPointModel): Long

    @Query("SELECT * FROM point where kotlin_topic_name = :kotlinTopicName")
    suspend fun getKotlinTopicPoints(kotlinTopicName: String): List<KotlinTopicPointModel>

    @Query("DELETE FROM point where kotlin_topic_name = :kotlinTopicName")
    suspend fun deleteKotlinTopicPoints(kotlinTopicName: String)
}