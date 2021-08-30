package com.example.listatodolya

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.listatodolya.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val model:MainViewModel by viewModels()
        val tusuario= mutableListOf("tarea 1","tarea 2","tarea 3")

        val adaptadora=ListaTareasAdapter(tusuario,this)
        binding.tareasenlistada.adapter=adaptadora

    }
}