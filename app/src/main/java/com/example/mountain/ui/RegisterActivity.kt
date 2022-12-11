package com.example.mountain.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mountain.R
import com.example.mountain.databinding.ActivityRegisterBinding
import com.example.mountain.viewmodel.AuthViewModel
import com.example.mountain.viewmodel.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<AuthViewModel>()
    private lateinit var email: String
    private lateinit var name: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        binding.btnSignUp.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnSignUp -> signup(name, email, password)
        }
    }

    private fun init(){
        email = binding.edtEmailSignup.text.toString()
        password = binding.edtPasswordSignup.text.toString()
        name = binding.edtFirstName.text.toString() + binding.edtLastName.text.toString()

    }

    private fun signup(name: String, email: String, password: String) {
        viewModel.signup(name, email, password)
        lifecycleScope.launchWhenStarted {
            viewModel.signupFlow.collect{
                when(it){
                    is Resource.Failure -> Toast.makeText(
                        this@RegisterActivity,
                        it.exception.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    Resource.Loading -> Toast.makeText(
                        this@RegisterActivity,
                        "loading",
                        Toast.LENGTH_SHORT
                    ).show()
                    is Resource.Succes -> startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                    null -> Toast.makeText(this@RegisterActivity, "null", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }


}