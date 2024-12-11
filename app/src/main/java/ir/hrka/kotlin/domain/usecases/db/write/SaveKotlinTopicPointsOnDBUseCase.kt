package ir.hrka.kotlin.domain.usecases.db.write

import ir.hrka.kotlin.core.utilities.Resource
import ir.hrka.kotlin.domain.entities.KotlinTopicPointDataModel
import ir.hrka.kotlin.domain.entities.db.KotlinTopicPointModel
import ir.hrka.kotlin.domain.entities.db.KotlinTopicSnippetCodeModel
import ir.hrka.kotlin.domain.entities.db.KotlinTopicSubPointModel
import ir.hrka.kotlin.domain.repositories.db.ReadDBKotlinTopicsRepo
import ir.hrka.kotlin.domain.repositories.db.WriteDBKotlinTopicsRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import javax.inject.Inject
import javax.inject.Named

class SaveKotlinTopicPointsOnDBUseCase @Inject constructor(
    @Named("IO") private val io: CoroutineDispatcher,
    private val writeDBCheatsheetRepo: WriteDBKotlinTopicsRepo,
    private val readDBCheatSheetRepo: ReadDBKotlinTopicsRepo
) {

    suspend operator fun invoke(
        points: List<KotlinTopicPointDataModel>,
        cheatsheetName: String
    ): Resource<Boolean> {

        var totalResult: Resource<Boolean> = Resource.Success(true)
        val deleteResult = deleteCheatsheetPoints(cheatsheetName)

        if (deleteResult is Resource.Error) {
            totalResult = deleteResult

            return totalResult
        }

        points.forEach { pointDataModel ->

            val savePointDiffered = CoroutineScope(io).async {
                writeDBCheatsheetRepo.saveKotlinTopicPointOnDB(
                    KotlinTopicPointModel(
                        pointText = pointDataModel.headPoint,
                        kotlinTopicName = cheatsheetName
                    )
                )
            }

            val savePointResult = savePointDiffered.await()

            if (savePointResult is Resource.Error) {
                totalResult = Resource.Error(savePointResult.error!!)
                return@forEach
            }

            val saveSubPointsDiffered = CoroutineScope(io).async {
                pointDataModel.subPoints
                    ?.map { str ->
                        KotlinTopicSubPointModel(
                            pointId = savePointResult.data!!,
                            subPointText = str
                        )
                    }
                    ?.toTypedArray()
                    ?.let {
                        writeDBCheatsheetRepo.saveKotlinTopicSubPointsOnDB(it)
                    }
            }

            val saveSnippetCodesDiffered = CoroutineScope(io).async {
                pointDataModel.snippetsCode
                    ?.map { str ->
                        KotlinTopicSnippetCodeModel(
                            pointId = savePointResult.data!!,
                            snippetCodeText = str
                        )
                    }
                    ?.toTypedArray()
                    ?.let {
                        writeDBCheatsheetRepo.saveKotlinTopicSnippetCodesOnDB(it)
                    }
            }

            val saveSubPointsResult = saveSubPointsDiffered.await()

            val saveSnippetCodesResult = saveSnippetCodesDiffered.await()

            if (saveSubPointsResult is Resource.Error) {
                totalResult = saveSubPointsResult
                return@forEach
            }

            if (saveSnippetCodesResult is Resource.Error) {
                totalResult = saveSnippetCodesResult
                return@forEach
            }
        }

        return totalResult
    }


    private suspend fun deleteCheatsheetPoints(cheatsheetName: String): Resource<Boolean> {
        val cheatsheetPointsIdResult =
            readDBCheatSheetRepo.getKotlinTopicPoints(cheatsheetName)

        if (cheatsheetPointsIdResult is Resource.Error)
            return Resource.Error(cheatsheetPointsIdResult.error!!)

        val cheatsheets = cheatsheetPointsIdResult.data
            ?.map { pointDataModel -> pointDataModel.databaseId!! }!!

        val deletePointsDiffered = CoroutineScope(io).async {
            writeDBCheatsheetRepo.deleteKotlinTopicPoints(cheatsheetName)
        }

        val deleteSubPointsDiffered = CoroutineScope(io).async {
            var result: Resource<Boolean> = Resource.Success(true)

            cheatsheets.forEach {
                result = writeDBCheatsheetRepo.deleteKotlinTopicPointSubPoints(it)
                if (result is Resource.Error) {
                    return@forEach
                }
            }

            result
        }

        val deleteSnippetCodesDiffered = CoroutineScope(io).async {
            var result: Resource<Boolean> = Resource.Success(true)

            cheatsheets.forEach {
                result = writeDBCheatsheetRepo.deleteKotlinTopicPointSnippetCodes(it)

                if (result is Resource.Error) {
                    return@forEach
                }
            }

            result
        }

        val deletePointsResult = deletePointsDiffered.await()
        val deleteSubPointsResult = deleteSubPointsDiffered.await()
        val deleteSnippetCodesResult = deleteSnippetCodesDiffered.await()

        if (deletePointsResult is Resource.Error) {
            return deletePointsResult
        }
        if (deleteSubPointsResult is Resource.Error) {
            return deleteSubPointsResult
        }
        if (deleteSnippetCodesResult is Resource.Error) {
            return deleteSnippetCodesResult
        }

        return Resource.Success(true)
    }
}