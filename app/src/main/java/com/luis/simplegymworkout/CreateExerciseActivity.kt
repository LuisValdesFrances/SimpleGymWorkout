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

class CreateExerciseActivity : AppCompatActivity(){

    private var muscleName : String? = null
    private var muscleService : MuscleServiceImp? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_exercise_activity)

        this.muscleService = MuscleServiceImp(applicationContext)
        this.muscleName = intent.getStringExtra("MUSCLE")

        createExerciseActivityLabel.text = this.resources.getString(R.string.createExercise)

        createExerciseAddRepetition.setOnClickListener {
            addRepetition()
        }
        createExerciseDeleteRepetition.setOnClickListener {
            this.deleteRepetition()
        }
        createExerciseActivityOk.setOnClickListener {
            this.save(muscleName!!, muscleService!!)
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

    private fun save(muscleName : String, muscleService : MuscleServiceImp){
        val name = createExerciseActivityExerciseName.text.toString()

        if(name.isEmpty()){
            UtilsUI.showToast(this, R.string.invalidName)
            return
        }
        if(muscleService.isExerciseExist(muscleName, name)){
            UtilsUI.showToast(this, R.string.existNameExercise)
            return
        }

        muscleService.saveExercise(Exercise(0, muscleName, name))
        val exercise = muscleService.getExerciseByName(muscleName, name)
        exercise?.let {
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
                muscleService.saveSeries(Series(
                        0, it.id, Integer.parseInt(rep), weight.toDouble()))
            }
            UtilsUI.showToast(this, R.string.exerciseSaved)
            this.returnToList()
            return
        }
        UtilsUI.showToast(this, R.string.errorSave)
    }

    override fun onBackPressed() {
        this.returnToList()
    }

    private fun returnToList(){
        val intent = Intent(this, ExercisesActivity::class.java).apply {
            putExtra("MUSCLE", muscleName)
        }
        this.startActivity(intent)
        this.finish()
    }
}