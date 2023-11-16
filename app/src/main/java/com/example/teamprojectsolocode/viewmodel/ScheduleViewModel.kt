package com.example.teamprojectsolocode.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teamprojectsolocode.Schedule
import com.example.teamprojectsolocode.repository.ScheduleRepository

// ViewModel은 데이터를 자료구조 형식으로 기본적으로 가지고있음
class ScheduleViewModel: ViewModel() {

    private val testList = arrayListOf<Schedule>()

    // 내부적으로는 바꿀 수 있는 데이터 형식을 사용 (Mutable)
    private val _scheduleList = MutableLiveData(testList)

    // 리포지토리 가져오기
    val repository = ScheduleRepository()

    init {
        repository.observeScheduleList(_scheduleList)
    }

    // 밖에서 볼 때는 바꿀 수 없는 형태로
    val scheduleList : LiveData<ArrayList<Schedule>> get() = _scheduleList

}