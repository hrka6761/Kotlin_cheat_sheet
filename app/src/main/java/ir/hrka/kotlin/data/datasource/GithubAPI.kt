package ir.hrka.kotlin.data.datasource

import ir.hrka.kotlin.domain.entities.RepoFileModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface GithubAPI {

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("app/build.gradle.kts")
    suspend fun getAppInfo(): Response<RepoFileModel>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("app/src/main/java/ir/hrka/kotlin/cheatSheet")
    suspend fun getCheatSheetsList(): Response<List<RepoFileModel>>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("app/src/main/java/ir/hrka/kotlin/cheatSheet/{fileName}")
    suspend fun getCheatSheetFile(@Path("fileName") fileName: String): Response<RepoFileModel>
}