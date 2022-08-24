package com.example.loayvodasample.views

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loayvodasample.databinding.ActivityMainBinding
import com.example.loayvodasample.viewmodel.MainViewModel
import com.example.loayvodasample.viewmodel.UserAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter= UserAdapter()
        adapter.notifyDataSetChanged()
        viewModel= ViewModelProvider(this,ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]

        binding.apply {
            rvUsersearch.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUsersearch.setHasFixedSize(true)
            rvUsersearch.adapter=adapter

            btnSearch.setOnClickListener {
                print("Button Search")
                searchUser()
            }

        }
        viewModel.getSearchUsers().observe(this) {
            if (it != null) {
                adapter.setList(it)
            }
        }

    }

    private fun searchUser() {
        binding.apply {
            val query = txtSearch.text.toString()
            if (query.isNotEmpty()) viewModel.setSearchUsers(query)
            return
        }
    }

}