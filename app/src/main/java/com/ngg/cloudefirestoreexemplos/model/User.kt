package com.ngg.cloudefirestoreexemplos.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val photo: String = "",
    val name: String = "",
    val id: String = "",
    val email: String = ""
) : Parcelable