package com.example.teamprojectsolocode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.teamprojectsolocode.databinding.FragmentGroupsBinding

/**
 * A simple [Fragment] subclass.
 * Use the [GroupsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupsFragment : Fragment() {
    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }*/
    var binding: FragmentGroupsBinding? = null // binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupsBinding.inflate(inflater) // binding

        // Inflate the layout for this fragment
        return binding?.root // binding이 최상위 view가 되기 때문에
    }

    // view가 다 binding이 되어서 create 되고난 이후 네비게이션 및 모든 설정 이후
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnTeamSearch?.setOnClickListener { // 팀 검색 버튼 누를 때 액션
            findNavController().navigate(R.id.action_groupsFragment_to_searchTeamFragment) //Resource.id.navigation action
        }

        binding?.btnTeamEdit?.setOnClickListener { // 팀 생성 버튼 누를 때 액션
            findNavController().navigate(R.id.action_groupsFragment_to_editTeamFragment) //Resource.id.navigation action
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

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