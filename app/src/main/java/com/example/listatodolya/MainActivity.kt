package com.example.listatodolya

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.listatodolya.bd.Lpendientes
import com.example.listatodolya.bd.LpendientesDb
import com.example.listatodolya.databinding.ActivityMainBinding
import com.example.listatodolya.internet.APIService
import com.example.listatodolya.internet.gatosresponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale.filter
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val model:MainViewModel by viewModels()
    var tareas= mutableListOf<Lpendientes>()
    @Inject lateinit var local: LpendientesDb
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                var nuevo=Lpendientes(tareas.size+1,titulonota,contenidonota,false)
                tareas.add(nuevo)
                model.tareasP.postValue(tareas)
                lifecycleScope.launch {
                    local.lpendientesDao().inserte(nuevo)
                }
                customDialog.dismiss()
            }


        }

        binding.gatos.setOnClickListener {
            val dialog= AlertDialog.Builder(this)
            val dialogView=layoutInflater.inflate(R.layout.gatitosagrega,null)
            val etgat=dialogView.findViewById<EditText>(R.id.gatiET)
            dialog.setView(dialogView)
            dialog.setPositiveButton("gatos",{ dialogInterface: DialogInterface, i: Int -> })
            dialog.setNegativeButton("cerrar",{ dialogInterface: DialogInterface, i: Int -> })
            val customDialog=dialog.create()
            customDialog.show()
            customDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                buscarPorNumero(etgat.text.toString().toInt())
                customDialog.dismiss()
            }
        }
        binding.searchView.setOnQueryTextListener(object  : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                var titulos= mutableListOf<String>()
                for (n in tareas){
                    titulos.add(n.titulo)
                }
                if (titulos.filter {s -> s == query}.isNullOrEmpty()){

                }else{

                    var busqueda=titulos.filter {s -> s == query}
                    val listafiltrada= mutableListOf<Lpendientes>()
                    for (y in busqueda){
                        for(j in tareas){
                            if (y==j.titulo){
                                listafiltrada.add(j)
                            }
                        }
                    }
                    var ides= mutableListOf<Int>()
                    var titulos= mutableListOf<String>()
                    var contenidos= mutableListOf<String>()
                    var chequeada= mutableListOf<Boolean>()
                    for (i in listafiltrada){
                        ides.add(i.id)
                    }
                    for (i in listafiltrada){
                        titulos.add(i.titulo)
                    }
                    for (i in listafiltrada){
                        contenidos.add(i.contenido)
                    }
                    for (i in listafiltrada){
                        chequeada.add(i.hechas)
                    }
                    val adaptadora=ListaTareasAdapter(ides,titulos,contenidos,chequeada,this@MainActivity,model)
                    binding.tareasenlistada.adapter=adaptadora

                }


                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var titulos= mutableListOf<String>()
                for (n in tareas){
                    titulos.add(n.titulo)
                }
                if (titulos.filter { s -> s.toLowerCase().contains(newText!!)}.isNullOrEmpty()){
                    if (titulos.filter { s -> s.toLowerCase().contains(newText!!)}.isEmpty()){
                        var ides = mutableListOf<Int>()
                        var titulos = mutableListOf<String>()
                        var contenidos = mutableListOf<String>()
                        var chequeada = mutableListOf<Boolean>()
                        for (i in tareas) {
                            ides.add(i.id)
                        }
                        for (i in tareas) {
                            titulos.add(i.titulo)
                        }
                        for (i in tareas) {
                            contenidos.add(i.contenido)
                        }
                        for (i in tareas) {
                            chequeada.add(i.hechas)
                        }
                        val adaptadora = ListaTareasAdapter(
                            ides,
                            titulos,
                            contenidos,
                            chequeada,
                            this@MainActivity,
                            model
                        )
                        binding.tareasenlistada.adapter = adaptadora

                    }

                } else{
                    var busqueda = titulos.filter { s -> s.toLowerCase().contains(newText!!)}
                    val listafiltrada = mutableListOf<Lpendientes>()
                    for (y in busqueda) {
                        for (j in tareas) {
                            if (y == j.titulo) {
                                listafiltrada.add(j)
                            }
                        }
                    }
                    var ides = mutableListOf<Int>()
                    var titulos = mutableListOf<String>()
                    var contenidos = mutableListOf<String>()
                    var chequeada = mutableListOf<Boolean>()
                    for (i in listafiltrada) {
                        ides.add(i.id)
                    }
                    for (i in listafiltrada) {
                        titulos.add(i.titulo)
                    }
                    for (i in listafiltrada) {
                        contenidos.add(i.contenido)
                    }
                    for (i in listafiltrada) {
                        chequeada.add(i.hechas)
                    }
                    val adaptadora = ListaTareasAdapter(
                        ides,
                        titulos,
                        contenidos,
                        chequeada,
                        this@MainActivity,
                        model
                    )
                    binding.tareasenlistada.adapter = adaptadora
                }


                    return false
            }


        })


        model.abriryeditar.observe(this,{
            var eliminaocambia=EliminarCambiarAdapter(it)
            eliminaocambia.show(supportFragmentManager,"Nuevatarea")

        })

        model.eliminar.observe(this,{
            lifecycleScope.launch {
                local.lpendientesDao().delete(local.lpendientesDao().obtenerID(it))
                val datos=local.lpendientesDao().obtenerTareas()
                model.tareasP.postValue(datos)
            }
        })

        model.actualizar.observe(this,{
            lifecycleScope.launch {
                local.lpendientesDao().update(it)
                val datos=local.lpendientesDao().obtenerTareas()
                model.tareasP.postValue(datos)
            }
        })


        model.tareasP.observe(this, Observer {
            tareas=it
            var ides= mutableListOf<Int>()
            var titulos= mutableListOf<String>()
            var contenidos= mutableListOf<String>()
            var chequeada= mutableListOf<Boolean>()
            for (i in it){
                ides.add(i.id)
            }
            for (i in it){
                titulos.add(i.titulo)
            }
            for (i in it){
                contenidos.add(i.contenido)
            }
            for (i in it){
                chequeada.add(i.hechas)
            }
            val adaptadora=ListaTareasAdapter(ides,titulos,contenidos,chequeada,this,model)
            binding.tareasenlistada.adapter=adaptadora
        })


    }
    private fun getRetrofit():Retrofit{
       return Retrofit.Builder()
           .baseUrl("https://catfact.ninja/")
           .addConverterFactory(GsonConverterFactory.create())
           .build()
    }
    private fun buscarPorNumero(number:Int){
        CoroutineScope(Dispatchers.IO).launch {
            for (i in 1..number) {
            val call: Response<gatosresponse> =getRetrofit().create(APIService::class.java).obtenerFrasesGatos("fact?max_length=140")
            val puppies = call.body()
            runOnUiThread {
                    if (call.isSuccessful) {
                        val respgatos = puppies?.frase?.toString() ?: ""
                        val actrepuesta =
                            Lpendientes(tareas.size + 1, "frase gato:", respgatos, false)
                        //local.lpendientesDao().inserte(actrepuesta)
                            val actuales = model.tareasP.value!!
                            actuales.add(actrepuesta)
                            model.tareasP.postValue(actuales)

                    } else {
                        //show error
                    }
                }
            }

        }
    }
}