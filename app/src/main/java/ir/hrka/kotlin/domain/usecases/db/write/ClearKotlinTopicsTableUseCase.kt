package ir.hrka.kotlin.domain.usecases.db.write

import ir.hrka.kotlin.domain.repositories.db.WriteDBKotlinTopicsRepo
import javax.inject.Inject

class ClearKotlinTopicsTableUseCase @Inject constructor(private val writeDBCheatsheetRepo: WriteDBKotlinTopicsRepo) {

    suspend operator fun invoke() = writeDBCheatsheetRepo.clearKotlinTopicsListTable()
}