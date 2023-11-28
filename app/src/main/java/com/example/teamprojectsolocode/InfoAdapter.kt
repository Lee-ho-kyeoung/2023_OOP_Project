package com.example.teamprojectsolocode

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class InfoAdapter {
}

/*
class InfoAdapter(private val dataList: List<String>) : RecyclerView.Adapter<InfoAdapter.ViewHolder>() {
    // 뷰홀더 클래스 정의

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
    }

    // onCreateViewHolder: 아이템 뷰를 위한 뷰홀더 객체 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // XML 레이아웃을 인플레이트하여 뷰 객체 생성
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_member, parent, false)
        // 뷰홀더 객체 생성 후 반환
        return ViewHolder(itemView)
    }

    // onBindViewHolder: 뷰홀더에 데이터 바인딩
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 데이터 리스트에서 현재 위치에 해당하는 데이터 가져오기
        val data = dataList[position]
        // 뷰홀더에 데이터 바인딩
        holder.textView.text = data
    }

    // getItemCount: 데이터 아이템 개수 반환
    override fun getItemCount(): Int {
        return dataList.size
    }
}*/