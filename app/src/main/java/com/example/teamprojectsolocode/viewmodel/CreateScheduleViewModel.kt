package com.example.teamprojectsolocode.viewmodel

import androidx.lifecycle.ViewModel
import com.example.teamprojectsolocode.databinding.FragmentEditScheduleBinding
import com.example.teamprojectsolocode.repository.ScheduleRepository
import com.example.teamprojectsolocode.schedules.ScheduleInfo

// ViewModel은 데이터를 자료구조 형식으로 기본적으로 가지고있음
class CreateScheduleViewModel: ViewModel() {

    // 리포지토리 가져오기
    private val repository = ScheduleRepository()

    // 데이터베이스에서 정보를 가져와 바인딩된 장소들에 정보를 기입해줌
    fun setAllText(itemNum: Int, scheduleInfo: ScheduleInfo, binding: FragmentEditScheduleBinding) {
        repository.setAllText(itemNum, scheduleInfo, binding)
    }

    // 스케줄 만드는 함수
    fun makeSchedule(scheduleInfo: ScheduleInfo) {
        repository.makeSchedule(scheduleInfo)
    }

    // 스케줄 수정하는 함수
    fun editSchedule(scheduleInfo: ScheduleInfo, itemNum : Int) {
        repository.editSchedule(scheduleInfo, itemNum)
    }

}