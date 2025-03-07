package com.composenhilt.di

import android.content.Context
import androidx.room.Room
import com.composenhilt.data.room_database.init_database.MyRoomDatabase
import com.composenhilt.data.room_database.init_database.MyRoomDatabase.Companion.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MyRoomDatabaseModule {

    private val LOCK = Any()

    @Provides
    @Singleton
    fun initRoomDataBase(@ApplicationContext context: Context): MyRoomDatabase {
        return synchronized(LOCK) {
            Room.databaseBuilder(
                context.applicationContext,
                MyRoomDatabase::class.java,
                DB_NAME
            ).build()
        }
    }
}