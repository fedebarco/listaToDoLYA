package com.example.listatodolya.di

import android.content.Context
import androidx.room.Room
import com.example.listatodolya.bd.LpendientesDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
class DependenciasModule {
    @Provides
    fun provideLpendientes(@ApplicationContext context: Context):LpendientesDb{
        val room= Room.databaseBuilder(context,LpendientesDb::class.java,"Lpendientes").build()
        return room
    }
}