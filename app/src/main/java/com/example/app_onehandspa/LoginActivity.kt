package com.example.app_onehandspa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PatternMatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.util.PatternsCompat
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

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

            if (emailText.text.isNotEmpty() && passwordText.text.isNotEmpty()){
                // SE CREA LA INSTANCA DE FIREBASEAUTH PERMITIENDO CREAR UN USUSARIO POR EMAIL Y PASSWORD
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    emailText.text.toString(),
                    passwordText.text.toString()
                ).addOnCompleteListener{
                    if (it.isSuccessful){
                        showMain(it.result?.user?.email ?:"",ProvideType.BASIC)
                    }else{
                        //Toast.makeText(this,"Usuario o contraseña incorrectos.", Toast.LENGTH_SHORT).show()
                        validate()
                    }
                }
            }else{
                //MENSAJE EMERGENTE CON CLASE TOAST
                //Toast.makeText(this,"Rellene los campos", Toast.LENGTH_SHORT).show()
                validate()
            }

        }
        btn_registro.setOnClickListener {
            val intent = Intent(this,RegistroActivity::class.java).apply {  }
            startActivity(intent)
        }
    }
    private fun showMain(email:String,provider: ProvideType){
        val mainIntent = Intent(this,MainActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider.name)
        }
        startActivity(mainIntent)
    }
    private fun validate(){
        val result = arrayOf(validateEmail(),validatePassword())
        if (false in result){
            return
        }
        Toast.makeText(this,"Exito",Toast.LENGTH_SHORT).show()
    }

    private fun validateEmail():Boolean{
        val email = findViewById<EditText>(R.id.emailEditText)
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
    private fun validatePassword():Boolean{
        val password = findViewById<EditText>(R.id.passwordEditText)
        val passwordRegex = Pattern.compile(
            "^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}\$"
        )
        return when {
            password.text.isEmpty() ->{
                password.error = " los campos no pueden estar vacios"
                false
            }!passwordRegex.matcher(password.text.toString()).matches() -> {
                password.error = "Al menos 1 dígito, 1 minúscula, 1 mayúscula, 8-16 caracteres."
                false
            }
            else -> {
                password.error = null
                true
            }
        }
    }



}