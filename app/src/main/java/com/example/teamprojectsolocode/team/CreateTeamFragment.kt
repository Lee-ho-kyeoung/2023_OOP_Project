package com.example.teamprojectsolocode.team

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
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.teamprojectsolocode.R
import com.example.teamprojectsolocode.databinding.FragmentCreateTeamBinding
import com.example.teamprojectsolocode.firebasedb.FBRef
import com.example.teamprojectsolocode.viewmodel.CreateTeamViewModel

class CreateTeamFragment : Fragment() {
    private lateinit var binding: FragmentCreateTeamBinding //binding
    private val viewModel: CreateTeamViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateTeamBinding.inflate(inflater) // binding
        // 이미지 가져오기
        val imagePicker = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) { // 이미지 선택 액티비티가 성공적으로 실행되고, 사용자가 이미지를 선택한 경우에만 실행됩니다
                val data: Intent? = result.data
                val imageUri = data?.data // 선택된 이미지의 Uri

                binding.selectedImg.setImageURI(imageUri) // 선택된 이미지를 ImageView에 설정
                binding.selectedImg.tag = imageUri        //  tag로 이미지 uri 저장
            }
        }

        binding.btnSetImage.setOnClickListener {  // 사진 선택 버튼을 누르면
            val intent = Intent(Intent.ACTION_PICK)      // intent 생성
            intent.type = "image/*"

            imagePicker.launch(intent)  //imagePicker로 지정된 ActivityResultLauncher에 전달하여 실행
        }

        binding.btnCreateTeam.setOnClickListener { // 팀 생성 버튼을 누르면
            val teamName = binding.txtInputTeamName.text.toString()
            val pinNum = binding.txtInputTeamCode.text.toString()
            val teamNotice = binding.txtInputTeamNotice.text.toString()
            var imageUri = binding.selectedImg.tag as? Uri // 이미지 Uri 가져오기
            val nickname = binding.txtNickName.text.toString()

            if (imageUri == null) { //이미지가 선택 안되면 기본 이미지로 설정
                imageUri = Uri.parse("android.resource://com.example.teamprojectsolocode/drawable/default_img")
            }

            if (teamName.isNotBlank() && teamNotice.isNotBlank() && pinNum.isNotBlank() && nickname.isNotBlank()) { // 모든 입력란이 비어있지 않을 때
                val uploadTask = imageUri?.let { FBRef.storageRef.putFile(it) } // null이 아닐때만 이미지 업로드

                uploadTask?.addOnSuccessListener { taskSnapshot ->
                    // 이미지 업로드 성공 시 URL 획득
                    FBRef.storageRef.downloadUrl.addOnSuccessListener { uri ->
                        val downloadUrl = uri.toString()

                        viewModel.existCheckTeamList(pinNum) {check ->
                            if(!check) { //중복되지 않으면 팀 생성
                                viewModel.createTeam(Teams(teamName, teamNotice, pinNum, downloadUrl), nickname)
                                findNavController().navigate(R.id.action_createTeamFragment_to_groupsFragment) // groupFragment로 이동
                            } else {     //중복되면 오류 메시지
                                Toast.makeText(context, "다른 팀 코드를 입력해주세요. (중복 오류)", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }.addOnFailureListener {
                        Toast.makeText(context, "이미지 업로드 실패", Toast.LENGTH_SHORT).show() // URL을 가져오지 못한 경우의 처리
                    }
                }?.addOnFailureListener {
                    Toast.makeText(context, "이미지 업로드 실패", Toast.LENGTH_SHORT).show() // 이미지 업로드 실패 시 처리
                }
            } else {
                Toast.makeText(context, "모든 정보를 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}