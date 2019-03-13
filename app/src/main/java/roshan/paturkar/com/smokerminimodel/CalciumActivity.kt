package roshan.paturkar.com.smokerminimodel

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_calcium.*
import kotlinx.android.synthetic.main.activity_himoglobin.*
import java.text.SimpleDateFormat
import java.util.*

class CalciumActivity : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null
    var userId: String? = null
    var mCurrentUser: FirebaseUser? = null

    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calcium)
        supportActionBar!!.title = "Calcium!"

        mCurrentUser = FirebaseAuth.getInstance().currentUser
        userId = mCurrentUser!!.uid

        mAuth = FirebaseAuth.getInstance()

        calciumCheckButton.setOnClickListener {
            var status = check()

            calciumStatus.text = status
        }

        calciumAddToReportButton.setOnClickListener {
            var cl = calciumEditText.text.toString().trim()
            val dataMap = HashMap<String, Any>()

            if (cl.isNotEmpty()) {
                var data = "---------------------" + "\n" + "Report Type: Calcium" + "\n" + "Date: ${date()}" + "\n" + "Calcium: $cl" + "\n" + "Status: ${check()}" + "\n" + "---------------------"

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
        var cl = calciumEditText.text.toString().trim()

        if (cl.isNotEmpty()) {
            var status = Support.checkCalcium(cl)

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
