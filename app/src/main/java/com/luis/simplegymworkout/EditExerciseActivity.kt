package com.luis.simplegymworkout

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.luis.simplegymworkout.model.Exercise
import com.luis.simplegymworkout.model.Series
import com.luis.simplegymworkout.service.MuscleServiceImp
import kotlinx.android.synthetic.main.create_exercise_activity.*

class EditExerciseActivity : AppCompatActivity(){

    private var muscleName : String? = null
    private var exerciseId : Int? = null
    private var muscleService : MuscleServiceImp? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_exercise_activity)

        this.muscleService = MuscleServiceImp(applicationContext)
        this.muscleName = intent.getStringExtra("MUSCLE")
        this.exerciseId = intent.getIntExtra("EXERCISE", 0)

        val exercise = this.muscleService!!.getExercise(this.exerciseId!!)
        val series = this.muscleService!!.getSeries(this.exerciseId!!)

        createExerciseActivityLabel.text = this.resources.getString(R.string.editExercise)
        createExerciseActivityExerciseName.setText(exercise.name)

        this.setRepetitions(series)
        createExerciseDeleteRepetition.isEnabled = series.size > 0

        createExerciseAddRepetition.setOnClickListener {
            addRepetition()
        }
        createExerciseDeleteRepetition.setOnClickListener {
            this.deleteRepetition()
        }
        createExerciseActivityOk.setOnClickListener {
            this.save(muscleName!!, exercise, muscleService!!)
        }
    }

    private fun setRepetitions(series : List<Series>){
        for(repetition in series){
            val repetitionItem = LayoutInflater.from(this)
                    .inflate(R.layout.series_item_edit, null)
            repetitionItem.findViewById<EditText>(R.id.seriesItemEditWeight).setText(repetition.weight.toString())
            repetitionItem.findViewById<EditText>(R.id.seriesItemEditRepetition).setText(repetition.repetition.toString())
            createExerciseRepetitions.addView(repetitionItem)
        }
    }

    private fun addRepetition(){
        val repetitionItem = LayoutInflater.from(this)
                .inflate(R.layout.series_item_edit, null)
        createExerciseRepetitions.addView(repetitionItem)
        createExerciseDeleteRepetition.isEnabled = true
    }

    private fun deleteRepetition(){
        createExerciseRepetitions.removeView(
                createExerciseRepetitions.getChildAt(createExerciseRepetitions.childCount-1)
        )
        createExerciseDeleteRepetition.isEnabled = createExerciseRepetitions.childCount > 0
    }

    private fun save(muscleName: String, exercise : Exercise, muscleService : MuscleServiceImp){
        val exerciseName = createExerciseActivityExerciseName.text.toString()
        if(exerciseName.isEmpty()){
            UtilsUI.showToast(this, R.string.invalidName)
        }
        if(muscleService.isExerciseExist(muscleName, exercise.id, exerciseName)){
            UtilsUI.showToast(this, R.string.existNameExercise)
            return
        }

        exercise.name = exerciseName
        muscleService.updateExercise(exercise)

        //Delete all series
        muscleService.deleteSeriesFromExercise(exercise.id)

        for(i in 0 until createExerciseRepetitions.childCount){
            val repetitionView = createExerciseRepetitions.getChildAt(i)
            val rep =
                    if(!repetitionView.findViewById<EditText>(R.id.seriesItemEditRepetition).text.isNullOrEmpty())
                        repetitionView.findViewById<EditText>(R.id.seriesItemEditRepetition).text.toString()
                    else "0"
            val weight =
                    if(!repetitionView.findViewById<EditText>(R.id.seriesItemEditWeight).text.isNullOrEmpty())
                        repetitionView.findViewById<EditText>(R.id.seriesItemEditWeight).text.toString()
                    else "0.0"
            muscleService.saveSeries(Series(0, exercise.id, Integer.parseInt(rep), weight.toDouble()))
        }
        UtilsUI.showToast(this, R.string.exerciseSaved)
        returnToList()
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