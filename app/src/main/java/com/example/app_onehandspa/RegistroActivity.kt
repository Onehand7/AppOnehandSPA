package com.example.app_onehandspa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.util.PatternsCompat
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        setup()
    }
    // METODO DE AUTENTICACION MEDIANTE CORREO Y CONTRASEÑA
    private fun setup(){
        val nombreText = findViewById<EditText>(R.id.nombreEditText)
        val emailRegistroText = findViewById<EditText>(R.id.emailRegistroEditText)
        val passwordText = findViewById<EditText>(R.id.passwordRegistroEditText)
        val passRepText = findViewById<EditText>(R.id.passwordRepRegistroEditText)
        val btn_siguiente = findViewById<Button>(R.id.siguienteButton)

        btn_siguiente.setOnClickListener {
            if (nombreText.text.isNotEmpty() && emailRegistroText.text.isNotEmpty()&& passwordText.text.isNotEmpty() && passRepText.text.isNotEmpty()){
                // SE CREA LA INSTANCA DE FIREBASEAUTH PERMITIENDO CREAR UN USUSARIO POR EMAIL Y PASSWORD
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    emailRegistroText.text.toString(),
                    passRepText.text.toString()
                ).addOnCompleteListener{
                    if (it.isSuccessful && passRepText.text.toString() == passwordText.text.toString()){
                        showMain(it.result?.user?.email ?:"",ProvideType.BASIC)
                    }else{
                        Toast.makeText(this,"Correo o contraseña erronea", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                //Toast.makeText(this,"Rellene los campos", Toast.LENGTH_SHORT).show()
                validate()
            }
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

        val result = arrayOf(validateNombre(),validateEmail(),validatePassword(),validateRepPassword())
        if (false in result){
            return
        }

        Toast.makeText(this,"Exito",Toast.LENGTH_SHORT).show()
    }
    private fun validateNombre():Boolean{
        val nombre = findViewById<EditText>(R.id.nombreEditText)
        return when {
            nombre.text.isEmpty() -> {
                nombre.error = " los campos no pueden estar vacios"
                false
            }
            else -> {
                nombre.error = null
                true
            }
        }
    }

    private fun validateEmail():Boolean{
        val email = findViewById<EditText>(R.id.emailRegistroEditText)
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
        val password = findViewById<EditText>(R.id.passwordRegistroEditText)
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
    private fun validateRepPassword():Boolean{
        val passwordRep = findViewById<EditText>(R.id.passwordRepRegistroEditText)
        val passwordRegex = Pattern.compile(
            "^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}\$"
        )
        return when {
            passwordRep.text.isEmpty() ->{
                passwordRep.error = " los campos no pueden estar vacios"
                false
            }!passwordRegex.matcher(passwordRep.text.toString()).matches() -> {
                passwordRep.error = "Al menos 1 dígito, 1 minúscula, 1 mayúscula, 8-16 caracteres."
                false
            }
            else -> {
                passwordRep.error = null
                true
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this,LoginActivity::class.java).apply {  }
        startActivity(intent)
        finish()
    }
}