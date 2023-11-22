package com.example.teamprojectsolocode.repository

import androidx.lifecycle.MutableLiveData
import com.example.teamprojectsolocode.team.Teams
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class TeamsRepository {
    private val database = Firebase.database
    private val uid = Firebase.auth.uid
    val teamListRef = database.getReference("user/${uid}/teamList")

    fun observeTeamList(teamlist: MutableLiveData<ArrayList<Teams>>) {
        teamListRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                teamlist.postValue(makeTeamList(snapshot))
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun makeTeamList(snapshot: DataSnapshot): ArrayList<Teams> {
        val arrayList = arrayListOf<Teams>()
        var num: Int = 1
        while(snapshot.child(num.toString()).exists()) {
            arrayList.add( Teams(
                snapshot.child(num.toString()).child("name").value.toString(),
                snapshot.child(num.toString()).child("notice").value.toString(),
                snapshot.child(num.toString()).child("pin").value.toString()
                )
            )
            num++
        }
        return arrayList
    }
}