package com.example.myapplication

import android.net.Uri
import com.example.myapplication.PlatRepository.Singleton.databaseRef

import com.example.myapplication.PlatRepository.Singleton.downloadUri
import com.example.myapplication.PlatRepository.Singleton.platList
import com.example.myapplication.PlatRepository.Singleton.storageReference
import com.example.myapplication.fragments.PlatModel
import com.google.android.gms.tasks.Task

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
//import com.google.firebase.storage.FirebaseStorage
//import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.net.URI

import java.util.*
import javax.security.auth.callback.Callback
import kotlin.coroutines.Continuation

class PlatRepository {
    object Singleton {
        // donner lien du bucket
        private val BUCKET_URL : String ="gs://recettescuisine-f9e71.appspot.com"
  // se connecter à notre espace de stockage
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)
        // se connecter à la référence plat
    val databaseRef = FirebaseDatabase.getInstance().getReference("plat")

    // créer une liste qui va contenir les plats
    val platList = arrayListOf<PlatModel>()
        // contient le lien de l'umage courante
        var downloadUri: Uri? = null
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
    // créer une fonction pour envoyer des fichiers sur le storage

   fun  uploadImage(file: Uri, callback:() -> Unit){
       // vérifier si ce fichier est null
       if(file!=null){
           val fileName = UUID.randomUUID().toString() + ".jpg"
           val ref = storageReference.child(fileName)
           val uploadTask = ref.putFile(file)

           // démarrer la tâche d'envoi


uploadTask.continueWithTask(com.google.android.gms.tasks.Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
       if(!task.isSuccessful){
           task.exception?.let { throw it }
       }
    return@Continuation ref.downloadUrl
}).addOnCompleteListener { task ->

    if(task.isSuccessful){
       // récupérer l'image
        downloadUri = task.result
        callback()
    }
}



       }
   }
        // mettre à jour l'objet plat dans la BDD
        fun updatePlat (plat: PlatModel) = databaseRef.child(plat.id).setValue(plat)

        // Inserer un nouveau plat à la bdd
        fun insertPlat (plat: PlatModel) = databaseRef.child(plat.id).setValue(plat)

        // supprimer le plat de la base
        fun deletePlat (plat: PlatModel) = databaseRef.child(plat.id).removeValue()


}


