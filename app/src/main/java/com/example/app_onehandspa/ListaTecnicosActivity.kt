package com.example.app_onehandspa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ListaTecnicosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_tecnicos)

        //setup
        setup()
    }

    private fun setup(){
        val btn_verMas = findViewById<Button>(R.id.verMasButton)
        //Boton de ver mas en lista de profesionales con el evento setOnClick
        btn_verMas.setOnClickListener {
            val intent = Intent(this,DescripcionProfesionalActivity::class.java)
            startActivity(intent)
        }
    }
}