package com.example.ejemploviewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ejemploviewmodel.databinding.ActivityMainBinding

class myViewModel: ViewModel() {
    var campo: String = "dasda"
}

data class func(val a:Int, val b:String)

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main)


        val model = ViewModelProvider(this).get(myViewModel::class.java)
            // update UI
        model.campo="paso"

        val a=func(20,"gabriel")
        binding.funcion = a
        }

}

