package com.ngg.cloudefirestoreexemplos.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.ngg.cloudefirestoreexemplos.R
import com.ngg.cloudefirestoreexemplos.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
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

        val bundle = intent.extras
        if (bundle != null){
            binding.editEmail.setText( bundle.getString("email"))
        }

        configClicks()
    }

    private fun configClicks() {
        binding.btnEntrar.setOnClickListener {
            validarCampos()
        }
        binding.textCriarConta.setOnClickListener {
            screenCadatro()
        }
    }

    private fun validarCampos() {

        val email = binding.editEmail.text.toString()
        val password = binding.editPassword.text.toString()

            if (email.isNotEmpty()){
                if (password.isNotEmpty()){
                    logarUser( email, password)
                }else{
                    binding.editPassword.requestFocus()
                    binding.editPassword.error = "Preencha a senha"
                }
            }else{
                binding.editEmail.requestFocus()
                binding.editEmail.error = "Preencha o email"
            }
    }

    private fun logarUser(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword( email, password)
            .addOnCompleteListener { task->
                if (task.isSuccessful){
                    screenHome()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Erro logging in", Toast.LENGTH_SHORT).show()
            }
    }

    private fun screenHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun screenCadatro() {
        startActivity(Intent(this, CadastroActivity::class.java))
    }


}