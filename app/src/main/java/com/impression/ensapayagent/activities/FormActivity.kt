package com.impression.ensapayagent.activities

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.impression.ensapayagent.R
import com.impression.ensapayagent.api.ApiClient
import com.impression.ensapayagent.model.Client
import com.impression.ensapayagent.model.ComptePayment
import com.impression.ensapayagent.model.Cst
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.jar.Manifest

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
        call.enqueue(object : Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                val msg = t.message
                Log.e(TAG, "createClient: onFailure: $msg")
                Cst.toast(this@FormActivity, msg!!)
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(!response.isSuccessful){
                    val msg = response.message()
                    Log.e(TAG, "createClient: onResponse not successful: $msg")
                    Cst.toast(this@FormActivity, msg!!)
                }
                else{
                    Cst.toast(this@FormActivity, "Le compte a été crée")
                    Log.d(TAG, "createClient: onResponse: Successful : compte created $newCompte")

                    val mdp = response.body()
                    if(mdp == null || mdp == ""){
                        Log.d(TAG, "createClient: onResponse: Mot de passe Vide !")
                        return
                    }

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        if(checkSelfPermission(android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                            sendSMS(newCompte.client!!.numTel!!, mdp)
                        }
                        else{
                            requestPermissions(arrayOf(android.Manifest.permission.SEND_SMS), 1)
                        }
                    }


                    setResult(Activity.RESULT_OK, null)
                    NavUtils.navigateUpFromSameTask(this@FormActivity)
                }
            }
        })

    }

    private fun sendSMS(num: String, code: String){
        val msg = "Votre mot de passe provisoire est : $code.\nVeuillez changer votre mot de passe lors de votre première connexion."
        val smsManager = SmsManager.getDefault()
        try {
            smsManager.sendTextMessage(num, null, msg, null, null)
            Log.d(TAG, "sendSMS: Message envoyé")
            Cst.toast(this, "Message envoyé")
        }
        catch (e: Exception){
            Cst.toast(this, "Erreur")
            Log.e(TAG, "sendSMS: Error: ${e.message}")
        }
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
