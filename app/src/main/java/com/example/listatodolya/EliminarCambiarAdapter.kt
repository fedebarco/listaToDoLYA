package com.example.listatodolya

import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.listatodolya.bd.Lpendientes
import com.example.listatodolya.databinding.EliminarcambiarBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class EliminarCambiarAdapter(Numero: Int): BottomSheetDialogFragment() {
    var numero=Numero
    private var _binding: EliminarcambiarBinding? = null
    private val binding get() = _binding!!
    val model: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=EliminarcambiarBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var actualizado=model.tareasP.value!![numero]
        val editableT: Editable = SpannableStringBuilder(actualizado.titulo)
        val editableE: Editable = SpannableStringBuilder(actualizado.contenido)
        binding.enunciadoSecundario.text=editableE
        binding.tituloSecundario.text=editableT
        binding.Elimina.setOnClickListener {
            model.eliminar.postValue(numero)
        }

        binding.Edita.setOnClickListener {
            var titulito=binding.tituloSecundario.text.toString()
            var enunciadito=binding.enunciadoSecundario.text.toString()
            var nuevoactual=Lpendientes(actualizado.id,titulito,enunciadito,actualizado.hechas)
            model.actualizar.postValue(nuevoactual)
        }
    }
}

