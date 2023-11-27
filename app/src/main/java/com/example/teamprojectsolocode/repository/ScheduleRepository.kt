package com.example.teamprojectsolocode.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.example.teamprojectsolocode.firebasedb.FBRef
import com.example.teamprojectsolocode.schedules.Schedule
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Date


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

    // 날짜가 바뀔 때 D-day도 같이 바꿔주는 함수
    fun setDday() {
        val today = Calendar.getInstance() // 오늘 날짜 받아줌
        val thisDay = Calendar.getInstance() // 한 스케줄의 날짜 받을 예정
        FBRef.scheduleListRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in 1 ..< snapshot.childrenCount) {
                    val date = snapshot.child(i.toString()).child("date").value.toString()
                    val year = date.substring(0, 2).toInt()
                    val month = date.substring(3, 5).toInt()
                    val day = date.substring(6, 8).toInt()

                    thisDay.set(2000 + year, month - 1, day)
                    val gap = ((thisDay.time.time - today.time.time) / (60 * 60 * 24 * 1000))
                    val dday = if (gap < 0) {
                        "${-gap}+day"
                    } else if (gap > 0) {
                        "${gap}-day"
                    } else {
                        "D-day"
                    }
                    FBRef.scheduleListRef.child(i.toString()).child("dday").setValue(dday)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    //fun sortSchedule()
}