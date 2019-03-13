package roshan.paturkar.com.smokerminimodel

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_blood_pressure.*
import java.text.SimpleDateFormat
import java.util.*

class BloodPressureActivity : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null
    var userId: String? = null
    var mCurrentUser: FirebaseUser? = null

    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blood_pressure)
        supportActionBar!!.title = "Blood Pressure!"

        mCurrentUser = FirebaseAuth.getInstance().currentUser
        userId = mCurrentUser!!.uid

        mAuth = FirebaseAuth.getInstance()

        checkBp.setOnClickListener {
            var status = check()

            if (!status.equals("")) {
                bpStatus.text = status
            } else {
                Toast.makeText(this, "Please fill all the details!", Toast.LENGTH_LONG).show()
            }
        }

        addBpToReport.setOnClickListener {
            var sy = systolicEditText.text.toString().trim()
            var di = diastolicEditText.text.toString().trim()
            val dataMap = HashMap<String, Any>()

            if (sy.isNotEmpty() && di.isNotEmpty()) {
                var data = "---------------------" + "\n" + "Report Type: Blood Pressure" + "\n" + "Date: ${date()}" + "\n" + "Systolic: $sy" + "\n" + "Diastolic: $di" + "\n" + "Status: ${check()}" + "\n" + "---------------------"

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
        var sy = systolicEditText.text.toString().trim()
        var di = diastolicEditText.text.toString().trim()

        if (sy.isNotEmpty() && di.isNotEmpty()) {
            var status = Support.checkBp(sy, di)
            return status
        } else {
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
