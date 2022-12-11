package com.example.mountain.ui

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.mountain.R
import com.example.mountain.databinding.ActivityAddMountainBinding
import com.example.mountain.model.Mountain
import com.example.mountain.viewmodel.MountainViewModel
import com.example.mountain.viewmodel.UiState
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class AddMountainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAddMountainBinding
    private val mountainViewModel: MountainViewModel by viewModels()
    private lateinit var uri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMountainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //add image from galeri
        binding.llAddImage.setOnClickListener(this)

        //submit value
        binding.btnSubmit.setOnClickListener(this)



    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.llAddImage -> {
                intent = Intent(MediaStore.ACTION_PICK_IMAGES)
                intent?.resolveActivity(packageManager).also {
                    startActivityForResult(intent, 101)
                }
            }

            R.id.btnSubmit ->{
                addMountain(uri)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK){
            binding.ivAddMountain.visibility = View.VISIBLE
            binding.ivaddImageDummy.visibility = View.GONE
            binding.tvAddCover.visibility = View.GONE
            uri = data?.data!!
            uploadToStorage(uri)
        }
    }

    private fun uploadToStorage(imgUri: Uri){
        mountainViewModel.uploadImage(imgUri){ state ->
            when(state){
                is UiState.Failure -> Log.e("AddMountainAcitivty", state.exception.toString())
                is UiState.Loading -> Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                is UiState.Succes -> uri = state.result
            }
        }
    }

    private fun addMountain(result: Uri) {
        mountainViewModel.addMountains(
            Mountain(
            id = "",
            mountainName = binding.edtMountainName.text.toString(),
            mountainImage = result.toString(),
            height = binding.edtHeight.text.length,
            location = binding.edtLocation.text.toString(),
            description = binding.edtDescription.text.toString()
        )
        )
    }

    private fun convertUri(imgBitmap: Bitmap): Uri {
        val byte = ByteArrayOutputStream()
        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byte)
        val path = MediaStore.Images.Media.insertImage(this.contentResolver, imgBitmap, "title", null)
        return Uri.parse(path)
    }



}