package ir.hrka.kotlin.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.hrka.kotlin.core.Constants.DATABASE_NAME
import ir.hrka.kotlin.data.datasource.db.AppDatabase
import ir.hrka.kotlin.data.datasource.db.dbinteractions.KotlinTopicsDao
import ir.hrka.kotlin.data.datasource.db.dbinteractions.KotlinTopicPointsDao
import ir.hrka.kotlin.data.datasource.db.dbinteractions.KotlinTopicSnippetCodesDao
import ir.hrka.kotlin.data.datasource.db.dbinteractions.KotlinTopicSubPointsDao
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DBModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideKotlinTopicsDao(db: AppDatabase): KotlinTopicsDao = db.kotlinTopicsDao()

    @Singleton
    @Provides
    fun provideKotlinPointDao(db: AppDatabase): KotlinTopicPointsDao = db.kotlinTopicPointsDao()

    @Singleton
    @Provides
    fun provideKotlinSubPointDao(db: AppDatabase): KotlinTopicSubPointsDao = db.kotlinTopicSupPointsDao()

    @Singleton
    @Provides
    fun provideKotlinSnippetCodeDao(db: AppDatabase): KotlinTopicSnippetCodesDao = db.kotlinTopicSnippetCodesDao()
}