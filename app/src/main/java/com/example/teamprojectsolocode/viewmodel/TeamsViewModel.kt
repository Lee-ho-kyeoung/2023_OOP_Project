package com.example.teamprojectsolocode.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teamprojectsolocode.repository.TeamsRepository
import com.example.teamprojectsolocode.team.Teams

class TeamsViewModel: ViewModel() {
    private val _teamList = MutableLiveData(arrayListOf<Teams>())

    val teamList: LiveData<ArrayList<Teams>> get() = _teamList

    private val repository = TeamsRepository()

    init {
        repository.observeTeamList(_teamList)
    }
}