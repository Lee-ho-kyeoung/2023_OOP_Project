package com.example.teamprojectsolocode.schedules

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teamprojectsolocode.R
import com.example.teamprojectsolocode.databinding.ListScheduleBinding

//Recycler View에 필요한 adapter 설정
class SchedulesAdapter(
    private val schedules: ArrayList<Schedule>,
    private val fragment: ScheduleFragment
)
    : RecyclerView.Adapter<SchedulesAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        // 바인딩
        val binding = ListScheduleBinding.inflate(LayoutInflater.from(parent.context))

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        // 포지션을 인자로 받으니, 몇 번째 일정을 랜더링 할 껀지 정해줌(순서대로)
        holder.bind(schedules[position])

        // D-day에 따른 색상 설정
        setScheduleColor(holder, position)

        // 클릭시
        holder.itemView.setOnClickListener {
            // 리스트 대화창을 띄우고, 리스트 번호를 같이 줌
            fragment.showListDialog(position + 1)
        }
    }

    // 할 일 배열의 크기를 받아줌
    override fun getItemCount() = schedules.size

    // D-day에 따라 색상을 설정해주는 함수
    private fun setScheduleColor(holder: Holder, position: Int) {
        //ㅁ-day에서 ㅁ을 받아오고
        val dateInfo = schedules[position].dDay.split("+", "-").toMutableList()
        //D면 0으로 바꿔줌
        if (dateInfo[0] == "D") dateInfo[0] = "0"
        //-day인지 +day인지도 조사
        val oper = if (schedules[position].dDay.contains("+")) "+" else "-"
        if (oper == "+")
            // 지난 날짜면 진한 회색
            holder.itemView.setBackgroundResource(R.drawable.schedule_style_0)
        else {
            //안 지났으면
            holder.itemView.apply {
                // 날짜에 맞게 배경을 지정
                when(dateInfo[0].toInt()) {
                    0 -> setBackgroundResource(R.drawable.schedule_style_1)
                    in 1 .. 3 -> setBackgroundResource(R.drawable.schedule_style_2)
                    else -> setBackgroundResource(R.drawable.schedule_style_3)
                }
            }
        }

    }

    // 할 일 text들을 바인딩에 넣어줌
    class Holder(private val binding: ListScheduleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(schedule: Schedule) {
            binding.todoText.text = schedule.todo
            binding.dateText.text = schedule.date
            binding.timeText.text = schedule.time
            binding.dDayText.text = schedule.dDay
        }
    }

}