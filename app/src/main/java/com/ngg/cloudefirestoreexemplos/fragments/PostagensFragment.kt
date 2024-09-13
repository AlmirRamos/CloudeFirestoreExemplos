package com.ngg.cloudefirestoreexemplos.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore
import com.ngg.cloudefirestoreexemplos.R
import com.ngg.cloudefirestoreexemplos.adapters.PostagensAdapter
import com.ngg.cloudefirestoreexemplos.databinding.FragmentContatosBinding
import com.ngg.cloudefirestoreexemplos.databinding.FragmentPostagensBinding
import com.ngg.cloudefirestoreexemplos.model.Postagem
import com.ngg.cloudefirestoreexemplos.model.User

class PostagensFragment : Fragment() {
    private lateinit var binding: FragmentPostagensBinding
    private lateinit var postagensAdapter: PostagensAdapter
    private lateinit var eventoSnapshot: ListenerRegistration

    private val firestore = Firebase.firestore
    private val auth = Firebase.auth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostagensBinding.inflate(
            inflater, container, false
        )

        obterListaPostagens()
        configClicks()

        postagensAdapter = PostagensAdapter()
        binding.rvPostagens.adapter = postagensAdapter
        binding.rvPostagens.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    private fun configClicks() {
        binding.fabAddPostagem.setOnClickListener {

        }
    }

    private fun obterListaPostagens() {
        eventoSnapshot = firestore.collection("postagens")
            .addSnapshotListener { querySnapshot, erro ->

                val document = querySnapshot?.documents
                val listaPostagens = mutableListOf<Postagem>()

                document?.forEach { documentSnapshot ->
                    val postagem = documentSnapshot.toObject(Postagem::class.java)

                    if (postagem != null) {

                        val usuarioLogado = auth.currentUser?.uid

                        if (usuarioLogado != null) {
                            listaPostagens.add( postagem )
                        }
                    }
                }
                //lista para o recyclerView
                if (listaPostagens.isNotEmpty()){
                    postagensAdapter.adicionarLista( listaPostagens )
                }

            }
    }

    override fun onDestroy() {
        super.onDestroy()
        eventoSnapshot.remove()
    }
}