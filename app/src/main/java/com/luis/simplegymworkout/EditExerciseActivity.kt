package com.luis.simplegymworkout

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.luis.simplegymworkout.model.Exercise
import com.luis.simplegymworkout.model.Repetition
import com.luis.simplegymworkout.service.TestRepositoryService
import kotlinx.android.synthetic.main.create_exercise_activity.*
import java.util.*

class EditExerciseActivity : AppCompatActivity(){

    private val repositoryService = TestRepositoryService()
    private var exercise = Exercise("", "", ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_exercise_activity)
        val groupName = intent.getStringExtra("GROUP")
        val exerciseName = intent.getStringExtra("EXERCISE")
        createExerciseActivityLabel.text = this.resources.getString(R.string.editExercise)
        createExerciseActivityExerciseName.setText(exerciseName)

        this.exercise = this.getExercise(groupName, exerciseName)!!

        createExerciseAddRepetition.setOnClickListener {
            addRepetition()
        }
        createExerciseDeleteRepetition.setOnClickListener {
            this.deleteRepetition()
        }
        createExerciseActivityOk.setOnClickListener {
            this.save(groupName)
        }
        createExerciseActivityCancel.setOnClickListener {
            val intent = Intent(this, ExercisesActivity::class.java).apply {
                putExtra("GROUP", groupName)
            }
            this.startActivity(intent)
        }
    }

    private fun getExercise(groupName: String, exerciseName: String) : Exercise? {
        val exercises = repositoryService.getExercises(groupName)
        exercises.forEach {
            if(it.name == exerciseName){
                return it
            }
        }
        return null
    }

    private fun addRepetition(){
        val repetitionItem = LayoutInflater.from(this)
                .inflate(R.layout.repetition_item_edit, null)
        createExerciseRepetitions.addView(repetitionItem)
        createExerciseDeleteRepetition.isEnabled = true
    }

    private fun deleteRepetition(){
        createExerciseRepetitions.removeView(
                createExerciseRepetitions.getChildAt(createExerciseRepetitions.childCount-1)
        )
        createExerciseDeleteRepetition.isEnabled = createExerciseRepetitions.childCount > 0
    }

    private fun save(groupName : String){
        val name = createExerciseActivityExerciseName.text.toString()
        if(name.isEmpty()){
            UtilsUI.showToast(this, R.string.invalidName)
        } else {
            val exercise = Exercise(groupName, name, ArrayList())
            for(i in 0 until createExerciseRepetitions.childCount){
                val repetitionView = createExerciseRepetitions.getChildAt(i)
                val rep =
                        if(!repetitionView.findViewById<EditText>(R.id.repetitionItemEditRepetition).text.isNullOrEmpty())
                            repetitionView.findViewById<EditText>(R.id.repetitionItemEditRepetition).text.toString()
                        else "0"
                val weight =
                        if(!repetitionView.findViewById<EditText>(R.id.repetitionItemEditWeight).text.isNullOrEmpty())
                            repetitionView.findViewById<EditText>(R.id.repetitionItemEditWeight).text.toString()
                        else "0.0"
                exercise.repetitions.add(Repetition(exercise.name, Integer.parseInt(rep), weight.toDouble()))
            }
            this.repositoryService.saveExercise(exercise)
            UtilsUI.showToast(this, R.string.exerciseSaved)
            val intent = Intent(this, ExercisesActivity::class.java).apply {
                putExtra("GROUP", groupName)
            }
            this.startActivity(intent)
        }
    }
}