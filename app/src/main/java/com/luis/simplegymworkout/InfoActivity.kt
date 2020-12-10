package com.luis.simplegymworkout

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info_activity)
    }

    override fun onBackPressed() {
        this.startActivity(Intent(this, MainActivity::class.java))
        this.finish()
    }
}