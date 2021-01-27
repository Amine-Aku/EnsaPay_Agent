package com.impression.ensapayagent.model

import android.content.Context
import android.widget.Toast

object Cst {

    var token = ""
    var authenticated = false

    fun toast(context: Context, msg: String) = Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

    fun login(token: String, context: Context) {
        this.token = token
        authenticated = true
    }

    fun logout(){
        authenticated = false
    }
}
