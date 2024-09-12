package com.ngg.cloudefirestoreexemplos.database

import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import com.ngg.cloudefirestoreexemplos.model.User
import com.ngg.cloudefirestoreexemplos.utils.exibirMensagemContext

class DataBase : AppCompatActivity() {

    private val auth = Firebase.auth
    private val storage = Firebase.storage
    private val firestore = Firebase.firestore

    //private val userID = auth.currentUser?.uid

    fun saveUserDataInFirestore(context: Context, user: User) {

        firestore
            .collection("user")
            .document(user.id)
            .set(user)
            .addOnSuccessListener {
                exibirMensagemContext(context, "Successful saving of user data")
            }
            .addOnFailureListener { e ->
                exibirMensagemContext(context, "Error saving user data: ${e.message}")
            }
    }

    fun uploadImagemStorage(context: Context, uri: Uri) {
        val userID = auth.currentUser?.uid
        if (userID != null) {
            // photos -> user -> userID -> profile.jpg
            storage.getReference("photo")
                .child("user")
                .child(userID)
                .child("profile.jpg")
                .putFile(uri)
                .addOnSuccessListener { task ->
                    exibirMensagemContext(context, "Successful when uploading the image")
                    task.metadata
                        ?.reference
                        ?.downloadUrl
                        ?.addOnSuccessListener { url ->
                            val data = mapOf(
                                "photo" to url.toString()
                            )

                            updateUserProfileData(context, userID, data)

                        }?.addOnFailureListener {

                        }
                }.addOnFailureListener {
                    exibirMensagemContext(context, "Error uploading the image")
                }
        }

    }

    fun updateUserProfileData(context: Context, userID: String, data: Map<String, String>) {
        firestore
            .collection("user")
            .document(userID)
            .update(data)
            .addOnSuccessListener {
                exibirMensagemContext(context, "Success when updating user profile")
            }.addOnFailureListener {
                exibirMensagemContext(context, "Error updating user profile")
            }
    }


}