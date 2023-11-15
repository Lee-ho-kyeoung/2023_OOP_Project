package com.example.teamprojectsolocode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teamprojectsolocode.databinding.FragmentGroupsBinding

class GroupsFragment : Fragment() {

    val teams = arrayOf(
        Teams("객프 팀", "객체지향 프로그래밍 팀입니다."),
        Teams("AD 팀", "Adventure Design 팀입니다."),
        Teams("임베 팀", "임베디드 SW 입문 팀입니다."),
        Teams("아무 팀", "아무 팀입니다.")
    )

    private lateinit var binding: FragmentGroupsBinding //binding
    //private lateinit var mainActivity: MainActivity //시도했는데 안됌.. 질문할 것
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupsBinding.inflate(inflater) //binding

        binding.recTeams.layoutManager = LinearLayoutManager(context) //recycler view 설정
        /*binding.recTeams.adapter = TeamsAdapter(teams) {//실제로 데이터가 주어졌을 때 recycler view에 랜더링하는 다리 역할
            findNavController().navigate(R.id.action_groupsFragment_to_testTeamFragment)//!!!!!이해 필요!!!!!
        }*/
        // Inflate the layout for this fragment
        return binding.root // binding이 최상위 view가 되기 때문에
    }

    // view가 다 binding이 되어서 create 되고난 이후 네비게이션 및 모든 설정 이후
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val teamsAdapter = TeamsAdapter(teams) {
            findNavController().navigate(R.id.action_groupsFragment_to_testTeamFragment)//!!!!!이해 필요!!!!!
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.rec_teams)//!!!!!이해 필요!!!!!
        recyclerView.adapter = teamsAdapter//!!!!!이해 필요!!!!!

        binding.btnTeamSearch.setOnClickListener { // 팀 검색 버튼 누를 때 액션
            findNavController().navigate(R.id.action_groupsFragment_to_searchTeamFragment) //Resource.id.navigation action
        }

        binding.btnTeamEdit.setOnClickListener { // 팀 생성 버튼 누를 때 액션
            findNavController().navigate(R.id.action_groupsFragment_to_editTeamFragment) //Resource.id.navigation action
        }
    }

    /*
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    } */

    /*
    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GroupsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }*/
}