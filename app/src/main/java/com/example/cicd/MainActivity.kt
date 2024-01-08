package com.example.cicd

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.cicd.databinding.ActivityMainBinding
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCenter.start(
            application,
            "bb5fd39f-6ba3-4c46-9203-23757575ef28",
            Analytics::class.java,
            Crashes::class.java
        )
        AppCenter.setLogLevel(Log.VERBOSE)
        binding.calculateButton.setOnClickListener {
            try {
                val interestRate = binding.interestEditText.text.toString().toFloat()
                val currentAge = binding.ageEditText.text.toString().toInt()
                val retirementAge = binding.retirementEditText.text.toString().toInt()
                val monthlySavings = binding.monthlySavingsEditText.text.toString().toFloat()
                val currentSavings = binding.currentSavingsEditText.text.toString().toFloat()

                val properties: HashMap<String, String> = HashMap()
                properties["interest_rate"] = interestRate.toString()
                properties["current_age"] = currentAge.toString()
                properties["retirement_age"] = retirementAge.toString()
                properties["monthly_savings"] = monthlySavings.toString()
                properties["current_savings"] = currentSavings.toString()

                if (interestRate <= 0f) {
                    Analytics.trackEvent("wrong_interest_rate", properties)
                }

                if (retirementAge <= currentAge) {
                    Analytics.trackEvent("wrong_age", properties)
                }
            } catch (exception: Exception) {
                Analytics.trackEvent(exception.message)
            }

        }
    }
}