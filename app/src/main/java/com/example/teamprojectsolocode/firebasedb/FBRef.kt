package com.example.teamprojectsolocode.firebasedb

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FBRef {
    companion object {
        private val database = Firebase.database
        val uid = Firebase.auth.uid ?: ""

        val uidRef = database.getReference("user/${uid}")
        val teamListRef = database.getReference("teamList")

        val myTeamListRef = database.getReference("user/${uid}/myTeamList")
        val scheduleListRef = database.getReference("user/$uid/scheduleList")
    }
}