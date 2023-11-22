package com.example.teamprojectsolocode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.teamprojectsolocode.databinding.FragmentEditTeamBinding
import com.example.teamprojectsolocode.databinding.FragmentGroupsBinding
import com.example.teamprojectsolocode.viewmodel.TeamsViewModel

class EditTeamFragment : Fragment() {

    val viewModel: TeamsViewModel by activityViewModels()
    private lateinit var binding: FragmentEditTeamBinding //binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditTeamBinding.inflate(inflater)

        binding.btnCreateTeam.setOnClickListener {

        }

        // Inflate the layout for this fragment
        return binding.root
    }

}