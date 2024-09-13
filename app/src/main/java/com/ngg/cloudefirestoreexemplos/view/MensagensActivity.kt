package com.ngg.cloudefirestoreexemplos.view

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ngg.cloudefirestoreexemplos.databinding.ActivityMensagensBinding
import com.ngg.cloudefirestoreexemplos.model.User
import com.ngg.cloudefirestoreexemplos.utils.Constantes
import com.squareup.picasso.Picasso

class MensagensActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMensagensBinding.inflate(layoutInflater)
    }
    private var dadosDestinatario: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        recuperarDadosUsuarioDestinatario()
        iniciarToolbar()
        iniciarClicks()
    }

    private fun iniciarClicks() {
        binding.fabEnviar.setOnClickListener {
            val mensagem  = binding.editMensagem.text.toString()
            salvarMensagem( mensagem )
        }
    }

    private fun salvarMensagem(mensagem: String) {

    }

    private fun iniciarToolbar() {
        val toolbar = binding.tbMensagens
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title =""
            if (dadosDestinatario != null) {
                binding.textNomeTbMensgens.text = dadosDestinatario!!.name
                Picasso.get().load(dadosDestinatario!!.photo).into(binding.imgPerfilTbMensagens)
            }
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun recuperarDadosUsuarioDestinatario() {
        val extras = intent.extras
        if (extras != null) {

            val origem = extras.getString("origem")
            if (origem == Constantes.ORIGEM_CONTATO) {

                dadosDestinatario = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    extras.getParcelable("dadosDestinatario", User::class.java)
                } else {
                    extras.getParcelable("dadosDestinatario")
                }

            } else if (origem == Constantes.ORIGEM_CONVERSA) {
                //Recuperar os dados da conversa
            }

        }
    }
}