package com.impression.ensapayagent.model

class ComptePaiement(){

    private var typeComptes: Int? = null
    private var nom: String? = null
    private var prenom: String? = null
    private var tel: String? = null
    private var email: String? = null

    constructor(typeComptes: Int?, nom: String, prenom: String, tel: String, email: String? = null): this() {
        this.typeComptes = typeComptes
        this.nom = nom
        this.prenom = prenom
        this.tel = tel
        this.email = email
    }

    override fun toString(): String {
        return "ComptePaiement(typeComptes=$typeComptes, nom=$nom, prenom=$prenom, tel=$tel, email=$email)"
    }

}
