package com.example.teamprojectsolocode.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teamprojectsolocode.repository.NoticeRepository
import com.example.teamprojectsolocode.Notice.Notice

class NoticeViewModel : ViewModel() {
    private val _noticeList = MutableLiveData<ArrayList<Notice>>()

    private val repository = NoticeRepository()

    init {
        _noticeList.value = arrayListOf()
        repository.observeNoticeList(_noticeList)
    }

    val noticeList: LiveData<ArrayList<Notice>> get() = _noticeList

    fun addNotice(title: String, content: String) {
        repository.addNoticeList(title, content)
    }

}