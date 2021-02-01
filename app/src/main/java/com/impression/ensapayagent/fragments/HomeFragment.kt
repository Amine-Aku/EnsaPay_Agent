package com.impression.ensapayagent.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.impression.ensapayagent.R
import com.impression.ensapayagent.adapters.ClientsAdapter
import com.impression.ensapayagent.api.ApiClient
import com.impression.ensapayagent.model.Agent
import com.impression.ensapayagent.model.Client
import com.impression.ensapayagent.model.ComptePayment
import com.impression.ensapayagent.model.Cst
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment: Fragment() {

    private val TAG = "HomeFragment"

    private lateinit var nomField: EditText
    private lateinit var emailField: EditText
    private lateinit var telField: EditText
    private lateinit var idField: EditText

    private lateinit var logoutBtn: Button

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ClientsAdapter
    
    private var currentList: List<ComptePayment>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v =  inflater.inflate(R.layout.fragment_home, container, false)

        currentList = emptyList()
        retrofitCall(v)

        return v
    }

    private fun retrofitCall(view: View) {
        val call = ApiClient.getClientServices().getListComptes(Cst.token)
        call.enqueue(object : Callback<List<ComptePayment>>{
            override fun onFailure(call: Call<List<ComptePayment>>, t: Throwable) {
                val msg = t.message
                Log.e(TAG, "retrofitCall: onFailure: $msg")
                Cst.toast(activity!!, msg!!)
            }

            override fun onResponse(call: Call<List<ComptePayment>>, response: Response<List<ComptePayment>>) {
                if(!response.isSuccessful){
                    val msg = response.message()
                    Log.d(TAG, "retrofitCall: onResponse not successful: $msg")
                    Cst.toast(activity!!, msg!!)
                }
                else {
                    currentList = response.body()
                    Log.d(TAG, "retrofitCall: onResponse: Call successful ! $currentList")
                    setRecyclerView(view, currentList!!)
                }
            }
        })
    }

    private fun setRecyclerView(view: View, list: List<ComptePayment>) {
        recyclerView = view.findViewById(R.id.recycler_clients)
        if(list.isEmpty()){
            Log.d(TAG, "setRecyclerView: Liste Vide")
            Cst.toast(activity!!, "Liste Vide")
            return
        }
        adapter = ClientsAdapter(activity!!, list)
        recyclerView.layoutManager = LinearLayoutManager(activity!!)
        recyclerView.adapter = adapter
    }


}
