package com.example.teamprojectsolocode.repository

import androidx.lifecycle.MutableLiveData
import com.example.teamprojectsolocode.firebasedb.FBRef
import com.example.teamprojectsolocode.team.Teams
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class TeamsRepository {
    fun observeMyTeamList(teamlist: MutableLiveData<ArrayList<Teams>>) { // 내 팀의 변화가 생길때마다 업데이트
        FBRef.myTeamListRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                setMyTeamList(snapshot) { newTeamList ->
                    teamlist.postValue(newTeamList)
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun setMyTeamList(snapshot: DataSnapshot, callback: (ArrayList<Teams>) -> Unit) { // 변화가 생긴 팀 목록 불러오기
        val pinList = mutableListOf<String>()
        for (num in 1..snapshot.childrenCount) { // 내 팀 목록에 있는 핀 번호 가져오기
            if (snapshot.child(num.toString()).exists()) {
                pinList.add(snapshot.child(num.toString()).value.toString())
            }
        }

        val arrayList = ArrayList<Teams>()

        FBRef.teamListRef.addListenerForSingleValueEvent(object: ValueEventListener{ // 핀 번호로 팀 정보 불러오기
            override fun onDataChange(snapshot2: DataSnapshot) {
                for(pin in pinList){
                    val teamSnapshot = snapshot2.child(pin)
                    val teamName = teamSnapshot.child("teamContent").child("name").value.toString()
                    val notice = teamSnapshot.child("teamContent").child("notice").value.toString()
                    val pinValue = teamSnapshot.child("teamContent").child("pin").value.toString()
                    val uri = teamSnapshot.child("teamContent").child("uri").value.toString()

                    val team = Teams(teamName, notice, pinValue, uri)
                    arrayList.add(team)
                }
                callback(arrayList)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
    fun existCheckTLCreate(pinNum: String, callback: (Boolean) -> Unit) { // 전체 팀 목록에 있는지 검사
        FBRef.teamListRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child(pinNum).exists() && pinNum.isNotBlank()) { // 전체 팀 목록에 있으면
                    callback(true)
                } else { // 전체 팀 목록에 없으면
                    callback(false)
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun addMyTeamListCreate(pinNum: String){ // 내 팀 목록에 추가
        FBRef.myTeamListRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val length = snapshot.childrenCount.toInt()
                FBRef.myTeamListRef.child(length.toString()).setValue(pinNum)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun addTeamList(team: Teams, nickName: String) { // 전체 팀 목록에 추
        FBRef.teamListRef.child(team.pin).child("members").child("0").child("roll").setValue("leader")
        FBRef.teamListRef.child(team.pin).child("members").child("0").child("nickName").setValue(nickName)
        FBRef.teamListRef.child(team.pin).child("teamContent").setValue(team)
    }

    fun existCheckTLSerch(pinNum: String, callback: (String) -> Unit) { // 전체 팀 목록에 있는지 검사
        FBRef.teamListRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child(pinNum).exists() && pinNum.isNotBlank()) {
                    val teamName = snapshot.child(pinNum)
                        .child("teamContent").child("name").value.toString()
                    callback(teamName)
                } else {
                    callback("")
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun existCheckedMyTL(pinNum: String, callback: (Boolean) -> Unit) { // 내 팀 목록에 있는지 검사
        FBRef.myTeamListRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(i in 1..snapshot.childrenCount){ // 이미 내 팀 목록에 존재하는지 확인
                    if(pinNum == snapshot.child(i.toString()).value) {
                        callback(false)
                        return
                    }
                }
                callback(true)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun addMyTeamListSearch(pinNum: String, nickName: String){ // 내 팀 목록에 추가
        FBRef.myTeamListRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val length = snapshot.childrenCount.toInt()
                FBRef.myTeamListRef.child(length.toString()).setValue(pinNum)

                FBRef.teamListRef.addListenerForSingleValueEvent(object: ValueEventListener { // 팀 목록에 내 역할, 닉네임 추가
                    override fun onDataChange(TLsnapshot: DataSnapshot) {
                        val num = TLsnapshot.child(pinNum).child("members").childrenCount.toInt()
                        FBRef.teamListRef.child(pinNum).child("members").child(num.toString())
                            .child("roll").setValue("member") // 팀 리스트에 역할 추가
                        FBRef.teamListRef.child(pinNum).child("members").child(num.toString())
                            .child("nickName").setValue(nickName) // 팀 리스트에 닉네임 추가
                    }
                    override fun onCancelled(error: DatabaseError) {}
                })
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}