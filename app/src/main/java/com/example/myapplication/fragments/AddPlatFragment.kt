package com.example.myapplication.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.myapplication.MainActivity
import com.example.myapplication.R

class AddPlatFragment (
    private val context:MainActivity
        ): Fragment() {


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
        // en cliquant sur le bouton on parcours l'image
        pickupImageButton.setOnClickListener { pickupImage() }
        return view
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
            val selectedImage = data.data
         // mettre à jour l'aperçu de l'img
            uploadedImage?.setImageURI(selectedImage)
        }

    }
}