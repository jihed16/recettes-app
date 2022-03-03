package com.example.myapplication.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.myapplication.MainActivity
import com.example.myapplication.PlatRepository
import com.example.myapplication.PlatRepository.Singleton.downloadUri
import com.example.myapplication.R
import java.util.*

class AddPlatFragment (
    private val context:MainActivity
        ): Fragment() {
private var file:Uri?=  null

    private var uploadedImage:ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_add_plat, container, false)

        // récupérer uploadedimage
        uploadedImage = view.findViewById(R.id.preview_image)
        //recuperer le bouton pour charger l'image
        val pickupImageButton = view.findViewById<Button>(R.id.upload_button)
        // en cliquant sur le bouton on parcours l'image au tel
        pickupImageButton.setOnClickListener { pickupImage() }
         // recuperer le bouton confirmer
        val confirmButton = view.findViewById<Button>(R.id.confirm_button)
        confirmButton.setOnClickListener {sendForm(view)}



        return view
    }

    private fun sendForm(view: View) {
        val repo =  PlatRepository()
        repo.uploadImage(file!!){
            val platName = view.findViewById<EditText>(R.id.name_input).text.toString()
            val platDescription = view.findViewById<EditText>(R.id.description_input).text.toString()
            val cuisson = view.findViewById<Spinner>(R.id.temps_cuisson_spinner).selectedItem.toString()
            val temp = view.findViewById<Spinner>(R.id.temperature_spinner).selectedItem.toString()
            val downloadImageUrl = downloadUri

            // créer un nouvel objet
            val plat = PlatModel(
            UUID.randomUUID().toString(),
            platName,
            platDescription,
            downloadImageUrl.toString(),
             cuisson,
             temp

            )
         // envoyer en bdd
          repo.insertPlat(plat)

        }



    }

    private fun pickupImage() {

        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture "), 47)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 47 && resultCode == Activity.RESULT_OK)
        {
            // vérifier si les données sont nulles
            if(data == null || data.data == null) return
            //recupérer l'image sélectionnée
            file = data.data
         // mettre à jour l'aperçu de l'img
           uploadedImage?.setImageURI(file)



        }

    }
}