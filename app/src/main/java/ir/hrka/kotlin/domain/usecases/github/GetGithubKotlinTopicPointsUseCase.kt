package ir.hrka.kotlin.domain.usecases.github

import ir.hrka.kotlin.domain.repositories.github.ReadGithubKotlinTopicsRepo
import javax.inject.Inject

class GetGithubKotlinTopicPointsUseCase @Inject constructor(private val readGithubCheatSheetRepo: ReadGithubKotlinTopicsRepo) {

    suspend operator fun invoke(fileName: String) =
        readGithubCheatSheetRepo.getKotlinTopicPoints(fileName)
}