package com.example.mountain.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mountain.R
import com.example.mountain.databinding.ActivityMainBinding
import com.example.mountain.ui.fragment.DownloadFragment
import com.example.mountain.ui.fragment.HomeFragment
import com.example.mountain.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavView.background = null
        replaceFragment(HomeFragment())


        //select for fragment
        binding.bottomNavView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navhome ->replaceFragment(HomeFragment())
                R.id.navlike ->replaceFragment(DownloadFragment())

            }
            true
        }
        //add activity
        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddMountainActivity::class.java))
        }


    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }


}