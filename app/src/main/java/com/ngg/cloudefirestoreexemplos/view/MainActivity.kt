package com.ngg.cloudefirestoreexemplos.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColor
import androidx.core.view.MenuProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore
import com.google.type.Color
import com.ngg.cloudefirestoreexemplos.R
import com.ngg.cloudefirestoreexemplos.adapters.ContatosAdapter
import com.ngg.cloudefirestoreexemplos.adapters.ViewPagerAdapter
import com.ngg.cloudefirestoreexemplos.databinding.ActivityMainBinding
import com.ngg.cloudefirestoreexemplos.model.User

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        iniciarToolbar()
        iniciarNavegacaoAbas()

    }

    private fun iniciarNavegacaoAbas() {

        val tabLayout = binding.tabLayoutPrincipal
        val viewPager = binding.viewPagerPrincipal

        //Adapter
        val abas = listOf("CONVERSAS", "CONTATOS", "POSTAGENS")
        viewPager.adapter = ViewPagerAdapter(abas, supportFragmentManager, lifecycle)

        tabLayout.isTabIndicatorFullWidth = true
        TabLayoutMediator(tabLayout, viewPager){ aba, posicao->
            aba.text = abas[ posicao ]
        }.attach()
    }

    private fun iniciarToolbar() {
        val toolbar = binding.includeToolbarMain.tbPrincipal
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Exemplos Firebase"
        }

        addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_main, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    when (menuItem.itemId) {
                        R.id.perfil -> {
                            screenPerfil()
                        }

                        R.id.deslogar -> {
                            deslogarUsuario()
                        }
                    }
                    return true
                }
            }
        )
    }

    private fun deslogarUsuario() {

        AlertDialog.Builder(this)
            .setTitle("Deslogar")
            .setMessage("Deseja realmente sair do App?")
            .setNegativeButton("Não") { dialog, posicao -> }
            .setPositiveButton("Sim") { dialog, posicao ->
                FirebaseAuth.getInstance().signOut()
                screenLogin()
            }
            .create()
            .show()
    }

    private fun screenPerfil() {
        startActivity(Intent(this, PerfilActivity::class.java))
    }

    private fun screenLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

}