package com.example.drsalam

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.drsalam.databinding.ActivityConsultBinding

class ConsultActivity : AppCompatActivity() {
    lateinit var binding : ActivityConsultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initViews()

    }


    private fun initViews() {
        lateinit var myDoctor: Doctor
        var id = intent.getIntExtra("id" , -1)
        if (id == -1){
            binding.textViewDoctorCalls.text = "ٔدکتر شما پیدا نشد"
        }else {
            myDoctor = Hospital.getDoctor(id)!!
            if(myDoctor?.onlineStatus == OnlineStatus.Online){
                binding.textViewDoctorCalls.text = "دکتر ${myDoctor?.name} با شما تماس خواهد گرفت"
            }else{
                binding.textViewDoctorCalls.text = "دکتر ${myDoctor?.name}آفلاین است"
                binding.buttonDrCallMe.isEnabled = false
            }
        }
        binding.buttonDrCallMe.setOnClickListener {
            getUserNameAndTel()
            openCheckActivity()
        }

        binding.buttonMeCallDr.setOnClickListener {
            getUserNameAndTel()
            openDial(myDoctor)
        }
        if (!getFromShared_name().isNullOrEmpty() ){
            binding.editTextName.visibility = View.GONE
        }
        if (!getFromShared_tel().isNullOrEmpty() ){
            binding.editTextTel.visibility = View.GONE
        }
    }

    private fun openDial(doctor: Doctor) {
        val drTel = doctor.phone
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:$drTel")
        startActivity(callIntent)
    }

    private fun openCheckActivity() {
        val intent = Intent(this , CheckPatientActivity::class.java)
        startForResult.launch(intent)
    }

    private fun getUserNameAndTel() {
        var username = binding.editTextName.text.toString()
        var userTel = binding.editTextTel.text.toString()
        saveInShared(username , userTel)
    }

    private fun saveInShared(username: String, userTel: String) {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("kotlinSharedPreference", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("name" , username)
        editor.putString("tel" , userTel)
        editor.apply()
    }
    private fun getFromShared_name() : String?{
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("kotlinSharedPreference", Context.MODE_PRIVATE)
        var name = sharedPreferences.getString("name" , "")
        return name
    }
    private fun getFromShared_tel() : String?{
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("kotlinSharedPreference", Context.MODE_PRIVATE)
        var tel = sharedPreferences.getString("tel" , "")
        return tel
    }
    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            val isOk =  intent?.getBooleanExtra("isChecked", true)
            val marriedStatus = intent?.getStringExtra("marriedStatus")
            Toast.makeText(this ,"isOk value is : " + isOk , Toast.LENGTH_SHORT).show()
            Toast.makeText(this ,marriedStatus , Toast.LENGTH_SHORT).show()
            isOk?.let{
                if(it)
                    Toast.makeText(this , "الان دکتر بهت زنگ می زنه" , Toast.LENGTH_SHORT).show()

            }
        }
    }

}