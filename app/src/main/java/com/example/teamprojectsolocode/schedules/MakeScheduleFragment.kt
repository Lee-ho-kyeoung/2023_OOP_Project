package com.example.teamprojectsolocode.schedules

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.teamprojectsolocode.R
import com.example.teamprojectsolocode.databinding.FragmentMakeScheduleBinding
import com.example.teamprojectsolocode.firebasedb.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Calendar

// 나중에 받을 요일이 숫자기 때문에, 배열에 한글로 값을 넣어줌
private val dayOfWeekArray = arrayOf("일", "월", "화", "수", "목", "금", "토")

class MakeScheduleFragment : Fragment() {

    private lateinit var binding: FragmentMakeScheduleBinding //binding

    private val currentTime: Long = System.currentTimeMillis() // 현재 시간을 받아줌
    private val today = Calendar.getInstance()
    private var todo = "" // 할일 변수
    private var date = SimpleDateFormat("yy.MM.dd (EE)").format(currentTime) // 날짜 변수 (오늘 날짜로 초기화)
    private var hour = -1 // 시간 변수
    private var minu = -1 // 분 변수
    private var ampm = "" // 오전오후 변수
    private var time = "" // 시간 + 분 + 오전오후 변수
    private var dday = "D-day" // 남은 날짜 변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentMakeScheduleBinding.inflate(inflater)
        // 요일을 선택할 때마다 호출되는 함수
        isCalendarChange()

        // 만들기 버튼을 눌렀을 때
        binding.btnMake.setOnClickListener {
            // 입력받은 할일 넣어줌
            todo = binding.txtWriteTodo.text.toString()

            if (todo.isEmpty() or time.isEmpty()) {
                Toast.makeText(this.context, "입력을 모두 완료해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // 팀 생성
            editSchedule(todo, date, time, dday)

            findNavController().navigate(R.id.action_makeScheduleFragment_to_scheduleFragment)
        }

        // 시간 설정 버튼 눌렀을 때
        binding.btnTimeSet.setOnClickListener {
            val timePickerDialog = TimePickerDialog(
                this.context,
                { _, hourOfDay, minute ->
                    // 선택한 시간을 처리
                    hour = hourOfDay
                    val viewHour = (hour%13 + hour/13)
                    minu = minute
                    ampm = if (hour in 0 .. 11) "AM" else "PM"
                    time = "${viewHour.toString().padStart(2,'0')}:${minu.toString().padStart(2,'0')} $ampm"
                    // 선택한 시간을 사용
                    binding.txtAmPm.text = ampm
                    binding.edtHour.text = viewHour.toString()
                    binding.edtMin.text = minu.toString()
                },
                hour,
                minu,
                false
            )
            timePickerDialog.show()
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    // 스케줄 만드는 함수
    private fun editSchedule(todo: String, date: String, time: String, dday: String) {
        FBRef.scheduleListRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val length = snapshot.childrenCount.toInt()
                FBRef.scheduleListRef.child(length.toString()).setValue(Schedule(todo, date, time, dday))
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun isCalendarChange() {
        // 달력에서 날짜를 바꿀 때 마다 listener를 통한 date수정
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // 달력 받아주고
            val calendar: Calendar = Calendar.getInstance()
            // 년, 월, 일 세팅
            calendar.set(year, month, dayOfMonth)
            // 무슨 요일인지 받아줌
            val dayOfWeek: Int = calendar.get(Calendar.DAY_OF_WEEK)
            // date 해당 요일로 변경
            date = "${year%100}.${month+1}.${dayOfMonth} (${dayOfWeekArray[dayOfWeek-1]})"
            val gap = ((calendar.time.time - today.time.time) / (60 * 60 * 24 * 1000))
            dday = if (gap < 0) {
                "${-gap}+day"
            } else if (gap > 0) {
                "${gap}-day"
            } else {
                "D-day"
            }
        }
    }
}
