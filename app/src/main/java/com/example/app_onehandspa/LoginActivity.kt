package com.example.app_onehandspa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

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
                        showAlert()
                    }
                }
            }

        }
        btn_registro.setOnClickListener {
            val intent = Intent(this,RegistroActivity::class.java).apply {  }
            startActivity(intent)
        }
    }
    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error AUTENTICANDO al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }
    private fun showMain(email:String,provider: ProvideType){
        val mainIntent = Intent(this,MainActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider.name)
        }
        startActivity(mainIntent)
    }


}