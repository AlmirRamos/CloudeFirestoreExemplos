package com.ngg.cloudefirestoreexemplos.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.ngg.cloudefirestoreexemplos.R
import com.ngg.cloudefirestoreexemplos.database.DataBase
import com.ngg.cloudefirestoreexemplos.databinding.ActivityCadastroBinding
import com.ngg.cloudefirestoreexemplos.model.User

class CadastroActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityCadastroBinding.inflate(layoutInflater)
    }

    private val auth = Firebase.auth
    private val database = DataBase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        iniciarToolbar()
        confgClicks()
    }

    private fun iniciarToolbar() {
        val toolbar = binding.includeToolbarCadastro.tbPrincipal
        setSupportActionBar( toolbar )
        supportActionBar?. apply {
            title = "Crie sua conta"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun confgClicks() {
        binding.btnCadastrar.setOnClickListener {
            validarCampos()
        }
    }

    private fun validarCampos() {
        val name = binding.editName.text.toString()
        val email = binding.editEmail.text.toString()
        val password = binding.editPassword.text.toString()

        if (name.isNotEmpty()){
            if (email.isNotEmpty()){
                if (password.isNotEmpty()){
                    createUser(name, email, password)
                }else{
                    binding.editPassword.requestFocus()
                    binding.editPassword.error = "Preencha a senha"
                }
            }else{
                binding.editEmail.requestFocus()
                binding.editEmail.error = "Preencha o email"
            }
        }else{
            binding.editName.requestFocus()
            binding.editName.error = "Preenca o nome"
        }

    }

    private fun createUser(name: String, email: String, password: String) {
        auth
            .createUserWithEmailAndPassword( email, password)
            .addOnCompleteListener { task->
                if ( task.isSuccessful ) {
                    val userID = task.result.user?.uid
                    if (userID != null) {
                        val user = User(
                           "", name, userID, email
                        )
                        database.saveUserDataInFirestore(this, user )
                    }
                    Toast.makeText(this, "User created with success", Toast.LENGTH_SHORT).show()
                    screenLogin(email)


                }
        }.addOnFailureListener {
                Toast.makeText(this, "Error creating user", Toast.LENGTH_SHORT).show()
            }
    }

    private fun screenLogin(email: String) {
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("email", email)
        startActivity(intent)
        finish()
    }

}