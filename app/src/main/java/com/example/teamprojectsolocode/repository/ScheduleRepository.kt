package com.example.teamprojectsolocode.repository

import androidx.lifecycle.MutableLiveData
import com.example.teamprojectsolocode.firebasedb.FBRef
import com.example.teamprojectsolocode.schedules.Schedule
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ScheduleRepository {
    fun observeScheduleList(scheduleList: MutableLiveData<ArrayList<Schedule>>) {
        FBRef.scheduleListRef.addValueEventListener(object: ValueEventListener{
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
                    snapshot.child(num.toString()).child("dday").value.toString())
                )
            }
        }
        return arrayList
    }

    fun removeSchedule(itemNum: Int) {
        FBRef.scheduleListRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (i in itemNum..< snapshot.childrenCount.toInt() - 1) {
                    FBRef.scheduleListRef.child(i.toString()).setValue(snapshot.child((i+1).toString()).value)
                }

                FBRef.scheduleListRef.child((snapshot.childrenCount - 1).toString()).removeValue()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}