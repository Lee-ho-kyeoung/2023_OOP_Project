package com.example.teamprojectsolocode.firebasedb

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class FBRef {
    companion object {
        private val database = Firebase.database
        val uid = Firebase.auth.uid ?: ""

        val storageRef = FirebaseStorage.getInstance().reference.child("images/${UUID.randomUUID()}")

        val uidRef = database.getReference("user/${uid}")
        val teamListRef = database.getReference("teamList")

        val myTeamListRef = database.getReference("user/${uid}/myTeamList")
        val scheduleListRef = database.getReference("user/$uid/scheduleList")

        val noticeListRef = database.getReference("teamList/123/notice")
        val memberListRef = database.getReference("teamList/123/members")


    }
}