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
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.teamprojectsolocode.R
import com.example.teamprojectsolocode.databinding.FragmentEditScheduleBinding
import com.example.teamprojectsolocode.databinding.FragmentMakeScheduleBinding
import com.example.teamprojectsolocode.viewmodel.CreateScheduleViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

private val dayOfWeekArray = arrayOf("일", "월", "화", "수", "목", "금", "토")
class MakeScheduleFragment : Fragment() {

    private lateinit var binding: FragmentMakeScheduleBinding //binding

    // viewModel 가져오고 초기화
    private val viewModel: CreateScheduleViewModel by activityViewModels()

    private val currentTime: Long = System.currentTimeMillis() // 현재 시간을 받아줌
    private val today = Calendar.getInstance() // 오늘 날짜 받아줌

    // 여러 날짜 관련 변수들의 초기 값을 입력해줌
    private val scheduleInfo = ScheduleInfo("",
        SimpleDateFormat("yy.MM.dd (EE)").format(currentTime),
        -1,
        -1,
        "",
        "",
        "D-day")
    val maxLines = 2 // 최대 두 줄

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // 바인딩
        binding = FragmentMakeScheduleBinding.inflate(inflater)

        // 요일을 선택할 때마다 호출되는 함수
        isCalendarChange()

        // 글자 줄 수 제한
        checkLineOut()

        // 만들기 버튼을 눌렀을 때
        binding.btnMake.setOnClickListener {
            // 입력받은 할일 넣어줌
            scheduleInfo.todo = binding.txtWriteTodo.text.toString()
            // 만약 입력 칸과 시간이 비었다면
            if (scheduleInfo.todo.isEmpty() or scheduleInfo.time.isEmpty()) {
                Toast.makeText(this.context, "입력을 모두 완료해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // 팀 생성
            viewModel.makeSchedule(scheduleInfo)
            // 화면 이동
            findNavController().navigate(R.id.action_makeScheduleFragment_to_scheduleFragment)
        }

        // 시간 설정 버튼 눌렀을 때
        binding.btnTimeSet.setOnClickListener {
            val timePickerDialog = TimePickerDialog(
                this.context,
                { _, hourOfDay, minute ->
                    // 선택한 시간을 처리
                    scheduleInfo.hour = hourOfDay
                    val viewHour = (scheduleInfo.hour % 13 + scheduleInfo.hour / 13)
                    scheduleInfo.minu = minute
                    scheduleInfo.ampm = if (scheduleInfo.hour in 0..11) "AM" else "PM"
                    scheduleInfo.time = "${viewHour.toString().padStart(2, '0')}:${
                        scheduleInfo.minu.toString().padStart(2, '0')
                    } ${scheduleInfo.ampm}"
                    // 선택한 시간을 사용
                    binding.txtAmPm.text = scheduleInfo.ampm
                    binding.edtHour.text = viewHour.toString()
                    binding.edtMin.text = scheduleInfo.minu.toString()
                },
                scheduleInfo.hour,
                scheduleInfo.minu,
                false
            )
            timePickerDialog.show()
        }
        // 최상위 뷰
        return binding.root
    }

    // 달력 관련 함수
    private fun isCalendarChange() {
        // 달력에서 날짜를 바꿀 때 마다 listener를 통한 date 및 dday수정
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar: Calendar = Calendar.getInstance()
            // 년, 월, 일 세팅
            calendar.set(year, month, dayOfMonth)
            // 무슨 요일인지 받아줌
            val dayOfWeek: Int = calendar.get(Calendar.DAY_OF_WEEK)
            // date 해당 요일로 변경
            scheduleInfo.date = "${(year%100).toString().padStart(2,'0')}." +
                    "${(month+1).toString().padStart(2,'0')}." +
                    "${dayOfMonth.toString().padStart(2,'0')} " +
                    "(${dayOfWeekArray[dayOfWeek-1]})"
            val gap = ((calendar.time.time - today.time.time) / (60 * 60 * 24 * 1000))
            scheduleInfo.dday = if (gap < 0) {
                "${-gap}+day"
            } else if (gap > 0) {
                "${gap}-day"
            } else {
                "D-day"
            }
        }
    }

    // 길이 조사 함수
    private fun checkLineOut() {
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
    }
}

