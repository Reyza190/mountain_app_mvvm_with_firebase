package com.example.mountain.di

import androidx.room.RoomDatabase
import com.example.mountain.repository.AuthRepository
import com.example.mountain.repository.AuthRepositoryImpl
import com.example.mountain.repository.MountainRepository
import com.example.mountain.repository.MountainRepositoryImpl
import com.example.mountain.utils.FirebaseTables
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(impl : AuthRepositoryImpl): AuthRepository = impl

    @Provides
    fun provideFirebaseStore(): FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }

    @Provides
    fun provideMountainRepository(
        database: FirebaseFirestore,
        storageReference: StorageReference
    ): MountainRepository{
        return MountainRepositoryImpl(database, storageReference)
    }

    @Provides
    fun provideFirebaseStorageInstance():StorageReference{
        return FirebaseStorage.getInstance().getReference(FirebaseTables.DIRECTORY)
    }

}