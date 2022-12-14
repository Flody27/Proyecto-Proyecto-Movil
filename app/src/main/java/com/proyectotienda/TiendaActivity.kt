package com.proyectotienda

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Window
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.proyectotienda.databinding.ActivityTiendaBinding

class TiendaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTiendaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTiendaBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)


        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_tienda)

        navView.setupWithNavController(navController)

        binding.btCerrarSesion.setOnClickListener {
            Firebase.auth.signOut()
            finish()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }
}