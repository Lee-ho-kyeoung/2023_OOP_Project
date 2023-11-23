package com.example.teamprojectsolocode.repository

import androidx.lifecycle.MutableLiveData
import com.example.teamprojectsolocode.schedules.Schedule
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database
import com.google.firebase.auth.ktx.auth

class ScheduleRepository {
    private val database = Firebase.database
    private val auth = Firebase.auth
    private val uid = auth.uid ?: ""
    private val scheduleListRef = database.getReference("user/$uid/scheduleList")

    fun observeScheduleList(scheduleList: MutableLiveData<ArrayList<Schedule>>) {
        scheduleListRef.addValueEventListener(object: ValueEventListener{
            // 데이터가 바뀌거나 처음 불릴 때 업데이트
            override fun onDataChange(snapshot: DataSnapshot) {
                scheduleList.postValue(makeScheduleList(snapshot))
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun makeScheduleList(snapshot: DataSnapshot): ArrayList<Schedule> {
        val arrayList = arrayListOf<Schedule>()
        for(num in 1 .. snapshot.childrenCount) {
            if (snapshot.child(num.toString()).exists()) {
                arrayList.add(Schedule(
                    snapshot.child(num.toString()).child("todo").value.toString(),
                    snapshot.child(num.toString()).child("date").value.toString(),
                    snapshot.child(num.toString()).child("time").value.toString(),
                    snapshot.child(num.toString()).child("dDay").value.toString())
                )
            }
        }
        return arrayList
    }
}