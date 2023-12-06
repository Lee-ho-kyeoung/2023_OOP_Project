package com.example.teamprojectsolocode.repository

import androidx.lifecycle.MutableLiveData
import com.example.teamprojectsolocode.Notice.Notice
import com.example.teamprojectsolocode.firebasedb.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class NoticeRepository {

    fun observeNoticeList(NoticeList: MutableLiveData<ArrayList<Notice>>) {
        FBRef.noticeListRef.addValueEventListener(object : ValueEventListener {
            // 데이터가 바뀌거나 처음 불릴 때 업데이트
            override fun onDataChange(snapshot: DataSnapshot) {
                NoticeList.postValue(makeNoticeList(snapshot))
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun makeNoticeList(snapshot: DataSnapshot): ArrayList<Notice> {
        val arrayList = arrayListOf<Notice>()
        for (childSnapshot in snapshot.children) {
            val title = childSnapshot.child("title").value.toString()
            val content = childSnapshot.child("content").value.toString()

            // 0번 아이템인 경우 표시하지 않음
            if (title.isNotEmpty() && content.isNotEmpty()) {
                arrayList.add(Notice(title, content))
            }
        }
        return arrayList
    }



    fun addNoticeList(title: String, content: String) { // Notice 추가

        FBRef.noticeListRef.get().addOnSuccessListener {
            val length = it.childrenCount.toInt()
            FBRef.noticeListRef.child(length.toString()).setValue(Notice(title, content))
        }
    }

    fun removeNotice(itemNum: Int) {
        FBRef.noticeListRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in itemNum until snapshot.childrenCount.toInt() - 1) {
                    val nextIndex = i + 1
                    FBRef.noticeListRef.child(i.toString()).setValue(snapshot.child(nextIndex.toString()).value)
                }

                FBRef.noticeListRef.child((snapshot.childrenCount - 1).toString()).removeValue()
            }

            override fun onCancelled(error: DatabaseError) {
                // 에러 처리
            }
        })
    }







}
