package com.luis.simplegymworkout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.luis.simplegymworkout.service.TestRepositoryService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val repositoryService = TestRepositoryService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("Debug", "Init APP")
        activityMainCreateGroup.setOnClickListener {
            val intent = Intent(this, CreateGroupActivity::class.java)
            this.startActivity(intent)
        }
        this.loadGroups()
    }

    private fun loadGroups(){
        val groups = this.repositoryService.getGroups()
        for(group in groups){
            //Cargo el xml que contiene el componente textView
            var linearLayout = LayoutInflater.from(this)
                .inflate(R.layout.group_item, null, false)
            var textView = linearLayout.findViewById<TextView>(R.id.groupItemView)
            textView.text = group.name
            textView.setOnClickListener {
                val intent = Intent(this, ExercisesActivity::class.java).apply {
                    putExtra("GROUP", group.name)
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