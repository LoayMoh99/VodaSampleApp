package com.example.loayvodasample.views

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loayvodasample.databinding.ActivityMainBinding
import com.example.loayvodasample.utilities.CheckConnectionType
import com.example.loayvodasample.viewmodel.MainViewModel
import com.example.loayvodasample.viewmodel.UserAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter= UserAdapter()
        viewModel= ViewModelProvider(this,ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]

        binding.apply {
            rvUsersearch.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUsersearch.setHasFixedSize(true)
            rvUsersearch.adapter=adapter

            btnSearch.setOnClickListener {
                searchUser()
            }

        }
        viewModel.getSearchUsers().observe(this) {
            binding.pbSearch.visibility = View.GONE
            if (it != null && it.size > 0) {
                binding.apply {
                    binding.rvUsersearch.visibility = View.VISIBLE
                    binding.errorView.visibility = View.GONE
                }
                adapter.setList(it)
            }
            else {
                binding.apply {
                    rvUsersearch.visibility = View.GONE
                    errorView.visibility = View.VISIBLE
                    errorView.text = "No Users Found"
                }
                adapter.clearList();
                Log.e("Error","No users");
            }
        }

    }
    private fun closeKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun searchUser() {
        if (CheckConnectionType(this)) {
            binding.apply {
                pbSearch.visibility = View.VISIBLE
                errorView.visibility = View.GONE
                adapter.clearList();
                val query = txtSearch.text.toString()
                if (query.isNotEmpty()) {
                    viewModel.setSearchUsers(query)
                }
            }
        }
        else {
            binding.apply {
                rvUsersearch.visibility = View.GONE
                pbSearch.visibility = View.GONE
                errorView.visibility = View.VISIBLE
                errorView.text = "No Internet"
            }
        }
        closeKeyBoard()
    }

}