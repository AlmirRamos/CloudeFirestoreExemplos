package com.ngg.cloudefirestoreexemplos.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.ngg.cloudefirestoreexemplos.R
import com.ngg.cloudefirestoreexemplos.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val usuarioLogado = FirebaseAuth.getInstance().currentUser
            if (usuarioLogado != null) {
                screenHome()
            }else{
                screenLogin()
            }
        }, 3000)

    }

    private fun screenHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun screenLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}