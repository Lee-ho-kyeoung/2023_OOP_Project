package com.example.teamprojectsolocode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.teamprojectsolocode.databinding.FragmentSearchTeamBinding
import com.example.teamprojectsolocode.firebasedb.FBRef
import com.example.teamprojectsolocode.team.Teams
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class SearchTeamFragment : Fragment() {

    private lateinit var binding: FragmentSearchTeamBinding //binding
    private lateinit var serchedCode: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchTeamBinding.inflate(inflater)
        var check = false // 팀 목록에 존재하는지 여부 확인

        binding.btnSerch.setOnClickListener {
            serchedCode = binding.txtInputParticipateCode.text.toString()
            FBRef.teamListRef.addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.child(serchedCode).exists() && serchedCode != ""){ //빈칸이 아니고 데이터베이스상에 존재하면
                        binding.txtResultTeamName.text = snapshot.child(serchedCode)
                            .child("teamContent").child("name").value.toString()
                        check = true
                    }
                    else{ //빈칸이거나 데이터베이스상에 존재하지 않으면
                        binding.txtResultTeamName.text = ""
                        check = false
                    }
                }
                override fun onCancelled(error: DatabaseError) {}
            })
        }

        binding.btnParticipateTeam.setOnClickListener {
            val nickName = binding.txtInputNickName.text.toString()

            if(check && nickName.isNotBlank()){
                FBRef.teamListRef.addListenerForSingleValueEvent(object: ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        serchedCode = binding.txtInputParticipateCode.text.toString()
                        val teamName = snapshot.child(serchedCode).child("teamContent").child("name").value.toString()
                        val teamNotice = snapshot.child(serchedCode).child("teamContent").child("notice").value.toString()
                        val pinNum = snapshot.child(serchedCode).child("teamContent").child("pin").value.toString()
                        val uri = snapshot.child(serchedCode).child("teamContent").child("uri").value.toString()
                        val num = snapshot.child(serchedCode).child("members").childrenCount.toInt()

                        addMyTeamList(teamName, teamNotice, pinNum, uri, nickName) // 내 팀 목록에 추가
                    }
                    override fun onCancelled(error: DatabaseError) {}
                })
            }
            else {
                Toast.makeText(context, "존재하지 않는 팀이거나 모든 정보가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root // Inflate the layout for this fragment
    }

    private fun addMyTeamList(teamName: String, teamNotice: String, pinNum: String, uri: String, nickName: String) { //myTeamList에 team 추가하는 함수
        FBRef.myTeamListRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(i in 1..snapshot.childrenCount){ // 이미 내 팀 목록에 존재하는지 확인
                    if(pinNum == snapshot.child(i.toString()).child("pin").value) {
                        Toast.makeText(context, "이미 가입된 팀입니다.", Toast.LENGTH_SHORT).show()
                        return
                    }
                }
                val length = snapshot.childrenCount.toInt()
                FBRef.myTeamListRef.child(length.toString()).setValue(Teams(teamName, teamNotice, pinNum, uri))
                FBRef.myTeamListRef.child(length.toString()).child("nickName").setValue(nickName)

                FBRef.teamListRef.addListenerForSingleValueEvent(object: ValueEventListener {
                    override fun onDataChange(TLsnapshot: DataSnapshot) {
                        val num = TLsnapshot.child(serchedCode).child("members").childrenCount.toInt()
                        FBRef.teamListRef.child(serchedCode).child("members").child(num.toString())
                            .child("roll").setValue("member") // 팀 리스트에 역할 추가
                        FBRef.teamListRef.child(serchedCode).child("members").child(num.toString())
                            .child("nickName").setValue(nickName) // 팀 리스트에 닉네임 추가
                    }
                    override fun onCancelled(error: DatabaseError) {}
                })

                findNavController().navigate(R.id.action_searchTeamFragment_to_groupsFragment)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}