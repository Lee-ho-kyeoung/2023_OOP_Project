package com.example.teamprojectsolocode.repository


import androidx.lifecycle.MutableLiveData
import com.example.teamprojectsolocode.firebasedb.FBRef
import com.example.teamprojectsolocode.groupInfo.member.Member
import com.google.firebase.database.*

class MemberRepository {

    fun observeGroupMembers(memberList: MutableLiveData<List<Member>>) {
        FBRef.memberListRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val members = mutableListOf<Member>()

                for (memberSnapshot in snapshot.children) {
                    val name = memberSnapshot.child("nickName").value.toString()
                    val role = memberSnapshot.child("roll").value.toString()

                    val emojiResourceId = when (role) {
                        "leader" -> com.example.teamprojectsolocode.R.drawable.baseline_emoji_emotions_24
                        "member" -> com.example.teamprojectsolocode.R.drawable.baseline_emoji_emotions_member_24
                        else -> com.example.teamprojectsolocode.R.drawable.baseline_emoji_emotions_member_24
                    }

                    members.add(Member(name, role, emojiResourceId))
                }

                memberList.postValue(members) // LiveData에 데이터를 전달
            }

            override fun onCancelled(error: DatabaseError) {
                // 오류 처리
            }
        })
    }

    fun addMember(name: String, role: String) {
        FBRef.memberListRef.get().addOnSuccessListener {
            val length = it.childrenCount.toInt()
            FBRef.memberListRef.child(length.toString()).setValue(Member(name, role, com.example.teamprojectsolocode.R.drawable.baseline_emoji_emotions_24))

        }
    }


}


