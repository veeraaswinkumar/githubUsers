package com.example.githubusers.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubusers.databinding.ActivityItemUserBinding
import com.example.githubusers.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserAdapter(var param: onItemClickListener) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val list  = ArrayList<User>()
    var bindingValue : ActivityItemUserBinding ?=null

    fun setList(user: ArrayList<User>){
        list.clear()
        list.addAll(user)
        notifyDataSetChanged()
    }

    fun addRemoveFav(isChecked: Boolean){
        bindingValue?.ivFavourite?.isChecked = isChecked
    }

    /*fun CheckFav(isChecked: Boolean){
        CoroutineScope(Dispatchers.IO).launch {
            val count = view
        }
    }*/



    inner class UserViewHolder(val binding: ActivityItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user:User){
            bindingValue = binding
            binding.root.setOnClickListener {
                param.onItemClick(user)
            }

            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivImage)
                textView.text = user.login
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        var view = ActivityItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size


    interface onItemClickListener {
        fun onItemClick(user: User)
        fun onFavClick(user: User)
    }

}