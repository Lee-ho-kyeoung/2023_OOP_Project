package com.example.teamprojectsolocode.groupInfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teamprojectsolocode.Notice.Notice
import com.example.teamprojectsolocode.Notice.NoticeAdapter
import com.example.teamprojectsolocode.Notice.NoticeFragment
import com.example.teamprojectsolocode.R
import com.example.teamprojectsolocode.databinding.FragmentGroupInfoBinding
import com.example.teamprojectsolocode.groupInfo.member.MemberAdapter
import com.example.teamprojectsolocode.groupInfo.member.MemberFragment
import com.example.teamprojectsolocode.viewmodel.MemberViewModel
import com.example.teamprojectsolocode.viewmodel.NoticeViewModel

class GroupInfoFragment : Fragment() {
    private lateinit var groupMembersAdapter: MemberAdapter

    private lateinit var noticeTitleTextView: TextView
    private lateinit var noticeContentTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_group_info, container, false)

        // 팀 구성원 정보 설정
        val memberRecyclerView: RecyclerView = view.findViewById(R.id.memberRecyclerView)
        groupMembersAdapter = MemberAdapter()
        memberRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        memberRecyclerView.adapter = groupMembersAdapter

        val memberViewModel: MemberViewModel by activityViewModels()
        memberViewModel.memberList.observe(viewLifecycleOwner) { members ->
            groupMembersAdapter.updateData(members)
        }

        // 팀 최근 공지사항 설정
        noticeTitleTextView = view.findViewById(R.id.noticeTitleTextView) // 수정된 부분
        noticeContentTextView = view.findViewById(R.id.noticeContentTextView)

        val noticeViewModel: NoticeViewModel by activityViewModels()
        noticeViewModel.noticeList.observe(viewLifecycleOwner) { notices ->
            // 최신 공지사항을 가져와서 뷰에 업데이트
            if (notices.isNotEmpty()) {
                val latestNotice = notices.last() // 최신 공지사항은 리스트의 마지막ㅣㄷㅎ
                updateNoticeUI(latestNotice)
            }
        }
        view.findViewById<Button>(R.id.noticeContentTextView).setOnClickListener {
            findNavController().navigate(R.id.action_groupInfoFragment_to_noticeFragment)
        }

        return view
    }

    private fun updateNoticeUI(notice: Notice) {
        noticeTitleTextView.text = notice.title
        noticeContentTextView.text = notice.content
    }
}








