package com.example.listatodolya.bd
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Lpendientes (
    @PrimaryKey val id:Int,
    val titulo:String,
    val contenido:String

)