package com.example.mountain.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mountain.adapter.MountainAdapter
import com.example.mountain.databinding.FragmentHomeBinding
import com.example.mountain.model.Mountain
import com.example.mountain.viewmodel.AuthViewModel
import com.example.mountain.viewmodel.MountainViewModel
import com.example.mountain.viewmodel.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val TAG: String = "HomeFragment"
    private lateinit var binding: FragmentHomeBinding
    private val mountainViewModel: MountainViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mountainAdapter = MountainAdapter()

        binding.tvUserName.text = authViewModel.currentUser?.displayName

        getDataMountain(mountainAdapter)

        binding.rvItemMountain.apply {
            adapter = mountainAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        setSearchView(mountainAdapter)

    }

    private fun setSearchView(mountainAdapter: MountainAdapter) {
        binding.svItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null){
                    search(newText, mountainAdapter)
                }
                return true
            }

        })
    }

    private fun search(newText: String, mountainAdapter: MountainAdapter) {
       val mountains = ArrayList<Mountain>()
        for (mountain in mountains){
            if (mountain?.mountainName?.lowercase()?.contains(newText.lowercase()) == true){
                    mountains.add(mountain)
            }
           mountainAdapter.submitList(mountains)
            binding.rvItemMountain.apply {
                adapter = mountainAdapter
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    private fun getDataMountain(mountainAdapter: MountainAdapter) {
        mountainViewModel.getMountain()
        mountainViewModel.mountain.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Failure -> {
                    Log.e(TAG, state.exception.toString())
                }
                is UiState.Loading -> {
                    Toast.makeText(context, "loading", Toast.LENGTH_SHORT).show()
                }
                is UiState.Succes -> {
                    state.result.forEach {
                        mountainAdapter.submitList(state.result.toMutableList())
                        Toast.makeText(context, state.result.size.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }
}