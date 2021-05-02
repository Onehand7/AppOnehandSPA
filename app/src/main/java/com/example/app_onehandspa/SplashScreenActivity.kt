package com.example.app_onehandspa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer



//Clase que se inicia cuando la aplicacion es ejecutada
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        startTime()
    }
    //Funcion que determina cuando dura nuestro splashScreen
    private fun startTime(){
        object: CountDownTimer(3000,1000){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {

               val intent = Intent(applicationContext, LoginActivity ::class.java).apply{}

                startActivity(intent)
            }

        }.start()
    }
}