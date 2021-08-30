package com.example.listatodolya

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class ListaTareasAdapter(listaTareas:MutableList<String>,context: Context): BaseAdapter() {
    var listatareas=listaTareas
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
            vh = ListRowHolder3(view,listatareas,position)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder3
        }
        vh.descripciontarea.text=listatareas[position]
        vh.enunciadoTarea.text=listatareas[position]
        return view
    }
    }
private class ListRowHolder3(row: View?, lasbaseD: MutableList<String>, position1: Int) {
    val descripciontarea: TextView = row?.findViewById(R.id.Descripción) as TextView
    val enunciadoTarea: CheckBox = row?.findViewById(R.id.Enunciado) as CheckBox


}

