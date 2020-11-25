package com.luis.simplegymworkout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.luis.simplegymworkout.model.Exercise
import com.luis.simplegymworkout.service.TestRepositoryService
import kotlinx.android.synthetic.main.exercises_activity.*

class ExercisesActivity : AppCompatActivity(){

    private val repositoryService = TestRepositoryService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exercises_activity)

        val groupName = intent.getStringExtra("GROUP")
        Log.d("Debug", "${groupName}: Init Exercise activity")

        exerciseActivityDeleteGroup.setOnClickListener {
            UtilsUI.showAlert(this, R.string.confirmDeleteGroup, R.string.yes, R.string.not) {
                this.repositoryService.deleteGroup(groupName)
                UtilsUI.showToast(this, R.string.groupDeleted)
                val intent = Intent(this, MainActivity::class.java)
                this.startActivity(intent)
            }
        }
        exerciseActivityCreateExercise.setOnClickListener {
            val intent = Intent(this, CreateExerciseActivity::class.java).apply {
                putExtra("GROUP", groupName)
            }
            this.startActivity(intent)
        }
        this.loadGroup(groupName)
        this.loadExercises(groupName)
    }

    private fun loadGroup(group: String){
        exerciseActivityGroup.text = group
    }

    private fun loadExercises(group: String){
        val exercises = this.repositoryService.getExercises(group)
        for(exercise in exercises){
            val exerciseItem = LayoutInflater.from(this)
                .inflate(R.layout.exercise_item, null)
            val exerciseView = exerciseItem.findViewById<TextView>(R.id.exerciseItemExercise)
            exerciseView.text = exercise.name

            //Add button listeners
            exerciseItem.findViewById<ImageButton>(R.id.exerciseItemExerciseDelete)
                .setOnClickListener {
                    this.deleteExercise(group, exercise.name) }
            exerciseItem.findViewById<ImageButton>(R.id.exerciseItemExerciseEdit)
                .setOnClickListener {
                    this.editExercise(group, exercise.name) }

            val repetitionView = exerciseItem.findViewById<LinearLayout>(R.id.exerciseItemRepetitions)
            loadRepetitions(repetitionView, exercise)
            //Add exercise to group
            exerciseActivityExercises.addView(exerciseItem)
        }
    }

    private fun loadRepetitions(repetitionView : LinearLayout, exercise : Exercise) {
        for(repetition in exercise.repetitions!!) {
            val repetitionItem = LayoutInflater.from(this)
                .inflate(R.layout.repetition_item, null)
            val weightView = repetitionItem.findViewById<TextView>(R.id.repetitionItemWeight)
            weightView.text = repetition.weight.toString()
            val repView = repetitionItem.findViewById<TextView>(R.id.repetitionItemRepetition)
            repView.text = repetition.repetition.toString()
            //Add repetition to exercise
            repetitionView.addView(repetitionItem)
        }
    }

    private fun deleteExercise(groupName: String, exerciseName: String)
    {
        this.repositoryService.deleteExercise(groupName, exerciseName)
        UtilsUI.showAlert(this, R.string.confirmDeleteExercise, R.string.yes, R.string.not){
            finish()
            startActivity(intent)
        }
    }

    private fun editExercise(groupName: String, exerciseName: String)
    {
        val intent = Intent(this, EditExerciseActivity::class.java).apply {
            this.putExtra("GROUP", groupName)
            this.putExtra("EXERCISE", exerciseName)
        }
        this.startActivity(intent)
    }
}