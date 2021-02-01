package com.impression.ensapayagent.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.impression.ensapayagent.R
import com.impression.ensapayagent.api.ApiClient
import com.impression.ensapayagent.model.Cst
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangerMdpActivity : AppCompatActivity() {

    private val TAG = "ChangerMdpActivity"
    private lateinit var nouveauMdpField: EditText
    private lateinit var confirmMdpField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changer_mdp)

        init()
    }

    private fun init() {
        nouveauMdpField = findViewById(R.id.field_nouveau_mdp)
        confirmMdpField = findViewById(R.id.field_confirm_mdp)

    }

    private fun checkMdp(): Boolean = nouveauMdpField.text.toString().trim() == confirmMdpField.text.toString().trim()

    fun valider(view: View) {
        if(!checkMdp()) confirmMdpField.error = "Mot de passe non identique"
        else{
            val call = ApiClient.getAppuserServices().changeMdp(Cst.token, confirmMdpField.text.toString())
            call.enqueue(object : Callback<Void>{
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    val msg = getString(R.string.cnx_failed)
                    Log.e(TAG, "onFailure: $msg")
                    Cst.toast(this@ChangerMdpActivity, msg)
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if(!response.isSuccessful){
                        val msg = response.message()
                        Log.d(TAG, "login: onResponse: $msg")
                        Cst.toast(this@ChangerMdpActivity, msg)
                    }
                    else {
                        Log.d(TAG, "login: getUser: onResponse: Mot de passe changé")
                        setResult(Activity.RESULT_OK, null)
                        startActivity(Intent(this@ChangerMdpActivity, MainActivity::class.java))
                        Cst.toast(this@ChangerMdpActivity, "Bienvenue")
                        finish()
                    }
                }
            })
        }
    }
}
