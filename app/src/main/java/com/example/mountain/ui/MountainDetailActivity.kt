package com.example.mountain.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.mountain.databinding.ActivityMountainDetailBinding
import com.example.mountain.model.Mountain
import com.example.mountain.room.viewmodel.RoomViewModel
class MountainDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMountainDetailBinding
    private val viewModel: RoomViewModel by viewModels()

    companion object{
        val key = "MOUNTAIN"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMountainDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mountain = intent.getParcelableExtra<Mountain>(key)
        setDetailData(mountain)

        binding.ivDownload.setOnClickListener{
            if (mountain != null) {
                viewModel.insertMountains(Mountain(id = mountain.id, mountainName = mountain.mountainName,
                mountainImage = mountain.mountainImage,
                height = mountain.height,
                location = mountain.location,
                description = mountain.description))
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setDetailData(mountain: Mountain?) {
        Glide.with(this).load(mountain?.mountainImage).into(binding.rivMountainDetail)
        binding.tvMountainNameDetail.text = mountain?.mountainName
        binding.tvLocationDetail.text = mountain?.location
        binding.tvMountainHeightDetail.text =  "${mountain?.height} mdpl"
        binding.tvDesriptionDetail.text = mountain?.description
    }
}