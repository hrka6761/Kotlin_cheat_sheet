package ir.hrka.kotlin.data.repositories

import ir.hrka.kotlin.core.utilities.Constants.READ_FILE_ERROR_CODE
import ir.hrka.kotlin.core.utilities.Constants.RETROFIT_ERROR_CODE
import ir.hrka.kotlin.core.utilities.Resource
import ir.hrka.kotlin.core.utilities.decodeBase64
import ir.hrka.kotlin.core.utilities.extractClearPointsFromRawPoint
import ir.hrka.kotlin.core.utilities.extractHeadPointsFromPointContent
import ir.hrka.kotlin.core.utilities.extractJavaDocsFromCheatSheetFileContent
import ir.hrka.kotlin.core.utilities.extractRawPointsFromJavaDocContent
import ir.hrka.kotlin.core.utilities.extractSnippetCodeFromPoint
import ir.hrka.kotlin.core.utilities.extractSubPointsFromPointContent
import ir.hrka.kotlin.data.datasource.GithubAPI
import ir.hrka.kotlin.domain.entities.ErrorModel
import ir.hrka.kotlin.domain.entities.PointDataModel
import ir.hrka.kotlin.domain.entities.RepoFileModel
import ir.hrka.kotlin.domain.repositories.ReadCheatSheetRepo
import javax.inject.Inject

class ReadGithubCheatSheetRepoImpl @Inject constructor(
    private val githubAPI: GithubAPI
) : ReadCheatSheetRepo {

    override suspend fun getCheatSheetsList(): Resource<List<RepoFileModel>?> {
        return try {
            val response = githubAPI.getCheatSheetsList()

            if (response.isSuccessful) {
                val sortedList = response.body()?.sortedBy { item -> item.id }
                Resource.Success(sortedList)
            } else {
                Resource.Error(
                    ErrorModel(
                        errorCode = response.code(),
                        errorMsg = response.message()
                    )
                )
            }
        } catch (e: Exception) {
            Resource.Error(
                ErrorModel(
                    errorCode = RETROFIT_ERROR_CODE,
                    errorMsg = e.message.toString()
                )
            )
        }
    }

    override suspend fun getCheatSheetPoints(cheatsheetName: String): Resource<List<PointDataModel>?> {
        val cheatSheetFile = githubAPI.getCheatSheetFile(fileName = cheatsheetName).body()
        val encodedCheatSheetContent = cheatSheetFile?.content ?: ""
        val decodedCheatSheetContent = encodedCheatSheetContent.decodeBase64()

        return if (decodedCheatSheetContent.isNotEmpty())
            Resource.Success(provideCheatSheetData(decodedCheatSheetContent))
        else
            Resource.Error(
                ErrorModel(
                    READ_FILE_ERROR_CODE,
                    "Can't access the github repository files."
                )
            )
    }


    private fun provideCheatSheetData(decodedCheatSheetContent: String): List<PointDataModel> {
        val content = decodedCheatSheetContent.extractJavaDocsFromCheatSheetFileContent()
        val list = mutableListOf<PointDataModel>()
        var index = 1

        content.forEach { javaDoc ->
            javaDoc.extractRawPointsFromJavaDocContent()
                .forEach { rawPoint ->
                    val clearPoint = rawPoint.extractClearPointsFromRawPoint()
                    val snippets = rawPoint.extractSnippetCodeFromPoint()
                    val headPoint = clearPoint.extractHeadPointsFromPointContent()
                    if (headPoint.isNotEmpty()) {
                        val subPoint = clearPoint.extractSubPointsFromPointContent()
                        val num = index++
                        list.add(
                            PointDataModel(
                                num,
                                -1L,
                                rawPoint,
                                headPoint,
                                subPoint,
                                snippets
                            )
                        )
                    }
                }
        }

        return list
    }
}