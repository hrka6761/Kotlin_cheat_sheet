package ir.hrka.kotlin.domain.usecases.db.write

import ir.hrka.kotlin.core.utilities.Resource
import ir.hrka.kotlin.domain.repositories.db.WriteDBCheatsheetRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

class UpdateCheatSheetUpdateStateUseCase @Inject constructor(private val writeDBCheatsheetRepo: WriteDBCheatsheetRepo) {

    suspend operator fun invoke(vararg ids: Int, hasContentUpdated: Boolean): Resource<Boolean> {
        ids.forEach { id ->
            val differed = CoroutineScope(Dispatchers.IO).async {
                writeDBCheatsheetRepo.updateCheatsheetStateOnDB(id, hasContentUpdated)
            }
            val updateResult = differed.await()

            if (updateResult is Resource.Error)
                return updateResult
        }

        return Resource.Success(true)
    }
}