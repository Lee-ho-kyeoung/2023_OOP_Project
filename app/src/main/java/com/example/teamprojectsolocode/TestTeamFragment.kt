package com.example.teamprojectsolocode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.teamprojectsolocode.databinding.FragmentTestTeamBinding
import com.example.teamprojectsolocode.firebasedb.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class TestTeamFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val receivedBundle = arguments
        val position = receivedBundle?.getInt("position")

        FBRef.myTeamListRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                (activity as MainActivity).supportActionBar?.title = snapshot.child(position.toString()).child("name").value.toString()
            }
            override fun onCancelled(error: DatabaseError) {}
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTestTeamBinding.inflate(inflater)
        val receivedBundle = arguments
        val position = receivedBundle?.getInt("position")

        FBRef.myTeamListRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.txtIdenty.text = snapshot.child(position.toString()).child("name").value.toString()
            }
            override fun onCancelled(error: DatabaseError) {}
        })

        return binding.root // Inflate the layout for this fragment
    }
}