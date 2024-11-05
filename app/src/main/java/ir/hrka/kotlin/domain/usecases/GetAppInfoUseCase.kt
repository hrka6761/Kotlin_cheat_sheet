package ir.hrka.kotlin.domain.usecases

import android.util.Base64
import ir.hrka.kotlin.core.utilities.Constants.READ_FILE_ERROR_CODE
import ir.hrka.kotlin.core.utilities.Resource
import ir.hrka.kotlin.domain.entities.AppInfoModel
import ir.hrka.kotlin.domain.entities.ErrorModel
import ir.hrka.kotlin.domain.repositories.AppInfoRepo
import javax.inject.Inject

class GetAppInfoUseCase @Inject constructor(private val appInfoRepo: AppInfoRepo) {

    suspend operator fun invoke(): Resource<AppInfoModel?> {
        val gradleFile = appInfoRepo.getAppInfo().data
        val gradleFileContent = if (gradleFile != null) decodeBase64(gradleFile.content) else null

        return if (gradleFileContent != null)
            Resource.Success(provideAppInfoModel(gradleFileContent))
        else
            Resource.Error(ErrorModel(READ_FILE_ERROR_CODE, "Can't access the github repository files."))
    }


    private fun decodeBase64(encodedString: String): String {
        val decodedBytes = Base64.decode(encodedString, Base64.DEFAULT)
        return String(decodedBytes)
    }

    private fun provideAppInfoModel(gradleText: String): AppInfoModel {
        val versionCodeRegex = """versionCode\s*=\s*(\d+)""".toRegex()
        val versionNameRegex = """versionName\s*=\s*"([^"]+)"""".toRegex()
        val versionNameSuffixRegex = """versionNameSuffix\s*=\s*"([^"]+)"""".toRegex()
        val versionCode = (versionCodeRegex.find(gradleText)?.groups?.get(1)?.value ?: "-1").toInt()
        val versionName = versionNameRegex.find(gradleText)?.groups?.get(1)?.value ?: "Unknown"
        val versionNameSuffix = versionNameSuffixRegex.find(gradleText)?.groups?.get(1)?.value ?: "Unknown"

        return AppInfoModel(versionCode, versionName, versionNameSuffix)
    }
}