package com.ngg.cloudefirestoreexemplos.utils

import android.app.Activity
import android.content.Context
import android.widget.Toast

fun Activity.exibirMensagem(texto: String){
    Toast.makeText(this, texto, Toast.LENGTH_SHORT).show()
}

fun exibirMensagemContext(context: Context,texto: String){
    Toast.makeText(context, texto, Toast.LENGTH_SHORT).show()
}
