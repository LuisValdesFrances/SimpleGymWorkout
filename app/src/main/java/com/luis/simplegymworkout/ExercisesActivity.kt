package com.luis.simplegymworkout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.luis.simplegymworkout.model.Series
import com.luis.simplegymworkout.service.MuscleServiceImp
import kotlinx.android.synthetic.main.exercises_activity.*

class ExercisesActivity : AppCompatActivity(){

    private var muscleName : String? = null
    private var muscleService : MuscleServiceImp? = null
    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exercises_activity)

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        this.muscleName = intent.getStringExtra("MUSCLE")
        this.muscleService = MuscleServiceImp(applicationContext)

        Log.d("Debug", "${this.muscleName}: Create Exercise activity")

        exerciseActivityDeleteMuscle.setOnClickListener {
            UtilsUI.showAlert(this, R.string.confirmDeleteMuscle, R.string.yes, R.string.not) {
                this.muscleService!!.deleteMuscle(this.muscleName!!)
                UtilsUI.showToast(this, R.string.muscleDeleted)

                val intent = Intent(this, MainActivity::class.java)
                this.startActivity(intent)
                this.finish()
            }
        }
        exerciseActivityCreateExercise.setOnClickListener {
            val intent = Intent(this, CreateExerciseActivity::class.java).apply {
                putExtra("MUSCLE", muscleName)
            }
            this.startActivity(intent)
            this.finish()
        }
        exerciseActivityMuscle.text = this.muscleName

        this.cleanExercises()
        this.loadExercises(this.muscleName!!, this.muscleService!!)
    }

    private fun cleanExercises(){
        exerciseActivityExercises.removeAllViews()
    }

    private fun loadExercises(muscleName: String, muscleService : MuscleServiceImp){
        val exercises = muscleService.getExercises(muscleName)
        for(exercise in exercises){
            val exerciseItem = LayoutInflater.from(this)
                .inflate(R.layout.exercise_item, null)
            val exerciseView = exerciseItem.findViewById<TextView>(R.id.exerciseItemExercise)
            exerciseView.text = exercise.name

            //Add button listeners
            exerciseItem.findViewById<ImageButton>(R.id.exerciseItemExerciseDelete)
                .setOnClickListener {
                    this.deleteExercise(exercise.id, muscleService) }
            exerciseItem.findViewById<ImageButton>(R.id.exerciseItemExerciseEdit)
                .setOnClickListener {
                    this.editExercise(muscleName, exercise.id) }
            exerciseItem.findViewById<ImageButton>(R.id.exerciseItemExerciseChrono)
                .setOnClickListener {
                    this.chrono(muscleName, exercise.id) }

            val repetitionView = exerciseItem.findViewById<LinearLayout>(R.id.exerciseItemSeries)
            this.loadSeries(repetitionView, muscleService.getSeries(exercise.id))
            //Add exercise to group
            exerciseActivityExercises.addView(exerciseItem)
        }
    }

    private fun loadSeries(repetitionView : LinearLayout, series : List<Series>) {
        for(repetition in series) {
            val repetitionItem = LayoutInflater.from(this)
                .inflate(R.layout.series_item, null)
            val weightView = repetitionItem.findViewById<TextView>(R.id.seriesItemWeight)
            weightView.text = repetition.weight.toString()
            val repView = repetitionItem.findViewById<TextView>(R.id.seriesItemRepetition)
            repView.text = repetition.repetition.toString()
            //Add repetition to exercise
            repetitionView.addView(repetitionItem)
        }
    }

    private fun deleteExercise(exerciseId : Int, muscleService : MuscleServiceImp){
       UtilsUI.showAlert(this, R.string.confirmDeleteExercise, R.string.yes, R.string.not){
           muscleService.deleteExercise(exerciseId)
           this.cleanExercises()
           this.loadExercises(this.muscleName!!, this.muscleService!!)
        }
    }

    private fun editExercise(muscleName: String, exerciseId : Int)
    {
        val intent = Intent(this, EditExerciseActivity::class.java).apply {
            this.putExtra("EXERCISE", exerciseId)
            this.putExtra("MUSCLE", muscleName)
        }
        this.startActivity(intent)
        this.finish()
    }

    private fun chrono(muscleName: String, exerciseId : Int){
        val intent = Intent(this, ChronoActivity::class.java).apply {
            this.putExtra("EXERCISE", exerciseId)
            this.putExtra("MUSCLE", muscleName)
        }
        this.startActivity(intent)
        this.finish()
    }

    override fun onBackPressed() {
        this.startActivity(Intent(this, MainActivity::class.java))
        this.finish()
    }
}