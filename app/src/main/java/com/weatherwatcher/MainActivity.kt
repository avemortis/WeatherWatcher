package com.weatherwatcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentFactory

class MainActivity : AppCompatActivity() {
    override fun onDestroy() {
        factory = supportFragmentManager.fragmentFactory
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = factory
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    companion object {
        private var factory = FragmentFactory()
    }
}