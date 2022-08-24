package com.example.loayvodasample.viewmodel

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.loayvodasample.data.model.User
import com.example.loayvodasample.databinding.ItemUserBinding

class UserAdapter: RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private val list = ArrayList<User>()
    @SuppressLint("NotifyDataSetChanged")
    fun setList(users: ArrayList<User>){
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding : ItemUserBinding) : RecyclerView.ViewHolder(binding.root){
      fun bind(user: User){
          binding.apply {
              Glide.with(itemView)
                  .load(user.avatar_url)
                  .transition(DrawableTransitionOptions.withCrossFade())
                  .centerCrop()
                  .into(imgUser)

              userName.text=user.login
          }

      }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
     val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context))
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
     holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}