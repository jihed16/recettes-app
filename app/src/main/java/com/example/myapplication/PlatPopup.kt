package com.example.myapplication

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.myapplication.adapter.PlatAdapter
import com.example.myapplication.fragments.PlatModel

class PlatPopup(private val adapter : PlatAdapter,
   private val currentPlat: PlatModel
) : Dialog(adapter.context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_plats_details)
        setupComponent()
        setupCloseButton()
        setupDeleteButton()
        setupStarButton()
    }
private fun updateStar(button : ImageView){
    if (currentPlat.liked) {
        button.setImageResource(R.drawable.ic_star)
    } else {
        button.setImageResource(R.drawable.ic_unstar)
    }

}
    private fun setupStarButton() {
        val starButton = findViewById<ImageView>(R.id.star_button)
      updateStar(starButton)
        if (currentPlat.liked) {
            starButton.setImageResource(R.drawable.ic_star)
        } else {
            starButton.setImageResource(R.drawable.ic_unstar)
        }
      starButton.setOnClickListener{
          currentPlat.liked = !currentPlat.liked
          val repo = PlatRepository()
          repo.updatePlat(currentPlat)
          updateStar(starButton)
      }
    }
    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.close_button).setOnClickListener{
            // fermer la fenetre
            dismiss()
        }
    }
    private fun setupDeleteButton() {
        findViewById<ImageView>(R.id.delete_button).setOnClickListener {
     // supprime le plat de la base
            val repo = PlatRepository()
            repo.deletePlat(currentPlat)
            dismiss()
        }
    }
    private fun setupComponent() {
        // actualiser l'image du plat
     val platImage = findViewById<ImageView>(R.id.image_item)
   Glide.with(adapter.context).load(Uri.parse(currentPlat.imageUrl)).into(platImage)
        // actualiser le nom du plat
        findViewById<TextView>(R.id.popup_plat_name).text = currentPlat.name
        // actualiser la description du plat
        findViewById<TextView>(R.id.popup_plat_description_subtitle).text = currentPlat.description
        // actualiser le temps de cuisson
        findViewById<TextView>(R.id.popup_description_temps_cuisson).text = currentPlat.temps_cuisson
       // actualiser la temperature
        findViewById<TextView>(R.id.popup_description_temperature).text = currentPlat.temperature
    }
}