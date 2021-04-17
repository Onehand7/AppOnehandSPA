package com.example.app_onehandspa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

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
            if (emailRegistroText.text.isNotEmpty() && passRepText.text.isNotEmpty()){
                // SE CREA LA INSTANCA DE FIREBASEAUTH PERMITIENDO CREAR UN USUSARIO POR EMAIL Y PASSWORD
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    emailRegistroText.text.toString(),
                    passRepText.text.toString()
                ).addOnCompleteListener{
                    if (it.isSuccessful){
                        showMain(it.result?.user?.email ?:"",ProvideType.BASIC)
                    }else{
                        showAlert()
                    }
                }
            }
        }

    }
    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error AUTENTICANDO al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog:AlertDialog=builder.create()
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