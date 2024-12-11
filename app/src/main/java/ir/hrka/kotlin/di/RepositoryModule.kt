package ir.hrka.kotlin.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.hrka.kotlin.data.repositories.github.AppInfoRepoImpl
import ir.hrka.kotlin.data.repositories.db.ReadDBKotlinTopicsRepoImpl
import ir.hrka.kotlin.data.repositories.github.ReadGithubKotlinTopicsRepoImpl
import ir.hrka.kotlin.data.repositories.db.WriteDBKotlinTopicsRepoImpl
import ir.hrka.kotlin.data.repositories.preference.VersionDataRepoImpl
import ir.hrka.kotlin.domain.repositories.github.AppInfoRepo
import ir.hrka.kotlin.domain.repositories.github.ReadGithubKotlinTopicsRepo
import ir.hrka.kotlin.domain.repositories.db.WriteDBKotlinTopicsRepo
import ir.hrka.kotlin.domain.repositories.preference.VersionDataRepo
import ir.hrka.kotlin.domain.repositories.db.ReadDBKotlinTopicsRepo

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    fun bindAppInfoRepo(appInfoRepoImpl: AppInfoRepoImpl): AppInfoRepo

    @Binds
    fun bindGithubKotlinTopicsRepo(readGithubKotlinTopicsRepoImpl: ReadGithubKotlinTopicsRepoImpl): ReadGithubKotlinTopicsRepo

    @Binds
    fun bindDBKotlinTopicsRepo(readDBKotlinTopicsRepoImpl: ReadDBKotlinTopicsRepoImpl): ReadDBKotlinTopicsRepo

    @Binds
    fun bindLocalDataRepo(localDataRepoImpl: VersionDataRepoImpl): VersionDataRepo

    @Binds
    fun bindDBRepo(writeDbCheatSheetsRepoImpl: WriteDBKotlinTopicsRepoImpl): WriteDBKotlinTopicsRepo
}