package com.ngg.cloudefirestoreexemplos.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Postagem(
    val categoria: String = "",
    val valor: String = "",
    val data: String = ""
) : Parcelable
