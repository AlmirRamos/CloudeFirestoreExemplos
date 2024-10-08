package com.ngg.cloudefirestoreexemplos.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore
import com.ngg.cloudefirestoreexemplos.adapters.ContatosAdapter
import com.ngg.cloudefirestoreexemplos.databinding.FragmentContatosBinding
import com.ngg.cloudefirestoreexemplos.model.User

class ContatosFragment : Fragment() {
    private lateinit var binding: FragmentContatosBinding
    private lateinit var contatosAdapter: ContatosAdapter
    private lateinit var eventoSnapshot: ListenerRegistration
    private val firestore = Firebase.firestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentContatosBinding.inflate(
            inflater, container, false
        )
        contatosAdapter = ContatosAdapter{

        }
        binding.rvContatos.adapter = contatosAdapter
        binding.rvContatos.layoutManager = LinearLayoutManager(context)
        binding.rvContatos.addItemDecoration( DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        adicionarListenerUsuarios()
    }

    private fun adicionarListenerUsuarios() {
        eventoSnapshot = firestore.collection("user")
            .addSnapshotListener { querySnapshot, erro ->

                val document = querySnapshot?.documents
                val listaContatos = mutableListOf<User>()

                document?.forEach { documentSnapshot ->
                    val user = documentSnapshot.toObject(User::class.java)
                    if (user != null) {
                        listaContatos.add( user )
                    }
                }
                //lista para o recyclerView
                if (listaContatos.isNotEmpty()){
                    contatosAdapter.adicionarLista( listaContatos )
                }

            }
    }

    override fun onDestroy() {
        super.onDestroy()
        eventoSnapshot.remove()
    }

}