package com.impression.ensapayagent.model

class ComptePayment(){

    var solde: Double? = null
    var typeCompte: String? = null
    var client: Client? = null

    constructor(typeCompte: String?, client: Client?): this() {
        this.solde = 0.0
        this.typeCompte = typeCompte
        this.client = client
    }

    override fun toString(): String {
        return "ComptePayment(solde=$solde, typeCompte=$typeCompte, client=$client)"
    }


}
