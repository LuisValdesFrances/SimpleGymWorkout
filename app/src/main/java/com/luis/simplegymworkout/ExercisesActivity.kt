package com.luis.simplegymworkout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.luis.simplegymworkout.model.Series
import com.luis.simplegymworkout.service.IMuscleService
import com.luis.simplegymworkout.service.MuscleServiceImp
import kotlinx.android.synthetic.main.exercises_activity.*

class ExercisesActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exercises_activity)
    }

    override fun onResume(){
        super.onResume()
        val muscleService = MuscleServiceImp.getInstance(applicationContext)
        val muscleName = intent.getStringExtra("MUSCLE")
        Log.d("Debug", "${muscleName}: Init Exercise activity")

        exerciseActivityDeleteMuscle.setOnClickListener {
            UtilsUI.showAlert(this, R.string.confirmDeleteMuscle, R.string.yes, R.string.not) {

                //TODO: Comprobar si se borra en cascada
                muscleService.deleteMuscle(muscleName)

                UtilsUI.showToast(this, R.string.muscleDeleted)
                val intent = Intent(this, MainActivity::class.java)
                this.startActivity(intent)
            }
        }
        exerciseActivityCreateExercise.setOnClickListener {
            val intent = Intent(this, CreateExerciseActivity::class.java).apply {
                putExtra("MUSCLE", muscleName)
            }
            this.startActivity(intent)
        }
        exerciseActivityMuscle.text = muscleName
        this.loadExercises(muscleName, muscleService)
    }

    private fun loadExercises(muscleName: String, muscleService : IMuscleService){
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

    private fun deleteExercise(exerciseId : Int, muscleService : IMuscleService)
    {
        muscleService.deleteExercise(exerciseId)
        //TODO: Si se borra en cascada, no es necesario
        muscleService.deleteSeriesFromExercise(exerciseId)
        UtilsUI.showAlert(this, R.string.confirmDeleteExercise, R.string.yes, R.string.not){
            finish()
            startActivity(intent)
        }
    }

    private fun editExercise(muscleName: String, exerciseId : Int)
    {
        val intent = Intent(this, EditExerciseActivity::class.java).apply {
            this.putExtra("EXERCISE", exerciseId)
            this.putExtra("MUSCLE", muscleName)
        }
        this.startActivity(intent)
    }
}