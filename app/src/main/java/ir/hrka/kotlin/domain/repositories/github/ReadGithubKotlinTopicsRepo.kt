package ir.hrka.kotlin.domain.repositories.github

import ir.hrka.kotlin.core.utilities.Resource
import ir.hrka.kotlin.domain.entities.KotlinTopicPointDataModel
import ir.hrka.kotlin.domain.entities.RepoFileModel

interface ReadGithubKotlinTopicsRepo {

    suspend fun getKotlinTopicsList(): Resource<List<RepoFileModel>?>
    suspend fun getKotlinTopicPoints(kotlinTopicName: String): Resource<List<KotlinTopicPointDataModel>?>
}