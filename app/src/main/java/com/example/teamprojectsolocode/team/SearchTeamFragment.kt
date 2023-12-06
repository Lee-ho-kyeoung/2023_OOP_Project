package com.example.teamprojectsolocode.team

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.teamprojectsolocode.R
import com.example.teamprojectsolocode.databinding.FragmentSearchTeamBinding
import com.example.teamprojectsolocode.repository.TeamsRepository
import com.example.teamprojectsolocode.viewmodel.SearchTeamViewModel

class SearchTeamFragment : Fragment() {
    private lateinit var binding: FragmentSearchTeamBinding //binding
    private lateinit var serchedCode: String
    //private val viewModel: SearchTeamViewModel by activityViewModels()
    private val repository = TeamsRepository() // 실제 객체 생성 방식은 여러 가지일 수 있습니다.
    private val viewModel = SearchTeamViewModel(repository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchTeamBinding.inflate(inflater)

        binding.btnSerch.setOnClickListener {
            serchedCode = binding.txtInputParticipateCode.text.toString()
            viewModel.existCheckTeamList(serchedCode)
        }

        binding.btnParticipateTeam.setOnClickListener {
            val nickName = binding.txtInputNickName.text.toString()

            if(viewModel.teamExistsCheckSerch.value == true && nickName.isNotBlank()){
                viewModel.existCheckMyTeamList(serchedCode) // 내 팀 목록에 있는지 검사
                viewModel.myTeamExistCheck.observe(viewLifecycleOwner) { myTeamExistCheck ->
                    if (myTeamExistCheck) {
                        viewModel.addMyTeam(serchedCode, nickName)
                        findNavController().navigate(R.id.action_searchTeamFragment_to_groupsFragment)
                    } else {
                        Toast.makeText(context, "이미 가입된 팀입니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(context, "존재하지 않는 팀이거나 모든 정보가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.teamExistsCheckSerch.observe(viewLifecycleOwner) { check ->
            if (check) {
                binding.txtResultTeamName.text = viewModel.teamName.value
            } else {
                binding.txtResultTeamName.text = ""
            }
        }

        return binding.root // Inflate the layout for this fragment
    }
}