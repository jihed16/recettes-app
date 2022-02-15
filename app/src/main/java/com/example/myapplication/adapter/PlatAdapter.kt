package com.example.myapplication.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.MainActivity
import com.example.myapplication.PlatPopup
import com.example.myapplication.PlatRepository
import com.example.myapplication.R
import com.example.myapplication.fragments.PlatModel

class PlatAdapter(
    val context: MainActivity,
    private val platList: List<PlatModel>,
    private val layoutId: Int
    ) :RecyclerView.Adapter<PlatAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val platImage = view.findViewById<ImageView>(R.id.image_item)
        val platName:TextView? = view.findViewById<TextView>(R.id.name_item)
        val platDescription:TextView? = view.findViewById<TextView>(R.id.description_item)
        val starIcon = view.findViewById<ImageView>(R.id.star_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view= LayoutInflater.from(parent.context)
           .inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val currentPlat = platList[position]

        // recuperer le repository
        val repo = PlatRepository()

       Glide.with(context).load(Uri.parse(currentPlat.imageUrl)).into(holder.platImage)
        holder.platName?.text = currentPlat.name
        holder.platDescription?.text = currentPlat.description
        if(currentPlat.liked){
            holder.starIcon.setImageResource(R.drawable.ic_star)
        }
        else{
            holder.starIcon.setImageResource(R.drawable.ic_unstar)
        }
        // rajouter une interaction sur l'étoile
        holder.starIcon.setOnClickListener{
            // inverse si le bouton est clicked ou non
            currentPlat.liked = !currentPlat.liked
            //mettre à jour l'objet plat
            repo.updatePlat(currentPlat)
        }
        // interaction lors du click sur un plat
        holder.itemView.setOnClickListener{
            // afficher la popup
            PlatPopup(this,currentPlat).show()
        }
    }

    override fun getItemCount(): Int = platList.size


}