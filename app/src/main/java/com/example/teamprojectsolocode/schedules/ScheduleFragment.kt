package com.example.teamprojectsolocode.schedules

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamprojectsolocode.R
import com.example.teamprojectsolocode.RecyclerViewDecoration
import com.example.teamprojectsolocode.databinding.FragmentScheduleBinding
import com.example.teamprojectsolocode.viewmodel.ScheduleViewModel

class ScheduleFragment : Fragment() {

    //Recycler View용 할 일 배열 (나중에 값을 ViewModel에서 가져옴)
    private var viewScheduleList = arrayListOf<Schedule>()

    // viewModel 가져오고 초기화
    private val viewModel: ScheduleViewModel by activityViewModels()

    // 바인딩 만들어주기
    private lateinit var binding: FragmentScheduleBinding // binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 바인딩
        binding = FragmentScheduleBinding.inflate(inflater)

        // Recycler View에 필요한 layoutManager 만들기 (현재 context 넣어줌)
        binding.recSchedules.layoutManager = LinearLayoutManager(context)

        // 각각의 스케줄에 마진 넣기
        binding.recSchedules.addItemDecoration(RecyclerViewDecoration(30))

        return binding.root // binding이 최상위 view가 되기 때문에
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // viewModel의 scheduleList가 바뀌거나 scheduleList를 최초로 읽는다면
        viewModel.scheduleList.observe(viewLifecycleOwner) {
            // viewScheduleList에 viewModel의 리스트를 넣어줌 (Null일 시 빈 리스트 넣어주기)
            viewScheduleList = viewModel.scheduleList.value?: arrayListOf()
            // adapter에 최신화된 scheduleList와 현 프레그먼트 넣어주기
            binding.recSchedules.adapter = SchedulesAdapter(viewScheduleList, this)

            viewModel.setDday() // D-day를 현재 날짜에 맞게 수정해줌
        }

        binding?.btnMakeSchedule?.setOnClickListener { // 팀 추가 버튼 누를 때 액션
            findNavController().navigate(R.id.action_scheduleFragment_to_makeScheduleFragment)
        }
    }

    // 리스트 클릭 시 대화창 뜨게 하는 함수
    fun showListDialog(itemNum : Int) {
        val oItems = arrayOf("완료", "수정", "삭제") // 목록에 들어갈 리스트
        val bundle = Bundle() // 번들 만들어 주고
        bundle.putInt("ITEM_NUM", itemNum) // 리스트 번호 넣어줌

        // 대화창 생성
        AlertDialog.Builder(context,
            android.R.style.Theme_DeviceDefault_Light_Dialog_Alert)
            .setItems(oItems) { _, which ->
                when(which) {
                    // 0 : 완료, 1 : 수정, 2 : 삭제
                    // 해당 번호의 리스트 완료
                    0 -> { // 완료 버튼을 누르면
                        viewModel.removeSchedule(itemNum) // 해당 번호의 스케줄을 삭제
                        Toast.makeText(this.context, "해당 일정을 완료했습니다.", Toast.LENGTH_SHORT).show()
                    }
                    // 번들과 함께 할일 수정 화면으로 전환
                    1 -> findNavController().navigate(R.id.action_scheduleFragment_to_editScheduleFragment, bundle)
                    // 해당 번호의 리스트 삭제
                    2 -> viewModel.removeSchedule(itemNum) // 해당 번호의 스케줄을 삭제
                }
            }
            .setCancelable(true)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}