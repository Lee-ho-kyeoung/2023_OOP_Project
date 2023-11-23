package com.example.teamprojectsolocode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.teamprojectsolocode.databinding.FragmentEditTeamBinding
import com.example.teamprojectsolocode.firebasedb.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class EditTeamFragment : Fragment() {

    private lateinit var binding: FragmentEditTeamBinding //binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditTeamBinding.inflate(inflater)

        binding.btnCreateTeam.setOnClickListener {
            val teamName = binding.txtInputTeamName.text.toString()
            val pinNum = binding.txtInputTeamCode.text.toString()
            val teamNotice = binding.txtInputTeamNotice.text.toString()

            FBRef.teamListRef.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(!snapshot.child(pinNum).exists()) {
                        FBRef.teamListRef.child(pinNum).child("members").child(FBRef.uid).setValue("leader")
                        FBRef.teamListRef.child(pinNum).child("name").setValue(teamName)
                        FBRef.teamListRef.child(pinNum).child("notice").setValue(teamNotice)
                        FBRef.teamListRef.child(pinNum).child("pin").setValue(pinNum)

                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }

        // Inflate the layout for this fragment
        return binding.root
    }

}