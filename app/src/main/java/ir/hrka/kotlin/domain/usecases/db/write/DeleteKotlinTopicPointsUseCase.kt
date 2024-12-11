package ir.hrka.kotlin.domain.usecases.db.write

import ir.hrka.kotlin.domain.repositories.db.WriteDBKotlinTopicsRepo
import javax.inject.Inject

class DeleteKotlinTopicPointsUseCase @Inject constructor(private val writeDBKotlinTopicsRepo: WriteDBKotlinTopicsRepo) {

    suspend operator fun invoke(cheatsheetName: String) =
        writeDBKotlinTopicsRepo.deleteKotlinTopicPoints(cheatsheetName)
}