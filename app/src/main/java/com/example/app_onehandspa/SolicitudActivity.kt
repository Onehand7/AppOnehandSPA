package com.example.app_onehandspa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SolicitudActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solicitud)

        //setup
        setup()
    }
    private fun setup(){
        val tituloTxt = findViewById<EditText>(R.id.tituloText)
        val descripcionTxt = findViewById<EditText>(R.id.descripcionText)
        val btn_continuar = findViewById<Button>(R.id.continuarButton)
        btn_continuar.setOnClickListener {
            if (tituloTxt.text.isNotEmpty() && descripcionTxt.text.isNotEmpty()){
                val intent = Intent(this,MenuActivity::class.java)
                startActivity(intent)
            }else{
                validate()
            }
        }
    }

    //Función que valida los campos titulo y descripcion juntos.
    private fun validate(){
        val result = arrayOf(validateTitulo(),validateDescripcion())
        if (false in result){
            return
        }else{
            Toast.makeText(this,"Exito", Toast.LENGTH_SHORT).show()
            
        }

    }
    //Función que valida el campo titulo
    private fun validateTitulo():Boolean{
        val titulo = findViewById<EditText>(R.id.nombreEditText)
        return when {
            titulo.text.isEmpty() -> {
                titulo.error = " los campos no pueden estar vacios"
                false
            }
            else -> {
                titulo.error = null
                true
            }
        }
    }
    //Funcion que valida el campo descripcion
    private fun validateDescripcion():Boolean{
        val descripcion = findViewById<EditText>(R.id.nombreEditText)
        return when {
            descripcion.text.isEmpty() -> {
                descripcion.error = " los campos no pueden estar vacios"
                false
            }
            else -> {
                descripcion.error = null
                true
            }
        }
    }
}