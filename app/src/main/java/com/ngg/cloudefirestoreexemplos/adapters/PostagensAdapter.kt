package com.ngg.cloudefirestoreexemplos.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ngg.cloudefirestoreexemplos.databinding.PostagemItemBinding
import com.ngg.cloudefirestoreexemplos.model.Postagem

class PostagensAdapter : Adapter<PostagensAdapter.PostagensViewHolder>() {

    private var listaPostagens = emptyList<Postagem>()
    @SuppressLint("NotifyDataSetChanged")
    fun adicionarLista(lista: List<Postagem>) {
        listaPostagens = lista
        notifyDataSetChanged()
    }
    inner class PostagensViewHolder(private val binding: PostagemItemBinding) : ViewHolder(binding.root){

        fun bind(postagem: Postagem) {
            binding.textCategoria.text = postagem.categoria
            binding.textValor.text = postagem.valor
            binding.textData.text = postagem.data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostagensViewHolder {
        return PostagensViewHolder(PostagemItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
       return listaPostagens.size
    }

    override fun onBindViewHolder(holder: PostagensViewHolder, position: Int) {
        val postagens = listaPostagens[position]
        holder.bind( postagens)
    }
}