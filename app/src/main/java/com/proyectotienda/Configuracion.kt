package com.proyectotienda

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.proyectotienda.databinding.ActivityConfiguracionBinding


class Configuracion : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var binding: ActivityConfiguracionBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfiguracionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        // Metodo para la navegacion entre activities
        bottomNavigationView = findViewById(R.id.bottom_nav)
        bottomNavigationView.selectedItemId = R.id.ajustes
        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ajustes -> return@OnNavigationItemSelectedListener true
                R.id.carrito -> {
                    startActivity(Intent(applicationContext, Carrito::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.principal -> {
                    startActivity(Intent(applicationContext, Producto::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })

        binding.btCerrarSesion.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
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