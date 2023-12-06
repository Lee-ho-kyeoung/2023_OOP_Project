package com.example.teamprojectsolocode.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teamprojectsolocode.schedules.Schedule
import com.example.teamprojectsolocode.repository.ScheduleRepository

// ViewModel은 데이터를 자료구조 형식으로 기본적으로 가지고있음
class ScheduleViewModel: ViewModel() {

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

    // 오늘 날짜와 스케줄 날짜를 비교해 d-day를 변경해주는 함수
    fun setDday() {
        repository.setDday()
    }
    
    // 스케줄 삭제하는 함수
    fun removeSchedule(itemNum: Int) {
        repository.removeSchedule(itemNum)
    }

}