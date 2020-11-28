package com.luis.simplegymworkout

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.luis.simplegymworkout.model.Series
import com.luis.simplegymworkout.service.MuscleServiceImp
import kotlinx.android.synthetic.main.chrono_activity.*

class ChronoActivity : AppCompatActivity(){

    private var muscleName : String? = null
    private var exerciseId : Int? = null
    private var muscleService : MuscleServiceImp? = null
    lateinit var mAdView : AdView
    private var pasuseTime : Long = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chrono_activity)

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        this.muscleService = MuscleServiceImp(applicationContext)
        this.muscleName = intent.getStringExtra("MUSCLE")
        this.exerciseId = intent.getIntExtra("EXERCISE", 0)

        chronoActivityStart.isEnabled = true
        chronoActivityStop.isEnabled = false
        chronoActivityPause.isEnabled = false

        chronoActivityStart.setOnClickListener {
            chronoActivityChrono.base = (SystemClock.elapsedRealtime() + pasuseTime)
            chronoActivityChrono.start()
            chronoActivityStart.isEnabled = false
            chronoActivityStop.isEnabled = true
            chronoActivityPause.isEnabled = true
        }
        chronoActivityStop.setOnClickListener {
            this.pasuseTime = 0
            chronoActivityChrono.base = SystemClock.elapsedRealtime();
            chronoActivityChrono.stop()
            chronoActivityStart.isEnabled = true
            chronoActivityStop.isEnabled = false
            chronoActivityPause.isEnabled = false
        }
        chronoActivityPause.setOnClickListener {
            this.pasuseTime = chronoActivityChrono.base - SystemClock.elapsedRealtime()
            chronoActivityChrono.stop()
            chronoActivityStart.isEnabled = true
            chronoActivityStop.isEnabled = true
            chronoActivityPause.isEnabled = false
        }
        val exercise = this.muscleService!!.getExercise(this.exerciseId!!)

        chronoActivityExercise.text = exercise.name
        val series = this.muscleService!!.getSeries(this.exerciseId!!)
        this.loadSeries(series)
    }

    private fun loadSeries(series : List<Series>) {
        for(repetition in series) {
            val seriesItem = LayoutInflater.from(this)
                .inflate(R.layout.series_item, null)
            val weightView = seriesItem.findViewById<TextView>(R.id.seriesItemWeight)
            weightView.text = repetition.weight.toString()
            val repView = seriesItem.findViewById<TextView>(R.id.seriesItemRepetition)
            repView.text = repetition.repetition.toString()
            chronoActivitySeries.addView(seriesItem)
        }
    }

    override fun onBackPressed() {
        returnToList()
    }

    private fun returnToList(){
        val intent = Intent(this, ExercisesActivity::class.java).apply {
            putExtra("MUSCLE", muscleName)
        }
        this.startActivity(intent)
        this.finish()
    }
}