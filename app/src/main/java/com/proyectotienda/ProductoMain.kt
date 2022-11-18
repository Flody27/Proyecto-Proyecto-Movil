package com.proyectotienda

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.proyectotienda.databinding.ActivityProductoBinding


class ProductoMain : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var binding: ActivityProductoBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductoBinding.inflate(layoutInflater)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

//        val relativeLayout: RelativeLayout = binding.ActivityProducto

//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_producto_host) as NavHostFragment
       findNavController(R.id.fragment_producto_host)


        auth = Firebase.auth

        bottomNavigationView = findViewById(R.id.bottom_nav)
        bottomNavigationView.selectedItemId = R.id.principal
        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.principal -> return@OnNavigationItemSelectedListener true
                R.id.carrito -> {
                    startActivity(Intent(applicationContext, CarritoMain::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.ajustes -> {
                    startActivity(Intent(applicationContext, ConfiguracionMain::class.java))
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