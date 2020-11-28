package com.luis.simplegymworkout

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.luis.simplegymworkout.model.Muscle
import com.luis.simplegymworkout.service.IMuscleService
import com.luis.simplegymworkout.service.MuscleServiceImp
import kotlinx.android.synthetic.main.create_muscle_activity.*

class CreateMuscleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_muscle_activity)
        val muscleService = MuscleServiceImp.getInstance(applicationContext)
        createMuscleActivityCancel.setOnClickListener {
            this.finish()
        }
        createMuscleActivityOk.setOnClickListener {
            this.save(muscleService);
        }
    }

    private fun save(muscleService: IMuscleService){
        val name = createMuscleActivityGroupName.text.toString()
        if(name.isEmpty()){
            UtilsUI.showToast(this, R.string.invalidName)
        } else {
            muscleService.saveMuscle(Muscle(name))
            UtilsUI.showToast(this, R.string.muscleSaved)
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
            this.finish()
        }
    }
}