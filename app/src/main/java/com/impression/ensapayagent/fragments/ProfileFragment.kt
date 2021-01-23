package com.impression.ensapayagent.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.impression.ensapayagent.R
import com.impression.ensapayagent.model.Agent


class ProfileFragment: Fragment() {

    private lateinit var nomField: TextView
    private lateinit var emailField: TextView
    private lateinit var telField: TextView
    private lateinit var idField: TextView
    private lateinit var historiqueBtn: Button


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_profile, container, false)

        init(v)
        populate(Agent)

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




}
