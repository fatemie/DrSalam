package com.example.drsalam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.drsalam.databinding.ActivityCheckPatientBinding

class CheckPatientActivity : AppCompatActivity() {
    lateinit var binding : ActivityCheckPatientBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckPatientBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initViews()
    }

    fun initViews(){
        binding.buttonOk.setOnClickListener{
            val isOk = binding.checkBox1.isChecked
            val marriedStatus = when(binding.marriedStatus.checkedRadioButtonId) {
                binding.married.id -> "متاهل"
                binding.single.id -> "مجرد"
                else -> "not choose!"
            }
            val resultIntent = Intent()
            resultIntent.putExtra("isChecked",isOk)
            resultIntent.putExtra("marriedStatus", marriedStatus)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}