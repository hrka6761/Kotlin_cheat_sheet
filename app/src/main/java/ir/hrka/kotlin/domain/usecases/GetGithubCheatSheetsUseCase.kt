package ir.hrka.kotlin.domain.usecases

import ir.hrka.kotlin.domain.repositories.ReadCheatSheetRepo
import javax.inject.Inject
import javax.inject.Named

class GetGithubCheatSheetsUseCase @Inject constructor(@Named("Github") private val readCheatSheetRepo: ReadCheatSheetRepo) {

    suspend operator fun invoke() = readCheatSheetRepo.getCheatSheetsList()
}