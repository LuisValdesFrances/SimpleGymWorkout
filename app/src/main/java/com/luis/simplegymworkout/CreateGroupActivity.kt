package com.luis.simplegymworkout

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.luis.simplegymworkout.model.Exercise
import com.luis.simplegymworkout.model.Group
import com.luis.simplegymworkout.service.TestRepositoryService
import kotlinx.android.synthetic.main.create_group_activity.*

class CreateGroupActivity : AppCompatActivity() {

    private val repositoryService = TestRepositoryService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_group_activity)
        createGroupActivityCancel.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
        }
        createGroupActivityOk.setOnClickListener {
            this.save();
        }
    }

    private fun save(){
        val name = createGroupActivityGroupName.text.toString()
        if(name.isEmpty()){
            UtilsUI.showToast(this, R.string.invalidName)
        } else {
            this.repositoryService.saveGroup(Group(name, ArrayList<Exercise>()))
            UtilsUI.showToast(this, R.string.groupSaved)
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
        }
    }
}