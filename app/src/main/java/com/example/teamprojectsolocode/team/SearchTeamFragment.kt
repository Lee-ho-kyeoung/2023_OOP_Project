package com.example.teamprojectsolocode.team

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.teamprojectsolocode.R
import com.example.teamprojectsolocode.databinding.FragmentSearchTeamBinding
import com.example.teamprojectsolocode.repository.TeamsRepository
import com.example.teamprojectsolocode.viewmodel.SearchTeamViewModel

class SearchTeamFragment : Fragment() {
    private lateinit var binding: FragmentSearchTeamBinding //binding
    private lateinit var serchedCode: String
    private val viewModel: SearchTeamViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchTeamBinding.inflate(inflater)

        binding.btnSerch.setOnClickListener {  // 팀 검색 버튼 누르면
            serchedCode = binding.txtInputParticipateCode.text.toString()
            viewModel.existCheckTeamList(serchedCode) // 전체 팀 목록에 있는지 확인
        }

        binding.btnParticipateTeam.setOnClickListener { // 팀 가입 버튼 누르면
            val nickName = binding.txtInputNickName.text.toString()

            if(viewModel.teamExistsCheckSerch.value == true && nickName.isNotBlank()){ // 팀 검색한 것이 있고, 닉네임 입력이 되어있으면
                viewModel.existCheckMyTeamList(serchedCode) // 내 팀 목록에 있는지 검사
                viewModel.myTeamExistCheck.observe(viewLifecycleOwner) { myTeamExistCheck ->
                    if (myTeamExistCheck) { // 내 팀 목록에 없으면
                        viewModel.addMyTeam(serchedCode, nickName) // 내 팀에 추가
                        findNavController().navigate(R.id.action_searchTeamFragment_to_groupsFragment) // GroupsFragment로 이동)
                    } else { // 내 팀 목록에 있으면
                        Toast.makeText(context, "이미 가입된 팀입니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else { // 팀 검색한 것이 없거나, 닉네임 입력이 되어있지 않으면
                Toast.makeText(context, "존재하지 않는 팀이거나 모든 정보가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        // 검색한 팀 목록 이름을 띄움
        viewModel.teamExistsCheckSerch.observe(viewLifecycleOwner) { check ->
            if (check) {
                binding.txtResultTeamName.text = viewModel.teamName.value
            } else {
                binding.txtResultTeamName.text = ""
            }
        }
        // Inflate the layout for this fragment
        return binding.root
    }
}