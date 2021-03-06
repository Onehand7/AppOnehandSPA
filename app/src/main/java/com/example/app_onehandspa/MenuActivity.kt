package com.example.app_onehandspa

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.app_onehandspa.ui.home.HomeFragment
import com.google.firebase.auth.FirebaseAuth

class

MenuActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var homeFragment: HomeFragment
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            //email recibido desde LoginActivity
            val bundle = intent.extras
            val email = bundle?.getString("email")

            // Guardar datos de inicio de sesion
            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.putString("email",email)
            prefs.apply()

            //
            ///val emailSolicitud= SolicitudActivity()

            //val bundleEmail = Bundle()
            //emailSolicitud.toString()
            //bundleEmail.putString("emailSoli",emailSolicitud.toString() )


            //pasarEmail(email)


            Toast.makeText(this,"PASANDO EL EMAIL $email", Toast.LENGTH_SHORT).show()

            setContentView(R.layout.activity_menu)

            val toolbar: Toolbar = findViewById(R.id.toolbar)
            setSupportActionBar(toolbar)
            val fab: FloatingActionButton = findViewById(R.id.fab)
            fab.setOnClickListener { view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
            }
            val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
            val navView: NavigationView = findViewById(R.id.nav_view)
            val navController = findNavController(R.id.nav_host_fragment)
            //Pasar cada ID de men?? como un conjunto de identificadores porque cada
            //       men?? debe considerarse como destinos de nivel superior.
            appBarConfiguration = AppBarConfiguration(setOf(
                    R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)

            //navHeader()
            //setup()
    }
    private fun pasarEmail(email: String?){

        var datoEmail = email
        val bundle:Bundle= Bundle()
        bundle.putString("email",datoEmail)
        intent.putExtras(bundle)
        startActivity(intent)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflar el men??; esto agrega elementos a la barra de acciones si est?? presente.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("??Quieres cerrar sesi??n?")
        builder.setMessage("")
        builder.setPositiveButton("Si") { dialogInterface: DialogInterface, i: Int ->
            //borrar datos de inicio de sesion
            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()
            FirebaseAuth.getInstance().signOut()
            val logIntent = Intent(this,LoginActivity::class.java)
            startActivity(logIntent)
            finish()
        }
        builder.setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->
        }
        builder.show()
    }
}