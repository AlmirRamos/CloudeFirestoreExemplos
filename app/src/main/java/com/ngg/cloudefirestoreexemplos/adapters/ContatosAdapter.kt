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
    private val onClick: (User)-> Unit
) : Adapter<ContatosAdapter.ContatosViewHolder>() {

    private var listacontatos = emptyList<User>()
    @SuppressLint("NotifyDataSetChanged")
    fun adicionarLista(lista: List<User>) {
        listacontatos = lista
        notifyDataSetChanged()
    }

    inner class ContatosViewHolder(private val binding: RecyclerViewItemBinding) : ViewHolder(binding.root){

        fun bind(usuario: User) {
            binding.textNameItem.text = usuario.name
            binding.textEmailItem.text = usuario.email
            Picasso.get().load(usuario.photo).into(binding.imgPerfilItem)

            binding.layoutItem.setOnClickListener {
                onClick( usuario )
            }
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