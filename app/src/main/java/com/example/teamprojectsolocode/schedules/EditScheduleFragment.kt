package com.example.teamprojectsolocode.schedules

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.teamprojectsolocode.R
import com.example.teamprojectsolocode.databinding.FragmentEditScheduleBinding
import com.example.teamprojectsolocode.firebasedb.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Calendar

private val dayOfWeekArray = arrayOf("일", "월", "화", "수", "목", "금", "토")
class EditScheduleFragment : Fragment() {

    private lateinit var binding: FragmentEditScheduleBinding //binding

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
        val receivedBundle = arguments
        val itemNum = receivedBundle?.getInt("ITEM_NUM")
        binding = FragmentEditScheduleBinding.inflate(inflater)
        setAllText(itemNum?:0)

        // 요일을 선택할 때마다 호출되는 함수
        isCalendarChange()

        // 글자 줄 수 제한
        with(binding){
            txtWriteTodo.addTextChangedListener(object : TextWatcher {
                var maxText = ""
                override fun beforeTextChanged(pos: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    maxText = pos.toString()
                }
                override fun onTextChanged(pos: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    if(txtWriteTodo.lineCount > 2){
                        Toast.makeText(context, "최대 2줄까지 입력 가능합니다", Toast.LENGTH_SHORT).show()

                        txtWriteTodo.setText(maxText)
                        txtWriteTodo.setSelection(txtWriteTodo.length())
                    } else if(txtWriteTodo.length() > 60){
                        Toast.makeText(context, "최대 60자까지 입력 가능합니다.",
                            Toast.LENGTH_SHORT).show()

                        txtWriteTodo.setText(maxText)
                        txtWriteTodo.setSelection(txtWriteTodo.length())
                    } else {
                    }
                }
                override fun afterTextChanged(p0: Editable?) {

                }
            })
        }

        // 만들기 버튼을 눌렀을 때
        binding.btnMake.setOnClickListener {
            // 입력받은 할일 넣어줌
            todo = binding.txtWriteTodo.text.toString()

            if (todo.isEmpty() or time.isEmpty()) {
                Toast.makeText(this.context, "입력을 모두 완료해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // 팀 생성
            editSchedule(todo, date, time, dday, itemNum?:0)

            findNavController().navigate(R.id.action_editScheduleFragment_to_scheduleFragment)
        }

        // 시간 설정 버튼 눌렀을 때
        binding.btnTimeSet.setOnClickListener {
            val timePickerDialog = TimePickerDialog(
                this.context,
                { _, hourOfDay, minute ->
                    // 선택한 시간을 처리
                    hour = hourOfDay
                    val viewHour = (hour % 13 + hour / 13)
                    minu = minute
                    ampm = if (hour in 0..11) "AM" else "PM"
                    time = "${viewHour.toString().padStart(2, '0')}:${
                        minu.toString().padStart(2, '0')
                    } $ampm"
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
    private fun editSchedule(todo: String, date: String, time: String, dday: String, itemNum : Int) {
        FBRef.scheduleListRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                FBRef.scheduleListRef.child(itemNum.toString()).setValue(
                    Schedule(
                        todo,
                        date,
                        time,
                        dday
                    )
                )
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun isCalendarChange() {
        // 달력에서 날짜를 바꿀 때 마다 listener를 통한 date수정
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar: Calendar = Calendar.getInstance()
            // 년, 월, 일 세팅
            calendar.set(year, month, dayOfMonth)
            // 무슨 요일인지 받아줌
            val dayOfWeek: Int = calendar.get(Calendar.DAY_OF_WEEK)
            // date 해당 요일로 변경
            date = "${(year%100).toString().padStart(2,'0')}." +
                    "${(month+1).toString().padStart(2,'0')}." +
                    "${dayOfMonth.toString().padStart(2,'0')} " +
                    "(${dayOfWeekArray[dayOfWeek-1]})"
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

    private fun setAllText(itemNum : Int) {
        FBRef.scheduleListRef.child(itemNum.toString()).addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                todo = snapshot.child("todo").value.toString()
                binding.txtWriteTodo.setText(snapshot.child("todo").value.toString())
                date = snapshot.child("date").value.toString()
                hour = snapshot.child("time").value.toString().substring(0, 2).toInt()
                binding.edtHour.text = hour.toString()
                minu = snapshot.child("time").value.toString().substring(3, 5).toInt()
                binding.edtMin.text = minu.toString()
                ampm = snapshot.child("time").value.toString().substring(6, 8)
                binding.txtAmPm.text = ampm
                time = "${hour.toString().padStart(2, '0')}:${minu.toString().padStart(2, '0')} $ampm"
                dday = snapshot.child("dday").value.toString() // 남은 날짜 변수

                val year = date.substring(0, 2).toInt()
                val month = date.substring(3, 5).toInt()
                val day = date.substring(6, 8).toInt()

                val calendar = Calendar.getInstance()
                calendar.set(2000 + year, month - 1, day) // 변경하고자 하는 날짜 설정 (2023년 11월 30일)
                val millis = calendar.timeInMillis // 밀리초 단위의 시간으로 변환

                binding.calendarView.setDate(millis, false, true) // 해당 날짜로 CalendarView의 날짜 변경

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}