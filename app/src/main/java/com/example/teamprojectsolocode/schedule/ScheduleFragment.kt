package com.example.teamprojectsolocode.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamprojectsolocode.R
import com.example.teamprojectsolocode.RecyclerViewDecoration
import com.example.teamprojectsolocode.databinding.FragmentScheduleBinding
import com.example.teamprojectsolocode.viewmodel.ScheduleViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [GroupsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScheduleFragment : Fragment() {

    // viewModel 가져오고 초기화
    private val viewModel: ScheduleViewModel by activityViewModels()
    //Recycler View용 할 일 배열 (나중에 값을 ViewModel에서 가져옴)
    private var scheduleList = arrayListOf<Schedule>()

    // 바인딩 만들어주기
    private lateinit var binding: FragmentScheduleBinding // binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleBinding.inflate(inflater) // binding
        // Recycler View에 필요한 layoutManager와 adapter 만들기
        binding.recSchedules.layoutManager = LinearLayoutManager(context)
        binding.recSchedules.adapter = SchedulesAdapter(scheduleList)

        // 각각의 스케줄에 마진 넣기
        binding.recSchedules.addItemDecoration(RecyclerViewDecoration(30))
        // Inflate the layout for this fragment
        return binding.root // binding이 최상위 view가 되기 때문에
    }

    // view가 다 binding이 되어서 create 되고난 이후 네비게이션 및 모든 설정 이후
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // viewModel의 scheduleList가 바뀌거나 scheduleList를 최초로 읽는다면
        viewModel.scheduleList.observe(viewLifecycleOwner) {
            //scheduleList에 viewModel의 리스트를 넣어줌
            scheduleList = viewModel.scheduleList.value?: arrayListOf()
            // adapter에 바뀐 scheduleList 다시 넣어주기
            binding.recSchedules.adapter = SchedulesAdapter(scheduleList)
        }

        binding?.btnMakeSchedule?.setOnClickListener { // 팀 추가 버튼 누를 때 액션
            findNavController().navigate(R.id.action_scheduleFragment_to_makeScheduleFragment) //Resource.id.navigation action
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}