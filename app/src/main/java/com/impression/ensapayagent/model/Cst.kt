package com.impression.ensapayagent.model

import android.content.Context
import android.widget.Toast

object Cst {

    val USER_PREFS = "EnsaPay Agent- user preferences"
    val AUTHENTICATED = "authenticated"
    val TOKEN = "token"

    var token: String? = ""
    var authenticated = false

    var currentUser: Agent? = null

    fun toast(context: Context, msg: String) = Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

    fun login(token: String, context: Context) {
        this.token = token
        authenticated = true
        saveData(context)
    }

    fun logout(context: Context){
        authenticated = false
        this.token = ""
        clearData(context)
    }

    fun saveData(context: Context){
        val sharedPreferences = context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putBoolean(AUTHENTICATED, authenticated)
        editor.putString(TOKEN, token)

        editor.apply()
    }

    fun loadData(context: Context): Boolean{
        val sharedPreferences = context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE)
        authenticated = sharedPreferences.getBoolean(AUTHENTICATED, false)

        if(authenticated){
            token = sharedPreferences.getString(TOKEN, null)
        }

        return authenticated
    }

     fun clearData(context: Context){
        val sharedPreferences = context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}
