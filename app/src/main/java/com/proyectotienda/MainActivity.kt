package com.proyectotienda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.proyectotienda.databinding.ActivityMainBinding
import com.proyectotienda.model.Usuario
import com.proyectotienda.viewModel.UsuarioViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    companion object{
        private const val RC_SIGN_IN = 5000
    }

    private lateinit var googleSignInClient : GoogleSignInClient

    private lateinit var usuarioViewModel : UsuarioViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        usuarioViewModel =
            ViewModelProvider(this)[UsuarioViewModel::class.java]

        binding.btIniciarSesion.setOnClickListener {
            iniciarSesion();
        }

        binding.btRegistrarse.setOnClickListener {
            registrarse();
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.token_google))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)
        binding.btGoogle.setOnClickListener {
            googleSignIn()
        }

    }

    private fun googleSignIn() {
        val sigInItent = googleSignInClient.signInIntent
        startActivityForResult(sigInItent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            }catch (e: ApiException){

            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken,null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener (this){ task ->
                if(task.isSuccessful){
                    val user = auth.currentUser
                    actuliza(user)
                }else{
                    actuliza(null)
                }
            }
    }

    private fun registrarse() {
        val email = binding.etCorreo.text.trim().toString()
        val clave = binding.etClave.text.trim().toString()

        auth.createUserWithEmailAndPassword(email, clave)
            .addOnCompleteListener(this) { task ->
                var user: FirebaseUser? = null
                if (task.isSuccessful) {
                    Log.d("Creando usuario", "Registrado")
                    user = auth.currentUser
                    val usuario = Usuario("",email,"cliente")
                    usuarioViewModel.addUsuario(usuario)
                    actuliza(user)
                } else {
                    Log.d("Creando usuario", "Fallo")
                    Toast.makeText(baseContext, "Fallo", Toast.LENGTH_SHORT).show()
                    actuliza(null)
                }
            }
    }

    private fun actuliza(user: FirebaseUser?) {
        if (user != null) {

            val intent = Intent(this, TiendaActivity::class.java)
            startActivity(intent)
        }
    }

    public override fun onStart() {
        super.onStart()
        val usuario = auth.currentUser
        actuliza(usuario)
    }

//    fun isAdmin(){
//        val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
//        val coleccion1 = "Usuario"
//        val email = binding.etCorreo.text.trim().toString()
//        val db : DocumentReference = firestore.collection(coleccion1).document(email)
//        db.get().addOnSuccessListener{
//            if(it.getString("admin") != null){
//                Log.e("Admin","Es admin")
//            }else{
//                Log.e("Error","Que a pasao")
//            }
//        }
//    }




    private fun iniciarSesion() {
        val email = binding.etCorreo.text.toString()
        val clave = binding.etClave.text.toString()

        auth.signInWithEmailAndPassword(email, clave)
            .addOnCompleteListener(this) { task ->
                var user: FirebaseUser? = null
                if (task.isSuccessful) {
                    Log.d("Autenticando", "Auntenticado")
                    user = auth.currentUser
                    actuliza(user)
                } else {
                    Log.d("Autenticando", "Fallo")
                    Toast.makeText(baseContext, "Fallo", Toast.LENGTH_SHORT).show()
                    actuliza(null)
                }
            }
    }
}