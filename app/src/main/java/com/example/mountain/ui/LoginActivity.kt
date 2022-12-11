package com.example.mountain.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide.init
import com.example.mountain.R
import com.example.mountain.databinding.ActivityLoginBinding
import com.example.mountain.viewmodel.AuthViewModel
import com.example.mountain.viewmodel.Resource
import com.google.android.material.progressindicator.CircularProgressIndicator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLogin.setOnClickListener(this)
        binding.tvSignup.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnLogin -> login(binding.edtEmail.text.toString().trim(),binding.edtPassword.text.toString().trim())
            R.id.tvSignup -> startActivity(Intent(this, RegisterActivity::class.java))
        }
    }


    private fun login(email: String, password: String) {
        if (viewModel.validateFormLogin(email, password)) {
            viewModel.login(email, password)
            //for collect the response
            lifecycleScope.launchWhenStarted {
                viewModel.loginFlow.collect {
                    when (it) {
                        is Resource.Failure -> Toast.makeText(
                            this@LoginActivity,
                            it.exception.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        Resource.Loading -> Toast.makeText(
                            this@LoginActivity,
                            "loading",
                            Toast.LENGTH_SHORT
                        ).show()
                        is Resource.Succes -> startActivity(
                            Intent(
                                this@LoginActivity,
                                MainActivity::class.java
                            )
                        )
                        null -> Toast.makeText(this@LoginActivity, "null", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }else{
            Toast.makeText(this, "Lengkapi form diatas", Toast.LENGTH_SHORT).show()
        }
    }
}