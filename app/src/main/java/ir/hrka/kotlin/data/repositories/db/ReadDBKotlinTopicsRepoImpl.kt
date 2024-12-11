package ir.hrka.kotlin.data.repositories.db

import ir.hrka.kotlin.core.Constants.DB_READ_CHEATSHEETS_LIST_ERROR_CODE
import ir.hrka.kotlin.core.Constants.DB_READ_CHEATSHEET_POINTS_ERROR_CODE
import ir.hrka.kotlin.core.utilities.Resource
import ir.hrka.kotlin.data.datasource.db.dbinteractions.KotlinTopicsDao
import ir.hrka.kotlin.data.datasource.db.dbinteractions.KotlinTopicPointsDao
import ir.hrka.kotlin.data.datasource.db.dbinteractions.KotlinTopicSnippetCodesDao
import ir.hrka.kotlin.data.datasource.db.dbinteractions.KotlinTopicSubPointsDao
import ir.hrka.kotlin.domain.entities.ErrorModel
import ir.hrka.kotlin.domain.entities.KotlinTopicPointDataModel
import ir.hrka.kotlin.domain.entities.db.KotlinTopicModel
import ir.hrka.kotlin.domain.repositories.db.ReadDBKotlinTopicsRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import javax.inject.Inject
import javax.inject.Named

class ReadDBKotlinTopicsRepoImpl @Inject constructor(
    @Named("IO") private val io: CoroutineDispatcher,
    private val kotlinTopicsDao: KotlinTopicsDao,
    private val kotlinPointDao: KotlinTopicPointsDao,
    private val kotlinSubPointDao: KotlinTopicSubPointsDao,
    private val kotlinSnippetCodeDao: KotlinTopicSnippetCodesDao
) : ReadDBKotlinTopicsRepo {

    override suspend fun getKotlinTopicsList(): Resource<List<KotlinTopicModel>?> {
        return try {
            Resource.Success(kotlinTopicsDao.getKotlinTopics())
        } catch (e: Exception) {
            Resource.Error(
                ErrorModel(
                    errorCode = DB_READ_CHEATSHEETS_LIST_ERROR_CODE,
                    errorMsg = "Can't read cheatsheet from the database."
                )
            )
        }
    }

    override suspend fun getKotlinTopicPoints(kotlinTopicName: String): Resource<List<KotlinTopicPointDataModel>?> {
        return try {
            val points = kotlinPointDao.getKotlinTopicPoints(kotlinTopicName)
            var index = 1

            return Resource.Success(
                points.map { point ->
                    val subPointsDiffered = CoroutineScope(io).async {
                        point.id?.let { kotlinSubPointDao.getPointSubPoints(it) }
                    }
                    val snippetsCodesDiffered = CoroutineScope(io).async {
                        point.id?.let { kotlinSnippetCodeDao.getPointSnippetCodes(it) }
                    }

                    val subPoints =
                        subPointsDiffered.await()?.map { subPoint -> subPoint.subPointText }
                    val snippetsCodes =
                        snippetsCodesDiffered.await()
                            ?.map { snippetsCode -> snippetsCode.snippetCodeText }

                    KotlinTopicPointDataModel(
                        num = index++,
                        databaseId = point.id,
                        rawPoint = "",
                        headPoint = point.pointText,
                        subPoints = subPoints,
                        snippetsCode = snippetsCodes
                    )
                }
            )
        } catch (e: Exception) {
            Resource.Error(
                ErrorModel(
                    errorCode = DB_READ_CHEATSHEET_POINTS_ERROR_CODE,
                    errorMsg = e.message.toString()
                )
            )
        }
    }
}