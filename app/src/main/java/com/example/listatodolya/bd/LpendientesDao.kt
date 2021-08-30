package com.example.listatodolya.bd

import androidx.room.*

@Dao
interface LpendientesDao {

    @Query("SELECT * FROM Lpendientes")
    suspend fun obtenerTareas():MutableList<Lpendientes>

    @Query("SELECT * FROM Lpendientes WHERE id=:id" )
    suspend fun obtenerID(id:Int):Lpendientes

    @Update
    suspend fun update(person:Lpendientes)

    @Insert
    suspend fun inserte(tareasPendientes:Lpendientes)

    @Delete
    suspend fun delete(person: Lpendientes)

}