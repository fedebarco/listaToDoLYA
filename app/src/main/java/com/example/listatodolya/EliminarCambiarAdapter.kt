package com.example.listatodolya

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.listatodolya.databinding.EliminarcambiarBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EliminarCambiarAdapter(Numero:Int): BottomSheetDialogFragment() {
    var numero=Numero
    private var _binding: EliminarcambiarBinding? = null
    private val binding get() = _binding!!
    val model: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=EliminarcambiarBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.Elimina.setOnClickListener {
            model.eliminar.postValue(numero)
        }
    }
}