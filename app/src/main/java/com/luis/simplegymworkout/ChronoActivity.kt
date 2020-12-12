package com.luis.simplegymworkout

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.luis.simplegymworkout.model.Series
import com.luis.simplegymworkout.service.MuscleServiceImp
import com.luis.simplegymworkout.viewmodel.ChronoViewModel
import kotlinx.android.synthetic.main.chrono_activity.*

class ChronoActivity : AppCompatActivity(){

    private var muscleName : String? = null
    private var exerciseId : Int? = null
    private var muscleService : MuscleServiceImp? = null
    lateinit var mAdView : AdView
    lateinit var chronoViewModel : ChronoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chrono_activity)

        this.chronoViewModel = ViewModelProvider(this).get(ChronoViewModel::class.java)

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        this.muscleService = MuscleServiceImp(applicationContext)
        this.muscleName = intent.getStringExtra("MUSCLE")
        this.exerciseId = intent.getIntExtra("EXERCISE", 0)

        chronoActivityStart.isEnabled = true
        chronoActivityStop.isEnabled = false
        chronoActivityPause.isEnabled = false

        when(this.chronoViewModel.state){
            ChronoViewModel.State.RUN -> {
                chronoActivityChrono.base =SystemClock.elapsedRealtime() + this.chronoViewModel.currentTime
                chronoActivityChrono.start()
                this.start()
            }
            ChronoViewModel.State.PAUSE -> {
                chronoActivityChrono.base = SystemClock.elapsedRealtime() + this.chronoViewModel.currentTime
                this.pause()
            }
        }

        chronoActivityStart.setOnClickListener {
            this.chronoViewModel.state = ChronoViewModel.State.RUN
            chronoActivityChrono.base = (SystemClock.elapsedRealtime() + this.chronoViewModel.currentTime)
            chronoActivityChrono.start()
            this.start()
        }
        chronoActivityStop.setOnClickListener {
            this.chronoViewModel.state = ChronoViewModel.State.STOP
            this.chronoViewModel.currentTime = 0
            chronoActivityChrono.base = SystemClock.elapsedRealtime();
            chronoActivityChrono.stop()
            this.stop()
        }
        chronoActivityPause.setOnClickListener {
            this.chronoViewModel.state = ChronoViewModel.State.PAUSE
            this.chronoViewModel.currentTime = chronoActivityChrono.base - SystemClock.elapsedRealtime()
            chronoActivityChrono.stop()
            this.pause()
        }
        val exercise = this.muscleService!!.getExercise(this.exerciseId!!)

        chronoActivityExercise.text = exercise.name
        val series = this.muscleService!!.getSeries(this.exerciseId!!)
        this.loadSeries(series)
    }

    private fun start() {
        chronoActivityStart.isEnabled = false
        chronoActivityStop.isEnabled = true
        chronoActivityPause.isEnabled = true
    }

    private fun stop() {
        chronoActivityStart.isEnabled = true
        chronoActivityStop.isEnabled = false
        chronoActivityPause.isEnabled = false
    }

    private fun pause() {
        chronoActivityStart.isEnabled = true
        chronoActivityStop.isEnabled = true
        chronoActivityPause.isEnabled = false
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

    override fun onStop(){
        super.onStop()
        if(this.chronoViewModel.state == ChronoViewModel.State.RUN){
            this.chronoViewModel.currentTime = chronoActivityChrono.base - SystemClock.elapsedRealtime()
        }
    }

    private fun returnToList(){
        val intent = Intent(this, ExercisesActivity::class.java).apply {
            putExtra("MUSCLE", muscleName)
        }
        this.startActivity(intent)
        this.finish()
    }
}