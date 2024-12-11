package ir.hrka.kotlin.data.datasource.db.dbinteractions

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ir.hrka.kotlin.domain.entities.db.KotlinTopicSnippetCodeModel

@Dao
interface KotlinTopicSnippetCodesDao {

    @Insert
    suspend fun insertPointSnippetCodes(vararg snippetCode: KotlinTopicSnippetCodeModel)

    @Query("SELECT * FROM snippet_code where point_id = :pointId")
    suspend fun getPointSnippetCodes(pointId: Long): List<KotlinTopicSnippetCodeModel>

    @Query("DELETE FROM snippet_code where point_id = :pointId")
    suspend fun deletePointSnippetCodes(pointId: Long)
}