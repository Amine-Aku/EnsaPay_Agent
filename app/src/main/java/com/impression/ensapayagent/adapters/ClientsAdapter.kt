package com.impression.ensapayagent.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.impression.ensapayagent.R
import com.impression.ensapayagent.model.Client
import com.impression.ensapayagent.model.ComptePayment

class ClientsAdapter(private var context: Context, private var listClient: List<ComptePayment>)
    : RecyclerView.Adapter<ClientsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_client, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listClient.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(listClient.isNotEmpty()) holder.setDate(listClient[position])

    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val nomField = itemView.findViewById<TextView>(R.id.item_nom)
        private val typeCompteField = itemView.findViewById<TextView>(R.id.item_typeCompte)
        private val numTelField = itemView.findViewById<TextView>(R.id.item_numTel)
        private val emailField = itemView.findViewById<TextView>(R.id.item_email)

        fun setDate(comptePayment: ComptePayment) {
            val client = comptePayment.client!!
            nomField.text = client.nom + " " + client.prenom
            numTelField.text = client.numTel
            emailField.text = client.email
            typeCompteField.text = comptePayment.typeCompte
        }

    }
}
