package com.example.drsalam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.drsalam.databinding.ActivityDoctorBinding

class DoctorActivity : AppCompatActivity() {
    lateinit var binding : ActivityDoctorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initViews()
    }
    private fun initViews() {
        Hospital.setTestDate()
        var myDoctor = Hospital.doctorList[0]
        binding.textViewName.text = myDoctor.name
        binding.textViewField.text = myDoctor.field
        binding.textViewOnlineStatus.text = myDoctor.onlineStatus.toString()

        var cons1 = Hospital.consultancyList[0]
        var cons2 = Hospital.consultancyList[1]
        var cons3 = Hospital.consultancyList[2]
        binding.textViewConsultancy.text = " مشاوره تلفنی " + cons1.time + " دقیقه ای "
        binding.textViewConsultancyPrice.text = cons1.price.toString() +  " تومان "
        binding.textViewConsultancy2.text = " مشاوره تلفنی " + cons2.time + " دقیقه ای "
        binding.textViewConsultancyPrice2.text = cons2.price.toString() +  " تومان "
        binding.textViewConsultancy3.text = " مشاوره تلفنی " + cons3.time + " دقیقه ای "
        binding.textViewConsultancyPrice3.text = cons3.price.toString() +  " تومان "

        binding.llConsultancy.setOnClickListener {
            Toast.makeText(this , "cunsultancy one is chosen" , Toast.LENGTH_SHORT).show()
            val intent = Intent(this , ConsultActivity::class.java)
            intent.putExtra("id" , myDoctor.id)
            startActivity(intent)
        }
    }

}