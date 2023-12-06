package com.example.teamprojectsolocode.groupInfo.member

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teamprojectsolocode.R
import com.example.teamprojectsolocode.databinding.FragmentGroupInfoBinding
import com.example.teamprojectsolocode.viewmodel.MemberViewModel

class MemberFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MemberAdapter
    private val viewModel: MemberViewModel by activityViewModels()
    private lateinit var binding: FragmentGroupInfoBinding// FragmentMemberBinding을 사용하려면 해당 레이아웃 파일을 정의해야 합니다.

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupInfoBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = view.findViewById(R.id.memberRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = MemberAdapter()
        recyclerView.adapter = adapter

        viewModel.memberList.observe(viewLifecycleOwner) { updatedMembers ->
            adapter.updateData(updatedMembers)
        }

        viewModel.addMember("nickname", "roll")


        return view
    }
}
