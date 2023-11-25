package com.example.teamprojectsolocode.schedules

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamprojectsolocode.R
import com.example.teamprojectsolocode.RecyclerViewDecoration
import com.example.teamprojectsolocode.databinding.FragmentScheduleBinding
import com.example.teamprojectsolocode.firebasedb.FBRef
import com.example.teamprojectsolocode.repository.ScheduleRepository
import com.example.teamprojectsolocode.viewmodel.ScheduleViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

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
        binding.recSchedules.adapter = SchedulesAdapter(scheduleList, this)

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
            binding.recSchedules.adapter = SchedulesAdapter(scheduleList, this)
        }

        binding?.btnMakeSchedule?.setOnClickListener { // 팀 추가 버튼 누를 때 액션
            findNavController().navigate(R.id.action_scheduleFragment_to_makeScheduleFragment) //Resource.id.navigation action
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
                    0 -> {
                        ScheduleRepository().removeSchedule(itemNum)
                        Toast.makeText(this.context, "해당 일정을 완료했습니다.", Toast.LENGTH_SHORT).show()
                    }
                    // 번들과 함께 할일 수정 화면으로 전환
                    1 -> findNavController().navigate(R.id.action_scheduleFragment_to_editScheduleFragment, bundle)
                    // 해당 번호의 리스트 삭제
                    2 -> ScheduleRepository().removeSchedule(itemNum)
                }
            }
            .setCancelable(true)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}