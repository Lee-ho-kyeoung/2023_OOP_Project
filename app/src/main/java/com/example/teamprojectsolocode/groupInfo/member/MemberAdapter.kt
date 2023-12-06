package com.example.teamprojectsolocode.groupInfo.member

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teamprojectsolocode.databinding.ItemMemberBinding

class MemberAdapter : RecyclerView.Adapter<MemberAdapter.MemberViewHolder>() {

    private var members: List<Member> = emptyList()

    fun updateData(newMembers: List<Member>) {
        members = newMembers
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val binding = ItemMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val member = members[position]
        holder.bind(member)
    }

    override fun getItemCount(): Int {
        return members.size
    }

    inner class MemberViewHolder(private val binding: ItemMemberBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(member: Member) {
            binding.vectorImageView.setImageResource(member.emojiResourceId)
            binding.memberNameTextView.text = member.nickname
        }
    }
}
