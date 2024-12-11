package ir.hrka.kotlin.domain.usecases.db.write

import ir.hrka.kotlin.domain.entities.db.KotlinTopicModel
import ir.hrka.kotlin.domain.repositories.db.WriteDBKotlinTopicsRepo
import javax.inject.Inject

class SaveKotlinTopicsOnDBUseCase @Inject constructor(private val writeDBKotlinTopicsRepo: WriteDBKotlinTopicsRepo) {

    suspend operator fun invoke(cheatsheets: List<KotlinTopicModel>) =
        writeDBKotlinTopicsRepo.saveKotlinTopicsListOnDB(cheatsheets)
}