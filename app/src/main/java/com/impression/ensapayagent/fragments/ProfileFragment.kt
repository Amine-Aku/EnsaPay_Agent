package com.impression.ensapayagent.fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.impression.ensapayagent.R
import com.impression.ensapayagent.api.ApiClient
import com.impression.ensapayagent.model.Agent
import com.impression.ensapayagent.model.Cst
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileFragment: Fragment() {

    private val TAG = "ProfileFragment"

    private lateinit var nomField: TextView
    private lateinit var emailField: TextView
    private lateinit var telField: TextView
    private lateinit var idField: TextView
    private lateinit var historiqueBtn: Button


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_profile, container, false)

        init(v)
        populate(Agent)
//        getUser()

        return v
    }

    private fun init(view: View) {
        nomField = view.findViewById(R.id.field_nom)
        emailField = view.findViewById(R.id.field_email)
        telField = view.findViewById(R.id.field_tel)
        idField = view.findViewById(R.id.field_id)
        historiqueBtn = view.findViewById(R.id.btn_historique)
    }

    private fun populate(agent: Agent) {
        nomField.text = agent.nom + " " + agent.prenom
        emailField.text = agent.email
        telField.text = agent.tel
        idField.text = agent.id
    }

    private fun getUser() {
        val call = ApiClient.getAppuserServices().getUser("")
        call.enqueue(object : Callback<Agent> {
            override fun onFailure(call: Call<Agent>, t: Throwable) {
                val msg = getString(R.string.cnx_failed)
                Log.e(TAG, "onFailure: $msg")
                Cst.toast(activity!!, msg)
            }

            override fun onResponse(call: Call<Agent>, response: Response<Agent>) {
                if(!response.isSuccessful){
                    val msg = getString(R.string.incorrect_credentials)
                    Log.d(TAG, "onResponse: $msg")
                    Cst.toast(activity!!, msg)
                }
                else {
                    ///////////////////// NEEEEEEED WORK HERE
                    response.body()
                    populate(Agent)
                    Log.d(TAG, "onResponse: ${Agent}")
                }
            }
        })
    }




}
