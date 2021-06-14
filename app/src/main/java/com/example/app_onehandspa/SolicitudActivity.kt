package com.example.app_onehandspa

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SolicitudActivity() : AppCompatActivity() {
    //Referencia a la base de datos en firebase
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solicitud)

        val bundleEmail = intent.extras
        val datoEmail = bundleEmail?.getString("email")

        Toast.makeText(this,"PASO A SOLICITUD: $datoEmail", Toast.LENGTH_SHORT).show()
        //setup
            setup()
    }
    //Funcion para el boton Continuar(Solo pasa a la siguiente pantalla ListaProfesional)
    private fun setup(){
        val tituloTxt = findViewById<EditText>(R.id.tituloText)
        val descripcionTxt = findViewById<EditText>(R.id.descripcionText)
        val btn_continuar = findViewById<Button>(R.id.continuarButton)
        btn_continuar.setOnClickListener {
            if (tituloTxt.text.isNotEmpty() && descripcionTxt.text.isNotEmpty()){
                db.collection("solicitud").document().set(
                    hashMapOf(
                        "Titulo" to tituloTxt.text.toString(),
                        "Descripción" to descripcionTxt.text.toString()
                    )
                )
                val intent = Intent(this,ListaTecnicosActivity::class.java)
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
            Toast.makeText(this,"Complete los campos para continuar",Toast.LENGTH_SHORT).show()
        }
        Toast.makeText(this,"Exito",Toast.LENGTH_SHORT).show()
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
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Quieres dejar de solicitar?")
        builder.setMessage("")
        builder.setPositiveButton("Si") { dialogInterface: DialogInterface, i: Int ->
            //borrar datos de inicio de sesion
            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()
            FirebaseAuth.getInstance().signOut()
            val logIntent = Intent(this,MenuActivity::class.java)
            startActivity(logIntent)
            finish()
        }
        builder.setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->
        }
        builder.show()
    }
}