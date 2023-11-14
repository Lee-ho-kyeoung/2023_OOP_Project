package com.example.teamprojectsolocode

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teamprojectsolocode.databinding.ListScheduleBinding

class SchedulesAdapter(private val schedules: Array<Schedule>)
    : RecyclerView.Adapter<SchedulesAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListScheduleBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        // 포지션을 인자로 받으니, 몇 번째 사람을 랜더링 할 껀지 정해줌(순서대로)
        holder.bind(schedules[position])

    }

    override fun getItemCount() = schedules.size

    class Holder(private val binding: ListScheduleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(schedule: Schedule) {

            binding.todoText.text = schedule.todo
            binding.dateText.text = schedule.date
            binding.timeText.text = schedule.time
            binding.dDayText.text = schedule.dDay
        }
    }
}