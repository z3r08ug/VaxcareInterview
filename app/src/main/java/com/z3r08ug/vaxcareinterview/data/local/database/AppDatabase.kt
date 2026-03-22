package com.z3r08ug.vaxcareinterview.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.z3r08ug.vaxcareinterview.data.local.dao.BookDao
import com.z3r08ug.vaxcareinterview.data.local.entity.BookEntity

@Database(entities = [BookEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}
