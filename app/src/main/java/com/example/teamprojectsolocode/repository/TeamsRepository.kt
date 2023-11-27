package com.example.teamprojectsolocode.repository

import androidx.lifecycle.MutableLiveData
import com.example.teamprojectsolocode.firebasedb.FBRef
import com.example.teamprojectsolocode.team.Teams
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class TeamsRepository {
    fun observeTeamList(teamlist: MutableLiveData<ArrayList<Teams>>) {
        FBRef.myTeamListRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                teamlist.postValue(makeTeamList(snapshot))
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun makeTeamList(snapshot: DataSnapshot): ArrayList<Teams> {
        val arrayList = arrayListOf<Teams>()
        for(num in 1 .. snapshot.childrenCount) {
            if(snapshot.child(num.toString()).exists()) {
                arrayList.add(
                    Teams(
                        snapshot.child(num.toString()).child("name").value.toString(),
                        snapshot.child(num.toString()).child("notice").value.toString(),
                        snapshot.child(num.toString()).child("pin").value.toString(),
                        snapshot.child(num.toString()).child("uri").value.toString()
                    )
                )
            }
        }
        return arrayList
    }
}