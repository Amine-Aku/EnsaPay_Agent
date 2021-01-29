package com.impression.ensapayagent

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.google.gson.JsonObject
import com.impression.ensapayagent.api.ApiClient
import com.impression.ensapayagent.model.Agent
import com.impression.ensapayagent.model.Cst
import com.impression.ensapayagent.services.AuthenticationRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"


    private lateinit var identifiantField: EditText
    private lateinit var mdpField: EditText
    private lateinit var feedbackView: TextView
    private var alreadyAuth = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        isAutenticated()
        init()



    }

    private fun isAutenticated() {
        alreadyAuth = Cst.loadData(this)
        if(alreadyAuth) {
            getUser()
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(0, 0)
            finish()
        }
        else Cst.clearData(this)
    }

    private fun init() {
        identifiantField = findViewById(R.id.field_identifiant)
        mdpField = findViewById(R.id.field_mdp)
        feedbackView = findViewById(R.id.login_feedback_view)
    }

    fun login(view: View) {
        val id = identifiantField.text.toString().trim()
        val mdp = mdpField.text.toString().trim()

        Log.d(TAG, "login: $id / $mdp")
        if(id.isEmpty()){
            identifiantField.setError("Champs vide")
            return
        }
        if(mdp.isEmpty()){
            mdpField.setError("Champs vide")
            return
        }

        val call = ApiClient.getAuthenticationService().authenticate(AuthenticationRequest(id, mdp))

        call.enqueue(object : Callback<Void>{
            override fun onFailure(call: Call<Void>, t: Throwable) {
//                val msg = getString(R.string.cnx_failed)
                val msg = t.message
                Log.e(TAG, "login: onFailure: $msg")
                feedbackView.text = msg
                Cst.toast(this@LoginActivity, msg!!)
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(!response.isSuccessful){
//                    val msg = getString(R.string.incorrect_credentials)
                    val msg = response.message()
                    Log.d(TAG, "login: onResponse: $msg")
                    feedbackView.text = msg
                    mdpField.text.clear()
                    Cst.toast(this@LoginActivity, msg)
                }
                else {
                    val token = response.headers().get("authorization")
//                    val jwt = response.body()!!.get("jwt").toString().removeSurrounding("\"")
                    Cst.login(token!!, this@LoginActivity)
                    Log.d(TAG, "login: onResponse: Login Successful: Token = $token")
                    setResult(Activity.RESULT_OK, null)
                    identifiantField.text.clear()
                    mdpField.text.clear()
                    getUser()
                    finish()
                }
            }
        })


    }

    private fun isFirstAuth(){
        val call = ApiClient.getAppuserServices().isFirstAuth(Cst.token)
        call.enqueue(object : Callback<Boolean>{
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                val msg = t.message
                Log.e(TAG, "login: isFirstAuth : onFailure: $msg")
                Cst.toast(this@LoginActivity, msg!!)
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(!response.isSuccessful){
                    val msg = response.message()
                    Log.d(TAG, "login: isFirstAuth: onResponse: $msg")
                    Cst.toast(this@LoginActivity, msg)
                }
                else {
                    val isFirstAuth = response.body()
//                    val jwt = response.body()!!.get("jwt").toString().removeSurrounding("\"")
                    Log.d(TAG, "login: onResponse: isFirstAuth Call Successful = $isFirstAuth")
                    setResult(Activity.RESULT_OK, null)
                    if(isFirstAuth!!) startActivity(Intent(this@LoginActivity, ChangerMdpActivity::class.java))
                    else startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
            }
        })
    }

    private fun getUser() {
        Log.d(TAG, "login: getUser: token = ${Cst.token}")
        val call = ApiClient.getAppuserServices().getUser(Cst.token)
        call.enqueue(object : Callback<Agent> {
            override fun onFailure(call: Call<Agent>, t: Throwable) {
                val msg = getString(R.string.cnx_failed)
                Log.e(TAG, "onFailure: $msg")
                Cst.toast(this@LoginActivity, msg)
            }

            override fun onResponse(call: Call<Agent>, response: Response<Agent>) {
                if(!response.isSuccessful){
                    val msg = response.message()
                    Log.d(TAG, "login: onResponse: $msg")
                    Cst.toast(this@LoginActivity, msg)
                }
                else {
                    val agent = response.body()
                    Log.d(TAG, "login: getUser: onResponse: $agent")
                    Cst.currentUser = agent
                    setResult(Activity.RESULT_OK, null)
                    if(!alreadyAuth){
                        if(agent!!.firstAuth!!) startActivity(Intent(this@LoginActivity, ChangerMdpActivity::class.java))
                        else {
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            Cst.toast(this@LoginActivity, "Bienvenue")
                        }
                        finish()
                    }
                }
            }
        })
    }
}
