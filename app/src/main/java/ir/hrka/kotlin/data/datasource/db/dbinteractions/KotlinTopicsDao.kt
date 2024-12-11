package ir.hrka.kotlin.data.datasource.db.dbinteractions

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ir.hrka.kotlin.domain.entities.db.KotlinTopicModel

@Dao
interface KotlinTopicsDao {

    @Insert
    suspend fun insertKotlinTopics(vararg cheatsheets: KotlinTopicModel)

    @Query("SELECT * FROM kotlin_topic")
    suspend fun getKotlinTopics(): List<KotlinTopicModel>

    @Query("DELETE FROM kotlin_topic")
    suspend fun deleteKotlinTopics()

    @Query("UPDATE kotlin_topic SET has_content_updated = :hasContentUpdated WHERE id = :id")
    suspend fun updateKotlinTopicUpdateState(id: Int, hasContentUpdated: Boolean)
}