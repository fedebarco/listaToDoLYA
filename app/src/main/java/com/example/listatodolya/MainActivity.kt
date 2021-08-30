package com.example.listatodolya

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.listatodolya.bd.Lpendientes
import com.example.listatodolya.bd.LpendientesDb
import com.example.listatodolya.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject lateinit var local: LpendientesDb
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val model:MainViewModel by viewModels()
        var tareas= mutableListOf<Lpendientes>()

        lifecycleScope.launch {
            val datos=local.lpendientesDao().obtenerTareas()
            model.tareasP.postValue(datos)


        }

        binding.agrega.setOnClickListener {
            val dialog= AlertDialog.Builder(this)
            var titulonota=""
            var contenidonota=""
            val dialogView=layoutInflater.inflate(R.layout.agregarespuesta,null)
            val etdilog=dialogView.findViewById<EditText>(R.id.tituloPrincipal)
            val eedilog=dialogView.findViewById<EditText>(R.id.enunciadoPrincipal)
            dialog.setView(dialogView)
            dialog.setPositiveButton("Nueva tarea",{ dialogInterface: DialogInterface, i: Int -> })
            dialog.setNegativeButton("cerrar",{ dialogInterface: DialogInterface, i: Int -> })
            val customDialog=dialog.create()
            customDialog.show()
            customDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                titulonota=etdilog.text.toString()
                contenidonota=eedilog.text.toString()
                var nuevo=Lpendientes(tareas.size+1,titulonota,contenidonota)
                tareas.add(nuevo)
                model.tareasP.postValue(tareas)
                lifecycleScope.launch {
                    local.lpendientesDao().inserte(nuevo)
                }
                customDialog.dismiss()
            }


        }

        model.tareasP.observe(this, Observer {
            tareas=it
            var titulos= mutableListOf<String>()
            var contenidos= mutableListOf<String>()
            for (i in it){
                titulos.add(i.titulo)
            }
            for (i in it){
                contenidos.add(i.contenido)
            }
            val adaptadora=ListaTareasAdapter(titulos,contenidos,this)
            binding.tareasenlistada.adapter=adaptadora
        })


    }
}