package ir.hrka.kotlin.domain.usecases.db.read

import ir.hrka.kotlin.domain.repositories.db.ReadDBKotlinTopicsRepo
import javax.inject.Inject

class GetDBKotlinTopicPointsUseCase @Inject constructor(private val readDBKotlinTopicsRepo: ReadDBKotlinTopicsRepo) {

    suspend operator fun invoke(fileName: String) = readDBKotlinTopicsRepo.getKotlinTopicPoints(fileName)
}