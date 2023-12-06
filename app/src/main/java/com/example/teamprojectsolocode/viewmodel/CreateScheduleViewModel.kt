package com.example.teamprojectsolocode.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teamprojectsolocode.databinding.FragmentEditScheduleBinding
import com.example.teamprojectsolocode.schedules.Schedule
import com.example.teamprojectsolocode.repository.ScheduleRepository
import com.example.teamprojectsolocode.schedules.ScheduleInfo

// ViewModel은 데이터를 자료구조 형식으로 기본적으로 가지고있음
class CreateScheduleViewModel: ViewModel() {

    // 리포지토리 가져오기
    private val repository = ScheduleRepository()

    // 스케줄을 담을 빈 리스트 만들기
    private val sList = arrayListOf<Schedule>()

    // 내부적으로는 바꿀 수 있는 데이터 형식을 사용 (Mutable)
    private val _scheduleList = MutableLiveData(sList)

    init {
        repository.observeScheduleList(_scheduleList)
    }

    // 밖에서 볼 때는 바꿀 수 없는 형태로 (Mutable 아님)
    val scheduleList : LiveData<ArrayList<Schedule>> get() = _scheduleList

    //
    fun setAllText(itemNum: Int, scheduleInfo: ScheduleInfo, binding: FragmentEditScheduleBinding) {
        repository.setAllText(itemNum, scheduleInfo, binding)
    }

    fun makeSchedule(scheduleInfo: ScheduleInfo) {
        repository.makeSchedule(scheduleInfo)
    }
    fun editSchedule(scheduleInfo: ScheduleInfo, itemNum : Int) {
        repository.editSchedule(scheduleInfo, itemNum)
    }

}