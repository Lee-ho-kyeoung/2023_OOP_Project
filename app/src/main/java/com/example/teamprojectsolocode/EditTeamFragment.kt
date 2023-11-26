package com.example.teamprojectsolocode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.teamprojectsolocode.databinding.FragmentEditTeamBinding
import com.example.teamprojectsolocode.firebasedb.FBRef
import com.example.teamprojectsolocode.team.Teams
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class EditTeamFragment : Fragment() {
    lateinit var key: String
    private lateinit var binding: FragmentEditTeamBinding //binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditTeamBinding.inflate(inflater)

        binding.btnCreateTeam.setOnClickListener {
            val teamName = binding.txtInputTeamName.text.toString()
            val pinNum = binding.txtInputTeamCode.text.toString()
            val teamNotice = binding.txtInputTeamNotice.text.toString()

            if(teamName != "" && teamNotice != "" && pinNum != "") {
                FBRef.teamListRef.addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(!snapshot.child(pinNum).exists()) {
                            FBRef.teamListRef.child(pinNum).child("members").child(FBRef.uid).setValue("leader")
                            FBRef.teamListRef.child(pinNum).child("teamContent").setValue(Teams(teamName, teamNotice, pinNum))

                            addMyTeamList(teamName, teamNotice, pinNum)
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {}
                })
                findNavController().navigate(R.id.action_editTeamFragment_to_groupsFragment) // groupFragment로 이동
            }
            else {
                Toast.makeText(context, "모든 정보를 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root // Inflate the layout for this fragment
    }

    private fun addMyTeamList(teamName: String, teamNotice: String, pinNum: String) { //myTeamList에 team 추가하는 함수
        FBRef.myTeamListRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val length = snapshot.childrenCount.toInt()
                FBRef.myTeamListRef.child(length.toString()).setValue(Teams(teamName, teamNotice, pinNum))
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}