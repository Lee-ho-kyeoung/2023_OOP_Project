package com.example.teamprojectsolocode.repository

import androidx.lifecycle.MutableLiveData
import com.example.teamprojectsolocode.databinding.FragmentEditScheduleBinding
import com.example.teamprojectsolocode.firebasedb.FBRef
import com.example.teamprojectsolocode.schedules.Schedule
import com.example.teamprojectsolocode.schedules.ScheduleInfo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.util.Calendar

class ScheduleRepository {
    fun observeScheduleList(scheduleList: MutableLiveData<ArrayList<Schedule>>) {
        FBRef.scheduleListRef.addValueEventListener(object: ValueEventListener{
            // 데이터가 바뀌거나 처음 불릴 때 업데이트
            override fun onDataChange(snapshot: DataSnapshot) {
                //
                scheduleList.postValue(makeScheduleList(snapshot))
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    // 위 함수에서 이용하는 함수. 데이터베이스에서 읽은 값을 스케줄 리스트에 넣어줌
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

    // 해당 스케줄을 지워주는 함수
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

    // 스케줄 만드는 함수
    fun makeSchedule(scheduleInfo: ScheduleInfo) {
        FBRef.scheduleListRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val length = snapshot.childrenCount.toInt()
                FBRef.scheduleListRef.child(length.toString()).setValue(Schedule(scheduleInfo.todo, scheduleInfo.date, scheduleInfo.time, scheduleInfo.dday))
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    // 스케줄 수정하는 함수
    fun editSchedule(scheduleInfo: ScheduleInfo, itemNum : Int) {
        FBRef.scheduleListRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                FBRef.scheduleListRef.child(itemNum.toString()).setValue(
                    Schedule(
                        scheduleInfo.todo,
                        scheduleInfo.date,
                        scheduleInfo.time,
                        scheduleInfo.dday
                    )
                )
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    // 바인딩을 받아서 각 자리에 맞게 정보들을 기입하는 함수
    fun setAllText(itemNum: Int, scheduleInfo: ScheduleInfo, binding: FragmentEditScheduleBinding) {
        FBRef.scheduleListRef.child(itemNum.toString()).addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                scheduleInfo.todo = snapshot.child("todo").value.toString()
                binding.txtWriteTodo.setText(snapshot.child("todo").value.toString())
                scheduleInfo.date = snapshot.child("date").value.toString()
                scheduleInfo.hour = snapshot.child("time").value.toString().substring(0, 2).toInt()
                binding.edtHour.text = scheduleInfo.hour.toString()
                scheduleInfo.minu = snapshot.child("time").value.toString().substring(3, 5).toInt()
                binding.edtMin.text = scheduleInfo.minu.toString()
                scheduleInfo.ampm = snapshot.child("time").value.toString().substring(6, 8)
                binding.txtAmPm.text = scheduleInfo.ampm
                scheduleInfo.time = "${scheduleInfo.hour.toString().padStart(2, '0')}:${scheduleInfo.minu.toString().padStart(2, '0')} ${scheduleInfo.ampm}"
                scheduleInfo.dday = snapshot.child("dday").value.toString() // 남은 날짜 변수

                val year = scheduleInfo.date.substring(0, 2).toInt()
                val month = scheduleInfo.date.substring(3, 5).toInt()
                val day = scheduleInfo.date.substring(6, 8).toInt()

                val calendar = Calendar.getInstance()
                calendar.set(2000 + year, month - 1, day)
                val millis = calendar.timeInMillis // 밀리초 단위의 시간으로 변환

                binding.calendarView.setDate(millis, false, true) // 해당 날짜로 CalendarView의 날짜 변경

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}