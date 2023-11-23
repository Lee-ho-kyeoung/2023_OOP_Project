package com.example.teamprojectsolocode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.teamprojectsolocode.databinding.FragmentEditTeamBinding
import com.example.teamprojectsolocode.viewmodel.TeamsViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class EditTeamFragment : Fragment() {

    val viewModel: TeamsViewModel by activityViewModels()
    private lateinit var binding: FragmentEditTeamBinding //binding
    private val database = Firebase.database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditTeamBinding.inflate(inflater)

        binding.btnCreateTeam.setOnClickListener {
            val teamName = binding.txtInputTeamName.toString()
            val pinNum = binding.txtInputTeamCode.toString()
            val teamNotice = binding.txtInputTeamNotice.toString()


        }

        // Inflate the layout for this fragment
        return binding.root
    }

}