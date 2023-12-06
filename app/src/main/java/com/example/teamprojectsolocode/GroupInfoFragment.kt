package com.example.teamprojectsolocode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teamprojectsolocode.databinding.FragmentGroupInfoBinding
import com.example.teamprojectsolocode.firebasedb.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class GroupInfoFragment : Fragment() {
    private lateinit var activityRecyclerView: RecyclerView
    private lateinit var memberRecyclerView: RecyclerView
/*
    override fun onCreate(savedInstanceState: Bundle?) { // 시작할때 세팅
        super.onCreate(savedInstanceState)
        val receivedBundle = arguments // 넘겨받은 번들
        val position = receivedBundle?.getInt("position") // 번들의 key에 맞는 value 저장

        FBRef.myTeamListRef.addValueEventListener(object: ValueEventListener{ // 액션바 제목 설정
            override fun onDataChange(snapshot: DataSnapshot) {
                val pinNum = snapshot.child(position.toString()).value.toString()
                FBRef.teamListRef.addValueEventListener(object: ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        (activity as MainActivity).supportActionBar?.title = snapshot.child(pinNum).child("teamContent").child("name").value.toString()
                    }
                    override fun onCancelled(error: DatabaseError) {}
                })
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentGroupInfoBinding.inflate(inflater)
        val receivedBundle = arguments // 넘겨받은 번들
        val position = receivedBundle?.getInt("position") // 번들의 key에 맞는 value 저장

        /*
        binding.noticeContentTextView.setOnClickListener { // 공지사항 클릭시 이벤트
            TODO()
        }

        // 가로로 된 RecyclerView 초기화
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        activityRecyclerView.layoutManager = layoutManager

        // 예시 데이터 생성
        val activityList = listOf("활동1", "활동2", "활동3", "활동4", "활동5")

        // 어댑터 설정
        val adapter = InfoAdapter(activityList)
        activityRecyclerView.adapter = adapter

        // 수평으로 된 GridLayoutManager 초기화
        val layoutManager = GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false)
        memberRecyclerView.layoutManager = layoutManager

        // 팀 구성원 데이터 생성
        val memberList = listOf("구성원1", "구성원2", "구성원3", "구성원4", "구성원5")

        // 어댑터 설정
        val adapter = InfoAdapter(memberList)
        memberRecyclerView.adapter = adapter
        */

        return binding.root // Inflate the layout for this fragment
    }*/
}

