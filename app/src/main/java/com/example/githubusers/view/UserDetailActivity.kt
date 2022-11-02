package com.example.githubusers.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubusers.databinding.ActivityUserDetailsBinding
import com.example.githubusers.viewmodel.UserDetailViewModel

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailsBinding
    private lateinit var viewmodel : UserDetailViewModel
    companion object{
        var USERNAME = "user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var username = intent.getStringExtra(USERNAME)

        viewmodel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(UserDetailViewModel::class.java)
        viewmodel.setUserDetails(username)

        viewmodel.getUserDetails().observe(this,{
            if (it!=null){
                binding.apply {
                    nameTextView.text = it.login
                    textRepositories.text = it.repos_url
                    Glide.with(this@UserDetailActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(photoImageView)

                }
            }
        })
    }
}