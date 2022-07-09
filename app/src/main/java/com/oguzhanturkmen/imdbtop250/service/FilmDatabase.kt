package com.oguzhanturkmen.imdbtop250.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.oguzhanturkmen.imdbtop250.models.Item

@Database(entities = arrayOf(Item::class), version = 1)
abstract class FilmDatabase : RoomDatabase() {
    abstract fun FilmDao() : FilmDao

    //Singleton

    companion object {

        @Volatile private var instance : FilmDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            FilmDatabase::class.java,"filmdatabase").build()
    }
}