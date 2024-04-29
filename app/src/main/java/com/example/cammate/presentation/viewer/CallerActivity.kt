package com.example.cammate.presentation.viewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.cammate.databinding.ActivityCallerBinding

class CallerActivity : AppCompatActivity() {
    lateinit var binding: ActivityCallerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 화면 꺼지지 않도록
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        //
    }
}