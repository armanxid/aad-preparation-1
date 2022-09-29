package com.dicoding.habitapp.ui.countdown

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import com.dicoding.habitapp.R
import com.dicoding.habitapp.data.Habit
import com.dicoding.habitapp.notification.NotificationWorker
import com.dicoding.habitapp.utils.*

class CountDownActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_down)
        supportActionBar?.title = "Count Down"

        val habit = intent.getParcelableExtra<Habit>(HABIT) as Habit

        findViewById<TextView>(R.id.tv_count_down_title).text = habit.title

        val viewModel = ViewModelProvider(this).get(CountDownViewModel::class.java)

        val workManager = WorkManager.getInstance(this)
        val data = Data.Builder()
            .putInt(HABIT_ID, habit.id)
            .putString(HABIT_TITLE, habit.title)
            .putLong(HABIT_MINUTE_FOCUS, habit.minutesFocus)
            .putString(HABIT_START_TIME, habit.startTime)
            .putString(HABIT_PRIORITY, habit.priorityLevel)
            .build()
        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInputData(data)
            .build()
        //TODO 10 : Set initial time and observe current time. Update button state when countdown is finished
        viewModel.setInitialTime(habit.minutesFocus)
        viewModel.currentTimeString.observe(this, {
            findViewById<TextView>(R.id.tv_count_down).text = it
        })

        viewModel.eventCountDownFinish.observe(this, { finish ->
            updateButtonState(finish)
            if (finish) {
                workManager.enqueueUniqueWork(
                    NOTIF_UNIQUE_WORK,
                    ExistingWorkPolicy.REPLACE,
                    workRequest
                )
                updateButtonState(false)
            }
        })

        //TODO 13 : Start and cancel One Time Request WorkManager to notify when time is up.

        findViewById<Button>(R.id.btn_start).setOnClickListener {
            viewModel.startTimer()
            updateButtonState(true)
        }

        findViewById<Button>(R.id.btn_stop).setOnClickListener {
            viewModel.stopTimer()
            updateButtonState(false)
        }
    }

    private fun updateButtonState(isRunning: Boolean) {
        Log.d("state", isRunning.toString())
        if(isRunning.equals(false)){
            findViewById<Button>(R.id.btn_start).isEnabled = true
            findViewById<Button>(R.id.btn_stop).isEnabled = false
        } else if (isRunning.equals(true)){
            findViewById<Button>(R.id.btn_start).isEnabled = false
            findViewById<Button>(R.id.btn_stop).isEnabled = true
        }

    }
}