package com.example.mountain.repository

import android.net.Uri
import com.example.mountain.DummyMountain
import com.example.mountain.model.Mountain
import com.example.mountain.utils.FirebaseTables
import com.example.mountain.utils.await
import com.example.mountain.viewmodel.UiState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MountainRepositoryImpl(
    val database : FirebaseFirestore,
    val storageReference: StorageReference
): MountainRepository {


    override fun getMountains(result: (UiState<List<Mountain>>) ->  Unit) {
        database.collection(FirebaseTables.mountain).get()
            .addOnSuccessListener {
                val mountains = arrayListOf<Mountain>()
                for (document in it){
                    val mountain = document.toObject(Mountain::class.java)
                    mountains.add(mountain)
                }
                result.invoke(
                    UiState.Succes(mountains)
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun addMountains(mountain: Mountain, result: (UiState<String>) -> Unit) {
        val document = database.collection(FirebaseTables.mountain).document()
        mountain.id = document.id
        document.set(mountain)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Succes("Data Berhasil dibuat")
                )
            }

    }

    override fun getMountainById(id: String, result: (UiState <Mountain>) -> Unit) {
        database.collection(FirebaseTables.mountain).document(id)
            .get().addOnSuccessListener{
                val mountain = it.toObject(Mountain::class.java)
            }
    }

    override suspend fun uploadImage(fileUri: Uri, onResult: (UiState<Uri>) -> Unit) {

        try {
            val uri: Uri = withContext(Dispatchers.IO){
                storageReference
                    .putFile(fileUri)
                    .await()
                    .storage
                    .downloadUrl
                    .await()
            }
            onResult.invoke(UiState.Succes(uri))
        } catch (e: FirebaseFirestoreException){
            onResult.invoke(UiState.Failure(e.message))
        }

    }

}