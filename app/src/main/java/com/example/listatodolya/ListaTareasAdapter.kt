package com.example.listatodolya

import android.annotation.SuppressLint
import android.content.Context
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.listatodolya.bd.Lpendientes

class ListaTareasAdapter(ides1:MutableList<Int>,listaTitulos:MutableList<String>,listaTareas:MutableList<String>,tHecha:MutableList<Boolean>,context: Context,model: MainViewModel): BaseAdapter() {
    var listatareas=listaTareas
    var listatitulos=listaTitulos
    var thecha=tHecha
    var model1=model
    var ides=ides1
    private val mInflator: LayoutInflater = LayoutInflater.from(context)
    override fun getCount(): Int {
        return listatareas.size
    }

    override fun getItem(position: Int): Any {
        return listatareas[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View?
        val vh: ListRowHolder3
        if (convertView == null) {
            view = this.mInflator.inflate(R.layout.itemlista, parent, false)
            vh = ListRowHolder3(view,listatareas,position,model1,ides)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder3
        }
        vh.descripciontarea.text=listatitulos[position]
        vh.enunciadoTarea.text=listatareas[position]
        vh.enunciadoTarea.isChecked=thecha[position]
        return view
    }
    }
private class ListRowHolder3(row: View?, lasbaseD: MutableList<String>, position1: Int, model: MainViewModel,ides2:MutableList<Int>) {
    val descripciontarea: TextView = row?.findViewById(R.id.Descripción) as TextView
    val enunciadoTarea: CheckBox = row?.findViewById(R.id.Enunciado) as CheckBox
    val boton: ImageButton = row?.findViewById(R.id.eoe) as ImageButton
    init {
        enunciadoTarea.setOnCheckedChangeListener { buttonView, isChecked ->
            val lista=model.tareasP.value!!
            val elque=lista[position1]
            val reemplazo=Lpendientes(elque.id,elque.titulo,elque.contenido,isChecked)
            //model.actualizar.postValue(reemplazo)

        }
        boton.setOnClickListener {
            model.abriryeditar.postValue(position1)
        }
    }


}

