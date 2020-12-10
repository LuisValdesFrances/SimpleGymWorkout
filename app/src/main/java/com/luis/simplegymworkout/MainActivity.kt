package com.luis.simplegymworkout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
import com.luis.simplegymworkout.service.MuscleServiceImp
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        MobileAds.initialize(this) {}
        val muscleService = MuscleServiceImp(applicationContext)
        Log.d("Debug", "Init APP")
        activityMainCreateGroup.setOnClickListener {
            val intent = Intent(this, CreateMuscleActivity::class.java)
            this.startActivity(intent)
            this.finish()
        }
        activityMainInfo.setOnClickListener {
            val intent = Intent(this, InfoActivity::class.java)
            this.startActivity(intent)
            this.finish()
        }
        this.loadGroups(muscleService)
    }

    private fun loadGroups(muscleService : MuscleServiceImp){
        val muscles = muscleService.getMuscles()
        for(muscle in muscles){
            //Cargo el xml que contiene el componente textView
            var linearLayout = LayoutInflater.from(this)
                .inflate(R.layout.muscle_item, null, false)
            var textView = linearLayout.findViewById<Button>(R.id.muscleItemView)
            textView.text = muscle.name
            textView.setOnClickListener {
                val intent = Intent(this, ExercisesActivity::class.java).apply {
                    putExtra("MUSCLE", muscle.name)
                }
                this.startActivity(intent)
                this.finish()
            }
            if(textView.parent != null)
            {
                (textView.parent as ViewGroup).removeView(textView)
            }
            activityMainGroups.addView(textView)
        }
    }

    override fun onBackPressed() {
        this.finish()
    }
}