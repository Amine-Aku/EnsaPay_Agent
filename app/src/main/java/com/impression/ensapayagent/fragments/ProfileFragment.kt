package com.impression.ensapayagent.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.impression.ensapayagent.activities.LoginActivity
import com.impression.ensapayagent.R
import com.impression.ensapayagent.model.Agent
import com.impression.ensapayagent.model.Cst


class ProfileFragment: Fragment() {

    private val TAG = "ProfileFragment"

    private lateinit var nomField: TextView
    private lateinit var prenomField: TextView
    private lateinit var usernameField: TextView
    private lateinit var numTelField: TextView
    private lateinit var logoutBtn: Button


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_profile, container, false)

        init(v)
        populate(Cst.currentUser!!)
        events(v.context)

        return v
    }

    private fun events(context: Context) {
        logoutBtn.setOnClickListener {
            Cst.logout(context)
            startActivity(Intent(activity!!, LoginActivity::class.java))
            activity!!.finish()
        }
    }

    private fun init(view: View) {
        nomField = view.findViewById(R.id.field_nom)
        prenomField = view.findViewById(R.id.field_prenom)
        usernameField = view.findViewById(R.id.field_email)
        numTelField = view.findViewById(R.id.field_tel)
        logoutBtn = view.findViewById(R.id.btn_logout)
    }

    private fun populate(agent: Agent) {
        nomField.text = agent.nom
        prenomField.text = agent.prenom
        usernameField.text = agent.username
        numTelField.text = agent.numTel
    }






}
