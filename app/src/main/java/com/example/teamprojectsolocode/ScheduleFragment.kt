package com.example.teamprojectsolocode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teamprojectsolocode.databinding.FragmentScheduleBinding

/**
 * A simple [Fragment] subclass.
 * Use the [GroupsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScheduleFragment : Fragment() {

    //Recycler View용 할 일 배열
    private val schedules = arrayOf(
        Schedule("To-Do List 만들기To-Do List 만들기To-Do List 만들기To-Do List 만들기To-Do List 만들기To-Do List 만들기", "23.11.13 (월)", "11:30 PM", "D-day"),
        Schedule("저녁 만들어 먹기", "23.11.14 (화)", "6:30 PM", "1-day"),
        Schedule("임베디드 과제 제출하기", "23.11.16 (목)", "8:00 PM", "3-day"),
        Schedule("To-Do List 만들기To-Do List 만들기To-Do List 만들기To-Do List 만들기To-Do List 만들기To-Do List 만들기", "23.11.13 (월)", "11:30 PM", "D-day"),
        Schedule("저녁 만들어 먹기", "23.11.14 (화)", "6:30 PM", "1-day"),
        Schedule("임베디드 과제 제출하기", "23.11.16 (목)", "8:00 PM", "3-day"),
        Schedule("To-Do List 만들기To-Do List 만들기To-Do List 만들기To-Do List 만들기To-Do List 만들기To-Do List 만들기", "23.11.13 (월)", "11:30 PM", "D-day"),
        Schedule("저녁 만들어 먹기", "23.11.14 (화)", "6:30 PM", "1-day"),
        Schedule("임베디드 과제 제출하기", "23.11.16 (목)", "8:00 PM", "3-day"),
        Schedule("To-Do List 만들기To-Do List 만들기To-Do List 만들기To-Do List 만들기To-Do List 만들기To-Do List 만들기", "23.11.13 (월)", "11:30 PM", "D-day"),
        Schedule("저녁 만들어 먹기", "23.11.14 (화)", "6:30 PM", "1-day"),
        Schedule("임베디드 과제 제출하기", "23.11.16 (목)", "8:00 PM", "3-day"),
        Schedule("To-Do List 만들기To-Do List 만들기To-Do List 만들기To-Do List 만들기To-Do List 만들기To-Do List 만들기", "23.11.13 (월)", "11:30 PM", "D-day"),
        Schedule("저녁 만들어 먹기", "23.11.14 (화)", "6:30 PM", "1-day"),
        Schedule("임베디드 과제 제출하기", "23.11.16 (목)", "8:00 PM", "3-day"),
    )

    // 바인딩 만들어주기
    private lateinit var binding: FragmentScheduleBinding // binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleBinding.inflate(inflater) // binding
        // Recycler View에 필요한 layoutManager와 adapter 만들기
        binding.recSchedules.layoutManager = LinearLayoutManager(context)
        binding.recSchedules.adapter = SchedulesAdapter(schedules)

        // 각각의 스케줄에 마진 넣기
        binding.recSchedules.addItemDecoration(RecyclerViewDecoration(30))
        // Inflate the layout for this fragment
        return binding?.root // binding이 최상위 view가 되기 때문에
    }

    // view가 다 binding이 되어서 create 되고난 이후 네비게이션 및 모든 설정 이후
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnMakeSchedule?.setOnClickListener { // 팀 추가 버튼 누를 때 액션
            findNavController().navigate(R.id.action_scheduleFragment_to_makeScheduleFragment) //Resource.id.navigation action
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}