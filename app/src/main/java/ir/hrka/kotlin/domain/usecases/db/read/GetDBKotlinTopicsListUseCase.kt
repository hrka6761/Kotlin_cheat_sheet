package ir.hrka.kotlin.domain.usecases.db.read

import ir.hrka.kotlin.domain.repositories.db.ReadDBKotlinTopicsRepo
import javax.inject.Inject

class GetDBKotlinTopicsListUseCase @Inject constructor(
    private val readDBKotlinTopicsRepo: ReadDBKotlinTopicsRepo
) {

    suspend operator fun invoke() = readDBKotlinTopicsRepo.getKotlinTopicsList()
}