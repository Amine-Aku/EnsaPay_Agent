package com.impression.ensapayagent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ChangerMdpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changer_mdp)
    }

    fun valider(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }
}
