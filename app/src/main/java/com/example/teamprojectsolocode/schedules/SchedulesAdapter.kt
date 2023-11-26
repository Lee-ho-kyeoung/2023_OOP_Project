package com.example.teamprojectsolocode.schedules

import android.app.AlertDialog
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.teamprojectsolocode.R
import com.example.teamprojectsolocode.databinding.ListScheduleBinding
import com.example.teamprojectsolocode.repository.ScheduleRepository

//Recycler View에 필요한 adapter 설정


class SchedulesAdapter(
    private val schedules: ArrayList<Schedule>,
    private val fragment: ScheduleFragment
)
    : RecyclerView.Adapter<SchedulesAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListScheduleBinding.inflate(LayoutInflater.from(parent.context))

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        // 포지션을 인자로 받으니, 몇 번째 일정을 랜더링 할 껀지 정해줌(순서대로)
        holder.bind(schedules[position])
        setScheduleColor(holder, position)

        // 클릭시
        holder.itemView.setOnClickListener {
            // 리스트 대화창을 띄우고, 리스트 번호를 같이 줌
            fragment.showListDialog(position + 1)
        }
    }

    // 할 일 배열의 크기를 받아줌
    override fun getItemCount() = schedules.size

    private fun setScheduleColor(holder: Holder, position: Int) {
        val dateInfo = schedules[position].dDay.split("+", "-").toMutableList()
        if (dateInfo[0] == "D") dateInfo[0] = "0"
        val oper = if (schedules[position].dDay.contains("+")) "+" else "-"
        Log.d(oper, "oper")
        if (oper == "+")
            holder.itemView.setBackgroundResource(R.drawable.schedule_style_0)
        else {
            holder.itemView.apply {
                when(dateInfo[0].toInt()) {
                    0 -> setBackgroundResource(R.drawable.schedule_style_1)
                    in 1 .. 3 -> setBackgroundResource(R.drawable.schedule_style_2)
                    else -> setBackgroundResource(R.drawable.schedule_style_3)
                }
            }
        }

    }

    // 할 일 text들을 인자로 받은
    class Holder(private val binding: ListScheduleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(schedule: Schedule) {
            binding.todoText.text = schedule.todo
            binding.dateText.text = schedule.date
            binding.timeText.text = schedule.time
            binding.dDayText.text = schedule.dDay
        }
    }

}