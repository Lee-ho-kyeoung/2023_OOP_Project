package com.example.teamprojectsolocode.team

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamprojectsolocode.databinding.ListTeamsBinding

//UI 랜더링할 때 필요한 것들을 넘겨주는 역할
class TeamsAdapter(val teams: ArrayList<Teams>, val clickListener: (position: Int, Teams) -> Unit) :
    RecyclerView.Adapter<TeamsAdapter.Holder>() { //data Structure를 넘겨받아야함.
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Holder { //viewHolder = 아이템 한 칸
        val binding = ListTeamsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) { //Holder가 몇 번째 포지션인지? 랜더링
        val currentTeam = teams[position]
        holder.bind(currentTeam)
        
        holder.itemView.setOnClickListener {// 생성자 부분에서 쓰는게 좋음
            clickListener((position + 1), currentTeam) // 클릭 리스너 호출
        }
    }

    override fun getItemCount() = teams.size //view안에 들어가는 item 수

    class Holder(private val binding: ListTeamsBinding): RecyclerView.ViewHolder(binding.root) { //item layout을 넘겨줌
        fun bind(team: Teams) {
            Glide.with(binding.root.context)
                .load(team.uri) // 이미지 URL
                .into(binding.imageView) // ImageView에 이미지 설정
            binding.txtTeamName.text = team.name
            binding.txtTeamExplain.text = team.notice
        }
    }
}