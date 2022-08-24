package com.example.loayvodasample.views

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loayvodasample.R
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
                searchUser()
            }

        }
        viewModel.getSearchUsers().observe(this) {
            if (it != null && it.size > 0) {
                // check if no internet
                if (it.size == 1 && it[0].id == -1){
                    //this means no internet
                    binding.apply {
                        rvUsersearch.visibility = View.INVISIBLE
                        errorView.visibility = View.VISIBLE
                        errorView.text = "No Internet"
                    }
                    adapter.setError();
                } else {
                    binding.apply {
                        binding.rvUsersearch.visibility = View.VISIBLE
                        binding.errorView.visibility = View.INVISIBLE
                    }
                    adapter.setList(it)
                }

            }
            else {
                binding.apply {
                    rvUsersearch.visibility = View.INVISIBLE
                    errorView.visibility = View.VISIBLE
                    errorView.text = "No Users Found"
                }
                adapter.setError();
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
        binding.apply {
            val query = txtSearch.text.toString()
            closeKeyBoard()
            if (query.isNotEmpty()) {
                viewModel.setSearchUsers(query)
            }
            return
        }
    }

}