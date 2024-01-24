package com.example.hamrofutsal

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.DumpableContainer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import com.example.hamrofutsal.databinding.ActivityUserProfileBinding

class UserProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun onCreateView(inflater: LayoutInflater,container: ViewGroup): View? {
        binding = ActivityUserProfileBinding.inflate(inflater,container,false)
        return binding.root
    }
}