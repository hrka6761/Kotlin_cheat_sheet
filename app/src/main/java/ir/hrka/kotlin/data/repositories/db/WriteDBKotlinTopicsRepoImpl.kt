package ir.hrka.kotlin.data.repositories.db

import ir.hrka.kotlin.core.Constants.DB_CLEAR_CHEATSHEET_TABLE_ERROR_CODE
import ir.hrka.kotlin.core.Constants.DB_DELETE_CHEATSHEET_POINTS_ERROR_CODE
import ir.hrka.kotlin.core.Constants.DB_DELETE_POINT_SNIPPET_CODES_ERROR_CODE
import ir.hrka.kotlin.core.Constants.DB_DELETE_POINT_SUB_POINTS_ERROR_CODE
import ir.hrka.kotlin.core.Constants.DB_UPDATE_CHEATSHEETS_ERROR_CODE
import ir.hrka.kotlin.core.Constants.DB_WRITE_CHEATSHEETS_ERROR_CODE
import ir.hrka.kotlin.core.Constants.DB_WRITE_POINTS_ERROR_CODE
import ir.hrka.kotlin.core.Constants.DB_WRITE_SNIPPET_CODES_ERROR_CODE
import ir.hrka.kotlin.core.Constants.DB_WRITE_SUB_POINTS_ERROR_CODE
import ir.hrka.kotlin.core.utilities.Resource
import ir.hrka.kotlin.data.datasource.db.dbinteractions.KotlinTopicsDao
import ir.hrka.kotlin.data.datasource.db.dbinteractions.KotlinTopicPointsDao
import ir.hrka.kotlin.data.datasource.db.dbinteractions.KotlinTopicSnippetCodesDao
import ir.hrka.kotlin.data.datasource.db.dbinteractions.KotlinTopicSubPointsDao
import ir.hrka.kotlin.domain.entities.ErrorModel
import ir.hrka.kotlin.domain.entities.db.KotlinTopicModel
import ir.hrka.kotlin.domain.entities.db.KotlinTopicPointModel
import ir.hrka.kotlin.domain.entities.db.KotlinTopicSnippetCodeModel
import ir.hrka.kotlin.domain.entities.db.KotlinTopicSubPointModel
import ir.hrka.kotlin.domain.repositories.db.WriteDBKotlinTopicsRepo
import javax.inject.Inject

class WriteDBKotlinTopicsRepoImpl @Inject constructor(
    private val cheatsheetDao: KotlinTopicsDao,
    private val pointDao: KotlinTopicPointsDao,
    private val subPointDao: KotlinTopicSubPointsDao,
    private val snippetCodeDao: KotlinTopicSnippetCodesDao
) : WriteDBKotlinTopicsRepo {

    override suspend fun saveKotlinTopicsListOnDB(kotlinTopics: List<KotlinTopicModel>): Resource<Boolean> {
        return try {
            cheatsheetDao.insertKotlinTopics(*kotlinTopics.toTypedArray())
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(
                ErrorModel(
                    errorCode = DB_WRITE_CHEATSHEETS_ERROR_CODE,
                    errorMsg = e.message.toString()
                )
            )
        }
    }

    override suspend fun clearKotlinTopicsListTable(): Resource<Boolean> {
        return try {
            cheatsheetDao.deleteKotlinTopics()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(
                ErrorModel(
                    errorCode = DB_CLEAR_CHEATSHEET_TABLE_ERROR_CODE,
                    errorMsg = e.message.toString()
                )
            )
        }
    }

    override suspend fun updateKotlinTopicStateOnDB(
        id: Int,
        hasContentUpdated: Boolean
    ): Resource<Boolean> {
        return try {
            cheatsheetDao.updateKotlinTopicUpdateState(id, hasContentUpdated)
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(
                ErrorModel(
                    errorCode = DB_UPDATE_CHEATSHEETS_ERROR_CODE,
                    errorMsg = e.message.toString()
                )
            )
        }
    }

    override suspend fun saveKotlinTopicPointOnDB(kotlinTopicPoint: KotlinTopicPointModel): Resource<Long> {
        return try {
            val rowId = pointDao.insertKotlinTopicPoints(kotlinTopicPoint)
            Resource.Success(rowId)
        } catch (e: Exception) {
            Resource.Error(
                ErrorModel(
                    errorCode = DB_WRITE_POINTS_ERROR_CODE,
                    errorMsg = e.message.toString()
                )
            )
        }
    }

    override suspend fun saveKotlinTopicSubPointsOnDB(kotlinTopicSubPoints: Array<KotlinTopicSubPointModel>): Resource<Boolean> {
        return try {
            subPointDao.insertPointSubPoints(*kotlinTopicSubPoints)
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(
                ErrorModel(
                    errorCode = DB_WRITE_SUB_POINTS_ERROR_CODE,
                    errorMsg = e.message.toString()
                )
            )
        }
    }

    override suspend fun saveKotlinTopicSnippetCodesOnDB(kotlinTopicSnippetCodes: Array<KotlinTopicSnippetCodeModel>): Resource<Boolean> {
        return try {
            snippetCodeDao.insertPointSnippetCodes(*kotlinTopicSnippetCodes)
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(
                ErrorModel(
                    errorCode = DB_WRITE_SNIPPET_CODES_ERROR_CODE,
                    errorMsg = e.message.toString()
                )
            )
        }
    }

    override suspend fun deleteKotlinTopicPoints(kotlinTopicName: String): Resource<Boolean> {
        return try {
            pointDao.deleteKotlinTopicPoints(kotlinTopicName)
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(
                ErrorModel(
                    errorCode = DB_DELETE_CHEATSHEET_POINTS_ERROR_CODE,
                    errorMsg = e.message.toString()
                )
            )
        }
    }

    override suspend fun deleteKotlinTopicPointSubPoints(kotlinTopicPointId: Long): Resource<Boolean> {
        return try {
            subPointDao.deletePointSubPoints(kotlinTopicPointId)
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(
                ErrorModel(
                    errorCode = DB_DELETE_POINT_SUB_POINTS_ERROR_CODE,
                    errorMsg = e.message.toString()
                )
            )
        }
    }

    override suspend fun deleteKotlinTopicPointSnippetCodes(kotlinTopicPointId: Long): Resource<Boolean> {
        return try {
            snippetCodeDao.deletePointSnippetCodes(kotlinTopicPointId)
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(
                ErrorModel(
                    errorCode = DB_DELETE_POINT_SNIPPET_CODES_ERROR_CODE,
                    errorMsg = e.message.toString()
                )
            )
        }
    }
}