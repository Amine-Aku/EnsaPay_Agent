package com.impression.ensapayagent.model

object Agent {

    var nom: String? = "Fattas"
    var prenom: String? = "Amine"
    var email: String? = "af@gmail.com"
    var tel: String? = "06xxxxxx"
    var cinURL: String? = null
    var id: String? = "FA123"

    override fun toString(): String {
        return "Agent(nom=$nom, prenom=$prenom, email=$email, tel=$tel, cinURL=$cinURL, id=$id)"
    }


}
