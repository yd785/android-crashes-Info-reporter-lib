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
        binding.caughtExceptionBtn.setOnClickListener(View.OnClickListener {
            try {
                val list: MutableList<String>? = null
                list!!.add("new element")
            } catch (ex: Exception) {
                CrashReporterMain.catchExceptionInfo(ex)
            }
        })

        // event of uncaught null pointer exception test
        binding.uncaghtExceptionBtn.setOnClickListener(View.OnClickListener {
            val list: MutableList<String>? = null
            list!!.add("new element")
        })

        // test send report of crashes
//        binding.sendReportBtn.setOnClickListener(View.OnClickListener {
//            CrashReporterMain.testSendCrashesReport()
//        })
    }


}