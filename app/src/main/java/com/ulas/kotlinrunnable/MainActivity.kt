package com.ulas.kotlinrunnable

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.ulas.kotlinrunnable.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var number = 0
    var highNumber = 0

    var runnable : Runnable = Runnable {}
    var handler : Handler = Handler(Looper.getMainLooper())
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        highNumber = sharedPreferences.getInt("highestTime", 0)
        binding.textHigh.text = "Highest Time : $highNumber"
    }



    fun start(view: View){

        number = 0
        runnable = object : Runnable {
            override fun run() {
                number = number +1
                binding.textView.text = "Time : ${number}"
                if (number > highNumber) {
                    highNumber = number
                    binding.textHigh.text = "Highest Time : $highNumber"
                    saveHighestTime()
                }
                handler.postDelayed(runnable,1000)
            }
        }

        handler.post(runnable)
        binding.buttonStart.isEnabled = false

    }

    fun stop (view: View){

        binding.buttonStart.isEnabled= true
        binding.textLast.text = "Last Time : ${number}"
        number = 0
        binding.textView.text = "Time : 0"
        handler.removeCallbacks(runnable)

    }

    private fun saveHighestTime() {
        val editor = sharedPreferences.edit()
        editor.putInt("highestTime", highNumber)
        editor.apply()
    }
}