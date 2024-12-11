package ir.hrka.kotlin.data.datasource.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.hrka.kotlin.data.datasource.db.dbinteractions.KotlinTopicsDao
import ir.hrka.kotlin.data.datasource.db.dbinteractions.KotlinTopicPointsDao
import ir.hrka.kotlin.data.datasource.db.dbinteractions.KotlinTopicSnippetCodesDao
import ir.hrka.kotlin.data.datasource.db.dbinteractions.KotlinTopicSubPointsDao
import ir.hrka.kotlin.domain.entities.db.KotlinTopicModel
import ir.hrka.kotlin.domain.entities.db.KotlinTopicPointModel
import ir.hrka.kotlin.domain.entities.db.KotlinTopicSnippetCodeModel
import ir.hrka.kotlin.domain.entities.db.KotlinTopicSubPointModel

@Database(
    entities = [
        KotlinTopicModel::class,
        KotlinTopicPointModel::class,
        KotlinTopicSubPointModel::class,
        KotlinTopicSnippetCodeModel::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun kotlinTopicsDao(): KotlinTopicsDao
    abstract fun kotlinTopicPointsDao(): KotlinTopicPointsDao
    abstract fun kotlinTopicSupPointsDao(): KotlinTopicSubPointsDao
    abstract fun kotlinTopicSnippetCodesDao(): KotlinTopicSnippetCodesDao
}