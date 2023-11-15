package com.example.teamprojectsolocode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.teamprojectsolocode.databinding.FragmentSearchTeamBinding

class SearchTeamFragment : Fragment() {

    private lateinit var binding: FragmentSearchTeamBinding //binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchTeamBinding.inflate(inflater)

        // Inflate the layout for this fragment
        return binding.root
    }
}