package com.example.app_onehandspa

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PatternMatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.util.PatternsCompat
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setup()
        session()
    }
    //Funcion que se invoca cada vez que se muestra la pantalla , permite hacer visible el login
    override fun onStart() {
        super.onStart()
        val auth_Layout = findViewById<LinearLayout>(R.id.authLayout)
        auth_Layout.visibility = View.VISIBLE
    }
    //Funcion que recupera si tenemos guardado un email y password
    private fun session(){
        val auth_Layout = findViewById<LinearLayout>(R.id.authLayout)
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email",null)

        if (email !=null){
            //permite hacer invisible a login si hay una cuenta iniciada
            auth_Layout.visibility = View.INVISIBLE
            showMain(email)
        }
    }
    //Funcion que contiene los eventos de botones en LoginActivity
    private fun setup(){
        val emailText = findViewById<EditText>(R.id.emailEditText)
        val passwordText = findViewById<EditText>(R.id.passwordEditText)
        val btn_Log = findViewById<Button>(R.id.loginButton)
        val btn_registro = findViewById<Button>(R.id.registroButton)
        val olvidasteContraseña = findViewById<TextView>(R.id.olvidoContraseñaText)

        btn_Log.setOnClickListener {

            if (emailText.text.isNotEmpty() && passwordText.text.isNotEmpty()){
                // SE CREA LA INSTANCA DE FIREBASEAUTH PERMITIENDO CREAR UN USUSARIO POR EMAIL Y PASSWORD
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    emailText.text.toString(),
                    passwordText.text.toString()
                ).addOnCompleteListener{
                    if (it.isSuccessful){
                        //val intent = Intent(this,MenuActivity::class.java)
                       // intent.putExtra("email",emailText.text.toString())
                       // startActivity(intent)
                        showMain(it.result?.user?.email?:"")

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
        olvidasteContraseña.setOnClickListener {
            val olvidoIntent = Intent(this, RecuperarActivity::class.java).apply {  }
            startActivity(olvidoIntent)
            finish()
        }
    }
    //Funcion que manda a llamar MainActivity
    private fun showMain(email:String){
        val mainIntent = Intent(this,MenuActivity::class.java).apply {
            putExtra("email",email)
        }
        startActivity(mainIntent)
    }
    //Funcion que valida los datos ingresados en email y contraseña
    private fun validate(){
        val result = arrayOf(validateEmail(),validatePassword())
        if (false in result){
            return
        }
        Toast.makeText(this,"Correo o contraseña no validos",Toast.LENGTH_SHORT).show()
    }
    //Funcion que valida el email
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
    //Funcion que valida la contraseña
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
    //Funcion hacia atras del boton de la interfaz del celular
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this,LoginActivity::class.java).apply {  }
        startActivity(intent)
        finish()
    }
}