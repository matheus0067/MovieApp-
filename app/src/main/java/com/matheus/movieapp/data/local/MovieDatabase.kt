package com.matheus.movieapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.matheus.movieapp.data.local.dao.MovieDao
import com.matheus.movieapp.data.local.entity.MovieEntity


@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}