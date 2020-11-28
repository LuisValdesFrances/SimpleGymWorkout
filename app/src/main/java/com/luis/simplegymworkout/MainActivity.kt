package com.luis.simplegymworkout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.luis.simplegymworkout.service.IMuscleService
import com.luis.simplegymworkout.service.MuscleServiceImp
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val muscleService = MuscleServiceImp.getInstance(applicationContext)
        Log.d("Debug", "Init APP")
        activityMainCreateGroup.setOnClickListener {
            val intent = Intent(this, CreateMuscleActivity::class.java)
            this.startActivity(intent)
        }
        this.loadGroups(muscleService)
    }

    private fun loadGroups(muscleService : IMuscleService){
        val muscles = muscleService.getMuscles()
        for(muscle in muscles){
            //Cargo el xml que contiene el componente textView
            var linearLayout = LayoutInflater.from(this)
                .inflate(R.layout.muscle_item, null, false)
            var textView = linearLayout.findViewById<TextView>(R.id.muscleItemView)
            textView.text = muscle.name
            textView.setOnClickListener {
                val intent = Intent(this, ExercisesActivity::class.java).apply {
                    putExtra("MUSCLE", muscle.name)
                }
                this.startActivity(intent)
            }
            if(textView.parent != null)
            {
                (textView.parent as ViewGroup).removeView(textView)
            }
            activityMainGroups.addView(textView)
        }
    }
}