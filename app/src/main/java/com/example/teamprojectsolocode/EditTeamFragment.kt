package com.example.teamprojectsolocode

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.teamprojectsolocode.databinding.FragmentEditTeamBinding
import com.example.teamprojectsolocode.firebasedb.FBRef
import com.example.teamprojectsolocode.team.Teams
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

        val imagePicker = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val imageUri = data?.data // 선택된 이미지의 Uri

                binding.selectedImg.setImageURI(imageUri) // 선택된 이미지를 ImageView에 설정
                binding.selectedImg.tag = imageUri
            }
        }

        binding.btnSetImage.setOnClickListener {  // 사진 선택 버튼을 누르면
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"

            imagePicker.launch(intent)
        }

        binding.btnCreateTeam.setOnClickListener { // 팀 생성 버튼을 누르면
            val teamName = binding.txtInputTeamName.text.toString()
            val pinNum = binding.txtInputTeamCode.text.toString()
            val teamNotice = binding.txtInputTeamNotice.text.toString()
            var imageUri = binding.selectedImg.tag as? Uri // 이미지 Uri 가져오기
            if (imageUri == null) {
                imageUri = Uri.parse("android.resource://com.example.teamprojectsolocode/drawable/default_img") // 기본 이미지가 있는 리소스의 Uri를 사용하거나, 다른 기본값으로 설정
            }

            if (teamName.isNotBlank() && teamNotice.isNotBlank() && pinNum.isNotBlank()) { // 모든 입력란이 비어있지 않고 이미지가 선택되었을 때
                val uploadTask = FBRef.storageRef.putFile(imageUri!!) // 이미지 업로드

                uploadTask.addOnSuccessListener { taskSnapshot ->
                    // 이미지 업로드 성공 시 URL 획득
                    FBRef.storageRef.downloadUrl.addOnSuccessListener { uri ->
                        val downloadUrl = uri.toString()

                        FBRef.teamListRef.addListenerForSingleValueEvent(object : ValueEventListener { // 팀 생성 로직 추가
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (!snapshot.child(pinNum).exists()) { // 팀 목록에 팀이 존재하지 않으면
                                    FBRef.teamListRef.child(pinNum).child("members").child(FBRef.uid).setValue("leader")
                                    FBRef.teamListRef.child(pinNum).child("teamContent").setValue(Teams(teamName, teamNotice, pinNum, downloadUrl))

                                    addMyTeamList(teamName, teamNotice, pinNum, downloadUrl)

                                    findNavController().navigate(R.id.action_editTeamFragment_to_groupsFragment) // groupFragment로 이동
                                } else {
                                    Toast.makeText(context, "다른 팀 코드를 입력해주세요. (중복 오류)", Toast.LENGTH_SHORT).show()
                                }
                            }
                            override fun onCancelled(error: DatabaseError) {}
                        })
                    }.addOnFailureListener {
                        Toast.makeText(context, "이미지 업로드 실패", Toast.LENGTH_SHORT).show() // URL을 가져오지 못한 경우의 처리
                    }
                }.addOnFailureListener {
                    Toast.makeText(context, "이미지 업로드 실패", Toast.LENGTH_SHORT).show() // 이미지 업로드 실패 시 처리
                }
            } else {
                Toast.makeText(context, "모든 정보를 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun addMyTeamList(teamName: String, teamNotice: String, pinNum: String, uri: String) { //myTeamList에 team 추가하는 함수
        FBRef.myTeamListRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val length = snapshot.childrenCount.toInt()
                FBRef.myTeamListRef.child(length.toString()).setValue(Teams(teamName, teamNotice, pinNum, uri))
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}