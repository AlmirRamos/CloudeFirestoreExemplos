package com.ngg.cloudefirestoreexemplos.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ngg.cloudefirestoreexemplos.databinding.RecyclerViewItemBinding
import com.ngg.cloudefirestoreexemplos.model.User
import com.squareup.picasso.Picasso

class ContatosAdapter(
    val onClick: (User)-> Unit
) : Adapter<ContatosAdapter.ContatosViewHolder>() {

    private var listacontatos = emptyList<User>()
    @SuppressLint("NotifyDataSetChanged")
    fun adicionarLista(lista: List<User>) {
        listacontatos = lista
        notifyDataSetChanged()
    }

    inner class ContatosViewHolder(private val binding: RecyclerViewItemBinding) : ViewHolder(binding.root){

        fun bind( lista: User) {
            binding.textNameItem.text = lista.name
            binding.textEmailItem.text = lista.email
            Picasso.get().load(lista.photo).into(binding.imgPerfilItem)
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatosViewHolder {
        return ContatosViewHolder(RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return listacontatos.size
    }

    override fun onBindViewHolder(holder: ContatosViewHolder, position: Int) {
        val usuario = listacontatos[position]
        holder.bind(usuario)
    }
}