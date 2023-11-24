package com.example.teamprojectsolocode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.teamprojectsolocode.databinding.ActivityMainBinding
import com.example.teamprojectsolocode.firebasedb.FBRef
import com.example.teamprojectsolocode.schedules.Schedule
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding // Up 버튼 설정시 지역 변수로하면 binding이 사용불가하기 때문에 전역변수로 설정

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater) // 기초 세팅

        // NavHostFragment = 현재 binding된 것에 대한 컨테이너로부터 Fragment을 가져온 것 (네비게이션을 위한 Fragment)
        val navController = binding.frgNav.getFragment<NavHostFragment>().navController // 네비게이션 컨트롤러 설정

        val appBarConfiguration = AppBarConfiguration( // top 레벨 Fragment의 id 집합을 파라미터로 함
            setOf(R.id.groupsFragment, R.id.scheduleFragment) // 이 두개의 화면에서는 Up버튼이 안보이게 함
        )
        setupActionBarWithNavController(navController, appBarConfiguration) // 액션바(상단 화면 제목) 설정
        binding.bottomNav.setupWithNavController(navController) // 하단바(화면 전환) 설정

        checkNewUid()

        setContentView(binding.root) // 기초 세팅
    }

    // Up(뒤로 가기) 버튼 설정
    override fun onSupportNavigateUp(): Boolean {
        val navController = binding.frgNav.getFragment<NavHostFragment>().navController // 네비게이션 컨트롤러 설정

        return navController.navigateUp() || super.onSupportNavigateUp() // navController가 있으면 뒤로가기 없으면 기본(기본은 뒤로가기 x)
    }

    private fun checkNewUid() {
        FBRef.uidRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(!snapshot.exists()) {
                    FBRef.uidRef.child("scheduleList").child("0").setValue(Schedule("todo", "date", "time", "dday"))
                    FBRef.uidRef.child("myTeamList").child("0").setValue("내 팀 목록")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}