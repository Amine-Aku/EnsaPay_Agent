package com.impression.ensapayagent.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.impression.ensapayagent.R
import com.impression.ensapayagent.api.ApiClient
import com.impression.ensapayagent.model.Agent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment: Fragment() {

    private lateinit var nomField: EditText
    private lateinit var emailField: EditText
    private lateinit var telField: EditText
    private lateinit var idField: EditText

    private lateinit var historiqueBtn: Button
    private lateinit var logoutBtn: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v =  inflater.inflate(R.layout.fragment_home, container, false)


        return v
    }


}
