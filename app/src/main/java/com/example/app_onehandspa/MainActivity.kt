package com.example.app_onehandspa

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.log

enum class ProvideType{
    BASIC,
    GOOGLE
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")

        setup(email ?:"", provider ?:"")

        // Guardar datosvde inicio de sesion
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email",email)
        prefs.putString("provider",provider)
        prefs.apply()
    }
    private fun setup(email: String, provider: String){
        title = "Inicio"

        val emailText = findViewById<TextView>(R.id.emailTextView)
        val providerText = findViewById<TextView>(R.id.providerTextView)
        val btn_cerrarSesion = findViewById<Button>(R.id.cerrarSesionButton)
        emailText.text = email
        providerText.text = provider

        btn_cerrarSesion.setOnClickListener {
            //borrar datos de inicio de sesion
            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            FirebaseAuth.getInstance().signOut()
            val logIntent = Intent(this,LoginActivity::class.java)
            startActivity(logIntent)

        }

    }
}