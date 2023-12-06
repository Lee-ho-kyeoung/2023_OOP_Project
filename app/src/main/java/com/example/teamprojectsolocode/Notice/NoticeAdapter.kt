package com.example.teamprojectsolocode.Notice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teamprojectsolocode.databinding.ItemNoticeBinding


class NoticeAdapter(var noticeList: ArrayList<Notice>, private val noticeFragment: NoticeFragment):RecyclerView.Adapter<NoticeAdapter.NoticeItemViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeItemViewHolder {
        val binding = ItemNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoticeItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoticeItemViewHolder, position: Int) {
        val notice = noticeList[position]
        holder.bind(notice)

        // 클릭 이벤트 처리
        holder.itemView.setOnClickListener {
            // 현재 아이템의 위치를 가져와서 대화창을 띄우기
            val itemNum = holder.adapterPosition
            noticeFragment.showNoticeDialog(itemNum)
        }
    }



    override fun getItemCount() = noticeList.size

    inner class NoticeItemViewHolder(private val binding: ItemNoticeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(notice: Notice) {
            binding.noticeTitleTextView.text = notice.title
            binding.noticeContentTextView.text = notice.content
            // 다른 데이터 바인딩 작업 추가 (예: 아이콘 표시)
        }
    }

    fun updateData(updatedNotices: List<Notice>) {
        noticeList.clear()
        noticeList.addAll(updatedNotices)
        notifyDataSetChanged()
    }






}
