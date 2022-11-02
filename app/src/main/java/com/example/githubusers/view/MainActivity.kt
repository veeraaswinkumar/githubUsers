package com.example.githubusers.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusers.databinding.ActivityMainBinding
import com.example.githubusers.model.User
import com.example.githubusers.room.FavoriteUser
import com.example.githubusers.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel : UserViewModel
    private lateinit var adapter : UserAdapter
    private var isChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter(object : UserAdapter.onItemClickListener{
            override fun onItemClick(user: User) {
               Intent(this@MainActivity, UserDetailActivity::class.java).also {
                   it.putExtra(UserDetailActivity.USERNAME,user.login)
                   startActivity(it)
               }
            }

            override fun onFavClick(user: User) {
                isChecked = !isChecked
                if (isChecked)
                    viewModel.addToFav(username = user.login, id = user.id)
                else
                    viewModel.removeFav(id = user.id)
                adapter.addRemoveFav(isChecked)
            }

        })
        adapter.notifyDataSetChanged()
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)

        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerView.adapter = adapter

            textEditText.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(text: Editable?) {
                   searchUser()
                }

            })

            viewModel.getSearchUser().observe(this@MainActivity,{
                if(it!=null)
                    adapter.setList(it)
            })
        }
    }

    private fun searchUser(){
        binding.apply {
            val query = textEditText.text.toString()
            if(query.isEmpty()) return
            viewModel.setSearchUsers(query)
        }
    }

    private fun CheckFav(id : Int){
        isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkFav(id = id)
            withContext(Dispatchers.Main){
                if(count!=null){
                    if (count>0) {
                        adapter.CheckFav(true)
                        isChecked = true
                    }else{
                        adapter.CheckFav(false)
                        isChecked = false
                    }
                }
            }
        }
    }


}