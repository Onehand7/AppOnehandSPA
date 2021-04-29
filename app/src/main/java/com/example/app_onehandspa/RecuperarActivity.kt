package com.example.app_onehandspa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.util.PatternsCompat
import com.google.firebase.auth.FirebaseAuth

class RecuperarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar)

        setup()
    }
    private fun setup() {
        val emailRecuperar = findViewById<EditText>(R.id.emailRecuperarText)
        val btn_recuperar = findViewById<Button>(R.id.recuperarButton)
        val iniciosesionText = findViewById<TextView>(R.id.inicioSesionText)
        val registroText = findViewById<TextView>(R.id.registroText)

        btn_recuperar.setOnClickListener {
            if (emailRecuperar.text.isNotEmpty()){
                FirebaseAuth.getInstance().sendPasswordResetEmail(emailRecuperar.text.toString())
                        .addOnCompleteListener {
                            if (it.isSuccessful){
                                Toast.makeText(this,"Correo enviado", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this,LoginActivity::class.java).apply {  }
                                startActivity(intent)
                                finish()
                            }else{
                                validate()
                            }
                        }
            }else
            {
                validate()

            }
        }
        iniciosesionText.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java).apply {  }
            startActivity(intent)
            finish()
        }
        registroText.setOnClickListener {
            val intent = Intent(this,RegistroActivity::class.java).apply {  }
            startActivity(intent)
            finish()
        }
    }
    private fun validate(){
        val result = arrayOf(validateEmail())
        if (false in result){
            return
        }
        Toast.makeText(this,"Correo invalido",Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this,LoginActivity::class.java).apply {  }
        startActivity(intent)
        finish()
    }

    private fun validateEmail():Boolean{
        val email = findViewById<EditText>(R.id.emailRecuperarText)
        return when {
            email.text.isEmpty() -> {
                email.error = " los campos no pueden estar vacios"
                false
            }
            !PatternsCompat.EMAIL_ADDRESS.matcher(email.text.toString()).matches() -> {
                email.error = "escriba un correo electronico valido"
                false
            }
            else -> {
                email.error = null
                true
            }
        }
    }

}