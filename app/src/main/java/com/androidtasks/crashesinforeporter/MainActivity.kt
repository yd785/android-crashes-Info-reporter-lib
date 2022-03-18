package com.androidtasks.crashesinforeporter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.androidtasks.crashesinfo_reporter.CrashReporterMain
import com.androidtasks.crashesinforeporter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // event of caught null pointer exception test
        binding.caughtExceptionBtn.setOnClickListener {
            try {
                val list: MutableList<String>? = null
                list!!.add("new element")
            } catch (ex: Exception) {
                CrashReporterMain.catchExceptionInfo(ex)
            }
        }

        // event of uncaught null pointer exception test
        binding.uncaghtExceptionBtn.setOnClickListener {
            val list: MutableList<String>? = null
            list!!.add("new element")
        }
    }
}