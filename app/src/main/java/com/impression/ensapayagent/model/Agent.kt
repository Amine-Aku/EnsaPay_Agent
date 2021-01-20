package com.impression.ensapayagent.model

class Agent() {

    private var nom: String? = null
    private var prenom: String? = null
    private var email: String? = null
    private var tel: String? = null
    private var cinURL: String? = null
    private var id: String? = null

    override fun toString(): String {
        return "Agent(nom=$nom, prenom=$prenom, email=$email, tel=$tel, cinURL=$cinURL, id=$id)"
    }


}
