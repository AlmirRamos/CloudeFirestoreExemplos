package com.ngg.cloudefirestoreexemplos.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.ngg.cloudefirestoreexemplos.R
import com.ngg.cloudefirestoreexemplos.database.DataBase
import com.ngg.cloudefirestoreexemplos.databinding.ActivityPerfilBinding
import com.ngg.cloudefirestoreexemplos.utils.exibirMensagem
import com.squareup.picasso.Picasso

class PerfilActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityPerfilBinding.inflate(layoutInflater)
    }

    private val auth = Firebase.auth
    private val firestore = Firebase.firestore
    private var tempermissaoCamera = false
    private var tempermissaoGaleria = false
    private val database = DataBase()

    private val gerenciadorGaleria = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ){ uri->
        if ( uri != null){
            binding.imgPerfil.setImageURI( uri )
            database.uploadImagemStorage( this, uri )
        }else{
            exibirMensagem("not a selected image")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        iniciarToolbar()
        cnfigClicks()
    }

    private fun cnfigClicks() {
        binding.fabAtualizarImgPerfil.setOnClickListener {
            gerenciadorGaleria.launch("image/*")
        }

        binding.btnAtualizarPerfil.setOnClickListener {

            val userName = binding.editTextNomePerfil.text.toString()
            val userID = auth.currentUser?.uid
            if (userName.isNotEmpty()){
                if (userID != null ){
                    val data = hashMapOf(
                        "name" to userName
                    )
                    database.updateUserProfileData(this, userID, data )
                }
            }else{
                exibirMensagem("fill in the name to update")
            }
        }
    }


    private fun iniciarToolbar() {
        val toolbar = binding.includeToolbarPerfil.tbPrincipal
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Perfil"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun solicitarPermissoes() {

        tempermissaoCamera = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        tempermissaoGaleria = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_MEDIA_IMAGES
        ) == PackageManager.PERMISSION_GRANTED

        //LISTA DE PERMISSOES NEGADAS
        val listaPermissoesNegadas = mutableListOf<String>()
        if (!tempermissaoCamera)
            listaPermissoesNegadas.add(Manifest.permission.CAMERA)
        if (!tempermissaoGaleria)
            listaPermissoesNegadas.add(Manifest.permission.READ_MEDIA_IMAGES)


        //SOLICITAR MULTIPLAS PERMISSOES
        if (listaPermissoesNegadas.isNotEmpty()) {

            val gerenciadorPermissoes = registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissoes ->

                tempermissaoCamera = permissoes[Manifest.permission.CAMERA] ?: tempermissaoCamera
                tempermissaoGaleria = permissoes[Manifest.permission.READ_MEDIA_IMAGES] ?: tempermissaoGaleria
            }
        }

    }

    override fun onStart() {
        super.onStart()
        getUserProfileList()
    }

    fun getUserProfileList(){
        val userID = auth.currentUser?.uid
        if (userID != null) {
            val docRef = firestore.collection("user").document(userID)
            docRef.get()
                .addOnSuccessListener { documentSnapshot->
                    val userData = documentSnapshot.data
                    if (userData != null){
                        val name = userData["name"] as String
                        val email = userData["email"] as String
                        val photo = userData["photo"] as String

                        binding.editTextNomePerfil.setText( name )
                        binding.textEmailPerfil.setText( email )

                        if (photo.isNotEmpty()){
                            Picasso.get().load(photo).into(binding.imgPerfil)
                        }
                    }
                }
        }

    }
}