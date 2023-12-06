package com.example.teamprojectsolocode.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teamprojectsolocode.repository.TeamsRepository

class SearchTeamViewModel() : ViewModel() {
    private val repository = TeamsRepository()
    private val _teamName = MutableLiveData<String>()
    private val _teamExistsCheckSerch = MutableLiveData<Boolean>()
    private val _myTeamExistCheck = MutableLiveData<Boolean>()

    val teamName: LiveData<String> = _teamName
    val teamExistsCheckSerch: LiveData<Boolean> = _teamExistsCheckSerch
    val myTeamExistCheck: LiveData<Boolean> = _myTeamExistCheck

    fun existCheckTeamList(pinNum: String) { // 전체 팀 목록에 있는지 검사
        repository.existCheckTLSerch(pinNum) { result ->
            _teamName.value = result // 검색된 팀 이름, 없으면 빈칸
            _teamExistsCheckSerch.value = result.isNotBlank() // 검색된 이름이 있으면 true 없으면 false
        }
    }
    fun existCheckMyTeamList(pinNum: String) { // 내팀 목록에 있는지 검사
        repository.existCheckedMyTL(pinNum) { result ->
            _myTeamExistCheck.value = result // 내 팀 목록에 있으면 false, 없으면 true
        }
    }

    fun addMyTeam(pinNum: String, nickName: String) {
        repository.addMyTeamListSearch(pinNum,nickName) // 내 팀 목록에 추가, 전체 팀 목록 멤버에 추가
    }
}