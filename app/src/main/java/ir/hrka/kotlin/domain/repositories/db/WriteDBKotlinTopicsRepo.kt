package ir.hrka.kotlin.domain.repositories.db

import ir.hrka.kotlin.core.utilities.Resource
import ir.hrka.kotlin.domain.entities.db.KotlinTopicModel
import ir.hrka.kotlin.domain.entities.db.KotlinTopicPointModel
import ir.hrka.kotlin.domain.entities.db.KotlinTopicSnippetCodeModel
import ir.hrka.kotlin.domain.entities.db.KotlinTopicSubPointModel

interface WriteDBKotlinTopicsRepo {

    suspend fun saveKotlinTopicsListOnDB(kotlinTopics: List<KotlinTopicModel>): Resource<Boolean>
    suspend fun clearKotlinTopicsListTable(): Resource<Boolean>
    suspend fun updateKotlinTopicStateOnDB(id: Int, hasContentUpdated: Boolean): Resource<Boolean>
    suspend fun saveKotlinTopicPointOnDB(kotlinTopicPoint: KotlinTopicPointModel): Resource<Long>
    suspend fun saveKotlinTopicSubPointsOnDB(kotlinTopicSubPoints: Array<KotlinTopicSubPointModel>): Resource<Boolean>
    suspend fun saveKotlinTopicSnippetCodesOnDB(kotlinTopicSnippetCodes: Array<KotlinTopicSnippetCodeModel>): Resource<Boolean>
    suspend fun deleteKotlinTopicPoints(kotlinTopicName: String): Resource<Boolean>
    suspend fun deleteKotlinTopicPointSubPoints(kotlinTopicPointId: Long): Resource<Boolean>
    suspend fun deleteKotlinTopicPointSnippetCodes(kotlinTopicPointId: Long): Resource<Boolean>
}