package com.example.listatodolya.bd

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Lpendientes::class],
    version = 1
)
abstract class LpendientesDb:RoomDatabase() {

    abstract fun lpendientesDao():LpendientesDao
}