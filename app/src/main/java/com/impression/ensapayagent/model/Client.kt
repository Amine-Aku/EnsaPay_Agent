package com.impression.ensapayagent.model

class Client(){

    var nom: String? = "Fattas"
    var prenom: String? = "Amine"
    var username: String? = "06xxxxxx"
    var numTel: String? = "06xxxxxx"
    var email: String? = ""
    var firstAuth: Boolean = true

    constructor(nom: String?, prenom: String?, numTel: String?, email: String? = ""): this() {
        this.nom = nom
        this.prenom = prenom
        this.username = email
        this.numTel = numTel
        this.firstAuth = true
    }

    override fun toString(): String {
        return "Client(nom=$nom, prenom=$prenom, username=$username, numTel=$numTel, email=$email, firstAuth=$firstAuth)"
    }


}


