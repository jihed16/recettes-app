package com.example.myapplication

import com.example.myapplication.PlatRepository.Singleton.databaseRef
import com.example.myapplication.PlatRepository.Singleton.platList
import com.example.myapplication.fragments.PlatModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.security.auth.callback.Callback

class PlatRepository {
    object Singleton {

        // se connecter à la référence plat
    val databaseRef = FirebaseDatabase.getInstance().getReference("plat")

    // créer une liste qui va contenir les plats
    val platList = arrayListOf<PlatModel>()
}
    fun updateData(callback:  () -> Unit) {
        //absorber les données depuis la datrabaseref
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // retirer les anciennes
                platList.clear()

                for (ds in snapshot.children) {
                    // construire un objet plat
                    val plat = ds.getValue(PlatModel::class.java)
                    // vérifier que le plat n'est pas null
                    if (plat != null) {
                        //ajouter le plat à la liste
                        platList.add(plat)
                    }
                }
                // actionner le callback
                callback()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
        // mettre à jour l'objet plat dans la BDD

        fun updatePlat (plat: PlatModel) = databaseRef.child(plat.id).setValue(plat)



}