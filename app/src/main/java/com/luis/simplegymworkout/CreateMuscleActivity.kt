package com.luis.simplegymworkout

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.luis.simplegymworkout.model.Muscle
import com.luis.simplegymworkout.service.MuscleServiceImp
import kotlinx.android.synthetic.main.create_muscle_activity.*

class CreateMuscleActivity : AppCompatActivity() {

    private var muscleName : String? = null
    private var muscleService : MuscleServiceImp? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_muscle_activity)

        this.muscleService = MuscleServiceImp(applicationContext)
        this.muscleName = intent.getStringExtra("MUSCLE")

        createMuscleActivitySave.setOnClickListener {
            this.save(muscleService!!);
        }
    }

    private fun save(muscleService: MuscleServiceImp){
        val name = createMuscleActivityGroupName.text.toString()
        if(name.isEmpty()) {
            UtilsUI.showToast(this, R.string.invalidName)
            return
        }
        if(muscleService.isMuscleExist(name)){
            UtilsUI.showToast(this, R.string.existNameMuscle)
            return
        }
        muscleService.saveMuscle(Muscle(name))
        UtilsUI.showToast(this, R.string.muscleSaved)
        returnToList()
    }

    override fun onBackPressed() {
        returnToList()
    }

    private fun returnToList(){
        this.startActivity(Intent(this, MainActivity::class.java))
        this.finish()
    }
}