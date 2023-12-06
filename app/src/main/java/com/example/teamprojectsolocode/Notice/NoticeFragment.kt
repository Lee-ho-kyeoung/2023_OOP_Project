package com.example.teamprojectsolocode.Notice

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teamprojectsolocode.R
import com.example.teamprojectsolocode.databinding.FragmentNoticeBinding
import com.example.teamprojectsolocode.repository.NoticeRepository
import com.example.teamprojectsolocode.repository.ScheduleRepository
import com.example.teamprojectsolocode.viewmodel.NoticeViewModel
import kotlinx.coroutines.launch

// ...

class NoticeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private var notices = ArrayList<Notice>()
    lateinit var adapter: NoticeAdapter
    private val viewModel: NoticeViewModel by activityViewModels()
    private lateinit var binding: FragmentNoticeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNoticeBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = view.findViewById(R.id.noticerecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = NoticeAdapter(notices, this)
        recyclerView.adapter = adapter

        binding.btncreateNotice.setOnClickListener {
            try {
                var title: String = binding.writeTitle.text.toString()
                var content: String = binding.writeContent.text.toString()

                viewModel.viewModelScope.launch {
                    viewModel.addNotice(title, content)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        viewModel.noticeList.observe(viewLifecycleOwner) { updatedNotices ->
            // 새로운 데이터로 어댑터를 업데이트합니다
            adapter.updateData(updatedNotices)
        }

        return binding.root
    }

    // showNoticeDialog 함수를 클래스의 메서드로 옮김
    fun showNoticeDialog(itemNum: Int) {
        val oItems = arrayOf("삭제") // 목록에 들어갈 리스트
        val bundle = Bundle() // 번들 만들어 주고
        bundle.putInt("ITEM_NUM", itemNum) // 리스트 번호 넣어줌

        // 대화창 생성
        AlertDialog.Builder(requireContext(), android.R.style.Theme_DeviceDefault_Light_Dialog_Alert)
            .setItems(oItems) { _, which ->
                when (which) {
                    // 0 : 삭제
                    0 -> {
                        NoticeRepository().removeNotice(itemNum)
                    }
                }
            }
            .setCancelable(true)
            .show()
    }
}















