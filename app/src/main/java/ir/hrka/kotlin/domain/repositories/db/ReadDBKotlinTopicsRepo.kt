package ir.hrka.kotlin.domain.repositories.db

import ir.hrka.kotlin.core.utilities.Resource
import ir.hrka.kotlin.domain.entities.KotlinTopicPointDataModel
import ir.hrka.kotlin.domain.entities.db.KotlinTopicModel

interface ReadDBKotlinTopicsRepo {

    suspend fun getKotlinTopicsList(): Resource<List<KotlinTopicModel>?>
    suspend fun getKotlinTopicPoints(kotlinTopicName: String): Resource<List<KotlinTopicPointDataModel>?>
}