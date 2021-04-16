package com.example.app_onehandspa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setup()
    }
    private fun setup(){
        val emailText = findViewById<EditText>(R.id.emailEditText)
        val passwordText = findViewById<EditText>(R.id.passwordEditText)
        val btn_Log = findViewById<Button>(R.id.loginButton)
        val btn_registro = findViewById<Button>(R.id.registroButton)

        btn_Log.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java).apply {  }
            startActivity(intent)
        }
        btn_registro.setOnClickListener {
            val intent = Intent(this,RegistroActivity::class.java).apply {  }
            startActivity(intent)
        }


    }
}