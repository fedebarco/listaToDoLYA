package com.example.listatodolya

import androidx.lifecycle.ViewModel
//import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.listatodolya.bd.Lpendientes
import com.example.listatodolya.bd.LpendientesDb
import kotlinx.coroutines.launch

class MainViewModel: ViewModel(){                               //@ViewModelInject constructor(val getRoom:LpendientesDb):ViewModel() {

    val tareasP=MutableLiveData<MutableList<Lpendientes>>()
    val abriryeditar= MutableLiveData<Int>()
    val eliminar= MutableLiveData<Int>()
    val actualizar= MutableLiveData<Lpendientes>()


    init {
        viewModelScope.launch {
            //tareasP.postValue(getRoom.lpendientesDao().obtenerTareas())
        }
    }



}