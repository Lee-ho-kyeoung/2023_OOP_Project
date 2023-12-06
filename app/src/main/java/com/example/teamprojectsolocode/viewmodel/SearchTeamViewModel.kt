package com.example.teamprojectsolocode.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teamprojectsolocode.repository.TeamsRepository

class SearchTeamViewModel(private val repository: TeamsRepository) : ViewModel() {
    val teamName = MutableLiveData<String>()
    val teamExistsCheckSerch = MutableLiveData<Boolean>()
    val myTeamExistCheck = MutableLiveData<Boolean>()

    fun existCheckTeamList(pinNum: String) { // 전체 팀 목록에 있는지 검사
        repository.existCheckTLSerch(pinNum) { result ->
            teamName.value = result
            teamExistsCheckSerch.value = result.isNotBlank()
        }
    }
    fun existCheckMyTeamList(pinNum: String) { // 내팀 목록에 있는지 검사
        repository.existCheckedMyTL(pinNum) { result ->
            myTeamExistCheck.value = result
        }
    }

    fun addMyTeam(pinNum: String, nickName: String) {
        repository.addMyTeamListSearch(pinNum,nickName)
    }
}