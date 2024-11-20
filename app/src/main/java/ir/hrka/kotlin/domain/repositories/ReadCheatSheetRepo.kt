package ir.hrka.kotlin.domain.repositories

import ir.hrka.kotlin.core.utilities.Resource
import ir.hrka.kotlin.domain.entities.PointDataModel
import ir.hrka.kotlin.domain.entities.RepoFileModel

interface ReadCheatSheetRepo {

    suspend fun getCheatSheetsList(): Resource<List<RepoFileModel>?>
    suspend fun getCheatSheetPoints(cheatsheetName: String): Resource<List<PointDataModel>?>
}