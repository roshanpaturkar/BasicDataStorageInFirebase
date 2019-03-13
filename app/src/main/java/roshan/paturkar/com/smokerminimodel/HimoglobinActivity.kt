package roshan.paturkar.com.smokerminimodel

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_blood_glucose.*
import kotlinx.android.synthetic.main.activity_himoglobin.*
import java.text.SimpleDateFormat
import java.util.*

class HimoglobinActivity : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null
    var userId: String? = null
    var mCurrentUser: FirebaseUser? = null

    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_himoglobin)
        supportActionBar!!.title = "Hemoglobin!"

        mCurrentUser = FirebaseAuth.getInstance().currentUser
        userId = mCurrentUser!!.uid

        mAuth = FirebaseAuth.getInstance()

        hemoglobinCheckButton.setOnClickListener {
            var status = check()

            hemoglobinStatus.text = status
        }

        hemoglobinAddToReport.setOnClickListener {
            var he = hemoglobinEditText.text.toString().trim()
            val dataMap = HashMap<String, Any>()

            if (he.isNotEmpty()) {
                var data = "------------------------------------------" + "\n" + "Report Type: Hemoglobin" + "\n" + "Date: ${date()}" + "\n" + "Hemoglobin: $he" + "\n" + "Status: ${check()}" + "\n" + "------------------------------------------"

                dataMap["report"] = data

                db.collection(userId!!)
                    .add(dataMap)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Report added successfully!", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener{
                        Toast.makeText(this, "Unable to add report!", Toast.LENGTH_LONG).show()
                    }

                Toast.makeText(this, dataMap["report"].toString(), Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Please fill all the details!", Toast.LENGTH_LONG).show()
            }
        }

    }
    private fun check(): String{
        var he = hemoglobinEditText.text.toString().trim()

        if (he.isNotEmpty()) {
            var status = Support.checkHemoglobine(he)

            return status
        } else {
            Toast.makeText(this, "Please fill all the details!", Toast.LENGTH_LONG).show()
            return ""
        }
    }


    private fun date(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        System.out.println(" C DATE is  "+currentDate)

        return currentDate
    }

    fun java.sql.Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }
}
