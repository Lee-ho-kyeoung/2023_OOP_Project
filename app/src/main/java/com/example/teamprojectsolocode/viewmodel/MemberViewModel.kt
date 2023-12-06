package com.example.teamprojectsolocode.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teamprojectsolocode.groupInfo.member.Member
import com.example.teamprojectsolocode.repository.MemberRepository

class MemberViewModel : ViewModel() {
    private val _memberList = MutableLiveData<List<Member>>()
    private val repository = MemberRepository()

    init {
        _memberList.value = emptyList()
        repository.observeGroupMembers(_memberList)
    }

    val memberList: LiveData<List<Member>> get() = _memberList

    fun addMember(name: String, role: String) {
        repository.addMember(name, role)
    }
}

