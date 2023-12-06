package com.example.teamprojectsolocode.team

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamprojectsolocode.R
import com.example.teamprojectsolocode.RecyclerViewDecoration
import com.example.teamprojectsolocode.databinding.FragmentGroupsBinding
import com.example.teamprojectsolocode.viewmodel.TeamsViewModel

class GroupsFragment : Fragment() {

    private val viewModel: TeamsViewModel by activityViewModels() // TeamsViewModel 연결
    private var myTeamList = arrayListOf<Teams>()                 // 새로 업데이트 할 내 팀 목록 리스트
    private lateinit var binding: FragmentGroupsBinding //binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupsBinding.inflate(inflater) //binding

        binding.recTeams.layoutManager = LinearLayoutManager(context) //recycler view 설정
        binding.recTeams.addItemDecoration(RecyclerViewDecoration(30)) // item layout설정

        return binding.root // binding이 최상위 view가 되기 때문에
    }

    // view가 다 binding이 되어서 create 되고난 이후 네비게이션 및 모든 설정 이후
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.teamList.observe(viewLifecycleOwner) {
            myTeamList = viewModel.teamList.value?: arrayListOf() // teamList에 viewModel의 리스트를 넣어줌
            binding.recTeams.adapter = TeamsAdapter(myTeamList) {position, teams ->
                val bundle = Bundle()
                bundle.putInt("position", position)
                findNavController().navigate(R.id.action_groupsFragment_to_groupInfoFragment, bundle)
            }
        }

        binding.btnTeamSearch.setOnClickListener { // 팀 검색 버튼 누를 때 팀 검색화면으로 이동
            findNavController().navigate(R.id.action_groupsFragment_to_searchTeamFragment)
        }
        binding.btnTeamEdit.setOnClickListener { // 팀 생성 버튼 누를 때 액션 팀 생성화면으로 이동
            findNavController().navigate(R.id.action_groupsFragment_to_createTeamFragment)
        }
    }
}