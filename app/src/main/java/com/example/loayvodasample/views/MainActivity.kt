package com.example.loayvodasample.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loayvodasample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter= UserAdapter()
        adapter.notifyDataSetChanged()
        viewModel=ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

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