package com.example.teamprojectsolocode.viewmodel

import androidx.lifecycle.ViewModel
import com.example.teamprojectsolocode.repository.TeamsRepository
import com.example.teamprojectsolocode.team.Teams

class CreateTeamViewModel: ViewModel() {
    private val repository = TeamsRepository()
    fun existCheckTeamList(pinNum: String, callback: (Boolean) -> Unit) { // 전체 팀 목록에 있는지 검사
        repository.existCheckTLCreate(pinNum) { result ->
            callback(result) // 팀 목록에 있으면 true 없으면 false
        }
    }

    fun createTeam(team: Teams, nickName: String) { // 팀 생성
        repository.addTeamList(team, nickName) // 전체 팀 목록에 추가
        repository.addMyTeamListCreate(team.pin) // 내 팀 목록에 추가
    }
}