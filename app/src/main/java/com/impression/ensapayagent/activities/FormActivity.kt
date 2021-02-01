package com.impression.ensapayagent.activities

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.NavUtils
import com.impression.ensapayagent.R
import com.impression.ensapayagent.api.ApiClient
import com.impression.ensapayagent.model.Client
import com.impression.ensapayagent.model.ComptePayment
import com.impression.ensapayagent.model.Cst
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormActivity : AppCompatActivity() {

    private val TAG = "FormActivity"

    private lateinit var typeCompteRadioGroup: RadioGroup
    private lateinit var nomField: EditText
    private lateinit var prenomField: EditText
    private lateinit var numTelField: EditText
    private lateinit var emailField: EditText
    private lateinit var enregistrerBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        init()

    }

    private fun init() {
        typeCompteRadioGroup = findViewById(R.id.radio_type_compte)
        typeCompteRadioGroup.check(R.id.radio_200)
        nomField = findViewById(R.id.field_nom)
        prenomField = findViewById(R.id.field_prenom)
        numTelField = findViewById(R.id.field_tel)
        emailField = findViewById(R.id.field_email)
        enregistrerBtn = findViewById(R.id.btn_enregistrer)

        enregistrerBtn.setOnClickListener{
            createClient()
        }
    }


    private fun createClient(){
        val newCompte = getClientFromFields() ?: return

        val call = ApiClient.getClientServices().createClient(Cst.token, newCompte)
        call.enqueue(object : Callback<Void>{
            override fun onFailure(call: Call<Void>, t: Throwable) {
                val msg = t.message
                Log.e(TAG, "createClient: onFailure: $msg")
                Cst.toast(this@FormActivity, msg!!)
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(!response.isSuccessful){
                    val msg = response.message()
                    Log.e(TAG, "createClient: onResponse not successful: $msg")
                    Cst.toast(this@FormActivity, msg!!)
                }
                else{
                    Cst.toast(this@FormActivity, "Le compte a été crée")
                    Log.d(TAG, "createClient: onResponse: Successful : compte created $newCompte")
                    setResult(Activity.RESULT_OK, null)
                    NavUtils.navigateUpFromSameTask(this@FormActivity)
                }
            }
        })

    }

    private fun getClientFromFields(): ComptePayment?{
        val selectedRadio = findViewById<RadioButton>(typeCompteRadioGroup.checkedRadioButtonId)
        val typeCompte = "c"+selectedRadio.text.toString().replace(" ", "")
        val nom = nomField.text.toString()
        val prenom = prenomField.text.toString()
        val numTel = numTelField.text.toString()
        val email = emailField.text.toString()

        if(nom.isBlank()) nomField.error = getString(R.string.champs_vide)
        if(prenom.isBlank()) prenomField.error = getString(R.string.champs_vide)
        if(numTel.isBlank()) numTelField.error = getString(R.string.champs_vide)
        if(email.isBlank()) numTelField.error = getString(R.string.champs_vide)

        if(nom.isBlank() || prenom.isBlank() || numTel.isBlank() || email.isBlank()) return null


        return ComptePayment(typeCompte, Client(nom, prenom, numTel, email))
    }

    override fun onBackPressed() = NavUtils.navigateUpFromSameTask(this)

    fun back(view: View) = NavUtils.navigateUpFromSameTask(this)


}
