package com.proyectotienda

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.proyectotienda.databinding.ActivityProductoBinding


class Producto : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var binding: ActivityProductoBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        bottomNavigationView = findViewById(R.id.bottom_nav)
        bottomNavigationView.selectedItemId = R.id.principal
        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.principal -> return@OnNavigationItemSelectedListener true
                R.id.carrito -> {
                    startActivity(Intent(applicationContext, Carrito::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.ajustes -> {
                    startActivity(Intent(applicationContext, Configuracion::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
    }

    override fun onStart() {
        super.onStart()
        val usuario = auth.currentUser
        if(usuario == null){
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
    }

}