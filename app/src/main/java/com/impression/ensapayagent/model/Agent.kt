package com.impression.ensapayagent.model

class Agent() {

    var nom: String? = "Fattas"
    var prenom: String? = "Amine"
    var username: String? = "af@gmail.com"
    var numTel: String? = "06xxxxxx"
    var firstAuth: Boolean = false

    override fun toString(): String {
        return "Agent(nom=$nom, prenom=$prenom, username=$username, numTel=$numTel, firstAuth=$firstAuth)"
    }

}
