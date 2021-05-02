package com.example.app_onehandspa.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.app_onehandspa.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val botonInformatica = view.findViewById<ImageButton>(R.id.btn_informatica)
        val botonElectromecanico = view.findViewById<ImageButton>(R.id.btn_electromecanico)
        val botonElectrico = view.findViewById<ImageButton>(R.id.btn_electrico)
        val botonGasfiter = view.findViewById<ImageButton>(R.id.btn_gasfiter)
        val botonMecanico = view.findViewById<ImageButton>(R.id.btn_mecanico)
        val botonPedagogia = view.findViewById<ImageButton>(R.id.btn_pedagogia)
        val botonEnfermeria = view.findViewById<ImageButton>(R.id.btn_enfermeria)
        val botonKinesiologia = view.findViewById<ImageButton>(R.id.btn_kinesiologo)
        val botonBabysister = view.findViewById<ImageButton>(R.id.btn_baby)
        val botonTurismo = view.findViewById<ImageButton>(R.id.btn_turismo)

        botonInformatica.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.solicitudActivity)
        }
        botonElectromecanico.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.solicitudActivity)
        }
        botonElectrico.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.solicitudActivity)
        }
        botonGasfiter.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.solicitudActivity)
        }
        botonMecanico.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.solicitudActivity)
        }
        botonPedagogia.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.solicitudActivity)
        }
        botonEnfermeria.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.solicitudActivity)
        }
        botonKinesiologia.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.solicitudActivity)
        }
        botonBabysister.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.solicitudActivity)
        }
        botonTurismo.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.solicitudActivity)
        }
    }
}